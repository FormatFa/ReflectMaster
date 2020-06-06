package com.github.esrrhs.fakescript.syntree;

public class if_stmt extends syntree_node
{
	public cmp_stmt m_cmp;
	public block_node m_block;
	public elseif_stmt_list m_elseifs;
	public else_stmt m_elses;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_if_stmt;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[if]:\n";
		ret += m_cmp.dump(indent + 1);
		if (m_block != null)
		{
			ret += m_block.dump(indent + 1);
		}
		if (m_elseifs != null)
		{
			ret += m_elseifs.dump(indent + 1);
		}
		if (m_elses != null)
		{
			ret += m_elses.dump(indent);
		}
		return ret;
	}
}
