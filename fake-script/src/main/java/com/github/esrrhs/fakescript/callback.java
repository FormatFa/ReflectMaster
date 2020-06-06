package com.github.esrrhs.fakescript;

public interface callback
{
	public void on_error(fake f, String file, int lineno, String func,
                         String str);
	
	public void on_print(fake f, String str);
}
