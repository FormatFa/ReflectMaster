package com.github.esrrhs.fakescript.syntree;

public class multi_assign_stmt extends syntree_node
{
	public var_list_node m_varlist;
	public syntree_node m_value;
	public boolean m_isnew;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_multi_assign_stmt;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[multi_assign]:\n";

		ret += gentab(indent + 1);
		ret += "[var]:\n";
		ret += m_varlist.dump(indent + 2);

		ret += gentab(indent + 1);
		ret += "[value]:\n";
		ret += m_value.dump(indent + 2);
		return ret;
	}
}
