package com.github.esrrhs.fakescript.syntree;

public class math_expr_node extends syntree_node
{
	public String m_oper;
	public syntree_node m_left;
	public syntree_node m_right;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_math_expr;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[math_expr]:";
		ret += m_oper;
		ret += "\n";
		ret += gentab(indent + 1);
		ret += "[left]:\n";
		ret += m_left.dump(indent + 2);
		ret += gentab(indent + 1);
		ret += "[right]:\n";
		ret += m_right.dump(indent + 2);
		return ret;
	}
}
