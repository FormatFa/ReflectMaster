package com.github.esrrhs.fakescript.syntree;

public class struct_pointer_node extends syntree_node
{
	public String m_str;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_struct_pointer;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[struct_pointer_node]:";
		ret += m_str;
		ret += "\n";
		return ret;
	}
}
