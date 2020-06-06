package com.github.esrrhs.fakescript.syntree;

import java.util.ArrayList;

public class switch_caselist_node extends syntree_node
{
	public ArrayList<syntree_node> m_list = new ArrayList<syntree_node>();
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_switch_caselist;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		for (int i = 0; i < (int)m_list.size(); i++)
		{
			ret += m_list.get(i).dump(indent);
		}
		return ret;
	}

	public void add_case(syntree_node p)
	{
		m_list.add(p);
	}
}
