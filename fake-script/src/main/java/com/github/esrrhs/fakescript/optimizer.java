package com.github.esrrhs.fakescript;

import java.util.ArrayList;
import java.util.HashSet;

class opt_ins_addr
{
	public opt_ins_addr(int offset, long addr)
	{
		this.offset = offset;
		this.addr = addr;
	}

	public int offset;
	public long addr;
}

class opt_ins
{
	public int offset;
	public int size;
	public int code;
	public ArrayList<Long> read = new ArrayList<Long>();
	public ArrayList<Long> write = new ArrayList<Long>();
	public ArrayList<opt_ins_addr> src = new ArrayList<opt_ins_addr>();
	public ArrayList<opt_ins_addr> dst = new ArrayList<opt_ins_addr>();

	public String toString()
	{
		return "{" + types.OpCodeStr(code) + ", size:" + size + ", read:" + read + ", write:" + write + "}";
	}
}

class optimizer
{
	private fake m_f;
	private boolean isopt;
	private ArrayList<opt_ins> inslist = new ArrayList<opt_ins>();
	private boolean isopen;

	public optimizer(fake f)
	{
		m_f = f;
	}

	public void open()
	{
		isopen = true;
	}

	public void close()
	{
		isopen = false;
	}

	public void optimize(func_binary fb) throws Exception
	{
		if (!isopen)
		{
			return;
		}

		if (m_f.cfg.open_debug_log != 0)
		{
			types.log(m_f, "before %s", fb.dump(-1));
		}
		while (true)
		{
			isopt = false;

			trans_inslist(fb);

			if (!isopt)
			{
				optimize_write_assign(fb);
			}
			if (!isopt)
			{
				optimize_write_write(fb);
			}
			if (!isopt)
			{
				optimize_no_use_const(fb);
			}
			if (!isopt)
			{
				optimize_no_use_container(fb);
			}
			if (!isopt)
			{
				optimize_no_use_stack(fb);
			}
			if (!isopt)
			{
				optimize_stack_pos(fb);
			}

			if (isopt)
			{
				if (m_f.cfg.open_debug_log != 0)
				{
					types.log(m_f, "optimize %s", fb.dump(-1));
				}
			}
			else
			{
				break;
			}
		}
	}

	private void optimize_no_use_stack(func_binary fb)
	{
		for (int i = 0; i < fb.m_debug_stack_variant_info.length; i++)
		{
			stack_variant_info info = fb.m_debug_stack_variant_info[i];
			int pos = info.m_pos;

			if (pos < fb.m_paramnum)
			{
				continue;
			}

			boolean use = false;
			for (int j = 0; j < fb.m_buff.length; j++)
			{
				long cmd = fb.m_buff[j];
				if (is_addr_type(cmd, command.ADDR_STACK, pos))
				{
					use = true;
					break;
				}
			}

			for (int j = 0; j < fb.m_container_addr_list.length; j++)
			{
				container_addr addr = fb.m_container_addr_list[j];
				if (is_addr_type(addr.m_con, command.ADDR_STACK, pos))
				{
					use = true;
					break;
				}
				if (is_addr_type(addr.m_key, command.ADDR_STACK, pos))
				{
					use = true;
					break;
				}
			}

			if (!use)
			{
				stack_variant_info[] newlist = new stack_variant_info[fb.m_debug_stack_variant_info.length - 1];
				for (int z = 0; z < i; z++)
				{
					newlist[z] = fb.m_debug_stack_variant_info[z];
				}
				for (int z = i; z < fb.m_debug_stack_variant_info.length - 1; z++)
				{
					newlist[z] = fb.m_debug_stack_variant_info[z + 1];
				}
				fb.m_debug_stack_variant_info = newlist;

				isopt = true;
			}
		}
	}

	private void optimize_no_use_container(func_binary fb)
	{
		for (int i = 0; i < fb.m_container_addr_list.length; i++)
		{
			boolean use = false;
			for (int j = 0; j < fb.m_buff.length; j++)
			{
				long cmd = fb.m_buff[j];
				if (is_addr_type(cmd, command.ADDR_CONTAINER, i))
				{
					use = true;
					break;
				}
			}

			for (int j = 0; j < fb.m_container_addr_list.length; j++)
			{
				container_addr addr = fb.m_container_addr_list[j];
				if (is_addr_type(addr.m_con, command.ADDR_CONTAINER, i))
				{
					use = true;
					break;
				}
				if (is_addr_type(addr.m_key, command.ADDR_CONTAINER, i))
				{
					use = true;
					break;
				}
			}

			if (!use)
			{
				for (int j = i; j < fb.m_container_addr_list.length - 1; j++)
				{
					replace_all_addr(fb, command.ADDR_CONTAINER, j + 1, j);
				}

				container_addr[] newlist = new container_addr[fb.m_container_addr_list.length - 1];
				for (int j = 0; j < i; j++)
				{
					newlist[j] = fb.m_container_addr_list[j];
				}
				for (int j = i; j < fb.m_container_addr_list.length - 1; j++)
				{
					newlist[j] = fb.m_container_addr_list[j + 1];
				}
				fb.m_container_addr_list = newlist;

				isopt = true;
				return;
			}
		}
	}

	private void optimize_no_use_const(func_binary fb)
	{
		for (int i = 0; i < fb.m_const_list.length; i++)
		{
			boolean use = false;
			for (int j = 0; j < fb.m_buff.length; j++)
			{
				long cmd = fb.m_buff[j];
				if (is_addr_type(cmd, command.ADDR_CONST, i))
				{
					use = true;
					break;
				}
			}

			for (int j = 0; j < fb.m_container_addr_list.length; j++)
			{
				container_addr addr = fb.m_container_addr_list[j];
				if (is_addr_type(addr.m_con, command.ADDR_CONST, i))
				{
					use = true;
					break;
				}
				if (is_addr_type(addr.m_key, command.ADDR_CONST, i))
				{
					use = true;
					break;
				}
			}

			if (!use)
			{
				for (int j = i; j < fb.m_const_list.length - 1; j++)
				{
					replace_all_addr(fb, command.ADDR_CONST, j + 1, j);
				}

				variant[] newlist = new variant[fb.m_const_list.length - 1];
				for (int j = 0; j < i; j++)
				{
					newlist[j] = fb.m_const_list[j];
				}
				for (int j = i; j < fb.m_const_list.length - 1; j++)
				{
					newlist[j] = fb.m_const_list[j + 1];
				}
				fb.m_const_list = newlist;

				isopt = true;
				return;
			}
		}
	}

	private boolean is_addr_type(long cmd, int dsttype, int dstpos)
	{
		int type = command.COMMAND_TYPE(cmd);
		int code = command.COMMAND_CODE(cmd);

		if (type == command.COMMAND_ADDR)
		{
			int addrtype = command.HIINT16(code);
			int pos = command.LOINT16(code);

			if (addrtype == dsttype && pos == dstpos)
			{
				return true;
			}
		}
		return false;
	}

	private void replace_all_addr(func_binary fb, int dsttype, int frompos, int topos)
	{
		for (int j = 0; j < fb.m_buff.length; j++)
		{
			long cmd = fb.m_buff[j];
			fb.m_buff[j] = replace_addr_cmd(cmd, dsttype, frompos, topos);
		}

		for (int j = 0; j < fb.m_container_addr_list.length; j++)
		{
			container_addr addr = fb.m_container_addr_list[j];
			addr.m_con = replace_addr_cmd(addr.m_con, dsttype, frompos, topos);
			addr.m_key = replace_addr_cmd(addr.m_key, dsttype, frompos, topos);
		}
	}

	private long replace_addr_cmd(long cmd, int dsttype, int frompos, int topos)
	{
		int type = command.COMMAND_TYPE(cmd);
		int code = command.COMMAND_CODE(cmd);

		if (type == command.COMMAND_ADDR)
		{
			int addrtype = command.HIINT16(code);
			int pos = command.LOINT16(code);
			if (addrtype == dsttype && pos == frompos)
			{
				pos = topos;
				cmd = command.MAKE_ADDR(addrtype, pos);
			}
		}

		return cmd;
	}

	private void optimize_stack_pos(func_binary fb)
	{
		// 整理下栈，可能有空洞
		HashSet<Integer> set = new HashSet<Integer>();

		int max = -1;
		for (int j = 0; j < fb.m_paramnum; j++)
		{
			set.add(j);
			if (j > max)
			{
				max = j;
			}
		}

		for (int j = 0; j < fb.m_buff.length; j++)
		{
			long cmd = fb.m_buff[j];
			int type = command.COMMAND_TYPE(cmd);
			int code = command.COMMAND_CODE(cmd);

			if (type == command.COMMAND_ADDR)
			{
				int addrtype = command.HIINT16(code);
				int pos = command.LOINT16(code);
				if (addrtype == command.ADDR_STACK)
				{
					set.add(pos);
					if (pos > max)
					{
						max = pos;
					}
				}
			}
		}
		for (int j = 0; j < fb.m_container_addr_list.length; j++)
		{
			container_addr addr = fb.m_container_addr_list[j];

			long cmd = addr.m_con;
			int type = command.COMMAND_TYPE(cmd);
			int code = command.COMMAND_CODE(cmd);

			if (type == command.COMMAND_ADDR)
			{
				int addrtype = command.HIINT16(code);
				int pos = command.LOINT16(code);
				if (addrtype == command.ADDR_STACK)
				{
					set.add(pos);
					if (pos > max)
					{
						max = pos;
					}
				}
			}

			cmd = addr.m_key;
			type = command.COMMAND_TYPE(cmd);
			code = command.COMMAND_CODE(cmd);

			if (type == command.COMMAND_ADDR)
			{
				int addrtype = command.HIINT16(code);
				int pos = command.LOINT16(code);
				if (addrtype == command.ADDR_STACK)
				{
					set.add(pos);
					if (pos > max)
					{
						max = pos;
					}
				}
			}
		}

		if (set.size() == max + 1)
		{
			return;
		}

		int empty = -1;
		for (int j = 0; j < max + 1; j++)
		{
			if (!set.contains(j))
			{
				empty = j;
				break;
			}
		}

		for (int i = empty; i < max; i++)
		{
			replace_all_addr(fb, command.ADDR_STACK, i + 1, i);
		}

		for (int i = empty; i < max; i++)
		{
			for (int j = 0; j < fb.m_debug_stack_variant_info.length; j++)
			{
				stack_variant_info info = fb.m_debug_stack_variant_info[j];
				if (info.m_pos == i)
				{
					stack_variant_info[] newlist = new stack_variant_info[fb.m_debug_stack_variant_info.length - 1];
					for (int z = 0; z < j; z++)
					{
						newlist[z] = fb.m_debug_stack_variant_info[z];
					}
					for (int z = j; z < fb.m_debug_stack_variant_info.length - 1; z++)
					{
						newlist[z] = fb.m_debug_stack_variant_info[z + 1];
					}
					fb.m_debug_stack_variant_info = newlist;
					break;
				}
			}
			for (int j = 0; j < fb.m_debug_stack_variant_info.length; j++)
			{
				stack_variant_info info = fb.m_debug_stack_variant_info[j];
				if (info.m_pos == i + 1)
				{
					info.m_pos = i;
				}
			}
		}

		isopt = true;
	}

	private void set_ins_write(opt_ins ins, func_binary fb, int ip)
	{
		long v_cmd = fb.m_buff[ip];
		ins.dst.add(new opt_ins_addr(ip, v_cmd));
		set_ins_write_by_cmd(ins, fb, v_cmd);
	}

	private void set_ins_write_by_cmd(opt_ins ins, func_binary fb, long v_cmd)
	{
		int v_addrtype = command.ADDR_TYPE(command.COMMAND_CODE(v_cmd));
		int v_addrpos = command.ADDR_POS(command.COMMAND_CODE(v_cmd));
		if (v_addrtype == command.ADDR_STACK)
		{
			ins.write.add(v_cmd);
		}
		else if (v_addrtype == command.ADDR_CONST)
		{
		}
		else if (v_addrtype == command.ADDR_CONTAINER)
		{
			container_addr ca = fb.m_container_addr_list[v_addrpos];

			set_ins_read_by_cmd(ins, fb, ca.m_con);
			set_ins_write_by_cmd(ins, fb, ca.m_con);
			set_ins_read_by_cmd(ins, fb, ca.m_key);
		}
	}

	private void set_ins_read(opt_ins ins, func_binary fb, int ip)
	{
		long v_cmd = fb.m_buff[ip];
		ins.src.add(new opt_ins_addr(ip, v_cmd));
		set_ins_read_by_cmd(ins, fb, v_cmd);
	}

	private void set_ins_read_by_cmd(opt_ins ins, func_binary fb, long v_cmd)
	{
		int v_addrtype = command.ADDR_TYPE(command.COMMAND_CODE(v_cmd));
		int v_addrpos = command.ADDR_POS(command.COMMAND_CODE(v_cmd));
		if (v_addrtype == command.ADDR_STACK)
		{
			ins.read.add(v_cmd);
		}
		else if (v_addrtype == command.ADDR_CONST)
		{
		}
		else if (v_addrtype == command.ADDR_CONTAINER)
		{
			container_addr ca = fb.m_container_addr_list[v_addrpos];

			set_ins_read_by_cmd(ins, fb, ca.m_con);
			set_ins_write_by_cmd(ins, fb, ca.m_con);
			set_ins_read_by_cmd(ins, fb, ca.m_key);
		}
	}

	private void trans_inslist(func_binary fb) throws Exception
	{
		inslist.clear();

		int ip = 0;
		while (ip < fb.m_buff.length)
		{
			int code = command.COMMAND_CODE(fb.m_buff[ip]);

			opt_ins ins = new opt_ins();
			ins.code = code;
			ins.offset = ip;
			inslist.add(ins);

			ip++;

			// 执行对应命令，放一起switch效率更高，cpu有缓存
			switch (code)
			{
				case command.OPCODE_ASSIGN:
				{
					set_ins_write(ins, fb, ip);
					ip++;

					// 赋值来源
					set_ins_read(ins, fb, ip);
					ip++;
				}
					break;
				case command.OPCODE_PLUS:
				case command.OPCODE_MINUS:
				case command.OPCODE_MULTIPLY:
				case command.OPCODE_DIVIDE:
				case command.OPCODE_DIVIDE_MOD:
				case command.OPCODE_STRING_CAT:
				case command.OPCODE_AND:
				case command.OPCODE_OR:
				case command.OPCODE_LESS:
				case command.OPCODE_MORE:
				case command.OPCODE_EQUAL:
				case command.OPCODE_MOREEQUAL:
				case command.OPCODE_LESSEQUAL:
				case command.OPCODE_NOTEQUAL:
				{
					set_ins_read(ins, fb, ip);
					ip++;

					set_ins_read(ins, fb, ip);
					ip++;

					set_ins_write(ins, fb, ip);
					ip++;
				}
					break;
				case command.OPCODE_NOT:
				{
					set_ins_read(ins, fb, ip);
					ip++;

					set_ins_write(ins, fb, ip);
					ip++;
				}
					break;
				case command.OPCODE_AND_JNE:
				case command.OPCODE_OR_JNE:
				case command.OPCODE_LESS_JNE:
				case command.OPCODE_MORE_JNE:
				case command.OPCODE_EQUAL_JNE:
				case command.OPCODE_MOREEQUAL_JNE:
				case command.OPCODE_LESSEQUAL_JNE:
				case command.OPCODE_NOTEQUAL_JNE:
				{
					set_ins_read(ins, fb, ip);
					ip++;

					set_ins_read(ins, fb, ip);
					ip++;

					/* dest */
					ip++;
					ip++;
				}
					break;
				case command.OPCODE_NOT_JNE:
				{
					set_ins_read(ins, fb, ip);
					ip++;

					/* dest */
					ip++;
					ip++;
				}
					break;
				case command.OPCODE_JNE:
				{
					set_ins_read(ins, fb, ip);
					ip++;

					ip++;
				}
					break;
				case command.OPCODE_JMP:
				{
					ip++;
				}
					break;
				case command.OPCODE_PLUS_ASSIGN:
				case command.OPCODE_MINUS_ASSIGN:
				case command.OPCODE_MULTIPLY_ASSIGN:
				case command.OPCODE_DIVIDE_ASSIGN:
				case command.OPCODE_DIVIDE_MOD_ASSIGN:
				{
					set_ins_read(ins, fb, ip);
					set_ins_write(ins, fb, ip);
					ip++;

					set_ins_read(ins, fb, ip);
					ip++;
				}
					break;
				case command.OPCODE_CALL:
				{
					ip++;

					set_ins_read(ins, fb, ip);
					ip++;

					int retnum = command.COMMAND_CODE(fb.m_buff[ip]);
					ip++;

					for (int i = 0; i < retnum; i++)
					{
						set_ins_write(ins, fb, ip);
						ip++;
					}

					int argnum = command.COMMAND_CODE(fb.m_buff[ip]);
					ip++;

					for (int i = 0; i < argnum; i++)
					{
						set_ins_write(ins, fb, ip);
						set_ins_read(ins, fb, ip);
						ip++;
					}
				}
					break;
				case command.OPCODE_RETURN:
				{
					int returnnum = command.COMMAND_CODE(fb.m_buff[ip]);
					ip++;

					// 塞给ret
					for (int i = 0; i < returnnum; i++)
					{
						set_ins_read(ins, fb, ip);
						ip++;
					}
				}
					break;
				case command.OPCODE_FORBEGIN:
				{
					// var
					set_ins_read(ins, fb, ip);
					set_ins_write(ins, fb, ip);
					ip++;

					// begin
					set_ins_read(ins, fb, ip);
					ip++;

					// end
					set_ins_read(ins, fb, ip);
					ip++;

					// add
					set_ins_read(ins, fb, ip);
					ip++;

					ip++;
				}
					break;
				case command.OPCODE_FORLOOP:
				{
					// var
					set_ins_read(ins, fb, ip);
					set_ins_write(ins, fb, ip);
					ip++;

					// end
					set_ins_read(ins, fb, ip);
					ip++;

					// add
					set_ins_read(ins, fb, ip);
					ip++;

					ip++;
				}
					break;
				case command.OPCODE_SLEEP:
				{
					set_ins_read(ins, fb, ip);
					ip++;
				}
					break;
				case command.OPCODE_YIELD:
				{
					set_ins_read(ins, fb, ip);
					ip++;
				}
					break;
				default:
					throw new Exception("next err code " + code + " " + types.OpCodeStr(code));
			}
			ins.size = ip - ins.offset;
		}
	}

	private opt_ins get_assign_src_ins_from(int pos, long cmd)
	{
		for (int i = pos; i < inslist.size(); i++)
		{
			opt_ins ins = inslist.get(i);
			if (ins.code == command.OPCODE_ASSIGN)
			{
				for (int j = 0; j < ins.src.size(); j++)
				{
					if (ins.src.get(j).addr == cmd)
					{
						return ins;
					}
				}
			}
		}
		return null;
	}

	private opt_ins get_assign_dst_ins_from(int pos, long cmd)
	{
		for (int i = pos; i < inslist.size(); i++)
		{
			opt_ins ins = inslist.get(i);
			if (ins.code == command.OPCODE_ASSIGN)
			{
				for (int j = 0; j < ins.dst.size(); j++)
				{
					if (ins.dst.get(j).addr == cmd)
					{
						return ins;
					}
				}
			}
		}
		return null;
	}

	private opt_ins get_write_ins_from(int pos, long cmd)
	{
		for (int i = pos; i < inslist.size(); i++)
		{
			opt_ins ins = inslist.get(i);
			for (int j = 0; j < ins.write.size(); j++)
			{
				if (ins.write.get(j) == cmd)
				{
					return ins;
				}
			}
		}
		return null;
	}

	private opt_ins get_read_ins_from(int pos, long cmd)
	{
		for (int i = pos; i < inslist.size(); i++)
		{
			opt_ins ins = inslist.get(i);
			for (int j = 0; j < ins.read.size(); j++)
			{
				if (ins.read.get(j) == cmd)
				{
					return ins;
				}
			}
		}
		return null;
	}

	private void replace_ins_addr(func_binary fb, opt_ins_addr oldaddr, opt_ins_addr newaddr)
	{
		int offset = oldaddr.offset;
		fb.m_buff[offset] = newaddr.addr;
	}

	private void remove_ins(func_binary fb, opt_ins delins)
	{
		// 刷一遍jmp，然后删除
		for (int i = 0; i < inslist.size(); i++)
		{
			opt_ins ins = inslist.get(i);
			switch (ins.code)
			{
				case command.OPCODE_AND_JNE:
				case command.OPCODE_OR_JNE:
				case command.OPCODE_LESS_JNE:
				case command.OPCODE_MORE_JNE:
				case command.OPCODE_EQUAL_JNE:
				case command.OPCODE_MOREEQUAL_JNE:
				case command.OPCODE_LESSEQUAL_JNE:
				case command.OPCODE_NOTEQUAL_JNE:
				case command.OPCODE_FORLOOP:
				{
					int destip = command.COMMAND_CODE(fb.m_buff[ins.offset + 4]);
					if (destip > delins.offset)
					{
						fb.m_buff[ins.offset + 4] = command.MAKE_POS(destip - delins.size);
					}
				}
					break;
				case command.OPCODE_NOT_JNE:
				{
					int destip = command.COMMAND_CODE(fb.m_buff[ins.offset + 3]);
					if (destip > delins.offset)
					{
						fb.m_buff[ins.offset + 3] = command.MAKE_POS(destip - delins.size);
					}
				}
					break;
				case command.OPCODE_JNE:
				{
					int destip = command.COMMAND_CODE(fb.m_buff[ins.offset + 2]);
					if (destip > delins.offset)
					{
						fb.m_buff[ins.offset + 2] = command.MAKE_POS(destip - delins.size);
					}
				}
					break;
				case command.OPCODE_JMP:
				{
					int destip = command.COMMAND_CODE(fb.m_buff[ins.offset + 1]);
					if (destip > delins.offset)
					{
						fb.m_buff[ins.offset + 1] = command.MAKE_POS(destip - delins.size);
					}
				}
					break;
				case command.OPCODE_FORBEGIN:
				{
					int destip = command.COMMAND_CODE(fb.m_buff[ins.offset + 5]);
					if (destip > delins.offset)
					{
						fb.m_buff[ins.offset + 5] = command.MAKE_POS(destip - delins.size);
					}
				}
					break;
			}
		}

		long[] newbuff = new long[fb.m_buff.length - delins.size];
		int[] newlinenobuff = new int[fb.m_lineno_buff.length - delins.size];

		for (int i = 0; i < delins.offset; i++)
		{
			newbuff[i] = fb.m_buff[i];
			newlinenobuff[i] = fb.m_lineno_buff[i];
		}

		for (int i = 0; i < fb.m_buff.length - delins.offset - delins.size; i++)
		{
			newbuff[delins.offset + i] = fb.m_buff[delins.offset + delins.size + i];
			newlinenobuff[delins.offset + i] = fb.m_lineno_buff[delins.offset + delins.size + i];
		}

		fb.m_buff = newbuff;
		fb.m_lineno_buff = newlinenobuff;
	}

	private void optimize_write_write(func_binary fb)
	{
		// eg.a=1 return 1
		// write，并没有read，删除这个write
		for (int i = 0; i < inslist.size(); i++)
		{
			opt_ins ins = inslist.get(i);
			if (ins.write.size() > 0)
			{
				int num = 0;
				for (int j = 0; j < ins.write.size(); j++)
				{
					long addr = ins.write.get(j);

					if (command.ADDR_TYPE(command.COMMAND_CODE(addr)) == command.ADDR_CONTAINER)
					{
						continue;
					}

					if (ins.code == command.OPCODE_CALL)
					{
						continue;
					}

					opt_ins readins = get_read_ins_from(0, addr);
					if (readins == null)
					{
						num++;
						continue;
					}
				}

				if (num == ins.write.size())
				{
					remove_ins(fb, ins);
					isopt = true;
					return;
				}
			}
		}
	}

	private void optimize_write_assign(func_binary fb)
	{
		// eg.a=b+c
		// write一个后，只assign了一次，再无read，替换掉write的dst，删除assign
		for (int i = 0; i < inslist.size(); i++)
		{
			opt_ins ins = inslist.get(i);
			for (int j = 0; j < ins.dst.size(); j++)
			{
				opt_ins_addr addr = ins.dst.get(j);
				if (command.ADDR_TYPE(command.COMMAND_CODE(addr.addr)) == command.ADDR_CONTAINER)
				{
					continue;
				}
				opt_ins assign_ins = get_assign_src_ins_from(i + 1, addr.addr);
				if (assign_ins != null && get_read_ins_from(i + 1, addr.addr) == assign_ins)
				{
					if (get_read_ins_from(i + 2, addr.addr) == null)
					{
						opt_ins_addr assign_ins_addr = assign_ins.dst.get(0);
						replace_ins_addr(fb, addr, assign_ins_addr);
						remove_ins(fb, assign_ins);
						isopt = true;
						return;
					}
				}
			}
		}
		isopt = false;
	}
}
