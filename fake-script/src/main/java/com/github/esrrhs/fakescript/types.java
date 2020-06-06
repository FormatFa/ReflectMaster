package com.github.esrrhs.fakescript;

import java.util.Iterator;
import java.util.Map.Entry;

class types
{
	public static void seterror(fake f, String file, int lineno, String func, String errorstr, Object... args)
	{
		f.error = true;
		f.errorstr = String.format(errorstr, args);

		if (f.cb != null)
		{
			f.cb.on_error(f, file, lineno, func, f.errorstr);
		}
	}

	public static void log(fake f, String str, Object... args)
	{
	;
		if (f.cfg.open_debug_log != 0)
		{
			if (args.length == 0)
			{
				System.out.println(str);
			}
			else
			{
				System.out.printf(str, args);
				System.out.println("");
			}
		}
	}

	public static String show_exception(Exception e)
	{
		String ret = "";
		for (StackTraceElement se : e.getStackTrace())
		{
			ret += se.toString() + "\n";
		}
		ret += e.toString();
		return ret;
	}

	public static String gen_package_name(String p, String n)
	{
		if (p.isEmpty())
		{
			return n;
		}
		else
		{
			return p + "." + n;
		}
	}

	public static boolean isint(double d)
	{
		long l = (long) d;
		return l == d;
	}

	public static String pointertoa(Object o)
	{
		if (o == null)
		{
			return "";
		}
		return o.toString();
	}

	public static String arraytoa(Object o)
	{
		variant_array va = (variant_array) o;
		if (va.m_recur != 0)
		{
			return "ARRAY IN RECUR";
		}
		va.m_recur++;

		String ret = "";
		ret += "[";

		for (int i = 0; i < va.m_va.size(); i++)
		{
			variant n = va.m_va.get(i);
			if (n != null)
			{
				ret += n.toString();
			}
			else
			{
				ret += " ";
			}
			ret += ",";
		}

		ret += "]";

		va.m_recur--;

		return ret;
	}

	public static String maptoa(Object o)
	{
		variant_map vm = (variant_map) o;
		if (vm.m_recur != 0)
		{
			return "MAP IN RECUR";
		}
		vm.m_recur++;

		String ret = "";
		ret += "{";
		int i = 0;
		Iterator<Entry<variant, variant>> it = vm.m_vm.entrySet().iterator();
		while (it.hasNext())
		{
			Entry<variant, variant> entry = it.next();
			variant kv = (variant) entry.getKey();
			variant vv = (variant) entry.getValue();
			if (i == 0)
			{
				ret += "(";
			}
			else
			{
				ret += ",(";
			}

			ret += kv.toString();
			ret += ",";
			ret += vv.toString();
			ret += ")";

			i++;
		}

		vm.m_recur--;

		return ret;
	}

	public static String dump_addr(int code)
	{
		String ret = "";
		int addrtype = command.HIINT16(code);
		int pos = command.LOINT16(code);
		switch (addrtype)
		{
			case command.ADDR_STACK:
				ret += "STACK";
				break;
			case command.ADDR_CONST:
				ret += "CONST";
				break;
			case command.ADDR_CONTAINER:
				ret += "CONTAINER";
				break;
			default:
				ret += "unknow ";
				ret += addrtype;
		}
		ret += "\t";
		ret += pos;
		return ret;
	}

	public static String OpCodeStr(int opcode)
	{
		switch (opcode)
		{
			case command.OPCODE_ASSIGN:
				return "ASSIGN";
			case command.OPCODE_PLUS:
				return "PLUS";
			case command.OPCODE_MINUS:
				return "MINUS";
			case command.OPCODE_MULTIPLY:
				return "MULTIPLY";
			case command.OPCODE_DIVIDE:
				return "DIVIDE";
			case command.OPCODE_DIVIDE_MOD:
				return "DIVIDE_MOD";
			case command.OPCODE_STRING_CAT:
				return "STRING_CAT";
			case command.OPCODE_PLUS_ASSIGN:
				return "PLUS_ASSIGN";
			case command.OPCODE_MINUS_ASSIGN:
				return "MINUS_ASSIGN";
			case command.OPCODE_MULTIPLY_ASSIGN:
				return "MULTIPLY_ASSIGN";
			case command.OPCODE_DIVIDE_ASSIGN:
				return "DIVIDE_ASSIGN";
			case command.OPCODE_DIVIDE_MOD_ASSIGN:
				return "DIVIDE_MOD_ASSIGN";
			case command.OPCODE_RETURN:
				return "RETURN";
			case command.OPCODE_JNE:
				return "JNE";
			case command.OPCODE_JMP:
				return "JMP";
			case command.OPCODE_FORBEGIN:
				return "FORBEGIN";
			case command.OPCODE_FORLOOP:
				return "FORLOOP";
			case command.OPCODE_AND:
				return "AND";
			case command.OPCODE_OR:
				return "OR";
			case command.OPCODE_LESS:
				return "LESS";
			case command.OPCODE_MORE:
				return "MORE";
			case command.OPCODE_EQUAL:
				return "EQUAL";
			case command.OPCODE_MOREEQUAL:
				return "MOREEQUAL";
			case command.OPCODE_LESSEQUAL:
				return "LESSEQUAL";
			case command.OPCODE_NOTEQUAL:
				return "NOTEQUAL";
			case command.OPCODE_NOT:
				return "NOT";
			case command.OPCODE_AND_JNE:
				return "AND_JNE";
			case command.OPCODE_OR_JNE:
				return "OR_JNE";
			case command.OPCODE_LESS_JNE:
				return "LESS_JNE";
			case command.OPCODE_MORE_JNE:
				return "MORE_JNE";
			case command.OPCODE_EQUAL_JNE:
				return "EQUAL_JNE";
			case command.OPCODE_MOREEQUAL_JNE:
				return "MOREEQUAL_JNE";
			case command.OPCODE_LESSEQUAL_JNE:
				return "LESSEQUAL_JNE";
			case command.OPCODE_NOTEQUAL_JNE:
				return "NOTEQUAL_JNE";
			case command.OPCODE_NOT_JNE:
				return "NOT_JNE";
			case command.OPCODE_CALL:
				return "CALL";
			case command.OPCODE_SLEEP:
				return "SLEEP";
			case command.OPCODE_YIELD:
				return "YIELD";
		}
		return "unknow";
	}

}
