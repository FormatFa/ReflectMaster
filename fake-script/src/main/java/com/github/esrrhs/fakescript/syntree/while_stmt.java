package com.github.esrrhs.fakescript.syntree;

public class while_stmt extends syntree_node
{
	public cmp_stmt m_cmp;
	public block_node m_block;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_while_stmt;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[while]:\n";
		ret += m_cmp.dump(indent + 1);
		if (m_block != null)
		{
			ret += m_block.dump(indent + 1);
		}
		else
		{
			ret += gentab(indent + 1);
			ret += "nil\n";
		}
		return ret;
	}
}