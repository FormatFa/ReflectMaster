package com.github.esrrhs.fakescript;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class fk
{
	/**
	 * 版本号
	 */
	public static final String version = "1.0.3";

	// 节省内存
	protected static final HashMap<String, variant> regName = new HashMap<String, variant>();
	protected static final HashMap<String, fkfunctor> regFunctor = new HashMap<String, fkfunctor>();
	protected static final HashMap<String, bifunc> regBindFunc = new HashMap<String, bifunc>();

	/**
	 * 创建fake对象
	 * <p>
	 * fake为上下文环境<br>
	 * 所有接口在fake中执行
	 * 
	 * @param config
	 *            具体的参数
	 * @return fake对象
	 */
	public static fake newfake(fkconfig config)
	{
		fake f = new fake();
		if (config != null)
		{
			f.cfg = config;
		}
		return f;
	}

	/**
	 * 复制fake对象
	 * <p>
	 * fake为上下文环境<br>
	 * 所有接口在fake中执行
	 *
	 * @param f
	 *            fake对象
	 * @return fake对象
	 */
	public static fake clone(fake f)
	{
		return f.clonef();
	}

	/**
	 * 上一次执行是否失败
	 * <p>
	 * fake为上下文环境<br>
	 * 所有接口在fake中执行
	 *
	 * @param f
	 *            fake对象
	 * @return 是否失败
	 */
	public static boolean error(fake f)
	{
		return f.error;
	}

	/**
	 * 绑定java函数
	 * <p>
	 * 遍历package下所有类<br>
	 * 绑定标记fakescript的函数
	 * 
	 * @param f
	 *            上下文环境
	 * 
	 * @param packagename
	 *            包的名字
	 *
	 */
	public static void reg(fake f, String packagename)
	{
		List<Class<?>> tmp = packagehelper.getClasses(f, packagename);
		for (Class<?> c : tmp)
		{
			Method[] ms = c.getMethods();
			for (Method m : ms)
			{
				if (m.isAnnotationPresent(fakescript.class))
				{
					fakescript fn = (fakescript) m.getAnnotation(fakescript.class);

					String name = fn.name();
					if (name.equals(""))
					{
						name = m.getName();
					}

					reg_method(f, name, c, m);
				}
			}
		}

	}

	/**
	 * 绑定java函数
	 * <p>
	 * 遍历package下所有类<br>
	 * 绑定所有的函数
	 * 
	 * @param f
	 *            上下文环境
	 * 
	 * @param packagename
	 *            包的名字
	 *
	 */
	public static void regall(fake f, String packagename)
	{
		List<Class<?>> tmp = packagehelper.getClasses(f, packagename);
		for (Class<?> c : tmp)
		{
			regclass(f, c);
		}
	}

	/**
	 * 绑定java函数
	 * <p>
	 * 遍历类下所有函数<br>
	 * 绑定类所有的函数
	 * 
	 * @param f
	 *            上下文环境
	 * 
	 * @param c
	 *            class
	 *
	 */
	public static void regclass(fake f, Class<?> c)
	{
		Class<?>[] cs = c.getDeclaredClasses();
		for (Class<?> cc : cs)
		{
			regclass(f, cc);
		}

		Method[] ms = c.getMethods();
		for (Method m : ms)
		{
			String name = m.getName();
			if (m.isAnnotationPresent(fakescript.class))
			{
				fakescript fn = (fakescript) m.getAnnotation(fakescript.class);
				if (fn != null && !fn.name().isEmpty())
				{
					name = fn.name();
				}
			}

			reg_method(f, name, c, m);
		}
	}

	/**
	 * 设置回调函数
	 * <p>
	 * 如错误处理<br>
	 * 打印函数
	 * 
	 * @param f
	 *            上下文环境
	 * 
	 * @param cb
	 *            回调类
	 *
	 */
	public static void set_callback(fake f, callback cb)
	{
		f.cb = cb;
	}

	/**
	 * 解析文件
	 * <p>
	 * 解析脚本<br>
	 * 编译成字节码
	 * 
	 * @param f
	 *            上下文环境
	 * 
	 * @param filename
	 *            文件名
	 * 
	 * @return 无
	 */
	public static boolean parse(fake f, String filename)
	{
		f.pa.clear();
		return f.pa.parse(filename);
	}

	/**
	 * 解析代码
	 * <p>
	 * 解析文本字符串代码<br>
	 * 编译成字节码
	 * 
	 * @param f
	 *            上下文环境
	 * 
	 * @param str
	 *            文件名
	 * 
	 * @return 无
	 */
	public static boolean parsestr(fake f, String str)
	{
		f.pa.clear();
		return f.pa.parsestr(str);
	}

	/**
	 * 执行脚本
	 * <p>
	 * 执行指定脚本函数<br>
	 * 结果通过Object返回，注意内部数值都是用double，所以转换时需要注意下
	 * 
	 * @param f
	 *            上下文环境
	 * 
	 * @param func
	 *            函数名
	 * 
	 * @param args
	 *            参数
	 * 
	 * @return 无
	 */
	public static Object run(fake f, String func, Object... args)
	{
		psclear(f);
		for (Object arg : args)
		{
			pspush(f, arg);
		}
		runps(f, func);
		return pspop(f);
	}

	public static Object debugrun(fake f, String func, Object... args)
	{
		psclear(f);
		for (Object arg : args)
		{
			pspush(f, arg);
		}
		openstepmod(f);
		rundebugps(f, func);
		closestepmod(f);
		return pspop(f);
	}

	public static void openstepmod(fake f)
	{
		f.rn.set_stepmod(true);
	}

	public static void closestepmod(fake f)
	{
		f.rn.set_stepmod(false);
	}

	/**
	 * 获取当前文件
	 * <p>
	 * 
	 * @param f
	 *            上下文环境
	 * 
	 * @return 无
	 */
	public static String getcurfile(fake f)
	{
		processor p = f.rn.cur_pro();
		if (p != null && p.get_curroutine() != null)
		{
			return p.get_curroutine().get_interpreter().get_running_file_name();
		}
		return "nil";
	}

	/**
	 * 获取当前文件行号
	 * <p>
	 * 
	 * @param f
	 *            上下文环境
	 * 
	 * @return 无
	 */
	public static int getcurline(fake f)
	{
		processor p = f.rn.cur_pro();
		if (p != null && p.get_curroutine() != null)
		{
			return p.get_curroutine().get_interpreter().get_running_file_line();
		}
		return 0;
	}

	/**
	 * 获取当前函数
	 * <p>
	 * 
	 * @param f
	 *            上下文环境
	 * 
	 * @return 无
	 */
	public static String getcurfunc(fake f)
	{
		processor p = f.rn.cur_pro();
		if (p != null && p.get_curroutine() != null)
		{
			return p.get_curroutine().get_interpreter().get_running_func_name();
		}
		return "nil";
	}

	/**
	 * 获取当前调用堆栈
	 * <p>
	 * 
	 * @param f
	 *            上下文环境
	 * 
	 * @return 无
	 */
	public static String getcurcallstack(fake f)
	{
		processor p = f.rn.cur_pro();
		if (p != null && p.get_curroutine() != null)
		{
			return p.get_curroutine().get_interpreter().get_running_call_stack();
		}
		return "nil";
	}

	/**
	 * 获取当前协程信息
	 * <p>
	 * 
	 * @param f
	 *            上下文环境
	 * 
	 * @return 无
	 */
	public static String getcurroutine(fake f)
	{
		processor p = f.rn.cur_pro();
		if (p != null && p.get_curroutine() != null)
		{
			return p.get_routine_info();
		}
		return "nil";
	}

	public static int getcurroutineid(fake f)
	{
		processor p = f.rn.cur_pro();
		if (p != null && p.get_curroutine() != null)
		{
			return p.get_curroutine().get_id();
		}
		return 0;
	}

	public static String getcurvaiantbyroutinebyframe(fake f, int rid, int frame, String name, int line)
	{
		processor p = f.rn.cur_pro();
		if (p != null && p.get_routine_by_id(rid) != null)
		{
			warper ret = new warper(new String());
			warper retline = new warper(new Integer(0));
			p.get_routine_by_id(rid).get_interpreter().get_running_vaiant(frame, name, line, ret, retline);
			return (String) ret.d;
		}
		return "";
	}

	public static void setcurvaiantbyroutinebyframe(fake f, int rid, int frame, String name, String value, int line)
	{
		processor p = f.rn.cur_pro();
		if (p != null && p.get_routine_by_id(rid) != null)
		{
			p.get_routine_by_id(rid).get_interpreter().set_running_vaiant(frame, name, line, value);
		}
	}

	public static String getcurfuncbyroutinebyframe(fake f, int rid, int frame)
	{
		processor p = f.rn.cur_pro();
		if (p != null && p.get_routine_by_id(rid) != null)
		{
			warper stackinfo = new warper(new String());
			warper func = new warper(new String());
			warper file = new warper(new String());
			warper line = new warper(new Integer(0));
			p.get_routine_by_id(rid).get_interpreter().get_running_call_stack_frame_info(frame, stackinfo, func, file,
					line);
			return (String) func.d;
		}
		return "nil";
	}

	public static String getcurroutinebyid(fake f, int rid)
	{
		processor p = f.rn.cur_pro();
		if (p != null)
		{
			return p.get_routine_info_by_id(rid);
		}
		return "";
	}

	public static int getcurcallstacklength(fake f)
	{
		processor p = f.rn.cur_pro();
		if (p != null && p.get_curroutine() != null)
		{
			return p.get_curroutine().get_interpreter().get_running_call_stack_length();
		}
		return 0;
	}

	public static String getcurfilebyroutinebyframe(fake f, int rid, int frame)
	{
		processor p = f.rn.cur_pro();
		if (p != null && p.get_routine_by_id(rid) != null)
		{
			warper stackinfo = new warper(new String());
			warper func = new warper(new String());
			warper file = new warper(new String());
			warper line = new warper(new Integer(0));
			p.get_routine_by_id(rid).get_interpreter().get_running_call_stack_frame_info(frame, stackinfo, func, file,
					line);
			return (String) file.d;
		}
		return "nil";
	}

	public static int getcurlinebyroutinebyframe(fake f, int rid, int frame)
	{
		processor p = f.rn.cur_pro();
		if (p != null && p.get_routine_by_id(rid) != null)
		{
			warper stackinfo = new warper(new String());
			warper func = new warper(new String());
			warper file = new warper(new String());
			warper line = new warper(new Integer(0));
			p.get_routine_by_id(rid).get_interpreter().get_running_call_stack_frame_info(frame, stackinfo, func, file,
					line);
			return (int) (Integer) line.d;
		}
		return 0;
	}

	public static String getcurcallstackbyroutinebyframe(fake f, int rid, int frame)
	{
		processor p = f.rn.cur_pro();
		if (p != null && p.get_routine_by_id(rid) != null)
		{
			warper stackinfo = new warper(new String());
			warper func = new warper(new String());
			warper file = new warper(new String());
			warper line = new warper(new Integer(0));
			p.get_routine_by_id(rid).get_interpreter().get_running_call_stack_frame_info(frame, stackinfo, func, file,
					line);
			return (String) stackinfo.d;
		}
		return "nil";
	}

	public static String getfuncfile(fake f, String func)
	{
		variant funcv = new variant();
		funcv.set_string(func);
		funcunion ff = f.fm.get_func(funcv);
		if (ff != null && ff.m_havefb)
		{
			return ff.m_fb.m_filename;
		}
		return "";
	}

	public static int getfuncstartline(fake f, String func)
	{
		variant funcv = new variant();
		funcv.set_string(func);
		funcunion ff = f.fm.get_func(funcv);
		if (ff != null && ff.m_havefb)
		{
			return ff.m_fb.get_binary_lineno(0);
		}
		return 0;
	}

	public static int getcurroutinenum(fake f)
	{
		processor p = f.rn.cur_pro();
		if (p != null)
		{
			return p.get_routine_num();
		}
		return 0;
	}

	public static int getroutineidbyindex(fake f, int index)
	{
		processor p = f.rn.cur_pro();
		if (p != null && p.get_routine_by_index(index) != null)
		{
			return p.get_routine_by_index(index).get_id();
		}
		return 0;
	}

	public static String getcurroutinebyindex(fake f, int index)
	{
		processor p = f.rn.cur_pro();
		if (p != null)
		{
			return p.get_routine_info_by_index(index);
		}
		return "";
	}

	public static int getcurcallstacklengthbyroutine(fake f, int rid)
	{
		processor p = f.rn.cur_pro();
		if (p != null && p.get_routine_by_id(rid) != null)
		{
			return p.get_routine_by_id(rid).get_interpreter().get_running_call_stack_length();
		}
		return 0;
	}

	public static int getcurbytecodeposbyroutine(fake f, int rid)
	{
		processor p = f.rn.cur_pro();
		if (p != null && p.get_routine_by_id(rid) != null)
		{
			return p.get_routine_by_id(rid).get_interpreter().get_running_bytecode_pos();
		}
		return -1;
	}

	public static String dumpfunc(fake f, String func, int pos)
	{
		return f.bin.dump(func, pos);
	}

	public static boolean ishaveroutine(fake f, int rid)
	{
		processor p = f.rn.cur_pro();
		if (p != null)
		{
			return p.get_routine_by_id(rid) != null;
		}
		return false;
	}

	public static String getfilecode(fake f, String filename, int line)
	{
		if (filename.isEmpty() || line <= 0)
		{
			return "";
		}

		try
		{
			String encoding = "utf-8";
			Reader reader = new InputStreamReader(new FileInputStream(filename), encoding);
			BufferedReader bufferedReader = new BufferedReader(reader);

			int i = 0;
			String ret = "";
			while (true)
			{
				String str = bufferedReader.readLine();
				if (str == null)
				{
					break;
				}

				i++;
				if (i >= line)
				{
					ret = str;
					break;
				}
			}
			return ret;
		}
		catch (Exception e)
		{
			return e.getMessage();
		}
	}

	/**
	 * 打开基本的内置函数
	 * <p>
	 * 
	 * @param f
	 *            上下文环境
	 *
	 */
	public static void openbaselib(fake f)
	{
		f.bif.openbasefunc();
	}

	/**
	 * 是否有某个函数
	 * <p>
	 * 注意类的非静态成员函数在绑定的时候会在前面加上类名<br>
	 * 如test.A类的aaa函数，他的实际函数名是test.Aaaa
	 * 
	 * @param f
	 *            上下文环境
	 * 
	 * @param name
	 *            函数名
	 * 
	 * @return 无
	 */
	public static boolean isfunc(fake f, String name)
	{
		variant funcv = new variant();
		funcv.set_string(name);
		return f.fm.get_func(funcv) != null;
	}

	/**
	 * 打开性能监控
	 * <p>
	 * 
	 * @param f
	 *            上下文环境
	 *
	 */
	public static void openprofile(fake f)
	{
		f.pf.open();
	}

	/**
	 * 关闭性能监控
	 * <p>
	 * 
	 * @param f
	 *            上下文环境
	 *
	 */
	public static void closeprofile(fake f)
	{
		f.pf.close();
	}

	/**
	 * 打开优化
	 * <p>
	 * 
	 * @param f
	 *            上下文环境
	 *
	 */
	public static void openoptimize(fake f)
	{
		f.opt.open();
	}

	/**
	 * 关闭优化
	 * <p>
	 * 
	 * @param f
	 *            上下文环境
	 *
	 */
	public static void closeoptimize(fake f)
	{
		f.opt.close();
	}

	/**
	 * 打印性能监控数据
	 * <p>
	 * 
	 * @param f
	 *            上下文环境
	 * 
	 * @return dump
	 */
	public static String dumpprofile(fake f)
	{
		return f.pf.dump();
	}

	protected static void psclear(fake f)
	{
		f.ps.clear();
	}

	protected static void pspush(fake f, Object arg)
	{
		variant v = f.ps.push_and_get();

		if (arg == null)
		{
			v.set_pointer(null);
			return;
		}

		Class<? extends Object> c = arg.getClass();
		if (c == Byte.class)
		{
			Byte b = (Byte) arg;
			v.set_real(b);
		}
		else if (c == Short.class)
		{
			Short b = (Short) arg;
			v.set_real(b);
		}
		else if (c == Integer.class)
		{
			Integer b = (Integer) arg;
			v.set_real(b);
		}
		else if (c == Long.class)
		{
			long b = (long) (Long) arg;
			v.set_uuid(b);
		}
		else if (c == Float.class)
		{
			Float b = (Float) arg;
			v.set_real(b);
		}
		else if (c == Double.class)
		{
			Double b = (Double) arg;
			v.set_real(b);
		}
		else if (c == Boolean.class)
		{
			Boolean b = (Boolean) arg;
			v.set_real(b ? 1 : 0);
		}
		else if (c == String.class)
		{
			String b = (String) arg;
			v.set_string(b);
		}
		else
		{
			v.set_pointer(arg);
		}
	}

	protected static Object psget(fake f, int i)
	{
		if (f.ps.size() == 0)
		{
			return null;
		}

		variant v = f.ps.get(i);
		if (v.m_type == variant_type.NIL)
		{
			return null;
		}
		else if (v.m_type == variant_type.REAL)
		{
			double b = (double) (Double) v.m_data;
			return b;
		}
		else if (v.m_type == variant_type.STRING)
		{
			String b = (String) v.m_data;
			return b;
		}
		else if (v.m_type == variant_type.POINTER)
		{
			return v.m_data;
		}
		else if (v.m_type == variant_type.UUID)
		{
			long b = (long) (Long) v.m_data;
			return b;
		}
		else
		{
			return null;
		}
	}

	protected static Object pspop(fake f)
	{
		if (f.ps.size() == 0)
		{
			return null;
		}

		variant v = f.ps.pop_and_get();
		if (v.m_type == variant_type.NIL)
		{
			return null;
		}
		else if (v.m_type == variant_type.REAL)
		{
			double b = (double) (Double) v.m_data;
			return b;
		}
		else if (v.m_type == variant_type.STRING)
		{
			String b = (String) v.m_data;
			return b;
		}
		else if (v.m_type == variant_type.POINTER)
		{
			return v.m_data;
		}
		else if (v.m_type == variant_type.UUID)
		{
			long b = (long) (Long) v.m_data;
			return b;
		}
		else
		{
			return null;
		}
	}

	protected static Object trans(Object src, Class<?> c)
	{
		if (src == null)
		{
			return null;
		}

		Class<?> srcc = src.getClass();

		if (c == Byte.class || c == Byte.TYPE)
		{
			if (srcc == Byte.class)
			{
				return (byte) (byte) (Byte) src;
			}
			else if (srcc == Short.class)
			{
				return (byte) (short) (Short) src;
			}
			else if (srcc == Integer.class)
			{
				return (byte) (int) (Integer) src;
			}
			else if (srcc == Long.class)
			{
				return (byte) (long) (Long) src;
			}
			else if (srcc == Float.class)
			{
				return (byte) (float) (Float) src;
			}
			else if (srcc == Double.class)
			{
				return (byte) (double) (Double) src;
			}
			else if (srcc == Boolean.class)
			{
				return (boolean) (Boolean) src ? (byte) 1 : (byte) 0;
			}
			else if (srcc == String.class)
			{
				return Byte.valueOf((String) src);
			}
			else
			{
				return (byte) 0;
			}
		}
		else if (c == Short.class || c == Short.TYPE)
		{
			if (srcc == Byte.class)
			{
				return (short) (byte) (Byte) src;
			}
			else if (srcc == Short.class)
			{
				return (short) (short) (Short) src;
			}
			else if (srcc == Integer.class)
			{
				return (short) (int) (Integer) src;
			}
			else if (srcc == Long.class)
			{
				return (short) (long) (Long) src;
			}
			else if (srcc == Float.class)
			{
				return (short) (float) (Float) src;
			}
			else if (srcc == Double.class)
			{
				return (short) (double) (Double) src;
			}
			else if (srcc == Boolean.class)
			{
				return (boolean) (Boolean) src ? (short) 1 : (short) 0;
			}
			else if (srcc == String.class)
			{
				return Short.valueOf((String) src);
			}
			else
			{
				return (short) 0;
			}
		}
		else if (c == Integer.class || c == Integer.TYPE)
		{
			if (srcc == Byte.class)
			{
				return (int) (byte) (Byte) src;
			}
			else if (srcc == Short.class)
			{
				return (int) (short) (Short) src;
			}
			else if (srcc == Integer.class)
			{
				return (int) (int) (Integer) src;
			}
			else if (srcc == Long.class)
			{
				return (int) (long) (Long) src;
			}
			else if (srcc == Float.class)
			{
				return (int) (float) (Float) src;
			}
			else if (srcc == Double.class)
			{
				return (int) (double) (Double) src;
			}
			else if (srcc == Boolean.class)
			{
				return (boolean) (Boolean) src ? (int) 1 : (int) 0;
			}
			else if (srcc == String.class)
			{
				return Integer.valueOf((String) src);
			}
			else
			{
				return (int) 0;
			}
		}
		else if (c == Long.class || c == Long.TYPE)
		{
			if (srcc == Byte.class)
			{
				return (long) (byte) (Byte) src;
			}
			else if (srcc == Short.class)
			{
				return (long) (short) (Short) src;
			}
			else if (srcc == Integer.class)
			{
				return (long) (int) (Integer) src;
			}
			else if (srcc == Long.class)
			{
				return (long) (long) (Long) src;
			}
			else if (srcc == Float.class)
			{
				return (long) (float) (Float) src;
			}
			else if (srcc == Double.class)
			{
				return (long) (double) (Double) src;
			}
			else if (srcc == Boolean.class)
			{
				return (boolean) (Boolean) src ? (long) 1 : (long) 0;
			}
			else if (srcc == String.class)
			{
				return Long.valueOf((String) src);
			}
			else
			{
				return (long) 0;
			}
		}
		else if (c == Float.class || c == Float.TYPE)
		{
			if (srcc == Byte.class)
			{
				return (float) (byte) (Byte) src;
			}
			else if (srcc == Short.class)
			{
				return (float) (short) (Short) src;
			}
			else if (srcc == Integer.class)
			{
				return (float) (int) (Integer) src;
			}
			else if (srcc == Long.class)
			{
				return (float) (long) (Long) src;
			}
			else if (srcc == Float.class)
			{
				return (float) (float) (Float) src;
			}
			else if (srcc == Double.class)
			{
				return (float) (double) (Double) src;
			}
			else if (srcc == Boolean.class)
			{
				return (boolean) (Boolean) src ? (float) 1 : (float) 0;
			}
			else if (srcc == String.class)
			{
				return Float.valueOf((String) src);
			}
			else
			{
				return (float) 0;
			}
		}
		else if (c == Double.class || c == Double.TYPE)
		{
			if (srcc == Byte.class)
			{
				return (double) (byte) (Byte) src;
			}
			else if (srcc == Short.class)
			{
				return (double) (short) (Short) src;
			}
			else if (srcc == Integer.class)
			{
				return (double) (int) (Integer) src;
			}
			else if (srcc == Long.class)
			{
				return (double) (long) (Long) src;
			}
			else if (srcc == Float.class)
			{
				return (double) (float) (Float) src;
			}
			else if (srcc == Double.class)
			{
				return (double) (double) (Double) src;
			}
			else if (srcc == Boolean.class)
			{
				return (boolean) (Boolean) src ? (double) 1 : (double) 0;
			}
			else if (srcc == String.class)
			{
				return Double.valueOf((String) src);
			}
			else
			{
				return (double) 0;
			}
		}
		else if (c == Boolean.class || c == Boolean.TYPE)
		{
			if (srcc == Byte.class)
			{
				return (byte) (Byte) src != 0;
			}
			else if (srcc == Short.class)
			{
				return (short) (Short) src != 0;
			}
			else if (srcc == Integer.class)
			{
				return (int) (Integer) src != 0;
			}
			else if (srcc == Long.class)
			{
				return (long) (Long) src != 0;
			}
			else if (srcc == Float.class)
			{
				return (float) (Float) src != 0;
			}
			else if (srcc == Double.class)
			{
				return (double) (Double) src != 0;
			}
			else if (srcc == Boolean.class)
			{
				return src;
			}
			else if (srcc == String.class)
			{
				return Integer.valueOf((String) src) != 0;
			}
			else
			{
				return false;
			}
		}
		else if (c == String.class)
		{
			return String.valueOf(src);
		}
		// 这种一般是模板，直接转过去
		else if (c == Object.class)
		{
			return src;
		}
		// 这些就是特定的类型了
		else
		{
			if (srcc == Byte.class)
			{
				return null;
			}
			else if (srcc == Short.class)
			{
				return null;
			}
			else if (srcc == Integer.class)
			{
				return null;
			}
			else if (srcc == Long.class)
			{
				return null;
			}
			else if (srcc == Float.class)
			{
				return null;
			}
			else if (srcc == Double.class)
			{
				return null;
			}
			else if (srcc == Boolean.class)
			{
				return null;
			}
			else if (srcc == String.class)
			{
				return null;
			}
			else
			{
				return src;
			}
		}
	}

	protected static boolean canTrans(Object src, Class<?> c)
	{
		if (src == null)
		{
			return true;
		}

		Class<?> srcc = src.getClass();

		if ((c == Byte.class || c == Byte.TYPE) || (c == Short.class || c == Short.TYPE)
				|| (c == Integer.class || c == Integer.TYPE) || (c == Long.class || c == Long.TYPE)
				|| (c == Float.class || c == Float.TYPE) || (c == Double.class || c == Double.TYPE)
				|| (c == Boolean.class || c == Boolean.TYPE))
		{
			if (srcc == Byte.class)
			{
				return true;
			}
			else if (srcc == Short.class)
			{
				return true;
			}
			else if (srcc == Integer.class)
			{
				return true;
			}
			else if (srcc == Long.class)
			{
				return true;
			}
			else if (srcc == Float.class)
			{
				return true;
			}
			else if (srcc == Double.class)
			{
				return true;
			}
			else if (srcc == Boolean.class)
			{
				return true;
			}
			else if (srcc == String.class)
			{
				return false;
			}
			else
			{
				return false;
			}
		}
		else if (c == String.class)
		{
			if (srcc == String.class)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		// 这种一般是模板，直接转过去
		else if (c == Object.class)
		{
			return true;
		}
		// 这些就是特定的类型了
		else
		{
			return c.isInstance(src);
		}
	}

	private static void rundebugps(fake f, String func)
	{
		variant funcv = new variant();
		funcv.set_string(func);

		processor pro = new processor(f);

		try
		{
			routine r = pro.start_routine(funcv, new ArrayList<Integer>());

			f.rn.push_pro(pro);

			f.dbg.debug();
		}
		catch (Exception e)
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			types.seterror(f, getcurfile(f), getcurline(f), getcurfunc(f), e.toString() + "\n" + sw.toString());
			pw.close();
			f.ps.push_and_get();
		}
	}

	private static void runps(fake f, String func)
	{
		variant funcv = new variant();
		funcv.set_string(func);

		f.clearerr();
		processor pro = new processor(f);

		try
		{
			routine r = pro.start_routine(funcv, new ArrayList<Integer>());

			f.rn.push_pro(pro);
			pro.run();

			variant ret = r.get_ret();

			variant v = f.ps.push_and_get();
			v.copy_from(ret);
		}
		catch (Exception e)
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			types.seterror(f, getcurfile(f), getcurline(f), getcurfunc(f), e.toString() + "\n" + sw.toString());
			pw.close();
			f.ps.push_and_get();
		}
		finally
		{
			f.rn.pop_pro();
		}
	}

	private static void reg_method(fake f, String name, Class<?> c, Method m)
	{
		boolean isstatic = Modifier.isStatic(m.getModifiers());
		if (!isstatic)
		{
			name = c.getName() + name;
		}
		else
		{
			name = c.getSimpleName() + "." + name;
		}


		synchronized (fk.class)
		{
			variant v = null;
			fkfunctor fkf = null;

			if (regName.get(name) != null)
			{
				v = regName.get(name);
				fkf = regFunctor.get(name);

				for (fkmethod fm : fkf.m_ms)
				{
					if (fm.m_param.length == m.getParameterTypes().length)
					{
						boolean equal = true;
						for (int i = 0; i < fm.m_param.length; i++)
						{
							if (fm.m_param[i] != m.getParameterTypes()[i])
							{
								equal = false;
								break;
							}
						}
						if (equal)
						{
							f.fm.add_func(v, fkf);
							types.log(f, "fk reg %s %s from cache", name, fkf);
							return;
						}
					}
				}
			}
			else
			{
				v = new variant();
				v.set_string(name);

				fkf = new fkfunctor();
				fkf.m_c = c.getName();
				fkf.m_is_staic = isstatic;

				f.fm.add_func(v, fkf);

				regName.put(name, v);
				regFunctor.put(name, fkf);
			}

			fkmethod fm = new fkmethod();
			fm.m_m = m;
			fm.m_param = m.getParameterTypes();
			fm.m_ret = m.getReturnType();

			if (fkf.m_ms == null)
			{
				fkf.m_ms = new fkmethod[1];
				fkf.m_ms[0] = fm;
			}
			else
			{
				fkmethod[] newarray = new fkmethod[fkf.m_ms.length + 1];
				for (int i = 0; i < fkf.m_ms.length; i++)
				{
					newarray[i] = fkf.m_ms[i];
				}
				newarray[fkf.m_ms.length] = fm;
				fkf.m_ms = newarray;
			}

			types.log(f, "fk reg %s %s", name, fkf);
		}
	}

	protected static boolean resumeps(fake f, boolean isend) throws Exception
	{
		isend = false;

		// 上次的processor
		processor pro = f.rn.cur_pro();
		if (pro == null)
		{
			variant ret = f.ps.push_and_get();
			ret.set_nil();
			return isend;
		}

		pro.run();
		if (pro.get_routine_num() != 0)
		{
			variant ret = f.ps.push_and_get();
			ret.set_nil();
			return isend;
		}

		// 结束了
		variant ret = f.ps.push_and_get();
		ret.copy_from(pro.get_entrycurroutine().get_ret());

		f.rn.pop_pro();

		isend = true;

		return isend;
	}
}