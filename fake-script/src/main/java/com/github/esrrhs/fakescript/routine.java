package com.github.esrrhs.fakescript;

import java.util.ArrayList;

class routine
{
	private fake m_f;
	private int m_id;
	private interpreter m_interpreter;

	public routine(fake f)
	{
		m_f = f;
		m_interpreter = new interpreter(f);
	}

	public interpreter get_interpreter()
	{
		return m_interpreter;
	}

	public variant get_ret()
	{
		return m_interpreter.get_ret();
	}

	public void set_id(int id)
	{
		m_id = id;
	}

	public int get_id()
	{
		return m_id;
	}

	public void set_processor(processor pro)
	{
		m_interpreter.set_processor(pro);
	}

	public void entry(variant func, ArrayList<Integer> retpos) throws Exception
	{
		m_interpreter.call(func, retpos);
	}

	public void run(int cmdnum) throws Exception
	{
		m_interpreter.run(cmdnum);
	}

	public boolean is_end()
	{
		return m_interpreter.is_end();
	}
}
