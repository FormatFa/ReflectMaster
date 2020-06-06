package com.github.esrrhs.fakescript.syntree;

import java.util.ArrayList;

public class function_call_arglist_node extends syntree_node
{
	public ArrayList<syntree_node> m_arglist = new ArrayList<syntree_node>();
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_call_arglist;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[func_call_arglist]:\n";
		for (int i = 0; i < (int)m_arglist.size(); i++)
		{
			ret += gentab(indent + 1);
			ret += "[arg";
			ret += i;
			ret += "]:\n";
			ret += m_arglist.get(i).dump(indent + 2);
		}
		return ret;
	}

	public void add_arg(syntree_node p)
	{	
		m_arglist.add(p);
	}
}
