package com.github.esrrhs.fakescript.syntree;

public class const_map_value_node extends syntree_node
{
	public syntree_node m_k;
	public syntree_node m_v;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_constmapvalue;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "(";
		ret += m_k.dump(indent + 1);
		ret += ":\n";
		ret += m_v.dump(indent + 1);
		ret += gentab(indent);
		ret += ")\n";
		return ret;
	}
}
