package com.github.esrrhs.fakescript.syntree;

public class elseif_stmt extends syntree_node
{
	public cmp_stmt m_cmp;
	public syntree_node m_block;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_elseif_stmt;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[elseif_stmt]:\n";
		ret += m_cmp.dump(indent + 1);
		if (m_block != null)
		{
			ret += m_block.dump(indent + 1);
		}
		return ret;
	}
}
