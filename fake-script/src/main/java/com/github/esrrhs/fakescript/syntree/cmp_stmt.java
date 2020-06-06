package com.github.esrrhs.fakescript.syntree;

public class cmp_stmt extends syntree_node
{
	public String m_cmp;
	public syntree_node m_left;
	public syntree_node m_right;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_cmp_stmt;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[cmp]:";
		ret += m_cmp;
		ret += "\n";
		if (m_left != null)
		{
			ret += gentab(indent + 1);
			ret += "[left]:\n";
			ret += m_left.dump(indent + 2);
		}
		if (m_right != null)
		{
			ret += gentab(indent + 1);
			ret += "[right]:\n";
			ret += m_right.dump(indent + 2);
		}
		return ret;
	}
}