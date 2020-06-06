package com.github.esrrhs.fakescript.syntree;

public class func_desc_node extends syntree_node
{
	public String m_funcname;
	public func_desc_arglist_node m_arglist;
	public block_node m_block;
	public int m_endline;
	
	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_func_desc;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		ret += "[func_desc]:";
		ret += m_funcname;
		ret += "\n";
		if (m_arglist != null)
		{
			ret += m_arglist.dump(indent + 1);
		}
		if (m_block != null)
		{
			ret += m_block.dump(indent + 1);
		}
		return ret;
	}
}
