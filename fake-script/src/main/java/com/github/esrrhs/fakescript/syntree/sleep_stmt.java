package com.github.esrrhs.fakescript.syntree;

public class sleep_stmt extends syntree_node
{
	public syntree_node m_time;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_sleep;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[sleep]:\n";
		ret += gentab(indent + 1);
		ret += "[time]:\n";
		ret += m_time.dump(indent + 2);
		return ret;
	}
}
