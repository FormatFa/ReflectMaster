package com.github.esrrhs.fakescript.syntree;

public class for_loop_stmt extends syntree_node
{
	public syntree_node m_var;
	public syntree_node m_begin;
	public syntree_node m_end;
	public syntree_node m_add;
	public block_node m_block;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_for_loop_stmt;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[for_loop_stmt]:\n";
		ret += gentab(indent + 1);
		ret += "[var]:\n";
		ret += m_var.dump(indent + 2);
		ret += gentab(indent + 1);
		ret += "[begin]:\n";
		ret += m_begin.dump(indent + 2);
		ret += gentab(indent + 1);
		ret += "[end]:\n";
		ret += m_end.dump(indent + 2);
		ret += gentab(indent + 1);
		ret += "[add]:\n";
		ret += m_add.dump(indent + 2);
		ret += gentab(indent + 1);
		if (m_block != null)
		{
			ret += "[block]:\n";
			ret += m_block.dump(indent + 2);
		}
		return ret;
	}
}
