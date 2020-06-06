package com.github.esrrhs.fakescript;

import java.util.ArrayList;

class running
{
	private fake m_f;
	private ArrayList<processor> m_processes = new ArrayList<processor>();
	private boolean m_stepmod;
	private variant_map m_gmap = new variant_map();

	public running(fake f)
	{
		m_f = f;
	}

	public processor cur_pro()
	{
		return m_processes.isEmpty() ? null : m_processes.get(m_processes.size() - 1);
	}

	public void push_pro(processor pro)
	{
		m_processes.add(pro);
	}

	public void pop_pro()
	{
		if (!m_processes.isEmpty())
		{
			m_processes.remove(m_processes.size() - 1);
		}
	}

	public boolean is_stepmod()
	{
		return m_stepmod;
	}

	public void set_stepmod(boolean stepmod)
	{
		this.m_stepmod = stepmod;
	}

	public variant_map get_gmap()
	{
		return m_gmap;
	}
}
