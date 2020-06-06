package com.github.esrrhs.fakescript.syntree;

import java.util.ArrayList;

public class return_value_list_node extends syntree_node
{
	public ArrayList<syntree_node> m_returnlist = new ArrayList<syntree_node>();;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_return_value_list;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[return_value_list]:\n";
		for (int i = 0; i < (int)m_returnlist.size(); i++)
		{
			ret += m_returnlist.get(i).dump(indent + 1);
		}
		return ret;
	}
	
	public void add_arg(syntree_node stmt)
	{
		m_returnlist.add(stmt);
	}
}