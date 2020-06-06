package com.github.esrrhs.fakescript.syntree;

public class switch_case_node extends syntree_node
{
	public syntree_node m_cmp;
	public syntree_node m_block;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_switch_case_node;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[case]:\n";
		ret += gentab(indent + 1);
		ret += "[cmp]:\n";
		ret += m_cmp.dump(indent + 2);
		ret += gentab(indent + 1);
		if (m_block != null)
		{
			ret += "[block]:\n";
			ret += m_block.dump(indent + 2);
		}
		return ret;
	}
}
