package com.github.esrrhs.fakescript.syntree;

public class else_stmt extends syntree_node
{
	public block_node m_block;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_else_stmt;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[else]:\n";
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
