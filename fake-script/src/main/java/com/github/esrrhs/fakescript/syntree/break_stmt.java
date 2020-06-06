package com.github.esrrhs.fakescript.syntree;

public class break_stmt extends syntree_node
{	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_break;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[break]:\n";
		return ret;
	}
}