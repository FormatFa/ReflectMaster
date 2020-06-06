package com.github.esrrhs.fakescript.syntree;

public class function_call_node extends syntree_node
{
	public boolean m_fakecall;
	public boolean m_classmem_call;
	public String m_fuc;
	public function_call_arglist_node m_arglist;
	public syntree_node m_prefuc;

	@Override
	public esyntreetype gettype()
	{
		return esyntreetype.est_function_call;
	}

	@Override
	public String dump(int indent)
	{
		String ret = "";
		ret += gentab(indent);
		if (m_fakecall)
		{
			ret += "[func_fake_call]:";
		}
		else if (m_classmem_call)
		{
			ret += "[class_mem_call]:";
		}
		else
		{
			ret += "[func_call]:";
		}
		if (m_prefuc != null)
		{
			ret += m_prefuc.dump(1);
		}
		else
		{
			ret += m_fuc;
		}
		ret += "\n";
		if (m_arglist != null)
		{
			ret += m_arglist.dump(indent + 1);
		}
		return ret;
	}
}