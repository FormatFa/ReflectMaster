package com.github.esrrhs.fakescript.syntree;

import java.util.ArrayList;

public class const_map_list_value_node extends syntree_node
{
	public ArrayList<syntree_node> m_lists = new ArrayList<syntree_node>();

	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_constmaplist;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		for (int i = 0; i < (int) m_lists.size(); i++)
		{
			ret += gentab(indent);
			ret += i;
			ret += "\n";
			ret += m_lists.get(i).dump(indent + 1);
		}
		return ret;
	}

	public void add_ele(syntree_node e)
	{
		m_lists.add(e);
	}
}
