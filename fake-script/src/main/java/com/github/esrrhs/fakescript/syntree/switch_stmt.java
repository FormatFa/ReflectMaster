package com.github.esrrhs.fakescript.syntree;

public class switch_stmt extends syntree_node
{
	public syntree_node m_cmp;
	public syntree_node m_caselist;
	public syntree_node m_def;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_switch_stmt;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[switch]:\n";
		ret += gentab(indent + 1);
		ret += "[cmp]:\n";
		ret += m_cmp.dump(indent + 2);
		ret += gentab(indent + 1);
		ret += "[case list]:\n";
		ret += m_caselist.dump(indent + 2);
		ret += gentab(indent + 1);
		if (m_def != null)
		{
			ret += "[default]:\n";
			ret += m_def.dump(indent + 2);
		}
		return ret;
	}
}
