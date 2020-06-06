package com.github.esrrhs.fakescript;

import java.util.ArrayList;

class codegen
{
	class block_identifiers
	{
		public String m_name;
		public int m_pos;

		public block_identifiers(String m_name, int m_pos)
		{
			this.m_name = m_name;
			this.m_pos = m_pos;
		}
	}

	private fake m_f;
	private ArrayList<ArrayList<block_identifiers>> m_block_identifiers_stack = new ArrayList<ArrayList<block_identifiers>>();
	private ArrayList<stack_variant_info> m_debug_block_identifiers_list = new ArrayList<stack_variant_info>();
	private int m_stackpos;
	private int m_maxstackpos;
	private ArrayList<Long> m_byte_code_list = new ArrayList<Long>();
	private ArrayList<Integer> m_byte_lineno_list = new ArrayList<Integer>();
	private ArrayList<variant> m_const_list = new ArrayList<variant>();
	private ArrayList<container_addr> m_containeraddr_list = new ArrayList<container_addr>();

	public codegen(fake f)
	{
		m_f = f;
	}

	public void push_stack_identifiers()
	{
		m_block_identifiers_stack.add(new ArrayList<block_identifiers>());
	}

	public void pop_stack_identifiers()
	{
		ArrayList<block_identifiers> list = m_block_identifiers_stack.get(m_block_identifiers_stack.size() - 1);
		int stacksize = list.size();
		m_block_identifiers_stack.remove(m_block_identifiers_stack.size() - 1);
		m_stackpos -= stacksize;
	}

	public int add_stack_identifier(String name, int line)
	{
		if (get_cur_variable_pos(name) != -1)
		{
			return -1;
		}
		ArrayList<block_identifiers> list = m_block_identifiers_stack.get(m_block_identifiers_stack.size() - 1);
		list.add(new block_identifiers(name, m_stackpos));
		int ret = m_stackpos;
		m_stackpos++;
		if (m_stackpos > m_maxstackpos)
		{
			m_maxstackpos = m_stackpos;
		}

		stack_variant_info tmp = new stack_variant_info();
		tmp.m_name = name;
		tmp.m_line = line;
		tmp.m_pos = ret;
		m_debug_block_identifiers_list.add(tmp);

		return ret;
	}

	public int get_cur_variable_pos(String name)
	{
		ArrayList<block_identifiers> list = m_block_identifiers_stack.get(m_block_identifiers_stack.size() - 1);
		for (int i = 0; i < (int) list.size(); i++)
		{
			if (name.equals(list.get(i).m_name))
			{
				return list.get(i).m_pos;
			}
		}
		return -1;
	}

	public int byte_code_size()
	{
		return m_byte_code_list.size();
	}

	public void push(long code, int lineno)
	{
		m_byte_code_list.add(code);
		m_byte_lineno_list.add(lineno);
	}

	public void set(int pos, long code)
	{
		m_byte_code_list.set(pos, code);
	}

	public int getconst(variant v)
	{
		for (int i = 0; i < (int) m_const_list.size(); i++)
		{
			variant vv = m_const_list.get(i);
			if (vv.equals(v))
			{
				return i;
			}
		}

		int pos = m_const_list.size();
		m_const_list.add(v);
		return pos;
	}

	public int alloc_stack_identifier()
	{
		int ret = m_stackpos;
		ArrayList<block_identifiers> list = m_block_identifiers_stack.get(m_block_identifiers_stack.size() - 1);
		list.add(new block_identifiers("", m_stackpos));
		m_stackpos++;
		if (m_stackpos > m_maxstackpos)
		{
			m_maxstackpos = m_stackpos;
		}
		return ret;
	}

	public int getvariable(String name)
	{
		// 从下往上找
		for (int i = (int) m_block_identifiers_stack.size() - 1; i >= 0; i--)
		{
			ArrayList<block_identifiers> list = m_block_identifiers_stack.get(i);
			for (int j = 0; j < (int) list.size(); j++)
			{
				if (name.equals(list.get(j).m_name))
				{
					return list.get(j).m_pos;
				}
			}
		}
		return -1;
	}

	public int getcontaineraddr(long con, long key)
	{
		for (int i = 0; i < (int) m_containeraddr_list.size(); i++)
		{
			container_addr pc = m_containeraddr_list.get(i);
			if (con == pc.m_con && key == pc.m_key)
			{
				return i;
			}
		}
		int pos = m_containeraddr_list.size();
		container_addr tmp = new container_addr();
		tmp.m_con = con;
		tmp.m_key = key;
		m_containeraddr_list.add(tmp);
		return pos;
	}

	public void output(String filename, String packagename, String name, func_binary bin)
	{
		bin.m_filename = filename;
		bin.m_packagename = packagename;
		bin.m_name = name;

		bin.m_maxstack = m_maxstackpos;

		bin.m_buff = new long[m_byte_code_list.size()];
		for (int i = 0; i < m_byte_code_list.size(); i++)
		{
			bin.m_buff[i] = m_byte_code_list.get(i);
		}

		bin.m_lineno_buff = new int[m_byte_lineno_list.size()];
		for (int i = 0; i < m_byte_lineno_list.size(); i++)
		{
			bin.m_lineno_buff[i] = m_byte_lineno_list.get(i);
		}

		bin.m_const_list = new variant[m_const_list.size()];
		for (int i = 0; i < m_const_list.size(); i++)
		{
			bin.m_const_list[i] = m_const_list.get(i);
		}

		bin.m_container_addr_list = new container_addr[m_containeraddr_list.size()];
		for (int i = 0; i < m_containeraddr_list.size(); i++)
		{
			bin.m_container_addr_list[i] = m_containeraddr_list.get(i);
		}

		bin.m_debug_stack_variant_info = new stack_variant_info[m_debug_block_identifiers_list.size()];
		for (int i = 0; i < m_debug_block_identifiers_list.size(); i++)
		{
			bin.m_debug_stack_variant_info[i] = m_debug_block_identifiers_list.get(i);
		}

		bin.m_fresh++;

		types.log(m_f, "codegen out %s %d", name, m_maxstackpos);
	}

}
