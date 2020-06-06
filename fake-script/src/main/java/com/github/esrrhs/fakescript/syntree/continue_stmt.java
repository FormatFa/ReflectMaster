package com.github.esrrhs.fakescript.syntree;

public class continue_stmt extends syntree_node
{
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_continue;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[continue]:\n";
		return ret;
	}
}
