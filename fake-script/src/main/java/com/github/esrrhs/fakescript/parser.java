package com.github.esrrhs.fakescript;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class parser
{
	private fake m_f;
	private int m_parse_dep = 0;
	private ArrayList<String> m_parsing_file_list = new ArrayList<String>();
	// const永久存在映射
	private HashMap<String, variant> m_constv_map = new HashMap<String, variant>();
	private HashMap<String, Integer> m_constline_map = new HashMap<String, Integer>();

	public parser(fake f)
	{
		m_f = f;
	}

	public parser clonef(fake f)
	{
		parser ret = new parser(f);
		ret.m_constv_map = (HashMap<String, variant>) this.m_constv_map.clone();
		ret.m_constline_map = (HashMap<String, Integer>) this.m_constline_map.clone();
		return ret;
	}

	public void clear()
	{
		m_parse_dep = 0;
		m_parsing_file_list.clear();
	}

	public void reg_const_define(String constname, variant v, int lineno)
	{
		m_constv_map.put(constname, v);
		m_constline_map.put(constname, lineno);
	}

	public variant get_const_define(String constname)
	{
		return m_constv_map.get(constname);
	}

	public int get_const_define_lineno(String constname)
	{
		return m_constline_map.get(constname);
	}

	public boolean parsestr(String str)
	{

		// 解析语法
		Reader reader = new java.io.StringReader(str);

		Yylex yylexer = new Yylex(reader);
		yylexer.set_fake(m_f);
		mybison mbs = new mybison(m_f, yylexer);
		mbs.set_filename("");
		yylexer.set_mybison(mbs);

		YYParser yyparser = new YYParser(yylexer);

		try
		{
			if (!yyparser.parse())
			{
				types.seterror(m_f, "", mbs.get_error_line(), "", "parse %s file fail for reason : %s", "",
						mbs.get_error());
				return false;
			}
		}
		catch (Exception e)
		{
			types.seterror(m_f, "", fk.getcurline(m_f), fk.getcurfunc(m_f),
					"parse " + "" + " fail " + types.show_exception(e));
			return false;
		}

		// 编译
		try
		{
			compiler mc = new compiler(m_f, mbs);

            if (!mc.compile())
			{
				return false;
			}
		}
		catch (Exception e)
		{
			types.seterror(m_f, "", fk.getcurline(m_f), fk.getcurfunc(m_f),
					"compiler " + "" + " fail " + types.show_exception(e));
			return false;
		}

		types.log(m_f, "parse str OK");

		return true;
	}

	public boolean parse(String filename)
	{
		m_parse_dep++;

		// 检查深度
		if (m_parse_dep >= m_f.cfg.include_deps)
		{
			types.seterror(m_f, filename, 0, "", "parse " + filename + " file too deep " + m_parse_dep);
			return false;
		}

		// 检查当前文件是否在解析中
		if (is_parsing(filename))
		{
			types.seterror(m_f, filename, 0, "",
					"already parsing " + filename + " file...include list \n" + get_parsing_file_list());
			return false;
		}

		// 加入
		m_parsing_file_list.add(filename);

		// 输入源文件
		String content = read_file(filename);
		if (content.isEmpty())
		{
			return false;
		}

		// 解析语法
		Reader reader = new java.io.StringReader(content);

		Yylex yylexer = new Yylex(reader);
		yylexer.set_fake(m_f);
		mybison mbs = new mybison(m_f, yylexer);
		mbs.set_filename(filename);
		yylexer.set_mybison(mbs);

		YYParser yyparser = new YYParser(yylexer);

		try
		{
			if (!yyparser.parse())
			{
				types.seterror(m_f, filename, yylexer.get_line() + 1, fk.getcurfunc(m_f),
						"parse %s file fail for reason : %s", filename, mbs.get_error());
				return false;
			}
		}
		catch (Exception e)
		{
			types.seterror(m_f, filename, yylexer.get_line() + 1, fk.getcurfunc(m_f),
					"parse " + filename + " fail " + types.show_exception(e));
			return false;
		}

		// 解析前置文件
		for (int i = 0; i < (int) mbs.get_include_list().size(); i++)
		{
			String name = mbs.get_include_list().get(i);
			if (!parse_include(filename, name))
			{
				return false;
			}
		}

		// 编译
		try
		{
			compiler mc = new compiler(m_f, mbs);
			if (!mc.compile())
			{
				return false;
			}
		}
		catch (Exception e)
		{
			types.seterror(m_f, filename, fk.getcurline(m_f), fk.getcurfunc(m_f),
					"compiler " + filename + " fail " + types.show_exception(e));
			return false;
		}

		// 弹出
		m_parsing_file_list.remove(m_parsing_file_list.size() - 1);

		types.log(m_f, "parse " + filename + " OK");

		return true;
	}

	private String read_file(String filename)
	{
		File file = new File(filename);
		if (!file.isFile() || !file.exists())
		{
			types.seterror(m_f, filename, 0, "", "open " + filename + " fail");
			return "";
		}

		try
		{
			String ret = "";
			String encoding = "utf-8";

			Reader reader = new InputStreamReader(new FileInputStream(file), encoding);
			BufferedReader bufferedReader = new BufferedReader(reader);
			char[] readbuff = new char[10];
			while (bufferedReader.read(readbuff) != -1)
			{
				ret += String.valueOf(readbuff);
				Arrays.fill(readbuff, '\0');
			}
			reader.close();

			types.log(m_f, ret);

			return ret;
		}
		catch (Exception e)
		{
			types.seterror(m_f, filename, 0, "", "read " + filename + " fail " + types.show_exception(e));
			return "";
		}
	}

	private boolean is_parsing(String filename)
	{
		boolean ret = false;
		for (int i = 0; i < (int) m_parsing_file_list.size(); i++)
		{
			if (m_parsing_file_list.get(i) == filename)
			{
				return true;
			}
		}
		return ret;
	}

	private String get_parsing_file_list()
	{
		String ret = "";
		for (int i = 0; i < (int) m_parsing_file_list.size(); i++)
		{
			ret += m_parsing_file_list.get(i);
			ret += "\n";
		}
		return ret;
	}

	private boolean parse_include(String srcname, String includename)
	{
		// 拼include的名字
		String dir = srcname;
		dir = dir.replace('\\', '/');
		int pos = dir.lastIndexOf('/');
		if (pos == -1)
		{
			dir = "";
		}
		else
		{
			dir = dir.substring(0, pos + 1);
		}
		dir += includename;

		// 解析
		if (!parse(dir))
		{
			return false;
		}
		return true;
	}

}
