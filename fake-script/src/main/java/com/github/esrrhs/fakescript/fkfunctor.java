package com.github.esrrhs.fakescript;

import java.lang.reflect.Method;

class fkmethod
{
	Method m_m;
	Class<?>[] m_param;
	Class<?> m_ret;
}

class fkfunctor
{
	String m_c;
	boolean m_is_staic;
	fkmethod[] m_ms;

	public void call(fake f) throws Exception
	{
		Object c = null;
		if (!m_is_staic)
		{
			c = fk.pspop(f);
			if (c == null)
			{
				throw new Exception("call bind class " + m_c.toString() + " " + m_ms[0].toString() + ", ref is null");
			}
			// 检查类型
			if (!c.getClass().getName().equals(m_c))
			{
				throw new Exception("call bind class " + m_c.toString() + " " + m_ms[0].toString()
						+ ", diff class type, give " + c.getClass().toString());
			}
		}

		fkmethod dest = null;
		for (fkmethod fm : m_ms)
		{
			if (f.ps.size() == fm.m_param.length)
			{
				boolean equal = true;
				for (int i = 0; i < f.ps.size(); i++)
				{
					if (!fk.canTrans(fk.psget(f, i), fm.m_param[i]))
					{
						equal = false;
					}
				}

				if (equal)
				{
					dest = fm;
					break;
				}
			}
		}

		// 检查param数目对不对
		if (dest == null)
		{
			String tmp = "";
			for (int i = f.ps.size() - 1; i >= 0; i--)
			{
				tmp += fk.psget(f, i) != null ? fk.psget(f, i).getClass().getName() + " " : "null";
			}
			throw new Exception(
					"call bind class " + m_c.toString() + " " + m_ms[0].toString() + ", param not match, give "
							+ f.ps.size() + " need " + m_ms[0].m_param.length + ", give type " + tmp);
		}

		// 参数
		Object[] param = new Object[dest.m_param.length];
		for (int i = dest.m_param.length - 1; i >= 0; i--)
		{
			param[i] = fk.trans(fk.pspop(f), dest.m_param[i]);
		}

		Object ret = dest.m_m.invoke(c, param);

		// 检查类型
		if (ret != null && ret.getClass().isInstance(dest.m_ret))
		{
			throw new Exception("call bind class " + m_c.toString() + " " + dest.m_m.toString()
					+ ", diff ret type, give " + ret.getClass().toString());
		}

		fk.pspush(f, ret);
	}
}
