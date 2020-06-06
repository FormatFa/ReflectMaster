package com.github.esrrhs.fakescript;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.github.esrrhs.fakescript.syntree.*;

class compiler
{
	private fake m_f;
	private mybison m_mbs;
	private String m_cur_compile_func;
	private ArrayList<ArrayList<Integer>> m_loop_break_pos_stack = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> m_loop_continue_pos_stack = new ArrayList<Integer>();
	private ArrayList<ArrayList<Integer>> m_continue_end_pos_stack = new ArrayList<ArrayList<Integer>>();
	private boolean m_cmp_jne;
	private long m_cur_addr;
	private ArrayList<Long> m_cur_addrs = new ArrayList<Long>();
	private int m_cmp_deps;
	private boolean m_new_var;
	private int m_func_ret_num = 1;

	public compiler(fake f, mybison mbs)
	{
		m_f = f;
		m_mbs = mbs;
	}

	public boolean compile() throws Exception
	{
		if (!compile_const_head())
		{
			return false;
		}

		if (!compile_body())
		{
			return false;
		}

		return true;
	}

	public boolean compile_const_head() throws Exception
	{
		types.log(m_f, "[compiler] compile_const_head");

		// 注册全局常量表
		HashMap<String, syntree_node> evm = m_mbs.get_const_map();
		Iterator<Entry<String, syntree_node>> it = evm.entrySet().iterator();
		while (it.hasNext())
		{
			Entry<String, syntree_node> entry = it.next();
			String name = (String) entry.getKey();
			explicit_value_node ev = (explicit_value_node) entry.getValue();

			variant v = compile_explicit_value_node_to_variant(ev);
			if (v == null)
			{
				types.log(m_f, "[compiler] compile_explicit_value_node_to_variant %s fail", name);
				return false;
			}

			String constname = types.gen_package_name(m_mbs.get_package(), name);

			m_f.pa.reg_const_define(constname, v, ev.lineno());

			types.log(m_f, "[compiler] reg_const_define %s %s", constname, v);

		}
		return true;
	}

	public boolean compile_body() throws Exception
	{
		for (func_desc_node funcnode : m_mbs.get_func_list())
		{
			if (!compile_func(funcnode))
			{
				types.log(m_f, "[compiler] compile_body %s fail", funcnode.m_funcname);
				return false;
			}
		}

		if (m_f.cfg.open_debug_log != 0)
		{
			types.log(m_f, "[compiler] compile_body funclist %d ok dump \n%s", m_mbs.get_func_list().size(),
					m_f.bin.dump());
		}
		types.log(m_f, "[compiler] compile_body funcmap %d ok dump \n%s", m_f.fm.size(), m_f.fm.dump());

		return true;
	}

	public void compile_seterror(syntree_node node, String formatstr, Object... args)
	{
		String str = String.format(formatstr, args);
		types.seterror(m_f, m_mbs.get_filename(), node.lineno(), m_cur_compile_func, str);
	}

	public boolean compile_func(func_desc_node funcnode) throws Exception
	{
		m_cur_compile_func = funcnode.m_funcname;

		codegen cg = new codegen(m_f);
		func_binary bin = new func_binary();
		bin.m_end_lineno = funcnode.m_endline;

		// 压栈
		cg.push_stack_identifiers();

		// 参数入栈
		if (funcnode.m_arglist != null)
		{
			ArrayList<String> arglist = funcnode.m_arglist.m_arglist;
			for (int i = 0; i < (int) arglist.size(); i++)
			{
				String arg = arglist.get(i);
				if (cg.add_stack_identifier(arg, funcnode.m_arglist.lineno()) == -1)
				{
					compile_seterror(funcnode.m_arglist, "double %s identifier error", arg);
					return false;
				}
			}
			bin.m_paramnum = arglist.size();
		}

		// 编译函数体
		if (funcnode.m_block != null)
		{
			if (!compile_block(cg, funcnode.m_block))
			{
				return false;
			}
		}

		// break必须为空
		if (!m_loop_break_pos_stack.isEmpty())
		{
			compile_seterror(funcnode, "compile extra break error");
			return false;
		}

		// 编译成功
		String funcname = types.gen_package_name(m_mbs.get_package(), funcnode.m_funcname);
		cg.output(m_mbs.get_filename(), m_mbs.get_package(), funcname, bin);

		// 优化
		m_f.opt.optimize(bin);

		// 看立即更新还是延迟更新
		variant fv = new variant();
		fv.set_string(funcname);
		m_f.bin.add_func(fv, bin);

		types.log(m_f, "[compiler] compile_func func %s OK", funcname);

		return true;
	}

	public boolean compile_block(codegen cg, block_node blocknode) throws Exception
	{
		for (int i = 0; i < (int) blocknode.m_stmtlist.size(); i++)
		{
			syntree_node stmt = blocknode.m_stmtlist.get(i);
			if (!compile_node(cg, stmt))
			{
				return false;
			}
		}

		return true;
	}

	public boolean compile_node(codegen cg, syntree_node node) throws Exception
	{
		esyntreetype type = node.gettype();
		switch (type)
		{
			case est_block:
			{
				block_node bn = (block_node) (node);
				if (!compile_block(cg, bn))
				{
					return false;
				}
				break;
			}
			case est_while_stmt:
			{
				while_stmt ws = (while_stmt) (node);
				if (!compile_while_stmt(cg, ws))
				{
					return false;
				}
				break;
			}
			case est_for_stmt:
			{
				for_stmt fs = (for_stmt) (node);
				if (!compile_for_stmt(cg, fs))
				{
					return false;
				}
				break;
			}
			case est_multi_assign_stmt:
			{
				multi_assign_stmt as = (multi_assign_stmt) (node);
				if (!compile_multi_assign_stmt(cg, as))
				{
					return false;
				}
				break;
			}
			case est_cmp_stmt:
			{
				cmp_stmt cs = (cmp_stmt) (node);
				if (!compile_cmp_stmt(cg, cs))
				{
					return false;
				}
				break;
			}
			case est_if_stmt:
			{
				if_stmt is = (if_stmt) (node);
				if (!compile_if_stmt(cg, is))
				{
					return false;
				}
				break;
			}
			case est_explicit_value:
			{
				explicit_value_node ev = (explicit_value_node) (node);
				if (!compile_explicit_value(cg, ev))
				{
					return false;
				}
				break;
			}
			case est_return_stmt:
			{
				return_stmt rs = (return_stmt) (node);
				if (!compile_return_stmt(cg, rs))
				{
					return false;
				}
				break;
			}
			case est_return_value_list:
			{
				return_value_list_node rn = (return_value_list_node) (node);
				if (!compile_return_value_list(cg, rn))
				{
					return false;
				}
				break;
			}
			case est_assign_stmt:
			{
				assign_stmt as = (assign_stmt) (node);
				if (!compile_assign_stmt(cg, as))
				{
					return false;
				}
				break;
			}
			case est_math_assign_stmt:
			{
				math_assign_stmt ms = (math_assign_stmt) (node);
				if (!compile_math_assign_stmt(cg, ms))
				{
					return false;
				}
				break;
			}
			case est_variable:
			{
				variable_node vn = (variable_node) (node);
				if (!compile_variable_node(cg, vn))
				{
					return false;
				}
				break;
			}
			case est_var:
			{
				var_node vn = (var_node) (node);
				if (!compile_var_node(cg, vn))
				{
					return false;
				}
				break;
			}
			case est_function_call:
			{
				function_call_node fn = (function_call_node) (node);
				if (!compile_function_call_node(cg, fn))
				{
					return false;
				}
				break;
			}
			case est_break:
			{
				break_stmt bs = (break_stmt) (node);
				if (!compile_break_stmt(cg, bs))
				{
					return false;
				}
				break;
			}
			case est_continue:
			{
				continue_stmt cs = (continue_stmt) (node);
				if (!compile_continue_stmt(cg, cs))
				{
					return false;
				}
				break;
			}
			case est_math_expr:
			{
				math_expr_node mn = (math_expr_node) (node);
				if (!compile_math_expr_node(cg, mn))
				{
					return false;
				}
				break;
			}
			case est_container_get:
			{
				container_get_node cn = (container_get_node) (node);
				if (!compile_container_get(cg, cn))
				{
					return false;
				}
				break;
			}
			case est_struct_pointer:
			{
				struct_pointer_node cn = (struct_pointer_node) (node);
				if (!compile_struct_pointer(cg, cn))
				{
					return false;
				}
				break;
			}
			case est_sleep:
			{
				sleep_stmt ss = (sleep_stmt) (node);
				if (!compile_sleep_stmt(cg, ss))
				{
					return false;
				}
				break;
			}
			case est_yield:
			{
				yield_stmt ys = (yield_stmt) (node);
				if (!compile_yield_stmt(cg, ys))
				{
					return false;
				}
				break;
			}
			case est_switch_stmt:
			{
				switch_stmt ss = (switch_stmt) (node);
				if (!compile_switch_stmt(cg, ss))
				{
					return false;
				}
				break;
			}
			case est_for_loop_stmt:
			{
				for_loop_stmt fs = (for_loop_stmt) (node);
				if (!compile_for_loop_stmt(cg, fs))
				{
					return false;
				}
				break;
			}
			default:
			{
				compile_seterror(node, "compile node type error %s", type.toString());
				return false;
			}
		}

		return true;
	}

	public variant compile_explicit_value_node_to_variant(explicit_value_node ev) throws Exception
	{
		variant v = new variant();
		switch (ev.m_type)
		{
			case EVT_NULL:
				v.set_pointer(null);
				break;
			case EVT_TRUE:
				v.set_real(1);
				break;
			case EVT_FALSE:
				v.set_real(0);
				break;
			case EVT_NUM:
				v.set_real(Integer.valueOf(ev.m_str));
				break;
			case EVT_STR:
				v.set_string(ev.m_str);
				break;
			case EVT_FLOAT:
				v.set_real(Double.valueOf(ev.m_str));
				break;
			case EVT_UUID:
				v.set_uuid(Long.valueOf(ev.m_str.substring(0, ev.m_str.length() - 1)));
				break;
			case EVT_MAP:
			{
				const_map_list_value_node cml = (const_map_list_value_node) ev.m_v;
				variant_map vm = new variant_map();
				vm.m_isconst = true;

				for (int i = 0; i < cml.m_lists.size(); i++)
				{
					const_map_value_node cmv = (const_map_value_node) cml.m_lists.get(i);

					explicit_value_node kn = (explicit_value_node) cmv.m_k;
					explicit_value_node vn = (explicit_value_node) cmv.m_v;

					variant kv = compile_explicit_value_node_to_variant(kn);
					variant vv = compile_explicit_value_node_to_variant(vn);

					variant des = vm.con_map_get(kv);
					des.copy_from(vv);
				}

				v.set_map(vm);
			}
				break;
			case EVT_ARRAY:
			{
				const_array_list_value_node cal = (const_array_list_value_node) ev.m_v;
				variant_array va = new variant_array();
				va.m_isconst = true;
				for (int i = 0; i < cal.m_lists.size(); i++)
				{
					explicit_value_node vn = (explicit_value_node) cal.m_lists.get(i);
					variant kv = new variant();
					kv.set_real(i);

					variant vv = compile_explicit_value_node_to_variant(vn);

					variant des = va.con_array_get(kv);
					des.copy_from(vv);
				}

				v.set_array(va);
			}
				break;

			default:
				throw new Exception("compile explicit value type error " + ev.m_type.toString());
		}
		return v;
	}

	public boolean compile_while_stmt(codegen cg, while_stmt ws) throws Exception
	{
		int startpos = 0;
		int jnepos = 0;

		m_loop_break_pos_stack.add(new ArrayList<Integer>());

		startpos = cg.byte_code_size();

		m_loop_continue_pos_stack.add(startpos);

		// 条件
		cg.push_stack_identifiers();
		if (!compile_node(cg, ws.m_cmp))
		{
			return false;
		}
		cg.pop_stack_identifiers();

		// cmp与jne结合
		if (m_cmp_jne)
		{
			cg.push(command.EMPTY_CMD, ws.m_cmp.lineno()); // 先塞个位置
			jnepos = cg.byte_code_size() - 1;
		}
		else
		{
			cg.push(command.MAKE_OPCODE(command.OPCODE_JNE), ws.lineno());
			cg.push(m_cur_addr, ws.lineno());
			cg.push(command.EMPTY_CMD, ws.lineno()); // 先塞个位置
			jnepos = cg.byte_code_size() - 1;
		}
		m_cmp_deps = 0;
		m_cmp_jne = false;

		// block块
		if (ws.m_block != null)
		{
			cg.push_stack_identifiers();
			if (!compile_node(cg, ws.m_block))
			{
				return false;
			}
			cg.pop_stack_identifiers();
		}

		// 跳回判断地方
		cg.push(command.MAKE_OPCODE(command.OPCODE_JMP), ws.lineno());
		cg.push(command.MAKE_POS(startpos), ws.lineno());

		// 跳转出block块
		cg.set(jnepos, command.MAKE_POS(cg.byte_code_size()));

		// 替换掉break
		ArrayList<Integer> bplist = m_loop_break_pos_stack.get(m_loop_break_pos_stack.size() - 1);
		for (int i = 0; i < (int) bplist.size(); i++)
		{
			cg.set(bplist.get(i), command.MAKE_POS(cg.byte_code_size()));
		}
		m_loop_break_pos_stack.remove(m_loop_break_pos_stack.size() - 1);

		m_loop_continue_pos_stack.remove(m_loop_continue_pos_stack.size() - 1);

		return true;
	}

	public boolean compile_for_stmt(codegen cg, for_stmt fs) throws Exception
	{
		int startpos = 0;
		int jnepos = 0;
		int continuepos = 0;

		m_loop_break_pos_stack.add(new ArrayList<Integer>());
		m_continue_end_pos_stack.add(new ArrayList<Integer>());

		// 开始语句，这个作用域是全for都有效的
		cg.push_stack_identifiers();
		if (fs.m_beginblock != null)
		{
			if (!compile_node(cg, fs.m_beginblock))
			{
				return false;
			}
		}

		startpos = cg.byte_code_size();
		// 需要continue end
		m_loop_continue_pos_stack.add(-1);

		// 条件
		cg.push_stack_identifiers();
		if (!compile_node(cg, fs.m_cmp))
		{
			return false;
		}
		cg.pop_stack_identifiers();

		// cmp与jne结合
		if (m_cmp_jne)
		{
			cg.push(command.EMPTY_CMD, fs.m_cmp.lineno()); // 先塞个位置
			jnepos = cg.byte_code_size() - 1;
		}
		else
		{
			cg.push(command.MAKE_OPCODE(command.OPCODE_JNE), fs.lineno());
			cg.push(m_cur_addr, fs.lineno());
			cg.push(command.EMPTY_CMD, fs.lineno()); // 先塞个位置
			jnepos = cg.byte_code_size() - 1;
		}
		m_cmp_deps = 0;
		m_cmp_jne = false;

		// block块
		if (fs.m_block != null)
		{
			cg.push_stack_identifiers();
			if (!compile_node(cg, fs.m_block))
			{
				return false;
			}
			cg.pop_stack_identifiers();
		}

		continuepos = cg.byte_code_size();

		// 结束
		if (fs.m_endblock != null)
		{
			cg.push_stack_identifiers();
			if (!compile_node(cg, fs.m_endblock))
			{
				return false;
			}
			cg.pop_stack_identifiers();
		}

		// 跳回判断地方
		cg.push(command.MAKE_OPCODE(command.OPCODE_JMP), fs.lineno());
		cg.push(command.MAKE_POS(startpos), fs.lineno());

		// 跳转出block块
		cg.set(jnepos, command.MAKE_POS(cg.byte_code_size()));

		// 替换掉break
		ArrayList<Integer> bplist = m_loop_break_pos_stack.get(m_loop_break_pos_stack.size() - 1);
		for (int i = 0; i < (int) bplist.size(); i++)
		{
			cg.set(bplist.get(i), command.MAKE_POS(cg.byte_code_size()));
		}
		m_loop_break_pos_stack.remove(m_loop_break_pos_stack.size() - 1);

		// 替换掉continue
		ArrayList<Integer> cplist = m_continue_end_pos_stack.get(m_continue_end_pos_stack.size() - 1);
		for (int i = 0; i < (int) cplist.size(); i++)
		{
			cg.set(cplist.get(i), command.MAKE_POS(continuepos));
		}
		m_continue_end_pos_stack.remove(m_continue_end_pos_stack.size() - 1);

		m_loop_continue_pos_stack.remove(m_loop_continue_pos_stack.size() - 1);

		// 离开作用域
		cg.pop_stack_identifiers();

		return true;
	}

	public boolean compile_if_stmt(codegen cg, if_stmt is) throws Exception
	{
		int jnepos = 0;
		ArrayList<Integer> jmpifpos = new ArrayList<Integer>();

		// 条件
		cg.push_stack_identifiers();
		if (!compile_node(cg, is.m_cmp))
		{
			return false;
		}
		cg.pop_stack_identifiers();

		// cmp与jne结合
		if (m_cmp_jne)
		{
			cg.push(command.EMPTY_CMD, is.m_cmp.lineno()); // 先塞个位置
			jnepos = cg.byte_code_size() - 1;
		}
		else
		{
			cg.push(command.MAKE_OPCODE(command.OPCODE_JNE), is.lineno());
			cg.push(m_cur_addr, is.lineno());
			cg.push(command.EMPTY_CMD, is.lineno()); // 先塞个位置
			jnepos = cg.byte_code_size() - 1;
		}
		m_cmp_deps = 0;
		m_cmp_jne = false;

		// if块
		if (is.m_block != null)
		{
			cg.push_stack_identifiers();
			if (!compile_node(cg, is.m_block))
			{
				return false;
			}
			cg.pop_stack_identifiers();
		}

		// 跳出if块
		if (is.m_elseifs != null || (is.m_elses != null && is.m_elses.m_block != null))
		{
			cg.push(command.MAKE_OPCODE(command.OPCODE_JMP), is.lineno());
			cg.push(command.EMPTY_CMD, is.lineno()); // 先塞个位置
			jmpifpos.add(cg.byte_code_size() - 1);
		}

		// 开始处理elseif的
		if (is.m_elseifs != null)
		{
			ArrayList<syntree_node> list = is.m_elseifs.m_stmtlist;
			for (int i = 0; i < (int) list.size(); i++)
			{
				elseif_stmt eis = (elseif_stmt) (list.get(i));

				// 跳转到else if
				cg.set(jnepos, command.MAKE_POS(cg.byte_code_size()));

				// 条件
				cg.push_stack_identifiers();
				if (!compile_node(cg, eis.m_cmp))
				{
					return false;
				}
				cg.pop_stack_identifiers();

				// cmp与jne结合
				if (m_cmp_jne)
				{
					cg.push(command.EMPTY_CMD, eis.m_cmp.lineno()); // 先塞个位置
					jnepos = cg.byte_code_size() - 1;
				}
				else
				{
					cg.push(command.MAKE_OPCODE(command.OPCODE_JNE), eis.lineno());
					cg.push(m_cur_addr, eis.lineno());
					cg.push(command.EMPTY_CMD, eis.lineno()); // 先塞个位置
					jnepos = cg.byte_code_size() - 1;
				}
				m_cmp_deps = 0;
				m_cmp_jne = false;

				// else if块
				if (eis.m_block != null)
				{
					cg.push_stack_identifiers();
					if (!compile_node(cg, eis.m_block))
					{
						return false;
					}
					cg.pop_stack_identifiers();
				}

				// 跳出if块
				cg.push(command.MAKE_OPCODE(command.OPCODE_JMP), eis.lineno());
				cg.push(command.EMPTY_CMD, eis.lineno()); // 先塞个位置
				jmpifpos.add(cg.byte_code_size() - 1);
			}
		}

		// 跳转到else
		cg.set(jnepos, command.MAKE_POS(cg.byte_code_size()));

		// else块
		if (is.m_elses != null && is.m_elses.m_block != null)
		{
			cg.push_stack_identifiers();
			if (!compile_node(cg, is.m_elses.m_block))
			{
				return false;
			}
			cg.pop_stack_identifiers();
		}

		// 跳转到结束
		for (int i = 0; i < (int) jmpifpos.size(); i++)
		{
			cg.set(jmpifpos.get(i), command.MAKE_POS(cg.byte_code_size()));
		}

		return true;
	}

	public boolean compile_return_stmt(codegen cg, return_stmt rs) throws Exception
	{
		if (rs.m_returnlist != null)
		{
			if (!compile_node(cg, rs.m_returnlist))
			{
				return false;
			}

			cg.push(command.MAKE_OPCODE(command.OPCODE_RETURN), rs.lineno());
			cg.push(command.MAKE_POS(rs.m_returnlist.m_returnlist.size()), rs.lineno());
			for (int i = 0; i < (int) rs.m_returnlist.m_returnlist.size(); i++)
			{
				cg.push(m_cur_addrs.get(i), rs.lineno());
			}
		}
		else
		{
			cg.push(command.MAKE_OPCODE(command.OPCODE_RETURN), rs.lineno());
			cg.push(command.MAKE_POS(0), rs.lineno());
		}

		return true;
	}

	public boolean compile_assign_stmt(codegen cg, assign_stmt as) throws Exception
	{
		long var = 0;
		long value = 0;

		if (!compile_node(cg, as.m_value))
		{
			return false;
		}
		value = m_cur_addr;

		m_new_var = as.m_isnew;
		if (!compile_node(cg, as.m_var))
		{
			return false;
		}
		m_new_var = false;
		var = m_cur_addr;

		cg.push(command.MAKE_OPCODE(command.OPCODE_ASSIGN), as.lineno());
		cg.push(var, as.lineno());
		cg.push(value, as.lineno());

		return true;
	}

	public boolean compile_multi_assign_stmt(codegen cg, multi_assign_stmt as) throws Exception
	{
		// 目前多重赋值只支持a,b,c = myfunc1()，需要告诉func1多返回几个值
		m_func_ret_num = as.m_varlist.m_varlist.size();

		// 编译value
		if (!compile_node(cg, as.m_value))
		{
			return false;
		}

		// 挨个编译var
		ArrayList<Long> varlist = new ArrayList<Long>();
		for (int i = 0; i < (int) as.m_varlist.m_varlist.size(); i++)
		{
			m_new_var = as.m_isnew;
			if (!compile_node(cg, as.m_varlist.m_varlist.get(i)))
			{
				return false;
			}
			m_new_var = false;
			varlist.add(m_cur_addr);
		}

		// 挨个赋值
		for (int i = 0; i < (int) as.m_varlist.m_varlist.size(); i++)
		{
			long var = 0;
			long value = 0;

			var = varlist.get(i);
			value = m_cur_addrs.get(i);

			cg.push(command.MAKE_OPCODE(command.OPCODE_ASSIGN), as.lineno());
			cg.push(var, as.lineno());
			cg.push(value, as.lineno());
		}

		return true;
	}

	public boolean compile_math_assign_stmt(codegen cg, math_assign_stmt ms) throws Exception
	{
		long oper = 0;
		long var = 0;
		long value = 0;

		if (ms.m_oper.equals("+="))
		{
			oper = command.MAKE_OPCODE(command.OPCODE_PLUS_ASSIGN);
		}
		else if (ms.m_oper.equals("-="))
		{
			oper = command.MAKE_OPCODE(command.OPCODE_MINUS_ASSIGN);
		}
		else if (ms.m_oper.equals("*="))
		{
			oper = command.MAKE_OPCODE(command.OPCODE_MULTIPLY_ASSIGN);
		}
		else if (ms.m_oper.equals("/="))
		{
			oper = command.MAKE_OPCODE(command.OPCODE_DIVIDE_ASSIGN);
		}
		else if (ms.m_oper.equals("%="))
		{
			oper = command.MAKE_OPCODE(command.OPCODE_DIVIDE_MOD_ASSIGN);
		}
		else
		{
			compile_seterror(ms, "compile math assign oper type %s error", ms.m_oper);
			return false;
		}

		// value
		if (!compile_node(cg, ms.m_value))
		{
			return false;
		}
		value = m_cur_addr;

		// var
		if (!compile_node(cg, ms.m_var))
		{
			return false;
		}
		var = m_cur_addr;

		cg.push(oper, ms.lineno());
		cg.push(var, ms.lineno());
		cg.push(value, ms.lineno());

		return true;
	}

	public boolean compile_break_stmt(codegen cg, break_stmt bs) throws Exception
	{
		cg.push(command.MAKE_OPCODE(command.OPCODE_JMP), bs.lineno());
		cg.push(command.EMPTY_CMD, bs.lineno()); // 先塞个位置
		int jmppos = cg.byte_code_size() - 1;

		ArrayList<Integer> bplist = m_loop_break_pos_stack.get(m_loop_break_pos_stack.size() - 1);
		bplist.add(jmppos);

		return true;
	}

	public boolean compile_continue_stmt(codegen cg, continue_stmt cs) throws Exception
	{
		if (m_loop_continue_pos_stack.isEmpty())
		{
			compile_seterror(cs, "no loop to continue");
			return false;
		}

		int continuepos = m_loop_continue_pos_stack.get(m_loop_continue_pos_stack.size() - 1);

		cg.push(command.MAKE_OPCODE(command.OPCODE_JMP), cs.lineno());
		cg.push(command.MAKE_POS(continuepos), cs.lineno());

		if (continuepos == -1)
		{
			// 一会统一设置
			int pos = cg.byte_code_size() - 1;
			ArrayList<Integer> cplist = m_continue_end_pos_stack.get(m_continue_end_pos_stack.size() - 1);
			cplist.add(pos);
		}

		return true;
	}

	public boolean compile_cmp_stmt(codegen cg, cmp_stmt cs) throws Exception
	{
		int deps = m_cmp_deps;
		m_cmp_deps++;

		long oper = 0;
		long left = 0;
		long right = 0;
		long dest = 0;

		if (!cs.m_cmp.equals("not"))
		{
			// oper
			if (cs.m_cmp.equals("&&"))
			{
				oper = deps == 0 ? command.MAKE_OPCODE(command.OPCODE_AND_JNE)
						: command.MAKE_OPCODE(command.OPCODE_AND);
			}
			else if (cs.m_cmp.equals("||"))
			{
				oper = deps == 0 ? command.MAKE_OPCODE(command.OPCODE_OR_JNE) : command.MAKE_OPCODE(command.OPCODE_OR);
			}
			else if (cs.m_cmp.equals("<"))
			{
				oper = deps == 0 ? command.MAKE_OPCODE(command.OPCODE_LESS_JNE)
						: command.MAKE_OPCODE(command.OPCODE_LESS);
			}
			else if (cs.m_cmp.equals(">"))
			{
				oper = deps == 0 ? command.MAKE_OPCODE(command.OPCODE_MORE_JNE)
						: command.MAKE_OPCODE(command.OPCODE_MORE);
			}
			else if (cs.m_cmp.equals("=="))
			{
				oper = deps == 0 ? command.MAKE_OPCODE(command.OPCODE_EQUAL_JNE)
						: command.MAKE_OPCODE(command.OPCODE_EQUAL);
			}
			else if (cs.m_cmp.equals(">="))
			{
				oper = deps == 0 ? command.MAKE_OPCODE(command.OPCODE_MOREEQUAL_JNE)
						: command.MAKE_OPCODE(command.OPCODE_MOREEQUAL);
			}
			else if (cs.m_cmp.equals("<="))
			{
				oper = deps == 0 ? command.MAKE_OPCODE(command.OPCODE_LESSEQUAL_JNE)
						: command.MAKE_OPCODE(command.OPCODE_LESSEQUAL);
			}
			else if (cs.m_cmp.equals("!="))
			{
				oper = deps == 0 ? command.MAKE_OPCODE(command.OPCODE_NOTEQUAL_JNE)
						: command.MAKE_OPCODE(command.OPCODE_NOTEQUAL);
			}
			else if (cs.m_cmp.equals("true"))
			{
				variant v = new variant();
				v.set_real(1);
				int pos = cg.getconst(v);
				m_cur_addr = command.MAKE_ADDR(command.ADDR_CONST, pos);

				m_cmp_deps--;
				m_cmp_jne = false;

				return true;
			}
			else if (cs.m_cmp.equals("false"))
			{
				variant v = new variant();
				v.set_real(0);
				int pos = cg.getconst(v);
				m_cur_addr = command.MAKE_ADDR(command.ADDR_CONST, pos);

				m_cmp_deps--;
				m_cmp_jne = false;

				return true;
			}
			else if (cs.m_cmp.equals("is"))
			{
				// left
				if (!compile_node(cg, cs.m_left))
				{
					return false;
				}

				m_cmp_deps--;
				m_cmp_jne = false;

				return true;
			}
			else
			{
				compile_seterror(cs, "cmp error %s", cs.m_cmp);
				return false;
			}

			// left
			if (!compile_node(cg, cs.m_left))
			{
				return false;
			}
			left = m_cur_addr;

			// right
			if (!compile_node(cg, cs.m_right))
			{
				return false;
			}
			right = m_cur_addr;

			// result
			int despos = cg.alloc_stack_identifier();
			dest = command.MAKE_ADDR(command.ADDR_STACK, despos);
			m_cur_addr = dest;

			cg.push(oper, cs.lineno());
			cg.push(left, cs.lineno());
			cg.push(right, cs.lineno());
			cg.push(dest, cs.lineno());
		}
		/* "not" */
		else
		{
			oper = deps == 0 ? command.MAKE_OPCODE(command.OPCODE_NOT_JNE) : command.MAKE_OPCODE(command.OPCODE_NOT);

			// left
			if (!compile_node(cg, cs.m_left))
			{
				return false;
			}
			left = m_cur_addr;

			int despos = cg.alloc_stack_identifier();
			dest = command.MAKE_ADDR(command.ADDR_STACK, despos);
			m_cur_addr = dest;

			cg.push(oper, cs.lineno());
			cg.push(left, cs.lineno());
			cg.push(dest, cs.lineno());
		}

		m_cmp_deps--;
		if (deps == 0)
		{
			m_cmp_jne = true;
		}

		return true;
	}

	public boolean compile_explicit_value(codegen cg, explicit_value_node ev) throws Exception
	{
		variant v = compile_explicit_value_node_to_variant(ev);

		int pos = cg.getconst(v);
		m_cur_addr = command.MAKE_ADDR(command.ADDR_CONST, pos);

		return true;
	}

	public boolean compile_variable_node(codegen cg, variable_node vn) throws Exception
	{
		// 看看是否是全局常量定义
		String constname = types.gen_package_name(m_mbs.get_package(), vn.m_str);
		variant gcv = m_f.pa.get_const_define(constname);
		if (gcv != null)
		{
			int pos = cg.getconst(gcv);
			m_cur_addr = command.MAKE_ADDR(command.ADDR_CONST, pos);

			return true;
		}

		gcv = m_f.pa.get_const_define(vn.m_str);
		if (gcv != null)
		{
			int pos = cg.getconst(gcv);
			m_cur_addr = command.MAKE_ADDR(command.ADDR_CONST, pos);

			return true;
		}

		// 从当前堆栈往上找
		int pos = cg.getvariable(vn.m_str);
		if (pos == -1)
		{
			// 是不是需要new出来
			if (m_new_var)
			{
				var_node tmp = new var_node();
				tmp.m_str = vn.m_str;
				return compile_var_node(cg, tmp);
			}
			else
			{
				compile_seterror(vn, "variable %s not found", vn.m_str);
				return false;
			}
		}
		m_cur_addr = command.MAKE_ADDR(command.ADDR_STACK, pos);

		return true;
	}

	public boolean compile_var_node(codegen cg, var_node vn) throws Exception
	{
		// 确保当前block没有
		if (cg.get_cur_variable_pos(vn.m_str) != -1)
		{
			compile_seterror(vn, "variable %s has define", vn.m_str);
			return false;
		}

		// 看看是否是常量定义
		HashMap<String, syntree_node> evm = m_mbs.get_const_map();
		if (evm.get(vn.m_str) != null)
		{
			compile_seterror(vn, "variable %s has defined const", vn.m_str);
			return false;
		}

		// 看看是否是全局常量定义
		variant gcv = m_f.pa.get_const_define(vn.m_str);
		if (gcv != null)
		{
			compile_seterror(vn, "variable %s has defined global const", vn.m_str);
			return false;
		}

		// 申请栈上空间
		int pos = cg.add_stack_identifier(vn.m_str, vn.lineno());
		if (pos == -1)
		{
			compile_seterror(vn, "double %s identifier error", vn.m_str);
			return false;
		}
		m_cur_addr = command.MAKE_ADDR(command.ADDR_STACK, pos);

		return true;
	}

	public boolean compile_function_call_node(codegen cg, function_call_node fn) throws Exception
	{
		int ret_num = m_func_ret_num;
		m_func_ret_num = 1;

		// 参数
		ArrayList<Long> arglist = new ArrayList<Long>();
		if (fn.m_arglist != null)
		{
			for (int i = 0; i < (int) fn.m_arglist.m_arglist.size(); i++)
			{
				syntree_node sn = fn.m_arglist.m_arglist.get(i);
				if (!compile_node(cg, sn))
				{
					return false;
				}
				arglist.add(m_cur_addr);
			}
		}

		// 调用位置
		long callpos;
		if (fn.m_prefuc == null)
		{
			String func = fn.m_fuc;
			// 1 检查变量
			int pos = cg.getvariable(func);
			if (pos != -1)
			{
				// 是用变量来调用函数
				callpos = command.MAKE_ADDR(command.ADDR_STACK, pos);
			}
			// 2 检查struct
			else if (m_mbs.is_have_struct(func))
			{
				// 直接替换成map
				variant v = new variant();
				v.set_string(interpreter.MAP_FUNC_NAME);
				pos = cg.getconst(v);
				callpos = command.MAKE_ADDR(command.ADDR_CONST, pos);
			}
			// 3 检查本地函数
			else if (m_mbs.is_have_func(func))
			{
				// 申请字符串变量
				variant v = new variant();
				// 拼上包名
				String pname = types.gen_package_name(m_mbs.get_package(), func);
				v.set_string(pname);
				pos = cg.getconst(v);
				callpos = command.MAKE_ADDR(command.ADDR_CONST, pos);
			}
			// 4 直接字符串使用
			else
			{
				// 申请字符串变量
				variant v = new variant();
				v.set_string(func);
				pos = cg.getconst(v);
				callpos = command.MAKE_ADDR(command.ADDR_CONST, pos);
			}
		}
		else
		{
			// 函数名从前面的函数得来
			if (!compile_node(cg, fn.m_prefuc))
			{
				return false;
			}

			callpos = m_cur_addr;
		}

		// oper
		long oper;
		oper = command.MAKE_OPCODE(command.OPCODE_CALL);

		// 调用类型
		long calltype;
		if (fn.m_fakecall)
		{
			calltype = command.MAKE_POS(command.CALL_FAKE);
		}
		else if (fn.m_classmem_call)
		{
			calltype = command.MAKE_POS(command.CALL_CLASSMEM);
		}
		else
		{
			calltype = command.MAKE_POS(command.CALL_NORMAL);
		}

		// 参数个数
		long argnum;
		argnum = command.MAKE_POS(arglist.size());

		// 返回值个数
		long retnum;
		retnum = command.MAKE_POS(ret_num);

		int oldretsize = m_cur_addrs.size();
		for (int i = 0; i < ret_num - oldretsize; i++)
		{
			m_cur_addrs.add(new Long(0));
		}

		// 返回值
		ArrayList<Long> ret = new ArrayList<Long>();
		for (int i = 0; i < ret_num; i++)
		{
			int retpos = cg.alloc_stack_identifier();
			ret.add(command.MAKE_ADDR(command.ADDR_STACK, retpos));

			m_cur_addrs.set(i, ret.get(i));
		}
		m_cur_addr = ret.size() > 0 ? ret.get(0) : 0;

		cg.push(oper, fn.lineno());
		cg.push(calltype, fn.lineno());
		cg.push(callpos, fn.lineno());
		cg.push(retnum, fn.lineno());
		for (int i = 0; i < ret_num; i++)
		{
			cg.push(ret.get(i), fn.lineno());
		}
		cg.push(argnum, fn.lineno());
		for (int i = 0; i < (int) arglist.size(); i++)
		{
			cg.push(arglist.get(i), fn.lineno());
		}

		return true;
	}

	public boolean compile_math_expr_node(codegen cg, math_expr_node mn) throws Exception
	{
		long oper = 0;
		long left = 0;
		long right = 0;
		long dest = 0;

		if (mn.m_oper.equals("+"))
		{
			oper = command.MAKE_OPCODE(command.OPCODE_PLUS);
		}
		else if (mn.m_oper.equals("-"))
		{
			oper = command.MAKE_OPCODE(command.OPCODE_MINUS);
		}
		else if (mn.m_oper.equals("*"))
		{
			oper = command.MAKE_OPCODE(command.OPCODE_MULTIPLY);
		}
		else if (mn.m_oper.equals("/"))
		{
			oper = command.MAKE_OPCODE(command.OPCODE_DIVIDE);
		}
		else if (mn.m_oper.equals("%"))
		{
			oper = command.MAKE_OPCODE(command.OPCODE_DIVIDE_MOD);
		}
		else if (mn.m_oper.equals(".."))
		{
			oper = command.MAKE_OPCODE(command.OPCODE_STRING_CAT);
		}
		else
		{
			compile_seterror(mn, "compile math oper type %s error", mn.m_oper);
			return false;
		}

		// left
		if (!compile_node(cg, mn.m_left))
		{
			return false;
		}
		left = m_cur_addr;

		// right
		if (!compile_node(cg, mn.m_right))
		{
			return false;
		}
		right = m_cur_addr;

		// result
		int despos = cg.alloc_stack_identifier();
		dest = command.MAKE_ADDR(command.ADDR_STACK, despos);
		m_cur_addr = dest;

		cg.push(oper, mn.lineno());
		cg.push(left, mn.lineno());
		cg.push(right, mn.lineno());
		cg.push(dest, mn.lineno());

		return true;
	}

	public boolean compile_return_value_list(codegen cg, return_value_list_node rn) throws Exception
	{
		ArrayList<Long> tmp = new ArrayList<Long>();
		for (int i = 0; i < (int) rn.m_returnlist.size(); i++)
		{
			if (!compile_node(cg, rn.m_returnlist.get(i)))
			{
				return false;
			}
			tmp.add(i, m_cur_addr);
		}
		m_cur_addrs = tmp;
		m_cur_addr = m_cur_addrs.get(0);

		return true;
	}

	public boolean compile_container_get(codegen cg, container_get_node cn) throws Exception
	{
		// 编译con
		long con = 0;

		// 看看是否是全局常量定义
		variant gcv = m_f.pa.get_const_define(cn.m_container);
		if (gcv != null)
		{
			int pos = cg.getconst(gcv);
			con = command.MAKE_ADDR(command.ADDR_CONST, pos);
		}
		else
		{
			int pos = cg.getvariable(cn.m_container);
			if (pos == -1)
			{
				compile_seterror(cn, "variable %s not found", cn.m_container);
				return false;
			}
			con = command.MAKE_ADDR(command.ADDR_STACK, pos);
		}

		// 编译key
		long key = 0;
		if (!compile_node(cg, cn.m_key))
		{
			return false;
		}
		key = m_cur_addr;

		// 返回
		int addrpos = cg.getcontaineraddr(con, key);
		m_cur_addr = command.MAKE_ADDR(command.ADDR_CONTAINER, addrpos);

		return true;
	}

	public boolean compile_struct_pointer(codegen cg, struct_pointer_node sn) throws Exception
	{
		String name = sn.m_str;
		ArrayList<String> tmp = new ArrayList<String>();
		do
		{
			int pos = name.indexOf("->");
			if (pos == -1)
			{
				tmp.add(name);
				break;
			}
			tmp.add(name.substring(0, pos));
			name = name.substring(pos + 2);
		}
		while (true);

		if (tmp.size() < 2)
		{
			return false;
		}

		String connname = tmp.get(0);

		// 编译con
		long con = 0;

		// 看看是否是全局常量定义
		variant gcv = m_f.pa.get_const_define(connname);
		if (gcv != null)
		{
			int pos = cg.getconst(gcv);
			con = command.MAKE_ADDR(command.ADDR_CONST, pos);
		}
		else
		{
			int pos = cg.getvariable(connname);
			if (pos == -1)
			{
				compile_seterror(sn, "variable %s not found", connname);
				return false;
			}
			con = command.MAKE_ADDR(command.ADDR_STACK, pos);
		}

		for (int i = 1; i < (int) tmp.size(); i++)
		{
			String keystr = tmp.get(i);

			// 编译key
			variant v = new variant();
			v.set_string(keystr);
			int pos = cg.getconst(v);
			long key = command.MAKE_ADDR(command.ADDR_CONST, pos);

			// 获取容器的位置
			int addrpos = cg.getcontaineraddr(con, key);
			m_cur_addr = command.MAKE_ADDR(command.ADDR_CONTAINER, addrpos);
			con = m_cur_addr;
		}

		return true;
	}

	public boolean compile_sleep_stmt(codegen cg, sleep_stmt ss) throws Exception
	{
		// 编译time
		long time = 0;
		if (!compile_node(cg, ss.m_time))
		{
			return false;
		}
		time = m_cur_addr;

		cg.push(command.MAKE_OPCODE(command.OPCODE_SLEEP), ss.lineno());
		cg.push(time, ss.lineno());

		return true;
	}

	public boolean compile_yield_stmt(codegen cg, yield_stmt ys) throws Exception
	{
		// 编译time
		long time = 0;
		if (!compile_node(cg, ys.m_time))
		{
			return false;
		}
		time = m_cur_addr;

		cg.push(command.MAKE_OPCODE(command.OPCODE_YIELD), ys.lineno());
		cg.push(time, ys.lineno());

		return true;
	}

	public boolean compile_switch_stmt(codegen cg, switch_stmt ss) throws Exception
	{
		long caseleft;
		long caseresult;

		cg.push_stack_identifiers();

		// caseleft
		if (!compile_node(cg, ss.m_cmp))
		{
			return false;
		}
		caseleft = m_cur_addr;

		// caseresult
		int despos = cg.alloc_stack_identifier();
		caseresult = command.MAKE_ADDR(command.ADDR_STACK, despos);

		switch_caselist_node scln = (switch_caselist_node) (ss.m_caselist);

		ArrayList<Integer> jmpswitchposlist = new ArrayList<Integer>();

		// 挨个和case的比较
		for (int i = 0; i < (int) scln.m_list.size(); i++)
		{
			long oper = command.MAKE_OPCODE(command.OPCODE_EQUAL);
			long left = caseleft;
			long right = 0;
			long dest = caseresult;

			switch_case_node scn = (switch_case_node) (scln.m_list.get(i));

			// right
			if (!compile_node(cg, scn.m_cmp))
			{
				return false;
			}
			right = m_cur_addr;

			// push case
			cg.push(oper, scn.lineno());
			cg.push(left, scn.lineno());
			cg.push(right, scn.lineno());
			cg.push(dest, scn.lineno());

			// push jmp
			cg.push(command.MAKE_OPCODE(command.OPCODE_JNE), scn.lineno());
			cg.push(dest, scn.lineno());
			cg.push(command.EMPTY_CMD, scn.lineno()); // 先塞个位置
			int jnepos = cg.byte_code_size() - 1;

			// build block
			if (scn.m_block != null)
			{
				cg.push_stack_identifiers();
				if (!compile_node(cg, scn.m_block))
				{
					return false;
				}
				cg.pop_stack_identifiers();
			}

			// 跳出switch块
			cg.push(command.MAKE_OPCODE(command.OPCODE_JMP), scn.lineno());
			cg.push(command.EMPTY_CMD, scn.lineno()); // 先塞个位置
			int jmpswitchpos = cg.byte_code_size() - 1;
			jmpswitchposlist.add(jmpswitchpos);

			// 跳转出case块
			cg.set(jnepos, command.MAKE_POS(cg.byte_code_size()));

		}

		// default
		if (ss.m_def != null)
		{
			cg.push_stack_identifiers();
			if (!compile_node(cg, ss.m_def))
			{
				return false;
			}
			cg.pop_stack_identifiers();
		}

		cg.pop_stack_identifiers();

		// 塞跳出的
		for (int i = 0; i < (int) jmpswitchposlist.size(); i++)
		{
			cg.set(jmpswitchposlist.get(i), command.MAKE_POS(cg.byte_code_size()));
		}

		return true;
	}

	public boolean compile_for_loop_stmt(codegen cg, for_loop_stmt fs) throws Exception
	{
		int startpos = 0;
		int jnepos = 0;
		int continuepos = 0;

		m_loop_break_pos_stack.add(new ArrayList<Integer>());
		m_continue_end_pos_stack.add(new ArrayList<Integer>());

		// 初始值
		if (!compile_node(cg, fs.m_begin))
		{
			return false;
		}
		long begin = m_cur_addr;

		// 循环变量，这个作用域是全for都有效的
		cg.push_stack_identifiers();
		if (!compile_node(cg, fs.m_var))
		{
			return false;
		}
		long var = m_cur_addr;

		// 最大值
		if (!compile_node(cg, fs.m_end))
		{
			return false;
		}
		long end = m_cur_addr;

		// 变化值
		if (!compile_node(cg, fs.m_add))
		{
			return false;
		}
		long add = m_cur_addr;

		// 塞for头
		cg.push(command.MAKE_OPCODE(command.OPCODE_FORBEGIN), fs.lineno());
		cg.push(var, fs.lineno());
		cg.push(begin, fs.lineno());
		cg.push(end, fs.lineno());
		cg.push(add, fs.lineno());
		cg.push(command.EMPTY_CMD, fs.lineno()); // 先塞个位置
		jnepos = cg.byte_code_size() - 1;

		// 需要continue end
		m_loop_continue_pos_stack.add(-1);

		startpos = cg.byte_code_size();

		// block块
		if (fs.m_block != null)
		{
			cg.push_stack_identifiers();
			if (!compile_node(cg, fs.m_block))
			{
				return false;
			}
			cg.pop_stack_identifiers();
		}

		continuepos = cg.byte_code_size();

		// 最大值
		if (!compile_node(cg, fs.m_end))
		{
			return false;
		}
		end = m_cur_addr;

		// 变化值
		if (!compile_node(cg, fs.m_add))
		{
			return false;
		}
		add = m_cur_addr;

		// 塞for loop
		cg.push(command.MAKE_OPCODE(command.OPCODE_FORLOOP), fs.lineno());
		cg.push(var, fs.lineno());
		cg.push(end, fs.lineno());
		cg.push(add, fs.lineno());
		cg.push(command.MAKE_POS(startpos), fs.lineno());

		// 跳转出block块
		cg.set(jnepos, command.MAKE_POS(cg.byte_code_size()));

		// 替换掉break
		ArrayList<Integer> bplist = m_loop_break_pos_stack.get(m_loop_break_pos_stack.size() - 1);
		for (int i = 0; i < (int) bplist.size(); i++)
		{
			cg.set(bplist.get(i), command.MAKE_POS(cg.byte_code_size()));
		}
		m_loop_break_pos_stack.remove(m_loop_break_pos_stack.size() - 1);

		// 替换掉continue
		ArrayList<Integer> cplist = m_continue_end_pos_stack.get(m_continue_end_pos_stack.size() - 1);
		for (int i = 0; i < (int) cplist.size(); i++)
		{
			cg.set(cplist.get(i), command.MAKE_POS(continuepos));
		}
		m_continue_end_pos_stack.remove(m_continue_end_pos_stack.size() - 1);

		m_loop_continue_pos_stack.remove(m_loop_continue_pos_stack.size() - 1);

		// 离开作用域
		cg.pop_stack_identifiers();

		return true;
	}
}
