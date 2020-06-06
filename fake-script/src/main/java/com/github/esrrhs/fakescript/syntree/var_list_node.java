package com.github.esrrhs.fakescript.syntree;

import java.util.ArrayList;

public class var_list_node extends syntree_node
{
	public ArrayList<syntree_node> m_varlist = new ArrayList<syntree_node>();;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_var_list;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[var_list]:\n";
		for (int i = 0; i < (int)m_varlist.size(); i++)
		{
			ret += m_varlist.get(i).dump(indent + 1);
		}
		return ret;
	}
	
	public void add_arg(syntree_node stmt)
	{
		m_varlist.add(stmt);
	}
}
