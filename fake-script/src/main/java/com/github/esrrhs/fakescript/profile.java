package com.github.esrrhs.fakescript;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

class profile
{
	private fake m_f;
	private boolean m_isopen;
	private HashMap<String, profilefuncele> m_map = new HashMap<String, profilefuncele>();
	private int[] m_codetype = new int[command.OPCODE_MAX];

	public profile(fake f)
	{
		m_f = f;
	}

	public void open()
	{
		m_isopen = true;
	}

	public void close()
	{
		m_isopen = false;
	}

	public boolean isopen()
	{
		return m_isopen;
	}

	public void add_func_sample(String func, long calltime)
	{
		profilefuncele p = m_map.get(func);
		if (p == null)
		{
			profilefuncele tmp = new profilefuncele();
			tmp.m_callnum = 1;
			tmp.m_calltime = calltime;
			m_map.put(func, tmp);
			return;
		}

		p.m_callnum++;
		p.m_calltime += calltime;
	}

	public String dump()
	{
		ArrayList<Map.Entry<String, profilefuncele>> sortelevec = new ArrayList<Map.Entry<String, profilefuncele>>();
		for (Map.Entry<String, profilefuncele> e : m_map.entrySet())
		{
			sortelevec.add(e);
		}

		Collections.sort(sortelevec, new Comparator<Map.Entry<String, profilefuncele>>() {
			public int compare(Map.Entry<String, profilefuncele> arg0, Map.Entry<String, profilefuncele> arg1)
			{
				Long a0 = arg0.getValue().m_calltime;
				Long a1 = arg1.getValue().m_calltime;
				return a1.compareTo(a0);
			}
		});

		String dumpstr = "";

		int wraplen = 30;

		dumpstr += "Call Func:\n";
		dumpstr += "\t";
		dumpstr += fix_string_wrap("Func", wraplen);
		dumpstr += fix_string_wrap("Calls", wraplen);
		dumpstr += fix_string_wrap("TotalTime(ms)", wraplen);
		dumpstr += fix_string_wrap("PerCallTime(ms)", wraplen);
		dumpstr += "\n";
		for (int i = 0; i < (int) sortelevec.size(); i++)
		{
			profilefuncele ele = sortelevec.get(i).getValue();
			dumpstr += "\t";
			dumpstr += fix_string_wrap(sortelevec.get(i).getKey(), wraplen);
			dumpstr += fix_string_wrap("" + ele.m_callnum, wraplen);
			dumpstr += fix_string_wrap("" + ele.m_calltime, wraplen);
			dumpstr += fix_string_wrap("" + (ele.m_callnum != 0 ? ele.m_calltime / ele.m_callnum : 0), wraplen);
			dumpstr += "\n";
		}

		dumpstr += "Code Num:\n";
		for (int i = 0; i < command.OPCODE_MAX; i++)
		{
			dumpstr += "\t";
			dumpstr += types.OpCodeStr(i);
			for (int j = 0; j < (int) (20 - types.OpCodeStr(i).length()); j++)
			{
				dumpstr += " ";
			}
			dumpstr += m_codetype[i];
			dumpstr += "\n";
		}

		return dumpstr;
	}

	public void add_code_sample(int code)
	{
		m_codetype[code]++;
	}

	private String fix_string_wrap(String str, int len)
	{
		String ret = str;
		if ((int) ret.length() < len)
		{
			for (int i = 0; i < len - ret.length() + 1; i++)
			{
				ret += " ";
			}
		}
		return ret;
	}
}
