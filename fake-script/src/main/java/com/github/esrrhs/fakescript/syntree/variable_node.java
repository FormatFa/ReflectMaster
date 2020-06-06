package com.github.esrrhs.fakescript.syntree;

public class variable_node extends syntree_node
{
	public String m_str;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_variable;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[variable]:";
		ret += m_str;
		ret += "\n";
		return ret;
	}
}
