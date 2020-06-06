package com.github.esrrhs.fakescript;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

class buildinfunc
{
	private fake m_f;

	public buildinfunc(fake f)
	{
		m_f = f;
	}

	public void openbasefunc()
	{
		reg_func("print", "buildin_print");
		reg_func("format", "buildin_format");
		reg_func("array", "buildin_array");
		reg_func(interpreter.MAP_FUNC_NAME, "buildin_map");
		reg_func(interpreter.GMAP_FUNC_NAME, "buildin_gmap");
		reg_func("size", "buildin_size");
		reg_func("range", "buildin_range");
		reg_func("typeof", "buildin_typeof");
		reg_func("dumpallfunc", "buildin_dumpallfunc");
		reg_func("dumpfunc", "buildin_dumpfunc");
		reg_func("dofile", "buildin_dofile");
		reg_func("dostring", "buildin_dostring");
		reg_func("getcurfile", "buildin_getcurfile");
		reg_func("getcurline", "buildin_getcurline");
		reg_func("getcurfunc", "buildin_getcurfunc");
		reg_func("getcurcallstack", "buildin_getcurcallstack");
		reg_func("isfunc", "buildin_isfunc");
		reg_func("tonumber", "buildin_tonumber");
		reg_func("tostring", "buildin_tostring");
		reg_func("tolong", "buildin_tolong");
		reg_func("getconst", "buildin_getconst");

		reg_func("new", "buildin_new");

        //构造方法
        reg_func("con", "java_con");

        //获取对象里的field
        reg_func("fld", "java_fld");
        //static field
        reg_func("sfld", "java_sfld");
        reg_func("clsname", "java_clsname");
        //数组赋值
        reg_func("setarray", "java_setarray");
        reg_func("setfld", "java_setfld");
        reg_func("setsfld", "java_setsfld");
        reg_func("imp", "java_imp");
        reg_func("arraysize", "java_arraysize");
//		reg_func("int","java_int");
//		reg_func("short","java_short");
//		reg_func("long","java_long");
//		reg_func("byte","java_byte");
//		reg_func("String","java_string");
    }

    //	public static void java_int(fake f,interpreter inter)
//	{
//		Integer result = null;
//		try {
//			if (f.ps.get(0).m_type == variant_type.REAL) {
//				result = Integer.valueOf((int) f.ps.get(0).get_real());
//			}
//			else if(f.ps.get(0).m_type == variant_type.STRING) {
//				result = Integer.parseInt(f.ps.get(0).get_string());
//			}
//			else
//			{
//				result =Integer.parseInt( String.valueOf(f.ps.get(0).get_object()));
//			}
//
//
//
//		}
//		catch(Exception e)
//		{
//			fk.pspush(f, e.toString());
//			return;
//		}
//		f.ps.clear();
//
//		fk.pspush(f, result);
//	}
//	public static void java_short(fake f,interpreter inter)
//	{
//		Object result = null;
//		try {
//			if (f.ps.get(0).m_type == variant_type.REAL) {
//				result = (short)f.ps.get(0).get_real();
//			}
//			else if(f.ps.get(0).m_type == variant_type.STRING) {
//				result = Short.parseShort(f.ps.get(0).get_string());
//			}
//			else
//			{
//				result =Short.parseShort( String.valueOf(f.ps.get(0).get_object()));
//			}
//
//
//		}
//		catch(Exception e)
//		{
//			result = e.toString();
//		}
//		f.ps.clear();
//		fk.pspush(f,result);
//	}
//
//	public static void java_long(fake f,interpreter inter)
//	{
//		Object result = null;
//		try {
//			if (f.ps.get(0).m_type == variant_type.REAL) {
//				result = (long)f.ps.get(0).get_real();
//			}
//			else if(f.ps.get(0).m_type == variant_type.STRING) {
//				result = Long.parseLong(f.ps.get(0).get_string());
//			}
//			else
//			{
//				result =Long.parseLong( String.valueOf(f.ps.get(0).get_object()));
//			}
//
//
//		}
//		catch(Exception e)
//		{
//			result = e.toString();
//		}
//		f.ps.clear();
//
//		fk.pspush(f,result);
//	}
//	public static void java_String(fake f,interpreter inter)
//	{
//		Object result = null;
//		try {
//			if (f.ps.get(0).m_type == variant_type.REAL) {
//				result = ""+f.ps.get(0).get_real();
//			}
//			else if(f.ps.get(0).m_type == variant_type.STRING) {
//				result = Short.parseShort(f.ps.get(0).get_string());
//			}
//			else
//			{
//				result =String.valueOf(f.ps.get(0).get_object());
//			}
//
//
//		}
//		catch(Exception e)
//		{
//			result = e.toString();
//		}
//		f.ps.clear();
//		fk.pspush(f,result);
//	}
//	public static void java_byte(fake f,interpreter inter)
//	{
//		Object result = null;
//		try {
//			if (f.ps.get(0).m_type == variant_type.REAL) {
//				result = (byte)f.ps.get(0).get_real();
//			}
//			else if(f.ps.get(0).m_type == variant_type.STRING) {
//				result = Byte.parseByte(f.ps.get(0).get_string());
//			}
//			else
//			{
//				result =Byte.parseByte( String.valueOf(f.ps.get(0).get_object()));
//			}
//
//
//		}
//		catch(Exception e)
//		{
//			result = e.toString();
//		}
//		f.ps.clear();
//		fk.pspush(f,result);
//	}
    //int会被处理成double，在设置field时要转换回来
    private static Object typeConvert(double obj, String type) {

        switch (type) {
            case "int":
                return Integer.valueOf((int) obj);
            case "short":
                return (short) obj;
            case "long":
                return (long) obj;
            case "byte":
                return (byte) obj;
            case "float":
                return (float) obj;

            default:
                return obj;


        }
    }

    public static void java_setsfld(fake f, interpreter inter) {
        Object result = 1;
        if (f.ps.size() >= 2) {
            try {
                Class cls = null;
                if (f.ps.get(0).m_type == variant_type.STRING)
                    cls = Class.forName(f.ps.get(0).get_string());
                else
                    cls = f.ps.get(0).get_object().getClass();

                String aim = f.ps.get(1).get_string();
                Field[] fields = cls.getDeclaredFields();
                for (Field fil : fields) {
                    String name = fil.getName() + " " + fil.getType().getName();


                    fil.setAccessible(true);
                    if (name.equals(aim)) {
                        Object end = null;
                        if (f.ps.get(2).m_type == variant_type.REAL) {
                            end = typeConvert(f.ps.get(2).get_real(), fil.getType().getName());
                        } else end = f.ps.get(2).get_object();


                        fil.set(null, end);
                        break;
                    }
                }
            } catch (Exception e) {
                result = e.toString();
            }
        }

        f.ps.clear();
        fk.pspush(f, result);
    }

    public static void java_setfld(fake f, interpreter inter) {
        Object result = 1;
        if (f.ps.size() >= 2) {
            try {

                Object obj = f.ps.get(0).get_object();
                String aim = f.ps.get(1).get_string();
                //System.out.println("obj "+obj+" aim:"+aim);
                Field[] fields = obj.getClass().getDeclaredFields();
                for (Field field : fields) {
                    String name = field.getName() + " " + field.getType().getName();
                    //System.out.println(name);
                    if (name.equals(aim)) {
                        Object end = null;
                        if (f.ps.get(2).m_type == variant_type.REAL) {
                            end = typeConvert(f.ps.get(2).get_real(), field.getType().getName());
                        } else end = f.ps.get(2).get_object();

                        field.setAccessible(true);
                        field.set(obj, end);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

                f.ps.clear();
                fk.pspush(f, e.toString());
                return;
            }
        }

        f.ps.clear();
        fk.pspush(f, result);
    }

    public static void java_imp(fake f, interpreter inter) {
        try {
            Class clz = null;
            if (f.ps.get(0).m_type == variant_type.STRING) {
                clz = Class.forName(f.ps.get(0).get_string());
            } else

                clz = f.ps.get(0).get_object().getClass();

            fk.regclass(f, clz);

            f.ps.clear();
            fk.pspush(f, 1);
        } catch (Exception e) {
            f.ps.clear();
            fk.pspush(f, e.toString());
        }
    }

    public static void java_setarray(fake f, interpreter inter) {

        try {
            String arraytype = f.ps.get(0).get_object().getClass().getName();
            //	String typename = f.ps.get(2).get_object().getClass().getName();
            Object end = null;
            if (f.ps.get(2).m_type == variant_type.REAL) {
                end = typeConvert(f.ps.get(2).get_real(), arraytype.replace("[]", ""));
            } else
                end = f.ps.get(2).get_object();

            switch (arraytype) {

                case "[I":
                    int[] q = (int[]) f.ps.get(0).get_object();
                    q[(int) f.ps.get(1).get_real()] = (int) end;
                    break;

                case "[J":
                    long[] q2 = (long[]) f.ps.get(0).get_object();
                    q2[(int) f.ps.get(1).get_real()] = (long) end;
                    break;

                case "[S":
                    short[] q3 = (short[]) f.ps.get(0).get_object();
                    q3[(int) f.ps.get(1).get_real()] = (short) end;
                    break;

                case "[B":
                    byte[] q4 = (byte[]) f.ps.get(0).get_object();
                    q4[(int) f.ps.get(1).get_real()] = (byte) end;
                    break;
                case "[D":
                    double[] q5 = (double[]) f.ps.get(0).get_object();
                    q5[(int) f.ps.get(1).get_real()] = (double) end;
                    break;
                case "[F":
                    float[] q6 = (float[]) f.ps.get(0).get_object();
                    q6[(int) f.ps.get(1).get_real()] = (float) end;
                    break;

                default:
                    Object[] ob = (Object[]) f.ps.get(0).get_object();
                    ob[(int) f.ps.get(1).get_real()] = f.ps.get(2).get_object();
            }

            f.ps.clear();
            fk.pspush(f, 1);
        } catch (Exception e) {
            e.printStackTrace();
            f.ps.clear();
            fk.pspush(f, e.toString());
        }

    }

    public static void java_arraysize(fake f, interpreter inter) {

        try {
            String arraytype = f.ps.get(0).get_object().getClass().getName();

            int size = 0;
            switch (arraytype) {

                case "[I":
                    int[] q = (int[]) f.ps.get(0).get_object();
                    size = q.length;
                    break;

                case "[J":
                    long[] q2 = (long[]) f.ps.get(0).get_object();
                    size = q2.length;
                    break;

                case "[S":
                    short[] q3 = (short[]) f.ps.get(0).get_object();
                    size = q3.length;
                    break;

                case "[B":
                    byte[] q4 = (byte[]) f.ps.get(0).get_object();
                    size = q4.length;
                    break;
                case "[D":
                    double[] q5 = (double[]) f.ps.get(0).get_object();
                    size = q5.length;
                    break;
                case "[F":
                    float[] q6 = (float[]) f.ps.get(0).get_object();
                    size = q6.length;
                    break;

                default:
                    Object[] ob = (Object[]) f.ps.get(0).get_object();
                    size = ob.length;
            }

            f.ps.clear();
            fk.pspush(f, size);
        } catch (Exception e) {
            //e.printStackTrace();
            f.ps.clear();
            fk.pspush(f, e.toString());
        }

    }

    public static void java_clsname(fake f, interpreter inter) {

        try {
            Object ob = f.ps.get(0).get_object();
            f.ps.clear();
            fk.pspush(f, ob.getClass().getName());
        } catch (Exception e) {
            fk.pspush(f, e.toString());
        }

    }

    public static void java_sfld(fake f, interpreter inter) {
        Object result = null;
        if (f.ps.size() >= 2) {
            try {

                Class cls = null;
                if (f.ps.get(0).m_type == variant_type.STRING)
                    cls = Class.forName(f.ps.get(0).get_string());
                else
                    cls = f.ps.get(0).get_object().getClass();
                String aim = f.ps.get(1).get_string();
                Field[] fields = cls.getDeclaredFields();
                for (Field fil : fields) {
                    String name = fil.getName() + " " + fil.getType().getName();
                    fil.setAccessible(true);
                    if (name.equals(aim)) {
                        result = fil.get(null);
                        break;
                    }
                }
            } catch (Exception e) {
                result = e.toString();
            }
        }
        f.ps.clear();
        fk.pspush(f, result);
    }

    public static void java_fld(fake f, interpreter inter) {
        Object result = null;
        if (f.ps.size() >= 2) {
            try {
                Object obj = f.ps.get(0).get_object();
                String aim = f.ps.get(1).get_string();
                //System.out.println("obj "+obj+" aim:"+aim);
                Field[] fields = obj.getClass().getDeclaredFields();
                for (Field field : fields) {
                    String name = field.getName() + " " + field.getType().getName();
                    //System.out.println(name);
                    if (name.equals(aim)) {
                        field.setAccessible(true);
                        result = field.get(obj);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        f.ps.clear();
        fk.pspush(f, result);
    }

    public static void java_con(fake f, interpreter inter) {
        Class cls = null;
        try {
            if (f.ps.get(0).m_type == variant_type.STRING) {
                cls = Class.forName(f.ps.get(0).get_string());
            } else {
                cls = (Class) f.ps.get(0).get_pointer();
            }

            Constructor cons[] = cls.getDeclaredConstructors();
            for (Constructor c : cons) {
                Class<?>[] params = c.getParameterTypes();
                if (params.length != f.ps.size() - 1) continue;
                boolean is = true;
                for (int i = 0; i < params.length; i += 1) {
                    Object inp = null;
                    inp = f.ps.get(i + 1).get_object();


                    if (!params[i].getName().equals(inp.getClass().getName())) {
                        is = false;
                        break;
                    }
                }
                if (is) {
                    Object[] para = new Object[params.length];
                    for (int i = 0; i < params.length; i += 1) {
                        para[i] = f.ps.get(i + 1).get_object();
                    }

                    Object result = c.newInstance(para);

                    f.ps.clear();
                    fk.pspush(f, result);
                    return;
                }
            }


            f.ps.clear();

            fk.pspush(f, null);
        } catch (Exception e) {
            f.ps.clear();
            fk.pspush(f, e.toString());
        }
    }
	public static void buildin_new(fake f, interpreter inter) throws Exception
	{
		BIF_CHECK_ARG_NUM(f, 1);

		String str = (String) fk.pspop(f);

		try
		{
			Class<?> c = Class.forName(str);
			Object o = c.newInstance();
			fk.pspush(f, o);
		}
		catch (Exception e)
		{
			fk.pspush(f, e.toString());
		}
	}

	public static void buildin_getconst(fake f, interpreter inter) throws Exception
	{
		BIF_CHECK_ARG_NUM(f, 1);

		String str = (String) fk.pspop(f);

		variant v = f.ps.push_and_get();

		variant gcv = f.pa.get_const_define(str);
		if (gcv != null)
		{
			v.copy_from(gcv);
		}
		else
		{
			v.set_nil();
		}
	}

	public static void buildin_tostring(fake f, interpreter inter) throws Exception
	{
		BIF_CHECK_ARG_NUM(f, 1);

		// container
		variant v = f.ps.pop_and_get();
		if (v != null)
		{
			fk.pspush(f, v.toString());
		}
		else
		{
			fk.pspush(f, "");
		}
	}

	public static void buildin_tonumber(fake f, interpreter inter) throws Exception
	{
		BIF_CHECK_ARG_NUM(f, 1);

		variant v = f.ps.pop_and_get();
		double ret = 0;
		if (v.m_type == variant_type.STRING)
		{
			ret = Double.valueOf((String) v.m_data);
		}
		else if (v.m_type == variant_type.REAL)
		{
			ret = (double) (Double) v.m_data;
		}
		else if (v.m_type == variant_type.UUID)
		{
			ret = (double) (long) (Long) v.m_data;
		}
		fk.pspush(f, ret);
	}

	public static void buildin_tolong(fake f, interpreter inter) throws Exception
	{
		BIF_CHECK_ARG_NUM(f, 1);

		variant v = f.ps.pop_and_get();
		long ret = 0;
		if (v.m_type == variant_type.STRING)
		{
			ret = Long.valueOf((String) v.m_data);
		}
		else if (v.m_type == variant_type.REAL)
		{
			ret = (long) (double) (Double) v.m_data;
		}
		else if (v.m_type == variant_type.UUID)
		{
			ret = (long) (Long) v.m_data;
		}
		fk.pspush(f, ret);
	}

	public static void buildin_isfunc(fake f, interpreter inter) throws Exception
	{
		BIF_CHECK_ARG_NUM(f, 1);

		String str = (String) fk.pspop(f);
		boolean ret = fk.isfunc(f, str);
		fk.pspush(f, ret);
	}

	public static void buildin_getcurcallstack(fake f, interpreter inter)
	{
		String str = fk.getcurcallstack(f);
		fk.pspush(f, str);
	}

	public static void buildin_getcurfunc(fake f, interpreter inter)
	{
		String str = fk.getcurfunc(f);
		fk.pspush(f, str);
	}

	public static void buildin_getcurline(fake f, interpreter inter)
	{
		int line = fk.getcurline(f);
		fk.pspush(f, line);
	}

	public static void buildin_getcurfile(fake f, interpreter inter)
	{
		String str = fk.getcurfile(f);
		fk.pspush(f, str);
	}

	public static void buildin_dofile(fake f, interpreter inter) throws Exception
	{
		BIF_CHECK_ARG_NUM(f, 1);

		String file = (String) fk.pspop(f);
		boolean ret = fk.parse(f, file);
		fk.pspush(f, ret);
	}

	public static void buildin_dostring(fake f, interpreter inter) throws Exception
	{
		BIF_CHECK_ARG_NUM(f, 1);

		String str = (String) fk.pspop(f);
		boolean ret = fk.parsestr(f, str);
		fk.pspush(f, ret);
	}

	public static void buildin_dumpfunc(fake f, interpreter inter) throws Exception
	{
		BIF_CHECK_ARG_NUM(f, 1);

		String func = (String) fk.pspop(f);
		String str = f.bin.dump(func, -1);
		fk.pspush(f, str);
	}

	public static void buildin_dumpallfunc(fake f, interpreter inter)
	{
		String str = f.bin.dump();
		fk.pspush(f, str);
	}

	public static void buildin_typeof(fake f, interpreter inter) throws Exception
	{
		BIF_CHECK_ARG_NUM(f, 1);

		variant v = f.ps.pop_and_get();
		String name = v.m_type.name();
		fk.pspush(f, name);
	}

	public static void buildin_range(fake f, interpreter inter) throws Exception
	{
		BIF_CHECK_ARG_NUM(f, 2);

		// pos
		int pos = (int) (Integer) fk.trans(fk.pspop(f), Integer.TYPE);

		// container
		variant v = f.ps.pop_and_get();

		if (v.m_type == variant_type.STRING)
		{
			if (pos >= 0 && pos < v.get_string().length())
			{
				String ret = v.get_string().substring(pos, pos + 1);
				fk.pspush(f, ret);
			}
			else
			{
				fk.pspush(f, "");
			}
		}
		else if (v.m_type == variant_type.ARRAY)
		{
			if (pos >= 0 && pos < v.get_array().m_va.size())
			{
				variant ret = f.ps.push_and_get();
				if (v.get_array().m_va.get(pos) != null)
				{
					ret.copy_from(v.get_array().m_va.get(pos));
				}
				else
				{
					ret.set_nil();
				}
			}
			else
			{
				fk.pspush(f, false);
			}
		}
		else if (v.m_type == variant_type.MAP)
		{
			if (pos >= 0 && pos < v.get_map().m_vm.size())
			{
				variant key = f.ps.push_and_get();

				variant value = f.ps.push_and_get();

				Set<Map.Entry<variant, variant>> set = v.get_map().m_vm.entrySet();

				Map.Entry<variant, variant> e = (Map.Entry<variant, variant>) set.toArray()[pos];
				key.copy_from(e.getKey());
				value.copy_from(e.getValue());
			}
			else
			{
				fk.pspush(f, false);
				fk.pspush(f, false);
			}
		}
		else
		{
			fk.pspush(f, false);
		}
	}

	public static void buildin_size(fake f, interpreter inter) throws Exception
	{
		BIF_CHECK_ARG_NUM(f, 1);

		variant v = f.ps.pop_and_get();
		int len = 0;
		if (v.m_type == variant_type.STRING)
		{
			len = v.get_string().length();
		}
		else if (v.m_type == variant_type.ARRAY)
		{
			len = v.get_array().m_va.size();
		}
		else if (v.m_type == variant_type.MAP)
		{
			len = v.get_map().m_vm.size();
		}
		fk.pspush(f, len);
	}

	public static void buildin_map(fake f, interpreter inter)
	{
		variant_map m = new variant_map();
		variant v = f.ps.push_and_get();
		v.set_map(m);
	}

	public static void buildin_gmap(fake f, interpreter inter)
	{
		variant_map m = f.rn.get_gmap();
		variant v = f.ps.push_and_get();
		v.set_map(m);
	}

	public static void buildin_array(fake f, interpreter inter)
	{
		variant_array a = new variant_array();
		variant v = f.ps.push_and_get();
		v.set_array(a);
	}

	public static void buildin_format(fake f, interpreter inter)
	{
		String formatstr = "";
		if (f.ps.size() > 0)
		{
			formatstr = f.ps.get(0).toString();
		}

		String str = "";
		int j = 1;
		for (int i = 0; i < (int) formatstr.length(); i++)
		{
			if (formatstr.getBytes()[i] == '$')
			{
				if (i + 1 < (int) formatstr.length() && formatstr.getBytes()[i + 1] == '$')
				{
					str += formatstr.substring(i, i + 1);
					i++;
				}
				else
				{
					if (j < (int) f.ps.size())
					{
						str += f.ps.get(j).toString();
						j++;
					}
				}
			}
			else
			{
				str += formatstr.substring(i, i + 1);
			}
		}

		f.ps.clear();
		// ret
		fk.pspush(f, str);
	}

	public static void buildin_print(fake f, interpreter inter)
	{
		String str = "";

		for (int i = 0; i < (int) f.ps.size(); i++)
		{
			str += f.ps.get(i).toString();
		}

		// printf
		f.cb.on_print(f, str);

		f.ps.clear();

		// ret
		fk.pspush(f, 1);
	}

	public static void BIF_CHECK_ARG_NUM(fake f, int n) throws Exception
	{
		if (f.ps.size() != n)
		{
			throw new Exception("buildin func param not match, give " + f.ps.size() + " need " + n);
		}
	}

	public void reg_func(String regname, String funcname)
	{
		synchronized (fk.class)
		{
			variant v = null;
			bifunc bif = null;
			if (fk.regName.get(regname) != null)
			{
				v = fk.regName.get(regname);
				bif = fk.regBindFunc.get(regname);
			}
			else
			{
				v = new variant();
				v.set_string(regname);

				bif = new bifunc();

				try
				{
					bif.m_m = this.getClass().getDeclaredMethod(funcname, fake.class, interpreter.class);
				}
				catch (Exception e)
				{
					System.out.println(e);
				}

				fk.regName.put(regname, v);
				fk.regBindFunc.put(regname, bif);
			}

			m_f.fm.add_func(v, bif);
		}
	}

}
