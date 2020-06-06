package com.github.esrrhs.fakescript;

import java.util.ArrayList;

class warper
{
	public Object d;

	public warper(Object d)
	{
		this.d = d;
	}
}

class interpreter
{
	public static final String MAP_FUNC_NAME = "map";
	public static final String GMAP_FUNC_NAME = "_G";

	private fake m_f;
	private boolean m_isend;
	private ArrayList<variant> m_ret = new ArrayList<variant>();
	private processor m_processor;
	// [ret pos] .. [ret pos] [ret num] [old ip] [call time] [old fb] [old bp]
	private static final int BP_SIZE = 5;
	private int m_sp;
	private int m_bp;
	private int m_ip;
	private func_binary m_fb;
	private ArrayList<variant> m_stack = new ArrayList<variant>();
	private long m_wakeuptime;
	private int m_yieldtime;
	private boolean m_sleeping;

	public interpreter(fake f)
	{
		m_f = f;
	}

	public boolean is_end()
	{
		return m_isend;
	}

	public variant get_ret()
	{
		return m_ret.isEmpty() ? new variant() : m_ret.get(0);
	}

	public void set_processor(processor pro)
	{
		m_processor = pro;
	}

	public int get_running_bytecode_pos()
	{
		return m_fb != null ? m_ip : -1;
	}

	public void call(variant func, ArrayList<Integer> retpos) throws Exception
	{
		funcunion f = m_f.fm.get_func(func);
		if (f == null)
		{
			throw new Exception("run no func " + func + " fail");
		}

		if (f.m_havefb)
		{
			func_binary fb = f.m_fb;
			variant v;

			// 准备栈大小
			int needsize = m_sp + BP_SIZE + retpos.size() + fb.m_maxstack;
			if (needsize > m_stack.size())
			{
				int oldsize = m_stack.size();
				for (int i = 0; i < needsize - oldsize; i++)
				{
					m_stack.add(new variant());
				}
			}

			// 老的bp
			int oldbp = m_bp;
			m_bp = m_sp;

			// 记录返回位置
			for (int i = 0; i < retpos.size(); i++)
			{
				v = m_stack.get(m_bp);
				v.m_type = variant_type.NIL;
				v.m_data = retpos.get(i);
				m_bp++;
			}

			// 记录返回值数目
			v = m_stack.get(m_bp);
			v.m_type = variant_type.NIL;
			v.m_data = retpos.size();
			m_bp++;

			// 记录老的ip
			v = m_stack.get(m_bp);
			v.m_type = variant_type.NIL;
			v.m_data = m_ip;
			m_bp++;

			// 记录profile
			if (m_f.pf.isopen())
			{
				v = m_stack.get(m_bp);
				v.m_data = System.currentTimeMillis();
			}
			v.m_type = variant_type.NIL;
			m_bp++;

			// 记录老的fb
			v = m_stack.get(m_bp);
			v.m_type = variant_type.NIL;
			v.m_data = m_fb;
			m_bp++;

			// 记录老的bp
			v = m_stack.get(m_bp);
			v.m_type = variant_type.NIL;
			v.m_data = oldbp;
			m_bp++;

			// 设置sp
			m_sp = m_bp + fb.m_maxstack;

			if (m_f.ps.size() != fb.m_paramnum)
			{
				m_isend = true;
				throw new Exception(
						"call func " + func + " param not match, need " + fb.m_paramnum + " give " + m_f.ps.size());
			}

			// 分配入参
			for (int i = 0; i < fb.m_paramnum; i++)
			{
				v = m_stack.get(m_bp + i);
				v.copy_from(m_f.ps.get(i));
			}
			m_f.ps.clear();

			// 重置ret
			if (m_ret.isEmpty())
			{
				m_ret.add(new variant());
			}
			else
			{
				m_ret.get(0).set_nil();
			}

			// 标记
			fb.m_use++;

			// 新函数
			m_fb = fb;
			m_ip = 0;

			return;
		}

		// 记录profile
		long s = 0;
		if (m_f.pf.isopen())
		{
			s = System.currentTimeMillis();
		}

		// 绑定函数
		if (f.m_haveff)
		{
			f.m_ff.call(m_f);
		}
		// 内置函数
		else if (f.m_havebif)
		{
			f.m_bif.call(m_f, this);
		}
		else
		{
			m_isend = true;
			throw new Exception("run no inter func " + func + " fail");
		}

		// 返回值
		// 这种情况是直接跳过脚本调用了java函数
		if (m_bp == 0)
		{
			variant cret = m_f.ps.pop_and_get();
			m_isend = true;
			// 直接塞返回值
			if (m_ret.isEmpty())
			{
				m_ret.add(new variant());
			}
			m_ret.get(0).copy_from(cret);
		}
		// 否则塞到当前堆栈上
		else
		{
			// 检查返回值数目对不对
			if (m_f.ps.size() != retpos.size())
			{
				m_isend = true;
				throw new Exception("native func " + func + " return num not match, give " + m_f.ps.size() + " need "
						+ retpos.size());
			}

			// 塞返回值
			for (int i = 0; i < retpos.size(); i++)
			{
				variant ret = GET_VARIANT(m_fb, m_bp, retpos.get(i));

				variant cret = m_f.ps.get(i);

				ret.copy_from(cret);
			}
		}

		if (m_f.pf.isopen())
		{
			String name = func.get_string();
			m_f.pf.add_func_sample(name, System.currentTimeMillis() - s);
		}
	}

	public variant GET_VARIANT(func_binary fb, int bp, int pos) throws Exception
	{
		return GET_VARIANT_BY_CMD(fb, bp, fb.m_buff[pos]);
	}

	public variant GET_VARIANT_BY_CMD(func_binary fb, int bp, long cmd) throws Exception
	{
		long v_cmd = cmd;
		int v_addrtype = command.ADDR_TYPE(command.COMMAND_CODE(v_cmd));
		int v_addrpos = command.ADDR_POS(command.COMMAND_CODE(v_cmd));
		if (v_addrtype == command.ADDR_STACK)
		{
			return m_stack.get(bp + v_addrpos);
		}
		else if (v_addrtype == command.ADDR_CONST)
		{
			return fb.m_const_list[v_addrpos];
		}
		else if (v_addrtype == command.ADDR_CONTAINER)
		{
			return get_container_variant(fb, v_addrpos);
		}
		else
		{
			throw new Exception("addrtype cannot be " + v_addrtype + " " + v_addrpos);
		}
	}

	public variant get_container_variant(func_binary fb, int conpos) throws Exception
	{
		container_addr ca = fb.m_container_addr_list[conpos];

		variant conv = GET_VARIANT_BY_CMD(fb, m_bp, ca.m_con);
		variant keyv = GET_VARIANT_BY_CMD(fb, m_bp, ca.m_key);

		if (m_isend)
		{
			return null;
		}

        if (conv.m_type != variant_type.ARRAY && conv.m_type != variant_type.MAP && conv.m_type != variant_type.POINTER)
		{
			m_isend = true;
			throw new Exception("interpreter get container variant fail, container type error, type " + conv.m_type);
		}

		if (conv.m_type == variant_type.MAP)
		{
			return conv.get_map().con_map_get(keyv);
		} else if (conv.m_type == variant_type.ARRAY) {
            return conv.get_array().con_array_get(keyv);
        } else if (conv.m_type == variant_type.POINTER) {
            Object[] array = (Object[]) conv.get_object();
            int i = (int) keyv.get_real();
            if (i >= array.length) {
                return null;
            } else {
                variant variant = new variant();
                variant.m_type = variant_type.POINTER;
                variant.set_pointer(array[i]);
                return variant;
            }

        } else {
            return null;
        }
	}

	public long BP_GET_CALLTIME(int bp)
	{
		variant v = m_stack.get(bp - 3);
		return (long) (Long) v.m_data;
	}

	public int BP_GET_RETNUM(int bp)
	{
		variant v = m_stack.get(bp - 5);
		return (int) (Integer) v.m_data;
	}

	public int BP_GET_BP(int bp)
	{
		variant v = m_stack.get(bp - 1);
		return (int) (Integer) v.m_data;
	}

	public func_binary BP_GET_FB(int bp)
	{
		variant v = m_stack.get(bp - 2);
		return (func_binary) v.m_data;
	}

	public int BP_GET_IP(int bp)
	{
		variant v = m_stack.get(bp - 4);
		return (int) (Integer) v.m_data;
	}

	public int BP_GET_RETPOS(int bp, int retnum, int i)
	{
		variant v = m_stack.get(bp - 5 - retnum + i);
		return (int) (Integer) v.m_data;
	}

	public boolean CHECK_DST_POS(func_binary fb, int ip)
	{
		return CHECK_STACK_POS(fb, ip) | CHECK_CONTAINER_POS(fb, ip);
	}

	public boolean CHECK_STACK_POS(func_binary fb, int ip)
	{
		return command.ADDR_TYPE(command.COMMAND_CODE(fb.m_buff[ip])) == command.ADDR_STACK;
	}

	public boolean CHECK_CONTAINER_POS(func_binary fb, int ip)
	{
		return command.ADDR_TYPE(command.COMMAND_CODE(fb.m_buff[ip])) == command.ADDR_CONTAINER;
	}

	public boolean CHECK_CONST_MAP_POS(variant v) throws Exception
	{
		return (v.m_type == variant_type.MAP && v.get_map().m_isconst);
	}

	public boolean CHECK_CONST_ARRAY_POS(variant v) throws Exception
	{
		return (v.m_type == variant_type.ARRAY && v.get_array().m_isconst);
	}

	public String POS_TYPE_NAME(func_binary fb, int ip)
	{
		int index = (int) command.ADDR_TYPE(command.COMMAND_CODE(fb.m_buff[ip]));
		return variant_type.values()[index].name();
	}

	public int run(int cmdnum) throws Exception
	{
		int runcmdnum = 0;

		// 栈溢出检查
		if (m_stack.size() > m_f.cfg.stack_max)
		{
			m_isend = true;
			throw new Exception("stack too big " + m_stack.size());
		}

		// 切换检查
		if (m_sleeping)
		{
			if (m_yieldtime != 0)
			{
				m_yieldtime--;
				return 0;
			}
			else if (System.currentTimeMillis() < m_wakeuptime)
			{
				return 0;
			}
			else
			{
				m_wakeuptime = 0;
			}
		}

		if (m_isend)
		{
			return 0;
		}

		while (true)
		{
			try
			{
				// 当前函数走完
				if (m_ip >= m_fb.m_buff.length)
				{
					// 记录profile
					if (m_f.pf.isopen())
					{
						long calltime = BP_GET_CALLTIME(m_bp);
						m_f.pf.add_func_sample(m_fb.m_name, System.currentTimeMillis() - calltime);
					}

					// 标记
					m_fb.m_use--;

					// 更新
					if (m_fb.m_use == 0 && m_fb.m_backup != null)
					{
						m_fb.backup_move();
					}

					// 出栈
					int oldretnum = BP_GET_RETNUM(m_bp);
					int callbp = BP_GET_BP(m_bp);
					m_fb = BP_GET_FB(m_bp);
					m_ip = BP_GET_IP(m_bp);
					int oldbp = m_bp;
					m_sp = m_bp - BP_SIZE - oldretnum;
					m_bp = callbp;

					// 所有都完
					if (m_bp == 0)
					{
						m_isend = true;
						break;
					}
					// 塞返回值
					else
					{
						for (int i = 0; i < oldretnum; i++)
						{
							int oldretpos = BP_GET_RETPOS(oldbp, oldretnum, i);

							variant ret = GET_VARIANT(m_fb, m_bp, oldretpos);
							ret.copy_from(m_ret.get(i));
						}
					}
					continue;
				}

				int code = command.COMMAND_CODE(m_fb.m_buff[m_ip]);

				m_ip++;

				if (m_f.pf.isopen())
				{
					m_f.pf.add_code_sample(code);
				}

				// 执行对应命令，放一起switch效率更高，cpu有缓存
				switch (code)
				{
					case command.OPCODE_ASSIGN:
					{
						// 赋值dest，必须为栈上或容器内
						if (!CHECK_DST_POS(m_fb, m_ip))
						{
							throw new Exception("interpreter assign error, dest is not stack or container, type "
									+ POS_TYPE_NAME(m_fb, m_ip));
						}

						variant varv = GET_VARIANT(m_fb, m_bp, m_ip);
						if ((CHECK_CONST_MAP_POS(varv) || CHECK_CONST_ARRAY_POS(varv)))
						{

							throw new Exception("interpreter assign error, dest is const container");
						}
						m_ip++;

						// 赋值来源
						variant valuev = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						// 赋值
						varv.copy_from(valuev);
					}
						break;
					case command.OPCODE_PLUS:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						if (!(CHECK_DST_POS(m_fb, m_ip)))
						{
							throw new Exception("interpreter math oper error, dest is not stack, type "
									+ POS_TYPE_NAME(m_fb, m_ip));
						}
						variant dest = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						dest.plus(left, right);
					}
						break;
					case command.OPCODE_MINUS:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						if (!(CHECK_DST_POS(m_fb, m_ip)))
						{
							throw new Exception("interpreter math oper error, dest is not stack, type "
									+ POS_TYPE_NAME(m_fb, m_ip));
						}
						variant dest = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						dest.minus(left, right);
					}
						break;
					case command.OPCODE_MULTIPLY:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						if (!(CHECK_DST_POS(m_fb, m_ip)))
						{
							throw new Exception("interpreter math oper error, dest is not stack, type "
									+ POS_TYPE_NAME(m_fb, m_ip));
						}
						variant dest = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						dest.multiply(left, right);
					}
						break;
					case command.OPCODE_DIVIDE:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						if (!(CHECK_DST_POS(m_fb, m_ip)))
						{
							throw new Exception("interpreter math oper error, dest is not stack, type "
									+ POS_TYPE_NAME(m_fb, m_ip));
						}
						variant dest = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						dest.divide(left, right);
					}
						break;
					case command.OPCODE_DIVIDE_MOD:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						if (!(CHECK_DST_POS(m_fb, m_ip)))
						{
							throw new Exception("interpreter math oper error, dest is not stack, type "
									+ POS_TYPE_NAME(m_fb, m_ip));
						}
						variant dest = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						dest.divide_mod(left, right);
					}
						break;
					case command.OPCODE_STRING_CAT:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						if (!(CHECK_DST_POS(m_fb, m_ip)))
						{
							throw new Exception("interpreter math oper error, dest is not stack, type "
									+ POS_TYPE_NAME(m_fb, m_ip));
						}
						variant dest = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						dest.string_cat(left, right);
					}
						break;
					case command.OPCODE_AND:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						if (!(CHECK_DST_POS(m_fb, m_ip)))
						{
							throw new Exception("interpreter math oper error, dest is not stack, type "
									+ POS_TYPE_NAME(m_fb, m_ip));
						}
						variant dest = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						dest.and(left, right);
					}
						break;
					case command.OPCODE_OR:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						if (!(CHECK_DST_POS(m_fb, m_ip)))
						{
							throw new Exception("interpreter math oper error, dest is not stack, type "
									+ POS_TYPE_NAME(m_fb, m_ip));
						}
						variant dest = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						dest.or(left, right);
					}
						break;
					case command.OPCODE_LESS:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						if (!(CHECK_DST_POS(m_fb, m_ip)))
						{
							throw new Exception("interpreter math oper error, dest is not stack, type "
									+ POS_TYPE_NAME(m_fb, m_ip));
						}
						variant dest = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						dest.less(left, right);
					}
						break;
					case command.OPCODE_MORE:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						if (!(CHECK_DST_POS(m_fb, m_ip)))
						{
							throw new Exception("interpreter math oper error, dest is not stack, type "
									+ POS_TYPE_NAME(m_fb, m_ip));
						}
						variant dest = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						dest.more(left, right);
					}
						break;
					case command.OPCODE_EQUAL:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						if (!(CHECK_DST_POS(m_fb, m_ip)))
						{
							throw new Exception("interpreter math oper error, dest is not stack, type "
									+ POS_TYPE_NAME(m_fb, m_ip));
						}
						variant dest = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						dest.equal(left, right);
					}
						break;
					case command.OPCODE_MOREEQUAL:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						if (!(CHECK_DST_POS(m_fb, m_ip)))
						{
							throw new Exception("interpreter math oper error, dest is not stack, type "
									+ POS_TYPE_NAME(m_fb, m_ip));
						}
						variant dest = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						dest.more_equal(left, right);
					}
						break;
					case command.OPCODE_LESSEQUAL:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						if (!(CHECK_DST_POS(m_fb, m_ip)))
						{
							throw new Exception("interpreter math oper error, dest is not stack, type "
									+ POS_TYPE_NAME(m_fb, m_ip));
						}
						variant dest = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						dest.less_equal(left, right);
					}
						break;
					case command.OPCODE_NOTEQUAL:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						if (!(CHECK_DST_POS(m_fb, m_ip)))
						{
							throw new Exception("interpreter math oper error, dest is not stack, type "
									+ POS_TYPE_NAME(m_fb, m_ip));
						}
						variant dest = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						dest.not_equal(left, right);
					}
						break;
					case command.OPCODE_NOT:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						if (!(CHECK_DST_POS(m_fb, m_ip)))
						{
							throw new Exception("interpreter math oper error, dest is not stack, type "
									+ POS_TYPE_NAME(m_fb, m_ip));
						}
						variant dest = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						dest.not(left);
					}
						break;
					case command.OPCODE_AND_JNE:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						/* dest */
						m_ip++;
						int destip = command.COMMAND_CODE(m_fb.m_buff[m_ip]);
						m_ip++;

						boolean b = variant.and_jne(left, right);

						if (!b)
						{
							m_ip = destip;
						}
					}
						break;
					case command.OPCODE_OR_JNE:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						/* dest */
						m_ip++;
						int destip = command.COMMAND_CODE(m_fb.m_buff[m_ip]);
						m_ip++;

						boolean b = variant.or_jne(left, right);

						if (!b)
						{
							m_ip = destip;
						}
					}
						break;
					case command.OPCODE_LESS_JNE:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						/* dest */
						m_ip++;
						int destip = command.COMMAND_CODE(m_fb.m_buff[m_ip]);
						m_ip++;

						boolean b = variant.less_jne(left, right);

						if (!b)
						{
							m_ip = destip;
						}
					}
						break;
					case command.OPCODE_MORE_JNE:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						/* dest */
						m_ip++;
						int destip = command.COMMAND_CODE(m_fb.m_buff[m_ip]);
						m_ip++;

						boolean b = variant.more_jne(left, right);

						if (!b)
						{
							m_ip = destip;
						}
					}
						break;
					case command.OPCODE_EQUAL_JNE:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						/* dest */
						m_ip++;
						int destip = command.COMMAND_CODE(m_fb.m_buff[m_ip]);
						m_ip++;

						boolean b = variant.equal_jne(left, right);

						if (!b)
						{
							m_ip = destip;
						}
					}
						break;
					case command.OPCODE_MOREEQUAL_JNE:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						/* dest */
						m_ip++;
						int destip = command.COMMAND_CODE(m_fb.m_buff[m_ip]);
						m_ip++;

						boolean b = variant.more_equal_jne(left, right);

						if (!b)
						{
							m_ip = destip;
						}
					}
						break;
					case command.OPCODE_LESSEQUAL_JNE:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						/* dest */
						m_ip++;
						int destip = command.COMMAND_CODE(m_fb.m_buff[m_ip]);
						m_ip++;

						boolean b = variant.less_equal_jne(left, right);

						if (!b)
						{
							m_ip = destip;
						}
					}
						break;
					case command.OPCODE_NOTEQUAL_JNE:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						variant right = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						/* dest */
						m_ip++;
						int destip = command.COMMAND_CODE(m_fb.m_buff[m_ip]);
						m_ip++;

						boolean b = variant.not_equal_jne(left, right);

						if (!b)
						{
							m_ip = destip;
						}
					}
						break;
					case command.OPCODE_NOT_JNE:
					{
						variant left = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						/* dest */
						m_ip++;
						int destip = command.COMMAND_CODE(m_fb.m_buff[m_ip]);
						m_ip++;

						boolean b = variant.not_jne(left);

						if (!b)
						{
							m_ip = destip;
						}
					}
						break;
					case command.OPCODE_JNE:
					{
						variant cmp = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						int ip = command.COMMAND_CODE(m_fb.m_buff[m_ip]);
						m_ip++;

						if (!cmp.bool())
						{
							m_ip = ip;
						}
					}
						break;
					case command.OPCODE_JMP:
					{
						int ip = command.COMMAND_CODE(m_fb.m_buff[m_ip]);
						m_ip++;

						m_ip = ip;
					}
						break;
					case command.OPCODE_PLUS_ASSIGN:
					{
						if (!CHECK_DST_POS(m_fb, m_ip))
						{
							throw new Exception(
									"interpreter math assign oper error, dest is not stack or container, type "
											+ POS_TYPE_NAME(m_fb, m_ip));
						}
						variant var = GET_VARIANT(m_fb, m_bp, m_ip);
						if ((CHECK_CONST_MAP_POS(var) || CHECK_CONST_ARRAY_POS(var)))
						{
							throw new Exception("interpreter assign error, dest is const container");
						}
						m_ip++;

						variant value = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						var.plus(var, value);
					}
						break;
					case command.OPCODE_MINUS_ASSIGN:
					{
						if (!CHECK_DST_POS(m_fb, m_ip))
						{
							throw new Exception(
									"interpreter math assign oper error, dest is not stack or container, type "
											+ POS_TYPE_NAME(m_fb, m_ip));
						}
						variant var = GET_VARIANT(m_fb, m_bp, m_ip);
						if ((CHECK_CONST_MAP_POS(var) || CHECK_CONST_ARRAY_POS(var)))
						{
							throw new Exception("interpreter assign error, dest is const container");
						}
						m_ip++;

						variant value = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						var.minus(var, value);
					}
						break;
					case command.OPCODE_MULTIPLY_ASSIGN:
					{
						if (!CHECK_DST_POS(m_fb, m_ip))
						{
							throw new Exception(
									"interpreter math assign oper error, dest is not stack or container, type "
											+ POS_TYPE_NAME(m_fb, m_ip));
						}
						variant var = GET_VARIANT(m_fb, m_bp, m_ip);
						if ((CHECK_CONST_MAP_POS(var) || CHECK_CONST_ARRAY_POS(var)))
						{
							throw new Exception("interpreter assign error, dest is const container");
						}
						m_ip++;

						variant value = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						var.multiply(var, value);
					}
						break;
					case command.OPCODE_DIVIDE_ASSIGN:
					{
						if (!CHECK_DST_POS(m_fb, m_ip))
						{
							throw new Exception(
									"interpreter math assign oper error, dest is not stack or container, type "
											+ POS_TYPE_NAME(m_fb, m_ip));
						}
						variant var = GET_VARIANT(m_fb, m_bp, m_ip);
						if ((CHECK_CONST_MAP_POS(var) || CHECK_CONST_ARRAY_POS(var)))
						{
							throw new Exception("interpreter assign error, dest is const container");
						}
						m_ip++;

						variant value = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						var.divide(var, value);
					}
						break;
					case command.OPCODE_DIVIDE_MOD_ASSIGN:
					{
						if (!CHECK_DST_POS(m_fb, m_ip))
						{
							throw new Exception(
									"interpreter math assign oper error, dest is not stack or container, type "
											+ POS_TYPE_NAME(m_fb, m_ip));
						}
						variant var = GET_VARIANT(m_fb, m_bp, m_ip);
						if ((CHECK_CONST_MAP_POS(var) || CHECK_CONST_ARRAY_POS(var)))
						{
							throw new Exception("interpreter assign error, dest is const container");
						}
						m_ip++;

						variant value = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						var.divide_mod(var, value);
					}
						break;
					case command.OPCODE_CALL:
					{
						int calltype = command.COMMAND_CODE(m_fb.m_buff[m_ip]);
						m_ip++;

						variant callpos = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						int retnum = command.COMMAND_CODE(m_fb.m_buff[m_ip]);
						m_ip++;

						ArrayList<Integer> retpos = new ArrayList<Integer>();

						for (int i = 0; i < retnum; i++)
						{
							retpos.add(m_ip);
							m_ip++;
						}

						int argnum = command.COMMAND_CODE(m_fb.m_buff[m_ip]);
						m_ip++;

						paramstack ps = m_f.ps;
						ps.clear();
						for (int i = 0; i < argnum; i++)
						{
							variant arg = GET_VARIANT(m_fb, m_bp, m_ip);
							m_ip++;

							variant argdest = ps.push_and_get();

							argdest.copy_from(arg);
						}

						if (calltype == command.CALL_NORMAL)
						{
							call(callpos, retpos);
						}
						else if (calltype == command.CALL_CLASSMEM)
						{
							// prefix
							variant classvar = ps.get(ps.size() - 1);
							if (classvar == null)
							{
								throw new Exception("interpreter class mem call error, the class ref is null");
							}

							Object classptr = classvar.get_pointer();
							if (classptr == null)
							{
								throw new Exception("interpreter class mem call error, the class ref is null");
							}

							// mem func name
							String funcname = callpos.get_string();

							// whole name
							String wholename = classptr.getClass().getName() + funcname;

							// call it
							variant tmp = new variant();
							tmp.set_string(wholename);

							call(tmp, retpos);
						}
						else
						{
							m_processor.start_routine(callpos, retpos);
						}
					}
						break;
					case command.OPCODE_RETURN:
					{
						int returnnum = command.COMMAND_CODE(m_fb.m_buff[m_ip]);
						if (returnnum == 0)
						{
							m_ip = (m_fb).m_buff.length;
							break;
						}
						m_ip++;

						int oldsize = m_ret.size();
						for (int i = 0; i < returnnum - oldsize; i++)
						{
							m_ret.add(new variant());
						}

						// 塞给ret
						for (int i = 0; i < returnnum; i++)
						{
							variant ret = GET_VARIANT(m_fb, m_bp, m_ip);
							m_ip++;

							variant retv = new variant();
							retv.copy_from(ret);
							m_ret.set(i, retv);
						}

						m_ip = (m_fb).m_buff.length;
					}
						break;
					case command.OPCODE_FORBEGIN:
					{
						// 赋值dest，必须为栈上或容器内
						if (!CHECK_DST_POS(m_fb, m_ip))
						{
							throw new Exception("interpreter assign error, dest is not stack or container, type "
									+ POS_TYPE_NAME(m_fb, m_ip));
						}

						// var
						variant varv = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						// begin
						variant beginv = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						// end
						variant endv = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						// add
						variant addv = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						int jneip = command.COMMAND_CODE(m_fb.m_buff[m_ip]);
						m_ip++;

						// 赋值
						varv.copy_from(beginv);

						// 增长
						if (addv.get_real() > 0)
						{
							// 判断是否超出
							if (varv.get_real() >= endv.get_real())
							{
								m_ip = jneip;
							}
						}
						else
						{
							// 判断是否小
							if (varv.get_real() <= endv.get_real())
							{
								m_ip = jneip;
							}
						}
					}
						break;
					case command.OPCODE_FORLOOP:
					{
						// var
						variant varv = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						// end
						variant endv = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						// add
						variant addv = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						int continueip = command.COMMAND_CODE(m_fb.m_buff[m_ip]);
						m_ip++;

						// 赋值
						varv.plus(varv, addv);

						// 增长
						if (addv.get_real() > 0)
						{
							// 判断是否超出
							if (varv.get_real() < endv.get_real())
							{
								m_ip = continueip;
							}
						}
						else
						{
							// 判断是否小
							if (varv.get_real() > endv.get_real())
							{
								m_ip = continueip;
							}
						}
					}
						break;
					case command.OPCODE_SLEEP:
					{
						variant time = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						long sleeptime = (long) (double) time.get_real();

						m_wakeuptime = System.currentTimeMillis() + sleeptime;
						m_sleeping = true;
						return runcmdnum + 1;
					}
					case command.OPCODE_YIELD:
					{
						variant time = GET_VARIANT(m_fb, m_bp, m_ip);
						m_ip++;

						m_yieldtime = (int) time.get_real();
						m_sleeping = true;
						return runcmdnum + 1;
					}
					default:
						throw new Exception("next err code " + code + " " + types.OpCodeStr(code));
				}
			}
			catch (Exception e)
			{
				// 发生错误
				m_isend = true;

				// 清除当前栈上函数的使用标记
				{
					int ip = m_ip;
					int bp = m_bp;
					func_binary fb = m_fb;

					while (bp != 0)
					{
						// 标记
						fb.m_use--;

						// 更新
						if (fb.m_use == 0 && fb.m_backup != null)
						{
							fb.backup_move();
						}

						fb = BP_GET_FB(bp);
						ip = BP_GET_IP(bp);
						int callbp = BP_GET_BP(bp);
						bp = callbp;
						if (bp == 0)
						{
							break;
						}
					}
				}

				throw e;
			}

			if ((m_isend))
			{
				break;
			}

			runcmdnum++;

			if ((runcmdnum >= cmdnum))
			{
				break;
			}
		}

		return runcmdnum;
	}

	public String get_running_file_name()
	{
		return m_fb != null ? m_fb.m_filename : "";
	}

	public String get_running_func_name()
	{
		return m_fb != null ? m_fb.m_name : "";
	}

	public int get_running_file_line()
	{
		return m_fb != null ? m_fb.get_binary_lineno(m_ip) : 0;
	}

	public String get_running_call_stack()
	{
		String cur_runinginfo = "";

		if (m_fb == null)
		{
			return "";
		}

		int deps = 0;

		int ip = m_ip;
		int bp = m_bp;
		func_binary fb = m_fb;

		while (bp != 0)
		{
			cur_runinginfo += "#";
			cur_runinginfo += deps;
			cur_runinginfo += "	";
			cur_runinginfo += fb != null ? fb.m_name : "";
			cur_runinginfo += " at ";
			cur_runinginfo += fb != null ? fb.m_filename : "";
			cur_runinginfo += ":";
			cur_runinginfo += fb != null ? fb.get_binary_lineno(ip) : 0;
			cur_runinginfo += "\n";
			for (int j = 0; fb != null && j < fb.m_maxstack; j++)
			{
				cur_runinginfo += "		";

				String variant_name = "";
				for (int i = 0; i < fb.m_debug_stack_variant_info.length; i++)
				{
					stack_variant_info info = fb.m_debug_stack_variant_info[i];
					if (info.m_pos == j)
					{
						variant_name += info.m_name;
						variant_name += "(line:";
						variant_name += info.m_line;
						variant_name += ") ";
					}
				}
				if (variant_name.isEmpty())
				{
					variant_name = "(anonymous)";
				}

				cur_runinginfo += variant_name;
				cur_runinginfo += "\t[";
				cur_runinginfo += j;
				cur_runinginfo += "]\t";
				variant v = bp + j < m_stack.size() ? m_stack.get(bp + j) : new variant();
				cur_runinginfo += v;
				cur_runinginfo += "\n";
			}

			fb = BP_GET_FB(bp);
			ip = BP_GET_IP(bp);
			int callbp = BP_GET_BP(bp);
			bp = callbp;
			if (bp == 0)
			{
				break;
			}

			deps++;
		}

		return cur_runinginfo;
	}

	public void get_running_vaiant(int frame, String name, int line, warper value, warper outline)
	{
		String valueret = "";

		if (m_fb == null)
		{
			return;
		}

		// const define
		variant gcv = m_f.pa.get_const_define(name);
		if (gcv != null)
		{
			if (gcv.m_type == variant_type.STRING)
			{
				valueret += "\"";
				valueret += gcv.toString();
				valueret += "\"";
			}
			else
			{
				valueret += gcv.toString();
			}
			value.d = valueret;
			outline.d = m_f.pa.get_const_define_lineno(name);
			return;
		}

		int deps = 0;

		int bp = m_bp;
		func_binary fb = m_fb;

		while (bp != 0)
		{
			if (deps >= frame)
			{
				for (int i = 0; i < fb.m_debug_stack_variant_info.length; i++)
				{
					stack_variant_info info = fb.m_debug_stack_variant_info[i];
					if ((line != -1 && info.m_name.equals(name) && info.m_line == line)
							|| (line == -1 && info.m_name.equals(name)))
					{
						variant v = m_stack.get(bp + info.m_pos);
						if (v.m_type == variant_type.STRING)
						{
							valueret += "\"";
							valueret += v.toString();
							valueret += "\"";
						}
						else
						{
							valueret += v.toString();
						}

						value.d = valueret;
						outline.d = info.m_line;
						return;
					}
				}
				break;
			}

			fb = BP_GET_FB(bp);
			int callbp = 0;
			callbp = BP_GET_BP(bp);
			bp = callbp;
			if (bp == 0)
			{
				break;
			}

			deps++;
		}

		return;
	}

	public void get_running_call_stack_frame_info(int frame, warper stackinfo, warper func, warper file, warper line)
	{
		String valueret = "";

		if (m_fb == null)
		{
			return;
		}

		int deps = 0;

		int ip = m_ip;
		int bp = m_bp;
		func_binary fb = m_fb;

		while (bp != 0)
		{
			if (deps >= frame)
			{
				func.d = fb != null ? fb.m_name : "";
				file.d = fb != null ? fb.m_filename : "";
				line.d = fb != null ? fb.get_binary_lineno(ip) : 0;

				valueret += "#";
				valueret += deps;
				valueret += "	";
				valueret += func;
				valueret += " at ";
				valueret += file;
				valueret += ":";
				valueret += line;
				valueret += "\n";

				stackinfo.d = valueret;

				return;
			}

			fb = BP_GET_FB(bp);
			ip = BP_GET_IP(bp);
			int callbp = 0;
			callbp = BP_GET_BP(bp);
			bp = callbp;
			if (bp == 0)
			{
				break;
			}

			deps++;
		}

	}

	public int get_running_call_stack_length()
	{
		if (m_fb == null)
		{
			return 0;
		}

		int deps = 0;

		int bp = m_bp;

		while (bp != 0)
		{
			deps++;
			int callbp = 0;
			callbp = BP_GET_BP(bp);
			bp = callbp;
			if (bp == 0)
			{
				break;
			}
		}

		return deps;
	}

	public void set_running_vaiant(int frame, String name, int line, String value)
	{
		fake f = m_f;

		if (m_fb == null)
		{
			return;
		}

		// const define
		variant gcv = m_f.pa.get_const_define(name);
		if (gcv != null)
		{
			// can not change
			return;
		}

		int deps = 0;

		int bp = m_bp;
		func_binary fb = m_fb;

		while (bp != 0)
		{
			if (deps >= frame)
			{
				for (int i = 0; i < fb.m_debug_stack_variant_info.length; i++)
				{
					stack_variant_info info = fb.m_debug_stack_variant_info[i];
					if ((line != -1 && info.m_name.equals(name) && info.m_line == line)
							|| (line == -1 && info.m_name.equals(name)))
					{
						variant v = m_stack.get(bp + info.m_pos);

						String valuestr = value;
						if (valuestr.isEmpty())
						{
							return;
						}

						if (valuestr.startsWith("\""))
						{
							valuestr = valuestr.substring(1, valuestr.length() - 1);
							v.set_string(valuestr);
						}
						else
						{
							v.set_real(Integer.parseInt(value));
						}

						return;
					}
				}
				break;
			}

			fb = BP_GET_FB(bp);
			int callbp = 0;
			callbp = BP_GET_BP(bp);
			bp = callbp;
			if (bp == 0)
			{
				break;
			}

			deps++;
		}

	}

}
