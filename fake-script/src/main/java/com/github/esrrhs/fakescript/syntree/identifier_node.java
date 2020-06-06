package com.github.esrrhs.fakescript.syntree;

public class identifier_node extends syntree_node
{
	public String m_str;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_identifier;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[identifier]:";
		ret += m_str;
		ret += "\n";
		return ret;
	}
}
