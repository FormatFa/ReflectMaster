package com.github.esrrhs.fakescript.syntree;

public class container_get_node extends syntree_node
{
	public String m_container;
	public syntree_node m_key;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_container_get;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[container_get]:\n";

		ret += gentab(indent + 1);
		ret += "[container]:";
		ret += m_container;
		ret += "\n";

		ret += gentab(indent + 1);
		ret += "[key]:\n";
		ret += m_key.dump(indent + 2);
		return ret;
	}
}
