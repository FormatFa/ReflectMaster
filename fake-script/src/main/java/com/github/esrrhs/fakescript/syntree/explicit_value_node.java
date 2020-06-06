package com.github.esrrhs.fakescript.syntree;

public class explicit_value_node extends syntree_node
{
	public String m_str;
	public explicit_value_type m_type;
	public syntree_node m_v;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_explicit_value;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[explicit_value]:";
		ret += m_str;
		ret += "\n";
		return ret;
	}
}
