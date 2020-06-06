package com.github.esrrhs.fakescript.syntree;

public class return_stmt extends syntree_node
{
	public return_value_list_node m_returnlist;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_return_stmt;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[return]:";
		ret += "\n";
		if (m_returnlist != null)
		{
			ret += m_returnlist.dump(indent + 1);
		}
		return ret;
	}
}
