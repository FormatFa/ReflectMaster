package com.github.esrrhs.fakescript.syntree;

public class for_stmt extends syntree_node
{
	public block_node m_beginblock;
	public cmp_stmt m_cmp;
	public block_node m_endblock;
	public block_node m_block;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_for_stmt;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[for]:\n";
		ret += gentab(indent + 1);
		ret += "[beginblock]:\n";
		if (m_beginblock != null)
		{
			ret += m_beginblock.dump(indent + 2);
		}
		ret += gentab(indent + 1);
		ret += "[cmp]:\n";
		if (m_cmp != null)
		{
			ret += m_cmp.dump(indent + 2);
		}
		ret += gentab(indent + 1);
		ret += "[endblock]:\n";
		if (m_endblock != null)
		{
			ret += m_endblock.dump(indent + 2);
		}
		ret += gentab(indent + 1);
		ret += "[block]:\n";
		if (m_block != null)
		{
			ret += m_block.dump(indent + 2);
		}
		return ret;
	}
}
