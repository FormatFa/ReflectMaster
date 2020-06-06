package com.github.esrrhs.fakescript;

import java.lang.reflect.Method;

class bifunc
{
	Method m_m;
	
	public void call(fake f, interpreter inter) throws Exception
	{
		m_m.invoke(null, f, inter);
	}
}
