package com.github.esrrhs.fakescript;

public class command
{
	public static final long EMPTY_CMD = -1;
	
	public static final int COMMAND_OPCODE = 0;
	public static final int COMMAND_ADDR = 1;
	public static final int COMMAND_POS = 2;
	
	public static final int OPCODE_ASSIGN = 0;
	public static final int OPCODE_PLUS = 1;
	public static final int OPCODE_MINUS = 2;
	public static final int OPCODE_MULTIPLY = 3;
	public static final int OPCODE_DIVIDE = 4;
	public static final int OPCODE_DIVIDE_MOD = 5;
	public static final int OPCODE_STRING_CAT = 6;
	public static final int OPCODE_PLUS_ASSIGN = 7;
	public static final int OPCODE_MINUS_ASSIGN = 8;
	public static final int OPCODE_MULTIPLY_ASSIGN = 9;
	public static final int OPCODE_DIVIDE_ASSIGN = 10;
	public static final int OPCODE_DIVIDE_MOD_ASSIGN = 11;
	public static final int OPCODE_RETURN = 12;
	public static final int OPCODE_JNE = 13;
	public static final int OPCODE_JMP = 14;
	public static final int OPCODE_FORBEGIN = 15;
	public static final int OPCODE_FORLOOP = 16;
	public static final int OPCODE_AND = 17;
	public static final int OPCODE_OR = 18;
	public static final int OPCODE_LESS = 19;
	public static final int OPCODE_MORE = 20;
	public static final int OPCODE_EQUAL = 21;
	public static final int OPCODE_MOREEQUAL = 22;
	public static final int OPCODE_LESSEQUAL = 23;
	public static final int OPCODE_NOTEQUAL = 24;
	public static final int OPCODE_NOT = 25;
	public static final int OPCODE_AND_JNE = 26;
	public static final int OPCODE_OR_JNE = 27;
	public static final int OPCODE_LESS_JNE = 28;
	public static final int OPCODE_MORE_JNE = 29;
	public static final int OPCODE_EQUAL_JNE = 30;
	public static final int OPCODE_MOREEQUAL_JNE = 31;
	public static final int OPCODE_LESSEQUAL_JNE = 32;
	public static final int OPCODE_NOTEQUAL_JNE = 33;
	public static final int OPCODE_NOT_JNE = 34;
	public static final int OPCODE_CALL = 35;
	public static final int OPCODE_SLEEP = 36;
	public static final int OPCODE_YIELD = 37;
	public static final int OPCODE_MAX = 38;
	
	public static final int ADDR_STACK = 0;
	public static final int ADDR_CONST = 1;
	public static final int ADDR_CONTAINER = 2;
	
	public static final int CALL_NORMAL = 0;
	public static final int CALL_FAKE = 1;
	public static final int CALL_CLASSMEM = 2;
	
	public static long MAKEINT64(int high, int low)
	{
		return ((long) ((low) | ((long) (high)) << 32));
	}
	
	public static int HIINT32(long i)
	{
		return ((int) (((long) (i) >> 32) & 0xFFFFFFFF));
	}
	
	public static int LOINT32(long i)
	{
		return ((int) (i));
	}
	
	public static int MAKEINT32(int high, int low)
	{
		return ((int) (((short) (low)) | ((int) ((short) (high))) << 16));
	}
	
	public static short HIINT16(int i)
	{
		return ((short) (((int) (i) >> 16) & 0xFFFF));
	}
	
	public static short LOINT16(int i)
	{
		return ((short) (i));
	}
	
	public static long MAKE_COMMAND(int type, int code)
	{
		return MAKEINT64(type, code);
	}
	
	public static long MAKE_OPCODE(int op)
	{
		return MAKE_COMMAND(COMMAND_OPCODE, op);
	}
	
	public static long MAKE_POS(int pos)
	{
		return MAKE_COMMAND(COMMAND_POS, pos);
	}
	
	public static long MAKE_ADDR(int addrtype, int pos)
	{
		return MAKE_COMMAND(COMMAND_ADDR, MAKEINT32(addrtype, pos));
	}
	
	public static int COMMAND_TYPE(long cmd)
	{
		return HIINT32(cmd);
	}
	
	public static int COMMAND_CODE(long cmd)
	{
		return LOINT32(cmd);
	}
	
	public static short ADDR_TYPE(int code)
	{
		return HIINT16(code);
	}
	
	public static short ADDR_POS(int code)
	{
		return LOINT16(code);
	}
}
