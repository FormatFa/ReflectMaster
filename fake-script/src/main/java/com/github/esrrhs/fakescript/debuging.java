package com.github.esrrhs.fakescript;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.util.ArrayList;

class breakpoint
{
	boolean enable;
	int id;
	String file;
	int line;
};

class debuging
{
	fake m_f;
	variant m_ret = new variant();

	static final int debug_next = 0;
	static final int debug_step = 1;
	static final int debug_next_instruction = 2;
	static final int debug_step_instruction = 3;
	static final int debug_continue = 4;
	static final int debug_breakpoint = 5;
	static final int debug_enable = 6;
	static final int debug_disable = 7;
	static final int debug_delete = 8;
	static final int debug_info = 9;
	static final int debug_finish = 10;
	static final int debug_list = 11;
	static final int debug_print = 12;
	static final int debug_set = 13;
	static final int debug_watch = 14;
	static final int debug_backtrace = 15;
	static final int debug_frame = 16;
	static final int debug_disa = 17;
	static final int debug_routine = 18;

	public debuging(fake f)
	{
		m_f = f;
	}

	void debug() throws Exception
	{
		fake f = m_f;
		boolean isend = false;
		int range = 0;
		int command = debug_next;
		ArrayList<String> paramvec = new ArrayList<String>();
		String lastfunc = "";
		ArrayList<breakpoint> blist = new ArrayList<breakpoint>();
		int bindex = 0;
		int frame = 0;
		int lastrid = fk.getcurroutineid(f);
		int rid = lastrid;
		ArrayList<String> watchvec = new ArrayList<String>();
		boolean firsttime = true;
		boolean isgoto = false;
		while (true)
		{
			if (!isgoto)
			{
				show_watch_variant(f, rid, frame, watchvec);
				warper lastridw = new warper(lastrid);
				warper lastfuncw = new warper(lastfunc);
				check_show_func_header(f, rid, frame, lastridw, lastfuncw);
				lastrid = (int) (Integer) lastridw.d;
				lastfunc = (String) lastfuncw.d;
				show_debug_code(f, rid, frame, range);
			}
			isgoto = false;

			command = get_debug_command(f, command, paramvec);
			switch (command)
			{
				case debug_next:
				{
					frame = 0;
					rid = fk.getcurroutineid(f);

					if (firsttime)
					{
						if (check_trigger_breakpoint(f, blist))
						{
							firsttime = false;
							break;
						}
					}
					firsttime = false;

					String lastfile = fk.getcurfile(f);
					int lastline = fk.getcurline(f);
					int laststacklength = fk.getcurcallstacklength(f);
					int lastridex = rid;
					String curfile = fk.getcurfile(f);
					int curline = fk.getcurline(f);
					int curstacklength = fk.getcurcallstacklength(f);
					int currid = rid;
					while (currid == lastridex
							&& (curstacklength > laststacklength || (lastfile.equals(curfile) && lastline == curline)))
					{
						isend = resume(isend);
						if (isend)
						{
							break;
						}
						curfile = fk.getcurfile(f);
						curline = fk.getcurline(f);
						curstacklength = fk.getcurcallstacklength(f);
						currid = fk.getcurroutineid(f);
						if (check_trigger_breakpoint(f, blist))
						{
							break;
						}
					}

					rid = fk.getcurroutineid(f);
				}
					break;
				case debug_step:
				{
					frame = 0;
					rid = fk.getcurroutineid(f);

					if (firsttime)
					{
						if (check_trigger_breakpoint(f, blist))
						{
							firsttime = false;
							break;
						}
					}
					firsttime = false;

					String lastfile = fk.getcurfile(f);
					int lastline = fk.getcurline(f);
					int lastridex = rid;
					String curfile = fk.getcurfile(f);
					int curline = fk.getcurline(f);
					int currid = rid;
					while (currid == lastridex && (lastfile.equals(curfile) && lastline == curline))
					{
						isend = resume(isend);
						if (isend)
						{
							break;
						}
						curfile = fk.getcurfile(f);
						curline = fk.getcurline(f);
						currid = fk.getcurroutineid(f);
						if (check_trigger_breakpoint(f, blist))
						{
							break;
						}
					}

					rid = fk.getcurroutineid(f);
				}
					break;
				case debug_next_instruction:
				{
					frame = 0;
					rid = fk.getcurroutineid(f);

					if (firsttime)
					{
						if (check_trigger_breakpoint(f, blist))
						{
							firsttime = false;
							break;
						}
					}
					firsttime = false;

					int laststacklength = fk.getcurcallstacklength(f);
					int lastridex = rid;
					int curstacklength = fk.getcurcallstacklength(f);
					int currid = rid;
					do
					{
						isend = resume(isend);
						if (isend)
						{
							break;
						}
						curstacklength = fk.getcurcallstacklength(f);
						currid = fk.getcurroutineid(f);
						if (check_trigger_breakpoint(f, blist))
						{
							break;
						}
					}
					while (currid == lastridex && curstacklength > laststacklength);

					rid = fk.getcurroutineid(f);
				}
					break;
				case debug_step_instruction:
				{
					frame = 0;
					rid = fk.getcurroutineid(f);

					if (firsttime)
					{
						if (check_trigger_breakpoint(f, blist))
						{
							firsttime = false;
							break;
						}
					}
					firsttime = false;

					isend = resume(isend);

					rid = fk.getcurroutineid(f);
					if (check_trigger_breakpoint(f, blist))
					{
						break;
					}
				}
					break;
				case debug_continue:
				{
					frame = 0;
					rid = fk.getcurroutineid(f);

					if (firsttime)
					{
						if (check_trigger_breakpoint(f, blist))
						{
							firsttime = false;
							break;
						}
					}
					firsttime = false;

					while (true)
					{
						isend = resume(isend);
						rid = fk.getcurroutineid(f);
						if (check_trigger_breakpoint(f, blist))
						{
							break;
						}
						if (isend)
						{
							break;
						}
					}
				}
					break;
				case debug_breakpoint:
				{
					breakpoint tmp = new breakpoint();
					if (paramvec.isEmpty())
					{
						tmp.file = fk.getcurfilebyroutinebyframe(f, rid, frame);
						tmp.line = fk.getcurlinebyroutinebyframe(f, rid, frame);
					}
					else
					{
						String str = paramvec.get(0);
						int subpos = str.indexOf(':');
						if (subpos != -1)
						{
							String filestr = str.substring(0, subpos);
							String linestr = str.substring(subpos + 1);

							tmp.file = filestr;
							tmp.line = Integer.parseInt(linestr);
						}
						else
						{
							boolean isnumber = true;
							for (int i = 0; i < (int) str.length(); i++)
							{
								if (!Character.isDigit(str.charAt(i)))
								{
									isnumber = false;
									break;
								}
							}

							if (isnumber)
							{
								tmp.file = fk.getcurfilebyroutinebyframe(f, rid, frame);
								tmp.line = Integer.parseInt(str);
							}
							else
							{
								if (!fk.isfunc(f, str))
								{
									System.out.printf("%s is not func\n", str);
									continue;
								}

								tmp.file = fk.getfuncfile(f, str);
								tmp.line = fk.getfuncstartline(f, str);
							}
						}
					}
					tmp.enable = true;
					tmp.id = bindex;

					if ((int) tmp.file.lastIndexOf('/') != -1)
					{
						tmp.file = tmp.file.substring(tmp.file.lastIndexOf('/') + 1);
					}

					blist.add(tmp);
					bindex++;
					System.out.printf("Breakpoint %d at file %s, line %d total %d\n", tmp.id, tmp.file, tmp.line,
							(int) blist.size());
				}
					break;
				case debug_enable:
				{
					if (paramvec.isEmpty())
					{
						for (int i = 0; i < (int) blist.size(); i++)
						{
							breakpoint tmp = blist.get(i);
							tmp.enable = true;
						}
					}
					else
					{
						int id = Integer.parseInt(paramvec.get(0));
						for (int i = 0; i < (int) blist.size(); i++)
						{
							breakpoint tmp = blist.get(i);
							if (tmp.id == id)
							{
								tmp.enable = true;
							}
						}
					}
				}
					break;
				case debug_disable:
				{
					if (paramvec.isEmpty())
					{
						for (int i = 0; i < (int) blist.size(); i++)
						{
							breakpoint tmp = blist.get(i);
							tmp.enable = false;
						}
					}
					else
					{
						int id = Integer.parseInt(paramvec.get(0));
						for (int i = 0; i < (int) blist.size(); i++)
						{
							breakpoint tmp = blist.get(i);
							if (tmp.id == id)
							{
								tmp.enable = false;
							}
						}
					}
				}
					break;
				case debug_delete:
				{
					if (paramvec.isEmpty())
					{
						blist.clear();
						watchvec.clear();
						continue;
					}
					else
					{
						int id = Integer.parseInt(paramvec.get(0));
						for (int i = 0; i < (int) blist.size(); i++)
						{
							breakpoint tmp = blist.get(i);
							if (tmp.id == id)
							{
								blist.remove(i);
							}
						}
					}
				}
					break;
				case debug_info:
				{
					if (paramvec.isEmpty())
					{
						System.out.printf("need arg, useage: i b, i r\n");
						continue;
					}

					if (paramvec.get(0).equals("b"))
					{
						System.out.printf("Id\tEnb\twhere\n");
						for (int i = 0; i < (int) blist.size(); i++)
						{
							breakpoint tmp = blist.get(i);
							System.out.printf("%d\t%s\tfile %s, line %d\n", tmp.id, tmp.enable ? "y" : "n", tmp.file,
									tmp.line);
						}
					}
					else if (paramvec.get(0).equals("r"))
					{
						for (int i = 0; i < (int) fk.getcurroutinenum(f); i++)
						{
							System.out.printf("%s%s%s\n", fk.getroutineidbyindex(f, i) == rid ? "*" : "",
									fk.getroutineidbyindex(f, i) == fk.getcurroutineid(f) ? "->" : "",
									fk.getcurroutinebyindex(f, i));
						}
					}

					System.out.printf("\n");
				}
					break;
				case debug_finish:
				{
					frame = 0;
					rid = fk.getcurroutineid(f);

					int laststacklength = fk.getcurcallstacklength(f);
					int curstacklength = fk.getcurcallstacklength(f);
					do
					{
						isend = resume(isend);
						if (isend)
						{
							break;
						}
						curstacklength = fk.getcurcallstacklength(f);
					}
					while (curstacklength >= laststacklength);
				}
					break;
				case debug_list:
				{
					int listrange = 3;
					if (!paramvec.isEmpty())
					{
						listrange = Integer.parseInt(paramvec.get(0));
					}
					show_debug_code(f, rid, frame, listrange);
					isgoto = true;
				}
					break;
				case debug_print:
				{
					if (paramvec.isEmpty())
					{
						System.out.printf("need arg, useage: p variant\n");
						continue;
					}

					String name = paramvec.get(0);
					System.out.printf("%s\n", fk.getcurvaiantbyroutinebyframe(f, rid, frame, name, -1));
				}
					break;
				case debug_set:
				{
					if (paramvec.size() < 2)
					{
						System.out.printf("need arg, useage: set variant value\n");
						continue;
					}

					String name = paramvec.get(0);
					String value = paramvec.get(1);
					fk.setcurvaiantbyroutinebyframe(f, rid, frame, name, value, -1);
					System.out.printf("%s\n", fk.getcurvaiantbyroutinebyframe(f, rid, frame, name, -1));
				}
					break;
				case debug_watch:
				{
					if (paramvec.isEmpty())
					{
						System.out.printf("need arg, useage: wa variant\n");
						continue;
					}

					String name = paramvec.get(0);
					watchvec.add(name);
				}
					break;
				case debug_backtrace:
				{
					int length = fk.getcurcallstacklengthbyroutine(f, rid);
					for (int i = 0; i < length; i++)
					{
						System.out.printf("%s%s\n", i == frame ? "*" : " ",
								fk.getcurcallstackbyroutinebyframe(f, rid, i));
					}
					isgoto = true;
				}
					break;
				case debug_frame:
				{
					if (paramvec.isEmpty())
					{
						frame = 0;
					}
					else
					{
						int theframe = Integer.parseInt(paramvec.get(0));
						if (theframe < 0 || theframe >= fk.getcurcallstacklengthbyroutine(f, rid))
						{
							System.out.printf("%d is invalid\n", theframe);
						}
						frame = theframe;
					}
				}
					break;
				case debug_disa:
				{
					int pos = fk.getcurbytecodeposbyroutine(f, rid);
					String func = fk.getcurfuncbyroutinebyframe(f, rid, frame);
					System.out.printf("%s\n", fk.dumpfunc(f, func, pos));
				}
					break;
				case debug_routine:
				{
					if (paramvec.isEmpty())
					{
						System.out.printf("need arg, useage: r rid\n");
						continue;
					}

					int id = Integer.parseInt(paramvec.get(0));
					if (!fk.ishaveroutine(f, id))
					{
						System.out.printf("no routine %d\n", id);
						continue;
					}

					rid = id;
				}
					break;
				default:
					continue;
			}
			if (isend)
			{
				break;
			}
		}
		System.out.printf("end\n");

		// ret
		{
			fk.psclear(f);
			variant ret = f.ps.push_and_get();
			ret.copy_from(m_ret);
		}
	}

	boolean resume(boolean isend) throws Exception
	{
		fake f = m_f;
		fk.psclear(f);
		isend = fk.resumeps(f, isend);
		variant ret = f.ps.pop_and_get();
		m_ret.copy_from(ret);
		return isend;
	}

	void show_debug_code(fake f, int rid, int frame, int range)
	{
		int curline = fk.getcurlinebyroutinebyframe(f, rid, frame);
		for (int i = curline - range; i <= curline + range; i++)
		{
			if (i > 0)
			{
				String code = fk.getfilecode(f, fk.getcurfilebyroutinebyframe(f, rid, frame), i);
				if (!code.isEmpty())
				{
					System.out.printf("%s%d\t%s\n", curline == i ? "*" : "", i, code);
				}
			}
		}
		System.out.printf("\n");
	}

	void show_watch_variant(fake f, int rid, int frame, ArrayList<String> watchvec)
	{
		for (int i = 0; i < (int) watchvec.size(); i++)
		{
			System.out.printf("%s\n", fk.getcurvaiantbyroutinebyframe(f, rid, frame, watchvec.get(i), -1));
		}
	}

	void check_show_func_header(fake f, int rid, int frame, warper lastrid, warper lastfunc)
	{
		String curfunc = fk.getcurfuncbyroutinebyframe(f, rid, frame);
		if (rid != (int) (Integer) lastrid.d)
		{
			System.out.printf("routine %s\n", fk.getcurroutinebyid(f, rid));
			lastrid.d = rid;
		}
		if (!curfunc.equals((String) lastfunc.d))
		{
			System.out.printf("file %s, line %d, func %s\n", fk.getcurfilebyroutinebyframe(f, rid, frame),
					fk.getcurlinebyroutinebyframe(f, rid, frame), curfunc);
			lastfunc.d = curfunc;
		}
	}

	boolean check_trigger_breakpoint(fake f, ArrayList<breakpoint> blist)
	{
		String curfile = fk.getcurfile(f);
		if ((int) curfile.lastIndexOf('/') != -1)
		{
			curfile = curfile.substring(curfile.lastIndexOf('/') + 1);
		}
		int line = fk.getcurline(f);
		for (int i = 0; i < (int) blist.size(); i++)
		{
			breakpoint tmp = blist.get(i);
			if (tmp.enable && tmp.line == line && tmp.file.equals(curfile))
			{
				System.out.printf("Trigger Breakpoint %d at file %s, line %d\n", tmp.id, tmp.file, tmp.line);
				return true;
			}
		}

		return false;
	}

	void show_debug_help(fake f)
	{
		System.out.printf("h help\n" + "n\tnext\n" + "s\tstep\n" + "ni\tnext bytecode\n" + "si\tstep bytecode\n"
				+ "c\tcontinue\n" + "l\tlist\n" + "p\tprint\n" + "set\tset\n" + "wa\twatch\n" + "b\tbreakpoint\n"
				+ "en\tenable\n" + "dis\tdisable\n" + "d\tdelete\n" + "i\tinfo\n" + "bt\tbacktrace\n" + "f\tframe\n"
				+ "fin\tfinish\n" + "r\troutine\n" + "disa\tview bytecode\n");
	}

	int get_debug_command(fake f, int command, ArrayList<String> paramvec)
	{
		while (true)
		{
			System.out.printf("(fake) ");

			String s = "";
			try
			{
				DataInputStream in = new DataInputStream(new BufferedInputStream(System.in));
				s = in.readLine().trim();
			}
			catch (Exception e)
			{
			}

			if (s.isEmpty())
			{
				if (command == -1)
				{
					continue;
				}
				return command;
			}

			paramvec.clear();
			String[] pv = s.split(" ");
			for (String p : pv)
			{
				paramvec.add(p);
			}

			if (paramvec.isEmpty())
			{
				return command;
			}

			String strcommand = paramvec.get(0);
			paramvec.remove(0);

			if (strcommand.equals("n"))
			{
				command = debug_next;
			}
			else if (strcommand.equals("s"))
			{
				command = debug_step;
			}
			else if (strcommand.equals("ni"))
			{
				command = debug_next_instruction;
			}
			else if (strcommand.equals("si"))
			{
				command = debug_step_instruction;
			}
			else if (strcommand.equals("c"))
			{
				command = debug_continue;
			}
			else if (strcommand.equals("b"))
			{
				command = debug_breakpoint;
			}
			else if (strcommand.equals("dis"))
			{
				command = debug_disable;
			}
			else if (strcommand.equals("en"))
			{
				command = debug_enable;
			}
			else if (strcommand.equals("d"))
			{
				command = debug_delete;
			}
			else if (strcommand.equals("i"))
			{
				command = debug_info;
			}
			else if (strcommand.equals("fin"))
			{
				command = debug_finish;
			}
			else if (strcommand.equals("l"))
			{
				command = debug_list;
			}
			else if (strcommand.equals("wa"))
			{
				command = debug_watch;
			}
			else if (strcommand.equals("p"))
			{
				command = debug_print;
			}
			else if (strcommand.equals("set"))
			{
				command = debug_set;
			}
			else if (strcommand.equals("bt"))
			{
				command = debug_backtrace;
			}
			else if (strcommand.equals("f"))
			{
				command = debug_frame;
			}
			else if (strcommand.equals("disa"))
			{
				command = debug_disa;
			}
			else if (strcommand.equals("r"))
			{
				command = debug_routine;
			}
			else if (strcommand.equals("h"))
			{
				show_debug_help(f);
				continue;
			}
			else
			{
				System.out.printf("use h to get help\n");
				continue;
			}

			return command;
		}
	}

}
