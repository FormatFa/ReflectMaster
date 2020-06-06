
/* A Bison parser, made by GNU Bison 2.4.1.  */

/* Skeleton implementation for Bison LALR(1) parsers in Java
   
      Copyright (C) 2007, 2008 Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* First part of user declarations.  */

/* Line 32 of lalr1.ja  */
/* Line 1 of "YYParser.y"  */

package com.github.esrrhs.fakescript;

import com.github.esrrhs.fakescript.syntree.*;
import java.io.*;



/**
 * A Bison parser, automatically generated from <tt>YYParser.y</tt>.
 *
 * @author LALR (1) parser skeleton written by Paolo Bonzini.
 */
class YYParser
{
    /** Version number for the Bison executable that generated this parser.  */
  public static final String bisonVersion = "2.4.1";

  /** Name of the skeleton that generated this parser.  */
  public static final String bisonSkeleton = "lalr1.ja";


  /** True if verbose error messages are enabled.  */
  public boolean errorVerbose = false;



  /** Token returned by the scanner to signal the end of its input.  */
  public static final int EOF = 0;

/* Tokens.  */
  /** Token number, to be returned by the scanner.  */
  public static final int VAR_BEGIN = 258;
  /** Token number, to be returned by the scanner.  */
  public static final int RETURN = 259;
  /** Token number, to be returned by the scanner.  */
  public static final int BREAK = 260;
  /** Token number, to be returned by the scanner.  */
  public static final int FUNC = 261;
  /** Token number, to be returned by the scanner.  */
  public static final int WHILE = 262;
  /** Token number, to be returned by the scanner.  */
  public static final int FTRUE = 263;
  /** Token number, to be returned by the scanner.  */
  public static final int FFALSE = 264;
  /** Token number, to be returned by the scanner.  */
  public static final int IF = 265;
  /** Token number, to be returned by the scanner.  */
  public static final int THEN = 266;
  /** Token number, to be returned by the scanner.  */
  public static final int ELSE = 267;
  /** Token number, to be returned by the scanner.  */
  public static final int END = 268;
  /** Token number, to be returned by the scanner.  */
  public static final int STRING_DEFINITION = 269;
  /** Token number, to be returned by the scanner.  */
  public static final int IDENTIFIER = 270;
  /** Token number, to be returned by the scanner.  */
  public static final int NUMBER = 271;
  /** Token number, to be returned by the scanner.  */
  public static final int SINGLE_LINE_COMMENT = 272;
  /** Token number, to be returned by the scanner.  */
  public static final int DIVIDE_MOD = 273;
  /** Token number, to be returned by the scanner.  */
  public static final int ARG_SPLITTER = 274;
  /** Token number, to be returned by the scanner.  */
  public static final int PLUS = 275;
  /** Token number, to be returned by the scanner.  */
  public static final int MINUS = 276;
  /** Token number, to be returned by the scanner.  */
  public static final int DIVIDE = 277;
  /** Token number, to be returned by the scanner.  */
  public static final int MULTIPLY = 278;
  /** Token number, to be returned by the scanner.  */
  public static final int ASSIGN = 279;
  /** Token number, to be returned by the scanner.  */
  public static final int MORE = 280;
  /** Token number, to be returned by the scanner.  */
  public static final int LESS = 281;
  /** Token number, to be returned by the scanner.  */
  public static final int MORE_OR_EQUAL = 282;
  /** Token number, to be returned by the scanner.  */
  public static final int LESS_OR_EQUAL = 283;
  /** Token number, to be returned by the scanner.  */
  public static final int EQUAL = 284;
  /** Token number, to be returned by the scanner.  */
  public static final int NOT_EQUAL = 285;
  /** Token number, to be returned by the scanner.  */
  public static final int OPEN_BRACKET = 286;
  /** Token number, to be returned by the scanner.  */
  public static final int CLOSE_BRACKET = 287;
  /** Token number, to be returned by the scanner.  */
  public static final int AND = 288;
  /** Token number, to be returned by the scanner.  */
  public static final int OR = 289;
  /** Token number, to be returned by the scanner.  */
  public static final int FKFLOAT = 290;
  /** Token number, to be returned by the scanner.  */
  public static final int PLUS_ASSIGN = 291;
  /** Token number, to be returned by the scanner.  */
  public static final int MINUS_ASSIGN = 292;
  /** Token number, to be returned by the scanner.  */
  public static final int DIVIDE_ASSIGN = 293;
  /** Token number, to be returned by the scanner.  */
  public static final int MULTIPLY_ASSIGN = 294;
  /** Token number, to be returned by the scanner.  */
  public static final int DIVIDE_MOD_ASSIGN = 295;
  /** Token number, to be returned by the scanner.  */
  public static final int COLON = 296;
  /** Token number, to be returned by the scanner.  */
  public static final int FOR = 297;
  /** Token number, to be returned by the scanner.  */
  public static final int INC = 298;
  /** Token number, to be returned by the scanner.  */
  public static final int FAKE = 299;
  /** Token number, to be returned by the scanner.  */
  public static final int FKUUID = 300;
  /** Token number, to be returned by the scanner.  */
  public static final int OPEN_SQUARE_BRACKET = 301;
  /** Token number, to be returned by the scanner.  */
  public static final int CLOSE_SQUARE_BRACKET = 302;
  /** Token number, to be returned by the scanner.  */
  public static final int FCONST = 303;
  /** Token number, to be returned by the scanner.  */
  public static final int PACKAGE = 304;
  /** Token number, to be returned by the scanner.  */
  public static final int INCLUDE = 305;
  /** Token number, to be returned by the scanner.  */
  public static final int IDENTIFIER_DOT = 306;
  /** Token number, to be returned by the scanner.  */
  public static final int IDENTIFIER_POINTER = 307;
  /** Token number, to be returned by the scanner.  */
  public static final int STRUCT = 308;
  /** Token number, to be returned by the scanner.  */
  public static final int IS = 309;
  /** Token number, to be returned by the scanner.  */
  public static final int NOT = 310;
  /** Token number, to be returned by the scanner.  */
  public static final int CONTINUE = 311;
  /** Token number, to be returned by the scanner.  */
  public static final int YIELD = 312;
  /** Token number, to be returned by the scanner.  */
  public static final int SLEEP = 313;
  /** Token number, to be returned by the scanner.  */
  public static final int SWITCH = 314;
  /** Token number, to be returned by the scanner.  */
  public static final int CASE = 315;
  /** Token number, to be returned by the scanner.  */
  public static final int DEFAULT = 316;
  /** Token number, to be returned by the scanner.  */
  public static final int NEW_ASSIGN = 317;
  /** Token number, to be returned by the scanner.  */
  public static final int ELSEIF = 318;
  /** Token number, to be returned by the scanner.  */
  public static final int RIGHT_POINTER = 319;
  /** Token number, to be returned by the scanner.  */
  public static final int STRING_CAT = 320;
  /** Token number, to be returned by the scanner.  */
  public static final int OPEN_BIG_BRACKET = 321;
  /** Token number, to be returned by the scanner.  */
  public static final int CLOSE_BIG_BRACKET = 322;
  /** Token number, to be returned by the scanner.  */
  public static final int NULL = 323;



  

  /**
   * Communication interface between the scanner and the Bison-generated
   * parser <tt>YYParser</tt>.
   */
  public interface Lexer {
    

    /**
     * Method to retrieve the semantic value of the last scanned token.
     * @return the semantic value of the last scanned token.  */
    Object getLVal();

    /**
     * Entry point for the scanner.  Returns the token identifier corresponding
     * to the next token and prepares to return the semantic value
     * of the token. 
     * @return the token identifier corresponding to the next token. */
    int yylex() throws IOException;

    /**
     * Entry point for error reporting.  Emits an error
     * in a user-defined way.
     *
     * 
     * @param s The string for the error message.  */
     void yyerror(String s);
  }

  /** The object doing lexical analysis for us.  */
  private Lexer yylexer;
  
  



  /**
   * Instantiates the Bison-generated parser.
   * @param yylexer The scanner that will supply tokens to the parser.
   */
  public YYParser (Lexer yylexer) {
    this.yylexer = yylexer;
    
  }

  private PrintStream yyDebugStream = System.err;

  /**
   * Return the <tt>PrintStream</tt> on which the debugging output is
   * printed.
   */
  public final PrintStream getDebugStream () { return yyDebugStream; }

  /**
   * Set the <tt>PrintStream</tt> on which the debug output is printed.
   * @param s The stream that is used for debugging output.
   */
  public final void setDebugStream(PrintStream s) { yyDebugStream = s; }

  private int yydebug = 0;

  /**
   * Answer the verbosity of the debugging output; 0 means that all kinds of
   * output from the parser are suppressed.
   */
  public final int getDebugLevel() { return yydebug; }

  /**
   * Set the verbosity of the debugging output; 0 means that all kinds of
   * output from the parser are suppressed.
   * @param level The verbosity level for debugging output.
   */
  public final void setDebugLevel(int level) { yydebug = level; }

  private final int yylex () throws IOException {
    return yylexer.yylex ();
  }
  protected final void yyerror (String s) {
    yylexer.yyerror (s);
  }

  

  protected final void yycdebug (String s) {
    if (yydebug > 0)
      yyDebugStream.println (s);
  }

  private final class YYStack {
    private int[] stateStack = new int[16];
    
    private Object[] valueStack = new Object[16];

    public int size = 16;
    public int height = -1;
    
    public final void push (int state, Object value    	   	      	    ) {
      height++;
      if (size == height) 
        {
	  int[] newStateStack = new int[size * 2];
	  System.arraycopy (stateStack, 0, newStateStack, 0, height);
	  stateStack = newStateStack;
	  
	  
	  Object[] newValueStack = new Object[size * 2];
	  System.arraycopy (valueStack, 0, newValueStack, 0, height);
	  valueStack = newValueStack;

	  size *= 2;
	}

      stateStack[height] = state;
      
      valueStack[height] = value;
    }

    public final void pop () {
      height--;
    }

    public final void pop (int num) {
      // Avoid memory leaks... garbage collection is a white lie!
      if (num > 0) {
	java.util.Arrays.fill (valueStack, height - num + 1, height, null);
        
      }
      height -= num;
    }

    public final int stateAt (int i) {
      return stateStack[height - i];
    }

    public final Object valueAt (int i) {
      return valueStack[height - i];
    }

    // Print the state stack on the debug stream.
    public void print (PrintStream out)
    {
      out.print ("Stack now");
      
      for (int i = 0; i < height; i++)
        {
	  out.print (' ');
	  out.print (stateStack[i]);
        }
      out.println ();
    }
  }

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return success (<tt>true</tt>).  */
  public static final int YYACCEPT = 0;

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return failure (<tt>false</tt>).  */
  public static final int YYABORT = 1;

  /**
   * Returned by a Bison action in order to start error recovery without
   * printing an error message.  */
  public static final int YYERROR = 2;

  /**
   * Returned by a Bison action in order to print an error message and start
   * error recovery.  */
  public static final int YYFAIL = 3;

  private static final int YYNEWSTATE = 4;
  private static final int YYDEFAULT = 5;
  private static final int YYREDUCE = 6;
  private static final int YYERRLAB1 = 7;
  private static final int YYRETURN = 8;

  private int yyerrstatus_ = 0;

  /**
   * Return whether error recovery is being done.  In this state, the parser
   * reads token until it reaches a known state, and then restarts normal
   * operation.  */
  public final boolean recovering ()
  {
    return yyerrstatus_ == 0;
  }

  private int yyaction (int yyn, YYStack yystack, int yylen) 
  {
    Object yyval;
    

    /* If YYLEN is nonzero, implement the default value of the action:
       `$$ = $1'.  Otherwise, use the top of the stack.
    
       Otherwise, the following line sets YYVAL to garbage.
       This behavior is undocumented and Bison
       users should not rely upon it.  */
    if (yylen > 0)
      yyval = yystack.valueAt (yylen - 1);
    else
      yyval = yystack.valueAt (0);
    
    yy_reduce_print (yyn, yystack);

    switch (yyn)
      {
	  case 3:
  if (yyn == 3)

      /* Line 353 of lalr1.ja  */
/* Line 99 of "YYParser.y"  */
    {
	};
  break;
    

  case 6:
  if (yyn == 6)

      /* Line 353 of lalr1.ja  */
/* Line 109 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: FUNC IDENTIFIER OPEN_BRACKET function_declaration_arguments CLOSE_BRACKET block END");
		func_desc_node p = ((Yylex)yylexer).new_node(func_desc_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (7-(2)))))).ival);
		p.m_funcname = ((ParserVal)((ParserVal)((yystack.valueAt (7-(2)))))).sval;
		p.m_arglist = (func_desc_arglist_node)((ParserVal)((yystack.valueAt (7-(4))))).obj;
		p.m_block = (block_node)((ParserVal)((yystack.valueAt (7-(6))))).obj;
		p.m_endline = ((Yylex)yylexer).get_mybison().get_jflex().get_line();
		((Yylex)yylexer).get_mybison().add_func_desc(p);
	};
  break;
    

  case 7:
  if (yyn == 7)

      /* Line 353 of lalr1.ja  */
/* Line 120 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: FUNC IDENTIFIER OPEN_BRACKET function_declaration_arguments CLOSE_BRACKET END");
		func_desc_node p = ((Yylex)yylexer).new_node(func_desc_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (6-(2)))))).ival);
		p.m_funcname = ((ParserVal)((ParserVal)((yystack.valueAt (6-(2)))))).sval;
		p.m_arglist = (func_desc_arglist_node)((ParserVal)((yystack.valueAt (6-(4))))).obj;
		p.m_endline = ((Yylex)yylexer).get_mybison().get_jflex().get_line();
		((Yylex)yylexer).get_mybison().add_func_desc(p);
	};
  break;
    

  case 8:
  if (yyn == 8)

      /* Line 353 of lalr1.ja  */
/* Line 132 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: empty");
	};
  break;
    

  case 9:
  if (yyn == 9)

      /* Line 353 of lalr1.ja  */
/* Line 137 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: function_declaration_arguments ARG_SPLITTER arg ");
		func_desc_arglist_node p = (func_desc_arglist_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.add_arg((syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj);
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 10:
  if (yyn == 10)

      /* Line 353 of lalr1.ja  */
/* Line 148 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: arg");
		func_desc_arglist_node p = ((Yylex)yylexer).new_node(func_desc_arglist_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).ival);
		p.add_arg((syntree_node)((ParserVal)((yystack.valueAt (1-(1))))).obj);
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 11:
  if (yyn == 11)

      /* Line 353 of lalr1.ja  */
/* Line 161 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: IDENTIFIER");
		identifier_node p = ((Yylex)yylexer).new_node(identifier_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).ival);
		p.m_str = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).sval;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 12:
  if (yyn == 12)

      /* Line 353 of lalr1.ja  */
/* Line 175 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: IDENTIFIER OPEN_BRACKET function_call_arguments CLOSE_BRACKET ");
		function_call_node p = ((Yylex)yylexer).new_node(function_call_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (4-(1)))))).ival);
		p.m_fuc = ((ParserVal)((ParserVal)((yystack.valueAt (4-(1)))))).sval;
		p.m_arglist = (function_call_arglist_node)((ParserVal)((yystack.valueAt (4-(3))))).obj;
		p.m_fakecall = false;
		p.m_classmem_call = false;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 13:
  if (yyn == 13)

      /* Line 353 of lalr1.ja  */
/* Line 189 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: IDENTIFIER_DOT OPEN_BRACKET function_call_arguments CLOSE_BRACKET ");
		function_call_node p = ((Yylex)yylexer).new_node(function_call_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (4-(1)))))).ival);
		p.m_fuc = ((ParserVal)((ParserVal)((yystack.valueAt (4-(1)))))).sval;
		p.m_arglist = (function_call_arglist_node)((ParserVal)((yystack.valueAt (4-(3))))).obj;
		p.m_fakecall = false;
		p.m_classmem_call = false;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 14:
  if (yyn == 14)

      /* Line 353 of lalr1.ja  */
/* Line 203 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: function_call OPEN_BRACKET function_call_arguments CLOSE_BRACKET ");
		function_call_node p = ((Yylex)yylexer).new_node(function_call_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (4-(1)))))).ival);
		p.m_prefuc = (syntree_node)((ParserVal)((yystack.valueAt (4-(1))))).obj;
		p.m_arglist = (function_call_arglist_node)((ParserVal)((yystack.valueAt (4-(3))))).obj;
		p.m_fakecall = false;
		p.m_classmem_call = false;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 15:
  if (yyn == 15)

      /* Line 353 of lalr1.ja  */
/* Line 217 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: variable COLON IDENTIFIER OPEN_BRACKET function_call_arguments CLOSE_BRACKET ");
		function_call_node p = ((Yylex)yylexer).new_node(function_call_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (6-(1)))))).ival);
		p.m_fuc = ((ParserVal)((ParserVal)((yystack.valueAt (6-(3)))))).sval;
		p.m_arglist = (function_call_arglist_node)((ParserVal)((yystack.valueAt (6-(5))))).obj;
		if (p.m_arglist == null)
		{
			p.m_arglist = ((Yylex)yylexer).new_node(function_call_arglist_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (6-(1)))))).ival);
		}
		p.m_arglist.add_arg((syntree_node)((ParserVal)((yystack.valueAt (6-(1))))).obj);
		p.m_fakecall = false;
		p.m_classmem_call = true;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 16:
  if (yyn == 16)

      /* Line 353 of lalr1.ja  */
/* Line 236 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: function_call COLON IDENTIFIER OPEN_BRACKET function_call_arguments CLOSE_BRACKET ");
		function_call_node p = ((Yylex)yylexer).new_node(function_call_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (6-(1)))))).ival);
		p.m_fuc = ((ParserVal)((ParserVal)((yystack.valueAt (6-(3)))))).sval;
		p.m_arglist = (function_call_arglist_node)((ParserVal)((yystack.valueAt (6-(5))))).obj;
		if (p.m_arglist == null)
		{
			p.m_arglist = ((Yylex)yylexer).new_node(function_call_arglist_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (6-(1)))))).ival);
		}
		p.m_arglist.add_arg((syntree_node)((ParserVal)((yystack.valueAt (6-(1))))).obj);
		p.m_fakecall = false;
		p.m_classmem_call = true;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 17:
  if (yyn == 17)

      /* Line 353 of lalr1.ja  */
/* Line 257 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: empty ");
	};
  break;
    

  case 18:
  if (yyn == 18)

      /* Line 353 of lalr1.ja  */
/* Line 262 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: function_call_arguments ARG_SPLITTER arg_expr ");
		function_call_arglist_node p = (function_call_arglist_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.add_arg((syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj);
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 19:
  if (yyn == 19)

      /* Line 353 of lalr1.ja  */
/* Line 273 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: arg_expr ");
		function_call_arglist_node p = ((Yylex)yylexer).new_node(function_call_arglist_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).ival);
		p.add_arg((syntree_node)((ParserVal)((yystack.valueAt (1-(1))))).obj);
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 20:
  if (yyn == 20)

      /* Line 353 of lalr1.ja  */
/* Line 286 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: expr_value");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 21:
  if (yyn == 21)

      /* Line 353 of lalr1.ja  */
/* Line 296 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: block stmt ");
		block_node p = (block_node)((ParserVal)((yystack.valueAt (2-(1))))).obj;
		p.add_stmt((syntree_node)((ParserVal)((yystack.valueAt (2-(2))))).obj);
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 22:
  if (yyn == 22)

      /* Line 353 of lalr1.ja  */
/* Line 307 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: stmt");
		block_node p = ((Yylex)yylexer).new_node(block_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).ival);
		p.add_stmt((syntree_node)((ParserVal)((yystack.valueAt (1-(1))))).obj);
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 23:
  if (yyn == 23)

      /* Line 353 of lalr1.ja  */
/* Line 320 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: while_stmt");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 24:
  if (yyn == 24)

      /* Line 353 of lalr1.ja  */
/* Line 326 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: if_stmt");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 25:
  if (yyn == 25)

      /* Line 353 of lalr1.ja  */
/* Line 332 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: return_stmt");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 26:
  if (yyn == 26)

      /* Line 353 of lalr1.ja  */
/* Line 338 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: assign_stmt");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 27:
  if (yyn == 27)

      /* Line 353 of lalr1.ja  */
/* Line 344 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: multi_assign_stmt");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 28:
  if (yyn == 28)

      /* Line 353 of lalr1.ja  */
/* Line 350 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: break");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 29:
  if (yyn == 29)

      /* Line 353 of lalr1.ja  */
/* Line 356 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: continue");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 30:
  if (yyn == 30)

      /* Line 353 of lalr1.ja  */
/* Line 362 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: expr");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 31:
  if (yyn == 31)

      /* Line 353 of lalr1.ja  */
/* Line 368 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: math_assign_stmt");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 32:
  if (yyn == 32)

      /* Line 353 of lalr1.ja  */
/* Line 374 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: for_stmt");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 33:
  if (yyn == 33)

      /* Line 353 of lalr1.ja  */
/* Line 380 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: for_loop_stmt");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 34:
  if (yyn == 34)

      /* Line 353 of lalr1.ja  */
/* Line 386 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: fake_call_stmt");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 35:
  if (yyn == 35)

      /* Line 353 of lalr1.ja  */
/* Line 392 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: sleep_stmt");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 36:
  if (yyn == 36)

      /* Line 353 of lalr1.ja  */
/* Line 398 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: yield_stmt");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 37:
  if (yyn == 37)

      /* Line 353 of lalr1.ja  */
/* Line 404 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: switch_stmt");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 38:
  if (yyn == 38)

      /* Line 353 of lalr1.ja  */
/* Line 412 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: FAKE function_call");
		function_call_node p = (function_call_node)((ParserVal)((yystack.valueAt (2-(2))))).obj;
		p.m_fakecall = true;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 39:
  if (yyn == 39)

      /* Line 353 of lalr1.ja  */
/* Line 425 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: FOR block ARG_SPLITTER cmp ARG_SPLITTER block THEN block END");
		for_stmt p = ((Yylex)yylexer).new_node(for_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (9-(2)))))).ival);
		p.m_cmp = (cmp_stmt)((ParserVal)((yystack.valueAt (9-(4))))).obj;
		p.m_beginblock = (block_node)((ParserVal)((yystack.valueAt (9-(2))))).obj;
		p.m_endblock = (block_node)((ParserVal)((yystack.valueAt (9-(6))))).obj;
		p.m_block = (block_node)((ParserVal)((yystack.valueAt (9-(8))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 40:
  if (yyn == 40)

      /* Line 353 of lalr1.ja  */
/* Line 439 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: FOR block ARG_SPLITTER cmp ARG_SPLITTER block THEN END");
		for_stmt p = ((Yylex)yylexer).new_node(for_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (8-(2)))))).ival);
		p.m_cmp = (cmp_stmt)((ParserVal)((yystack.valueAt (8-(4))))).obj;
		p.m_beginblock = (block_node)((ParserVal)((yystack.valueAt (8-(2))))).obj;
		p.m_endblock = (block_node)((ParserVal)((yystack.valueAt (8-(6))))).obj;
		p.m_block = null;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 41:
  if (yyn == 41)

      /* Line 353 of lalr1.ja  */
/* Line 455 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: FOR var ASSIGN assign_value RIGHT_POINTER cmp_value ARG_SPLITTER expr_value THEN block END");
		for_loop_stmt p = ((Yylex)yylexer).new_node(for_loop_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (11-(2)))))).ival);
		p.m_var = (syntree_node)((ParserVal)((yystack.valueAt (11-(2))))).obj;
		p.m_begin = (syntree_node)((ParserVal)((yystack.valueAt (11-(4))))).obj;
		p.m_end = (syntree_node)((ParserVal)((yystack.valueAt (11-(6))))).obj;
		p.m_add = (syntree_node)((ParserVal)((yystack.valueAt (11-(8))))).obj;
		p.m_block = (block_node)((ParserVal)((yystack.valueAt (11-(10))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 42:
  if (yyn == 42)

      /* Line 353 of lalr1.ja  */
/* Line 470 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: FOR var ASSIGN assign_value RIGHT_POINTER cmp_value ARG_SPLITTER expr_value THEN END");
		for_loop_stmt p = ((Yylex)yylexer).new_node(for_loop_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (10-(2)))))).ival);
		p.m_var = (syntree_node)((ParserVal)((yystack.valueAt (10-(2))))).obj;
		p.m_begin = (syntree_node)((ParserVal)((yystack.valueAt (10-(4))))).obj;
		p.m_end = (syntree_node)((ParserVal)((yystack.valueAt (10-(6))))).obj;
		p.m_add = (syntree_node)((ParserVal)((yystack.valueAt (10-(8))))).obj;
		p.m_block = null;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 43:
  if (yyn == 43)

      /* Line 353 of lalr1.ja  */
/* Line 487 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: WHILE cmp THEN block END ");
		while_stmt p = ((Yylex)yylexer).new_node(while_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (5-(2)))))).ival);
		p.m_cmp = (cmp_stmt)((ParserVal)((yystack.valueAt (5-(2))))).obj;
		p.m_block = (block_node)((ParserVal)((yystack.valueAt (5-(4))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 44:
  if (yyn == 44)

      /* Line 353 of lalr1.ja  */
/* Line 499 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: WHILE cmp THEN END ");
		while_stmt p = ((Yylex)yylexer).new_node(while_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (4-(2)))))).ival);
		p.m_cmp = (cmp_stmt)((ParserVal)((yystack.valueAt (4-(2))))).obj;
		p.m_block = null;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 45:
  if (yyn == 45)

      /* Line 353 of lalr1.ja  */
/* Line 513 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: IF cmp THEN block elseif_stmt_list else_stmt END");
		if_stmt p = ((Yylex)yylexer).new_node(if_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (7-(2)))))).ival);
		p.m_cmp = (cmp_stmt)((ParserVal)((yystack.valueAt (7-(2))))).obj;
		p.m_block = (block_node)((ParserVal)((yystack.valueAt (7-(4))))).obj;
		p.m_elseifs = (elseif_stmt_list)((ParserVal)((yystack.valueAt (7-(5))))).obj;
		p.m_elses = (else_stmt)((ParserVal)((yystack.valueAt (7-(6))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 46:
  if (yyn == 46)

      /* Line 353 of lalr1.ja  */
/* Line 527 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: IF cmp THEN elseif_stmt_list else_stmt END");
		if_stmt p = ((Yylex)yylexer).new_node(if_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (6-(2)))))).ival);
		p.m_cmp = (cmp_stmt)((ParserVal)((yystack.valueAt (6-(2))))).obj;
		p.m_block = null;
		p.m_elseifs = (elseif_stmt_list)((ParserVal)((yystack.valueAt (6-(4))))).obj;
		p.m_elses = (else_stmt)((ParserVal)((yystack.valueAt (6-(5))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 47:
  if (yyn == 47)

      /* Line 353 of lalr1.ja  */
/* Line 543 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: empty");
		
		ParserVal ret = new ParserVal(null);
		ret.ival = ((Yylex)yylexer).get_mybison().get_jflex().get_line();
		yyval = ret;
	};
  break;
    

  case 48:
  if (yyn == 48)

      /* Line 353 of lalr1.ja  */
/* Line 552 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: elseif_stmt_list elseif_stmt");
		elseif_stmt_list p = (elseif_stmt_list)((ParserVal)((yystack.valueAt (2-(1))))).obj;
		p.add_stmt((syntree_node)((ParserVal)((yystack.valueAt (2-(2))))).obj);
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 49:
  if (yyn == 49)

      /* Line 353 of lalr1.ja  */
/* Line 563 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: elseif_stmt");
		elseif_stmt_list p = ((Yylex)yylexer).new_node(elseif_stmt_list.class, ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).ival);
		p.add_stmt((syntree_node)((ParserVal)((yystack.valueAt (1-(1))))).obj);
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 50:
  if (yyn == 50)

      /* Line 353 of lalr1.ja  */
/* Line 576 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: ELSEIF cmp THEN block");
		elseif_stmt p = ((Yylex)yylexer).new_node(elseif_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (4-(2)))))).ival);
		p.m_cmp = (cmp_stmt)((ParserVal)((yystack.valueAt (4-(2))))).obj;
		p.m_block = (syntree_node)((ParserVal)((yystack.valueAt (4-(4))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 51:
  if (yyn == 51)

      /* Line 353 of lalr1.ja  */
/* Line 588 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: ELSEIF cmp THEN");
		elseif_stmt p = ((Yylex)yylexer).new_node(elseif_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(2)))))).ival);
		p.m_cmp = (cmp_stmt)((ParserVal)((yystack.valueAt (3-(2))))).obj;
		p.m_block = null;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 52:
  if (yyn == 52)

      /* Line 353 of lalr1.ja  */
/* Line 602 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: empty");
		
		ParserVal ret = new ParserVal(null);
		ret.ival = ((Yylex)yylexer).get_mybison().get_jflex().get_line();
		yyval = ret;
	};
  break;
    

  case 53:
  if (yyn == 53)

      /* Line 353 of lalr1.ja  */
/* Line 611 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: ELSE block");
		else_stmt p = ((Yylex)yylexer).new_node(else_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (2-(2)))))).ival);
		p.m_block = (block_node)((ParserVal)((yystack.valueAt (2-(2))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 54:
  if (yyn == 54)

      /* Line 353 of lalr1.ja  */
/* Line 622 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: ELSE");
		else_stmt p = ((Yylex)yylexer).new_node(else_stmt.class, ((Yylex)yylexer).get_mybison().get_jflex().get_line());
		p.m_block = null;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 55:
  if (yyn == 55)

      /* Line 353 of lalr1.ja  */
/* Line 635 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: OPEN_BRACKET cmp CLOSE_BRACKET");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (3-(2))))));
	};
  break;
    

  case 56:
  if (yyn == 56)

      /* Line 353 of lalr1.ja  */
/* Line 641 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: cmp AND cmp");
		cmp_stmt p = ((Yylex)yylexer).new_node(cmp_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_cmp = "&&";
		p.m_left = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_right = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 57:
  if (yyn == 57)

      /* Line 353 of lalr1.ja  */
/* Line 654 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: cmp OR cmp");
		cmp_stmt p = ((Yylex)yylexer).new_node(cmp_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_cmp = "||";
		p.m_left = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_right = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 58:
  if (yyn == 58)

      /* Line 353 of lalr1.ja  */
/* Line 667 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: cmp_value LESS cmp_value");
		cmp_stmt p = ((Yylex)yylexer).new_node(cmp_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_cmp = "<";
		p.m_left = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_right = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 59:
  if (yyn == 59)

      /* Line 353 of lalr1.ja  */
/* Line 680 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: cmp_value MORE cmp_value");
		cmp_stmt p = ((Yylex)yylexer).new_node(cmp_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_cmp = ">";
		p.m_left = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_right = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 60:
  if (yyn == 60)

      /* Line 353 of lalr1.ja  */
/* Line 693 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: cmp_value EQUAL cmp_value");
		cmp_stmt p = ((Yylex)yylexer).new_node(cmp_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_cmp = "==";
		p.m_left = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_right = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 61:
  if (yyn == 61)

      /* Line 353 of lalr1.ja  */
/* Line 706 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: cmp_value MORE_OR_EQUAL cmp_value");
		cmp_stmt p = ((Yylex)yylexer).new_node(cmp_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_cmp = ">=";
		p.m_left = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_right = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 62:
  if (yyn == 62)

      /* Line 353 of lalr1.ja  */
/* Line 719 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: cmp_value LESS_OR_EQUAL cmp_value");
		cmp_stmt p = ((Yylex)yylexer).new_node(cmp_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_cmp = "<=";
		p.m_left = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_right = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 63:
  if (yyn == 63)

      /* Line 353 of lalr1.ja  */
/* Line 732 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: cmp_value NOT_EQUAL cmp_value");
		cmp_stmt p = ((Yylex)yylexer).new_node(cmp_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_cmp = "!=";
		p.m_left = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_right = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 64:
  if (yyn == 64)

      /* Line 353 of lalr1.ja  */
/* Line 745 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: FTRUE");
		cmp_stmt p = ((Yylex)yylexer).new_node(cmp_stmt.class, ((Yylex)yylexer).get_mybison().get_jflex().get_line());
		p.m_cmp = "true";
		p.m_left = null;
		p.m_right = null;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 65:
  if (yyn == 65)

      /* Line 353 of lalr1.ja  */
/* Line 758 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: FFALSE");
		cmp_stmt p = ((Yylex)yylexer).new_node(cmp_stmt.class, ((Yylex)yylexer).get_mybison().get_jflex().get_line());
		p.m_cmp = "false";
		p.m_left = null;
		p.m_right = null;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 66:
  if (yyn == 66)

      /* Line 353 of lalr1.ja  */
/* Line 771 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: IS cmp_value");
		cmp_stmt p = ((Yylex)yylexer).new_node(cmp_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (2-(2)))))).ival);
		p.m_cmp = "is";
		p.m_left = (syntree_node)((ParserVal)((yystack.valueAt (2-(2))))).obj;
		p.m_right = null;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 67:
  if (yyn == 67)

      /* Line 353 of lalr1.ja  */
/* Line 784 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: NOT cmp_value");
		cmp_stmt p = ((Yylex)yylexer).new_node(cmp_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (2-(2)))))).ival);
		p.m_cmp = "not";
		p.m_left = (syntree_node)((ParserVal)((yystack.valueAt (2-(2))))).obj;
		p.m_right = null;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 68:
  if (yyn == 68)

      /* Line 353 of lalr1.ja  */
/* Line 799 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: explicit_value");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 69:
  if (yyn == 69)

      /* Line 353 of lalr1.ja  */
/* Line 805 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: variable");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 70:
  if (yyn == 70)

      /* Line 353 of lalr1.ja  */
/* Line 811 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: expr");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 71:
  if (yyn == 71)

      /* Line 353 of lalr1.ja  */
/* Line 819 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: RETURN return_value_list");
		return_stmt p = ((Yylex)yylexer).new_node(return_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (2-(2)))))).ival);
		p.m_returnlist = (return_value_list_node)((ParserVal)((yystack.valueAt (2-(2))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 72:
  if (yyn == 72)

      /* Line 353 of lalr1.ja  */
/* Line 830 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: RETURN");
		return_stmt p = ((Yylex)yylexer).new_node(return_stmt.class, ((Yylex)yylexer).get_mybison().get_jflex().get_line());
		p.m_returnlist = null;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 73:
  if (yyn == 73)

      /* Line 353 of lalr1.ja  */
/* Line 843 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: return_value_list ARG_SPLITTER return_value");
		return_value_list_node p = (return_value_list_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.add_arg((syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj);
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 74:
  if (yyn == 74)

      /* Line 353 of lalr1.ja  */
/* Line 854 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: return_value");
		return_value_list_node p = ((Yylex)yylexer).new_node(return_value_list_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).ival);
		p.add_arg((syntree_node)((ParserVal)((yystack.valueAt (1-(1))))).obj);
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 75:
  if (yyn == 75)

      /* Line 353 of lalr1.ja  */
/* Line 867 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: explicit_value");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 76:
  if (yyn == 76)

      /* Line 353 of lalr1.ja  */
/* Line 873 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: variable");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 77:
  if (yyn == 77)

      /* Line 353 of lalr1.ja  */
/* Line 879 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: expr");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 78:
  if (yyn == 78)

      /* Line 353 of lalr1.ja  */
/* Line 887 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: var ASSIGN assign_value");
		assign_stmt p = ((Yylex)yylexer).new_node(assign_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_var = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_value = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		p.m_isnew = false;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 79:
  if (yyn == 79)

      /* Line 353 of lalr1.ja  */
/* Line 900 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: var NEW_ASSIGN assign_value");
		assign_stmt p = ((Yylex)yylexer).new_node(assign_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_var = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_value = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		p.m_isnew = true;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 80:
  if (yyn == 80)

      /* Line 353 of lalr1.ja  */
/* Line 915 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: var_list ASSIGN function_call");
		multi_assign_stmt p = ((Yylex)yylexer).new_node(multi_assign_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_varlist = (var_list_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_value = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		p.m_isnew = false;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 81:
  if (yyn == 81)

      /* Line 353 of lalr1.ja  */
/* Line 928 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: var_list NEW_ASSIGN function_call");
		multi_assign_stmt p = ((Yylex)yylexer).new_node(multi_assign_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_varlist = (var_list_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_value = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		p.m_isnew = true;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 82:
  if (yyn == 82)

      /* Line 353 of lalr1.ja  */
/* Line 943 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: var_list ARG_SPLITTER var");
		var_list_node p = (var_list_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.add_arg((syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj);
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 83:
  if (yyn == 83)

      /* Line 353 of lalr1.ja  */
/* Line 954 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: var");
		var_list_node p = ((Yylex)yylexer).new_node(var_list_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).ival);
		p.add_arg((syntree_node)((ParserVal)((yystack.valueAt (1-(1))))).obj);
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 84:
  if (yyn == 84)

      /* Line 353 of lalr1.ja  */
/* Line 967 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: explicit_value");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 85:
  if (yyn == 85)

      /* Line 353 of lalr1.ja  */
/* Line 973 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: variable");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 86:
  if (yyn == 86)

      /* Line 353 of lalr1.ja  */
/* Line 979 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: expr");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 87:
  if (yyn == 87)

      /* Line 353 of lalr1.ja  */
/* Line 987 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: variable PLUS_ASSIGN assign_value");
		math_assign_stmt p = ((Yylex)yylexer).new_node(math_assign_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_var = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_oper = "+=";
		p.m_value = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 88:
  if (yyn == 88)

      /* Line 353 of lalr1.ja  */
/* Line 1000 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: variable MINUS_ASSIGN assign_value");
		math_assign_stmt p = ((Yylex)yylexer).new_node(math_assign_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_var = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_oper = "-=";
		p.m_value = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 89:
  if (yyn == 89)

      /* Line 353 of lalr1.ja  */
/* Line 1013 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: variable DIVIDE_ASSIGN assign_value");
		math_assign_stmt p = ((Yylex)yylexer).new_node(math_assign_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_var = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_oper = "/=";
		p.m_value = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 90:
  if (yyn == 90)

      /* Line 353 of lalr1.ja  */
/* Line 1026 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: variable MULTIPLY_ASSIGN assign_value");
		math_assign_stmt p = ((Yylex)yylexer).new_node(math_assign_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_var = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_oper = "*=";
		p.m_value = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 91:
  if (yyn == 91)

      /* Line 353 of lalr1.ja  */
/* Line 1039 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: variable DIVIDE_MOD_ASSIGN assign_value");
		math_assign_stmt p = ((Yylex)yylexer).new_node(math_assign_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_var = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_oper = "%=";
		p.m_value = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 92:
  if (yyn == 92)

      /* Line 353 of lalr1.ja  */
/* Line 1052 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: variable INC");
		explicit_value_node pp = ((Yylex)yylexer).new_node(explicit_value_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (2-(1)))))).ival);
		pp.m_str = "1";
		pp.m_type = explicit_value_type.EVT_NUM;
		
		math_assign_stmt p = ((Yylex)yylexer).new_node(math_assign_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (2-(1)))))).ival);
		p.m_var = (syntree_node)((ParserVal)((yystack.valueAt (2-(1))))).obj;
		p.m_oper = "+=";
		p.m_value = pp;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 93:
  if (yyn == 93)

      /* Line 353 of lalr1.ja  */
/* Line 1071 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: VAR_BEGIN IDENTIFIER");
		var_node p = ((Yylex)yylexer).new_node(var_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (2-(2)))))).ival);
		p.m_str = ((ParserVal)((ParserVal)((yystack.valueAt (2-(2)))))).sval;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 94:
  if (yyn == 94)

      /* Line 353 of lalr1.ja  */
/* Line 1082 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: variable");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 95:
  if (yyn == 95)

      /* Line 353 of lalr1.ja  */
/* Line 1090 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: IDENTIFIER");
		variable_node p = ((Yylex)yylexer).new_node(variable_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).ival);
		p.m_str = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).sval;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 96:
  if (yyn == 96)

      /* Line 353 of lalr1.ja  */
/* Line 1101 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: IDENTIFIER OPEN_SQUARE_BRACKET expr_value CLOSE_SQUARE_BRACKET");
		container_get_node p = ((Yylex)yylexer).new_node(container_get_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (4-(1)))))).ival);
		p.m_container = ((ParserVal)((ParserVal)((yystack.valueAt (4-(1)))))).sval;
		p.m_key = (syntree_node)((ParserVal)((yystack.valueAt (4-(3))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 97:
  if (yyn == 97)

      /* Line 353 of lalr1.ja  */
/* Line 1113 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: IDENTIFIER_POINTER");
		struct_pointer_node p = ((Yylex)yylexer).new_node(struct_pointer_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).ival);
		p.m_str = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).sval;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 98:
  if (yyn == 98)

      /* Line 353 of lalr1.ja  */
/* Line 1124 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: IDENTIFIER_DOT");
		variable_node p = ((Yylex)yylexer).new_node(variable_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).ival);
		p.m_str = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).sval;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 99:
  if (yyn == 99)

      /* Line 353 of lalr1.ja  */
/* Line 1137 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: OPEN_BRACKET expr CLOSE_BRACKET");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (3-(2))))));
	};
  break;
    

  case 100:
  if (yyn == 100)

      /* Line 353 of lalr1.ja  */
/* Line 1143 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: function_call");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 101:
  if (yyn == 101)

      /* Line 353 of lalr1.ja  */
/* Line 1149 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: math_expr");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 102:
  if (yyn == 102)

      /* Line 353 of lalr1.ja  */
/* Line 1157 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: OPEN_BRACKET math_expr CLOSE_BRACKET");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (3-(2))))));
	};
  break;
    

  case 103:
  if (yyn == 103)

      /* Line 353 of lalr1.ja  */
/* Line 1163 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: expr_value PLUS expr_value");
		math_expr_node p = ((Yylex)yylexer).new_node(math_expr_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_oper = "+";
		p.m_left = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_right = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 104:
  if (yyn == 104)

      /* Line 353 of lalr1.ja  */
/* Line 1176 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: expr_value MINUS expr_value");
		math_expr_node p = ((Yylex)yylexer).new_node(math_expr_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_oper = "-";
		p.m_left = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_right = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 105:
  if (yyn == 105)

      /* Line 353 of lalr1.ja  */
/* Line 1189 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: expr_value MULTIPLY expr_value");
		math_expr_node p = ((Yylex)yylexer).new_node(math_expr_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_oper = "*";
		p.m_left = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_right = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 106:
  if (yyn == 106)

      /* Line 353 of lalr1.ja  */
/* Line 1202 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: expr_value DIVIDE expr_value");
		math_expr_node p = ((Yylex)yylexer).new_node(math_expr_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_oper = "/";
		p.m_left = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_right = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 107:
  if (yyn == 107)

      /* Line 353 of lalr1.ja  */
/* Line 1215 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: expr_value DIVIDE_MOD expr_value");
		math_expr_node p = ((Yylex)yylexer).new_node(math_expr_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_oper = "%";
		p.m_left = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_right = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 108:
  if (yyn == 108)

      /* Line 353 of lalr1.ja  */
/* Line 1228 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: expr_value STRING_CAT expr_value");
		math_expr_node p = ((Yylex)yylexer).new_node(math_expr_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_oper = "..";
		p.m_left = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_right = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 109:
  if (yyn == 109)

      /* Line 353 of lalr1.ja  */
/* Line 1243 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: math_expr");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 110:
  if (yyn == 110)

      /* Line 353 of lalr1.ja  */
/* Line 1249 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: explicit_value");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 111:
  if (yyn == 111)

      /* Line 353 of lalr1.ja  */
/* Line 1255 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: function_call");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 112:
  if (yyn == 112)

      /* Line 353 of lalr1.ja  */
/* Line 1261 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: variable");
		yyval = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1))))));
	};
  break;
    

  case 113:
  if (yyn == 113)

      /* Line 353 of lalr1.ja  */
/* Line 1269 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: BREAK");
		break_stmt p = ((Yylex)yylexer).new_node(break_stmt.class, ((Yylex)yylexer).get_mybison().get_jflex().get_line());
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 114:
  if (yyn == 114)

      /* Line 353 of lalr1.ja  */
/* Line 1281 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: CONTINUE");
		continue_stmt p = ((Yylex)yylexer).new_node(continue_stmt.class, ((Yylex)yylexer).get_mybison().get_jflex().get_line());
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 115:
  if (yyn == 115)

      /* Line 353 of lalr1.ja  */
/* Line 1293 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: SLEEP");
		sleep_stmt p = ((Yylex)yylexer).new_node(sleep_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (2-(2)))))).ival);
		p.m_time = (syntree_node)((ParserVal)((yystack.valueAt (2-(2))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 116:
  if (yyn == 116)

      /* Line 353 of lalr1.ja  */
/* Line 1306 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: YIELD");
		yield_stmt p = ((Yylex)yylexer).new_node(yield_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (2-(2)))))).ival);
		p.m_time = (syntree_node)((ParserVal)((yystack.valueAt (2-(2))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 117:
  if (yyn == 117)

      /* Line 353 of lalr1.ja  */
/* Line 1319 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: SWITCH cmp_value switch_case_list DEFAULT block END");
		switch_stmt p = ((Yylex)yylexer).new_node(switch_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (6-(2)))))).ival);
		p.m_cmp = (syntree_node)((ParserVal)((yystack.valueAt (6-(2))))).obj;
		p.m_caselist = (syntree_node)((ParserVal)((yystack.valueAt (6-(3))))).obj;
		p.m_def = (syntree_node)((ParserVal)((yystack.valueAt (6-(5))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 118:
  if (yyn == 118)

      /* Line 353 of lalr1.ja  */
/* Line 1332 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: SWITCH cmp_value switch_case_list DEFAULT END");
		switch_stmt p = ((Yylex)yylexer).new_node(switch_stmt.class, ((ParserVal)((ParserVal)((yystack.valueAt (5-(2)))))).ival);
		p.m_cmp = (syntree_node)((ParserVal)((yystack.valueAt (5-(2))))).obj;
		p.m_caselist = (syntree_node)((ParserVal)((yystack.valueAt (5-(3))))).obj;
		p.m_def = null;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 119:
  if (yyn == 119)

      /* Line 353 of lalr1.ja  */
/* Line 1347 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: switch_case_define");
		switch_caselist_node p = ((Yylex)yylexer).new_node(switch_caselist_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).ival);
		p.add_case((syntree_node)((ParserVal)((yystack.valueAt (1-(1))))).obj);
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 120:
  if (yyn == 120)

      /* Line 353 of lalr1.ja  */
/* Line 1358 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: switch_case_list switch_case_define");
		switch_caselist_node p = (switch_caselist_node)((ParserVal)((yystack.valueAt (2-(1))))).obj;
		p.add_case((syntree_node)((ParserVal)((yystack.valueAt (2-(2))))).obj);
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 121:
  if (yyn == 121)

      /* Line 353 of lalr1.ja  */
/* Line 1371 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: CASE cmp_value THEN block");
		switch_case_node p = ((Yylex)yylexer).new_node(switch_case_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (4-(2)))))).ival);
		p.m_cmp = (syntree_node)((ParserVal)((yystack.valueAt (4-(2))))).obj;
		p.m_block = (syntree_node)((ParserVal)((yystack.valueAt (4-(4))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 122:
  if (yyn == 122)

      /* Line 353 of lalr1.ja  */
/* Line 1383 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: CASE cmp_value THEN");
		switch_case_node p = ((Yylex)yylexer).new_node(switch_case_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(2)))))).ival);
		p.m_cmp = (syntree_node)((ParserVal)((yystack.valueAt (3-(2))))).obj;
		p.m_block = null;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 123:
  if (yyn == 123)

      /* Line 353 of lalr1.ja  */
/* Line 1397 of "YYParser.y"  */
    {
	};
  break;
    

  case 124:
  if (yyn == 124)

      /* Line 353 of lalr1.ja  */
/* Line 1401 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: PACKAGE IDENTIFIER ");
		((Yylex)yylexer).get_mybison().set_package(((ParserVal)((yystack.valueAt (2-(2))))).sval);
	};
  break;
    

  case 125:
  if (yyn == 125)

      /* Line 353 of lalr1.ja  */
/* Line 1407 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: PACKAGE IDENTIFIER_DOT ");
		((Yylex)yylexer).get_mybison().set_package(((ParserVal)((yystack.valueAt (2-(2))))).sval);
	};
  break;
    

  case 126:
  if (yyn == 126)

      /* Line 353 of lalr1.ja  */
/* Line 1415 of "YYParser.y"  */
    {
	};
  break;
    

  case 129:
  if (yyn == 129)

      /* Line 353 of lalr1.ja  */
/* Line 1425 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: INCLUDE STRING_DEFINITION ");
		((Yylex)yylexer).get_mybison().add_include(((ParserVal)((yystack.valueAt (2-(2))))).sval);
	};
  break;
    

  case 130:
  if (yyn == 130)

      /* Line 353 of lalr1.ja  */
/* Line 1433 of "YYParser.y"  */
    {
	};
  break;
    

  case 133:
  if (yyn == 133)

      /* Line 353 of lalr1.ja  */
/* Line 1443 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: STRUCT IDENTIFIER struct_mem_declaration END ");
		((Yylex)yylexer).get_mybison().add_struct_desc(((ParserVal)((yystack.valueAt (4-(2))))).sval);
	};
  break;
    

  case 134:
  if (yyn == 134)

      /* Line 353 of lalr1.ja  */
/* Line 1451 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: struct_mem_declaration IDENTIFIER ");
	};
  break;
    

  case 135:
  if (yyn == 135)

      /* Line 353 of lalr1.ja  */
/* Line 1456 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: IDENTIFIER ");
	};
  break;
    

  case 136:
  if (yyn == 136)

      /* Line 353 of lalr1.ja  */
/* Line 1463 of "YYParser.y"  */
    {
	};
  break;
    

  case 139:
  if (yyn == 139)

      /* Line 353 of lalr1.ja  */
/* Line 1473 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: FCONST IDENTIFIER ASSIGN explicit_value ");
		((Yylex)yylexer).get_mybison().add_const_desc(((ParserVal)((yystack.valueAt (4-(2))))).sval, (syntree_node)((ParserVal)((yystack.valueAt (4-(4))))).obj);
	};
  break;
    

  case 140:
  if (yyn == 140)

      /* Line 353 of lalr1.ja  */
/* Line 1481 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: NULL ");
		explicit_value_node p = ((Yylex)yylexer).new_node(explicit_value_node.class, ((Yylex)yylexer).get_mybison().get_jflex().get_line() + 1);
		p.m_type = explicit_value_type.EVT_NULL;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 141:
  if (yyn == 141)

      /* Line 353 of lalr1.ja  */
/* Line 1492 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: FTRUE ");
		explicit_value_node p = ((Yylex)yylexer).new_node(explicit_value_node.class, ((Yylex)yylexer).get_mybison().get_jflex().get_line() + 1);
		p.m_type = explicit_value_type.EVT_TRUE;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 142:
  if (yyn == 142)

      /* Line 353 of lalr1.ja  */
/* Line 1503 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: FFALSE ");
		explicit_value_node p = ((Yylex)yylexer).new_node(explicit_value_node.class, ((Yylex)yylexer).get_mybison().get_jflex().get_line() + 1);
		p.m_type = explicit_value_type.EVT_FALSE;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 143:
  if (yyn == 143)

      /* Line 353 of lalr1.ja  */
/* Line 1514 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: NUMBER ");
		explicit_value_node p = ((Yylex)yylexer).new_node(explicit_value_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).ival);
		p.m_str = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).sval;
		p.m_type = explicit_value_type.EVT_NUM;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 144:
  if (yyn == 144)

      /* Line 353 of lalr1.ja  */
/* Line 1526 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: FKUUID ");
		explicit_value_node p = ((Yylex)yylexer).new_node(explicit_value_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).ival);
		p.m_str = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).sval;
		p.m_type = explicit_value_type.EVT_UUID;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 145:
  if (yyn == 145)

      /* Line 353 of lalr1.ja  */
/* Line 1538 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: STRING_DEFINITION ");
		explicit_value_node p = ((Yylex)yylexer).new_node(explicit_value_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).ival);
		p.m_str = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).sval;
		p.m_type = explicit_value_type.EVT_STR;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 146:
  if (yyn == 146)

      /* Line 353 of lalr1.ja  */
/* Line 1550 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: FKFLOAT ");
		explicit_value_node p = ((Yylex)yylexer).new_node(explicit_value_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).ival);
		p.m_str = ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).sval;
		p.m_type = explicit_value_type.EVT_FLOAT;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 147:
  if (yyn == 147)

      /* Line 353 of lalr1.ja  */
/* Line 1562 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: OPEN_BIG_BRACKET const_map_list_value CLOSE_BIG_BRACKET ");
		explicit_value_node p = ((Yylex)yylexer).new_node(explicit_value_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(2)))))).ival);
		p.m_type = explicit_value_type.EVT_MAP;
		p.m_v = (const_map_list_value_node)((ParserVal)((yystack.valueAt (3-(2))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 148:
  if (yyn == 148)

      /* Line 353 of lalr1.ja  */
/* Line 1574 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: OPEN_BIG_BRACKET const_array_list_value CLOSE_BIG_BRACKET ");
		explicit_value_node p = ((Yylex)yylexer).new_node(explicit_value_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(2)))))).ival);
		p.m_type = explicit_value_type.EVT_ARRAY;
		p.m_v = (const_array_list_value_node)((ParserVal)((yystack.valueAt (3-(2))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 149:
  if (yyn == 149)

      /* Line 353 of lalr1.ja  */
/* Line 1589 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: empty ");
		const_map_list_value_node p = ((Yylex)yylexer).new_node(const_map_list_value_node.class, ((Yylex)yylexer).get_mybison().get_jflex().get_line());
				
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 150:
  if (yyn == 150)

      /* Line 353 of lalr1.ja  */
/* Line 1599 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: const_map_value ");
		const_map_list_value_node p = ((Yylex)yylexer).new_node(const_map_list_value_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).ival);
		p.add_ele((const_map_value_node)((ParserVal)((yystack.valueAt (1-(1))))).obj);
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 151:
  if (yyn == 151)

      /* Line 353 of lalr1.ja  */
/* Line 1610 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: const_map_list_value const_map_value ");
		const_map_list_value_node p = (const_map_list_value_node)((ParserVal)((yystack.valueAt (2-(1))))).obj;
		p.add_ele((const_map_value_node)((ParserVal)((yystack.valueAt (2-(2))))).obj);
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 152:
  if (yyn == 152)

      /* Line 353 of lalr1.ja  */
/* Line 1624 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: explicit_value COLON explicit_value ");
		const_map_value_node p = ((Yylex)yylexer).new_node(const_map_value_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (3-(1)))))).ival);
		p.m_k = (syntree_node)((ParserVal)((yystack.valueAt (3-(1))))).obj;
		p.m_v = (syntree_node)((ParserVal)((yystack.valueAt (3-(3))))).obj;
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 153:
  if (yyn == 153)

      /* Line 353 of lalr1.ja  */
/* Line 1638 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: empty ");
		const_array_list_value_node p = ((Yylex)yylexer).new_node(const_array_list_value_node.class, ((Yylex)yylexer).get_mybison().get_jflex().get_line());
				
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 154:
  if (yyn == 154)

      /* Line 353 of lalr1.ja  */
/* Line 1648 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: explicit_value ");
		const_array_list_value_node p = ((Yylex)yylexer).new_node(const_array_list_value_node.class, ((ParserVal)((ParserVal)((yystack.valueAt (1-(1)))))).ival);
		p.add_ele((explicit_value_node)((ParserVal)((yystack.valueAt (1-(1))))).obj);
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;
    

  case 155:
  if (yyn == 155)

      /* Line 353 of lalr1.ja  */
/* Line 1659 of "YYParser.y"  */
    {
		types.log(((Yylex)yylexer).get_mybison().get_fake(), "[BISON]: const_array_list_value explicit_value ");
		const_array_list_value_node p = (const_array_list_value_node)((ParserVal)((yystack.valueAt (2-(1))))).obj;
		p.add_ele((explicit_value_node)((ParserVal)((yystack.valueAt (2-(2))))).obj);
		
		ParserVal ret = new ParserVal(p);
		ret.ival = p.m_lno;
		yyval = ret;
	};
  break;




          /* Line 353 of lalr1.ja  */
          /* Line 2738 of "YYParser.ja"  */
	default: break;
      }

    yy_symbol_print ("-> $$ =", yyr1_[yyn], yyval);

    yystack.pop (yylen);
    yylen = 0;

    /* Shift the result of the reduction.  */
    yyn = yyr1_[yyn];
    int yystate = yypgoto_[yyn - yyntokens_] + yystack.stateAt (0);
    if (0 <= yystate && yystate <= yylast_
	&& yycheck_[yystate] == yystack.stateAt (0))
      yystate = yytable_[yystate];
    else
      yystate = yydefgoto_[yyn - yyntokens_];

    yystack.push (yystate, yyval);
    return YYNEWSTATE;
  }

  /* Return YYSTR after stripping away unnecessary quotes and
     backslashes, so that it's suitable for yyerror.  The heuristic is
     that double-quoting is unnecessary unless the string contains an
     apostrophe, a comma, or backslash (other than backslash-backslash).
     YYSTR is taken from yytname.  */
  private final String yytnamerr_ (String yystr)
  {
    if (yystr.charAt (0) == '"')
      {
        StringBuffer yyr = new StringBuffer ();
        strip_quotes: for (int i = 1; i < yystr.length (); i++)
          switch (yystr.charAt (i))
            {
            case '\'':
            case ',':
              break strip_quotes;

            case '\\':
	      if (yystr.charAt(++i) != '\\')
                break strip_quotes;
              /* Fall through.  */
            default:
              yyr.append (yystr.charAt (i));
              break;

            case '"':
              return yyr.toString ();
            }
      }
    else if (yystr.equals ("$end"))
      return "end of input";

    return yystr;
  }

  /*--------------------------------.
  | Print this symbol on YYOUTPUT.  |
  `--------------------------------*/

  private void yy_symbol_print (String s, int yytype,
			         Object yyvaluep				 )
  {
    if (yydebug > 0)
    yycdebug (s + (yytype < yyntokens_ ? " token " : " nterm ")
	      + yytname_[yytype] + " ("
	      + (yyvaluep == null ? "(null)" : yyvaluep.toString ()) + ")");
  }

  /**
   * Parse input from the scanner that was specified at object construction
   * time.  Return whether the end of the input was reached successfully.
   *
   * @return <tt>true</tt> if the parsing succeeds.  Note that this does not
   *          imply that there were no syntax errors.
   */
  public boolean parse () throws IOException
  {
    /// Lookahead and lookahead in internal form.
    int yychar = yyempty_;
    int yytoken = 0;

    /* State.  */
    int yyn = 0;
    int yylen = 0;
    int yystate = 0;

    YYStack yystack = new YYStack ();

    /* Error handling.  */
    int yynerrs_ = 0;
    

    /// Semantic value of the lookahead.
    Object yylval = null;

    int yyresult;

    yycdebug ("Starting parse\n");
    yyerrstatus_ = 0;


    /* Initialize the stack.  */
    yystack.push (yystate, yylval);

    int label = YYNEWSTATE;
    for (;;)
      switch (label)
      {
        /* New state.  Unlike in the C/C++ skeletons, the state is already
	   pushed when we come here.  */
      case YYNEWSTATE:
        yycdebug ("Entering state " + yystate + "\n");
        if (yydebug > 0)
          yystack.print (yyDebugStream);
    
        /* Accept?  */
        if (yystate == yyfinal_)
          return true;
    
        /* Take a decision.  First try without lookahead.  */
        yyn = yypact_[yystate];
        if (yyn == yypact_ninf_)
          {
            label = YYDEFAULT;
	    break;
          }
    
        /* Read a lookahead token.  */
        if (yychar == yyempty_)
          {
	    yycdebug ("Reading a token: ");
	    yychar = yylex ();
            
            yylval = yylexer.getLVal ();
          }
    
        /* Convert token to internal form.  */
        if (yychar <= EOF)
          {
	    yychar = yytoken = EOF;
	    yycdebug ("Now at end of input.\n");
          }
        else
          {
	    yytoken = yytranslate_ (yychar);
	    yy_symbol_print ("Next token is", yytoken,
	    		     yylval);
          }
    
        /* If the proper action on seeing token YYTOKEN is to reduce or to
           detect an error, take that action.  */
        yyn += yytoken;
        if (yyn < 0 || yylast_ < yyn || yycheck_[yyn] != yytoken)
          label = YYDEFAULT;
    
        /* <= 0 means reduce or error.  */
        else if ((yyn = yytable_[yyn]) <= 0)
          {
	    if (yyn == 0 || yyn == yytable_ninf_)
	      label = YYFAIL;
	    else
	      {
	        yyn = -yyn;
	        label = YYREDUCE;
	      }
          }
    
        else
          {
            /* Shift the lookahead token.  */
	    yy_symbol_print ("Shifting", yytoken,
	    		     yylval);
    
            /* Discard the token being shifted.  */
            yychar = yyempty_;
    
            /* Count tokens shifted since error; after three, turn off error
               status.  */
            if (yyerrstatus_ > 0)
              --yyerrstatus_;
    
            yystate = yyn;
            yystack.push (yystate, yylval);
            label = YYNEWSTATE;
          }
        break;
    
      /*-----------------------------------------------------------.
      | yydefault -- do the default action for the current state.  |
      `-----------------------------------------------------------*/
      case YYDEFAULT:
        yyn = yydefact_[yystate];
        if (yyn == 0)
          label = YYFAIL;
        else
          label = YYREDUCE;
        break;
    
      /*-----------------------------.
      | yyreduce -- Do a reduction.  |
      `-----------------------------*/
      case YYREDUCE:
        yylen = yyr2_[yyn];
        label = yyaction (yyn, yystack, yylen);
	yystate = yystack.stateAt (0);
        break;
    
      /*------------------------------------.
      | yyerrlab -- here on detecting error |
      `------------------------------------*/
      case YYFAIL:
        /* If not already recovering from an error, report this error.  */
        if (yyerrstatus_ == 0)
          {
	    ++yynerrs_;
	    yyerror (yysyntax_error (yystate, yytoken));
          }
    
        
        if (yyerrstatus_ == 3)
          {
	    /* If just tried and failed to reuse lookahead token after an
	     error, discard it.  */
    
	    if (yychar <= EOF)
	      {
	      /* Return failure if at end of input.  */
	      if (yychar == EOF)
	        return false;
	      }
	    else
	      yychar = yyempty_;
          }
    
        /* Else will try to reuse lookahead token after shifting the error
           token.  */
        label = YYERRLAB1;
        break;
    
      /*---------------------------------------------------.
      | errorlab -- error raised explicitly by YYERROR.  |
      `---------------------------------------------------*/
      case YYERROR:
    
        
        /* Do not reclaim the symbols of the rule which action triggered
           this YYERROR.  */
        yystack.pop (yylen);
        yylen = 0;
        yystate = yystack.stateAt (0);
        label = YYERRLAB1;
        break;
    
      /*-------------------------------------------------------------.
      | yyerrlab1 -- common code for both syntax error and YYERROR.  |
      `-------------------------------------------------------------*/
      case YYERRLAB1:
        yyerrstatus_ = 3;	/* Each real token shifted decrements this.  */
    
        for (;;)
          {
	    yyn = yypact_[yystate];
	    if (yyn != yypact_ninf_)
	      {
	        yyn += yyterror_;
	        if (0 <= yyn && yyn <= yylast_ && yycheck_[yyn] == yyterror_)
	          {
	            yyn = yytable_[yyn];
	            if (0 < yyn)
		      break;
	          }
	      }
    
	    /* Pop the current state because it cannot handle the error token.  */
	    if (yystack.height == 1)
	      return false;
    
	    
	    yystack.pop ();
	    yystate = yystack.stateAt (0);
	    if (yydebug > 0)
	      yystack.print (yyDebugStream);
          }
    
	

        /* Shift the error token.  */
        yy_symbol_print ("Shifting", yystos_[yyn],
			 yylval);
    
        yystate = yyn;
	yystack.push (yyn, yylval);
        label = YYNEWSTATE;
        break;
    
        /* Accept.  */
      case YYACCEPT:
        return true;
    
        /* Abort.  */
      case YYABORT:
        return false;
      }
  }

  // Generate an error message.
  private String yysyntax_error (int yystate, int tok)
  {
    if (errorVerbose)
      {
        int yyn = yypact_[yystate];
        if (yypact_ninf_ < yyn && yyn <= yylast_)
          {
	    StringBuffer res;

	    /* Start YYX at -YYN if negative to avoid negative indexes in
	       YYCHECK.  */
	    int yyxbegin = yyn < 0 ? -yyn : 0;

	    /* Stay within bounds of both yycheck and yytname.  */
	    int yychecklim = yylast_ - yyn + 1;
	    int yyxend = yychecklim < yyntokens_ ? yychecklim : yyntokens_;
	    int count = 0;
	    for (int x = yyxbegin; x < yyxend; ++x)
	      if (yycheck_[x + yyn] == x && x != yyterror_)
	        ++count;

	    // FIXME: This method of building the message is not compatible
	    // with internationalization.
	    res = new StringBuffer ("syntax error, unexpected ");
	    res.append (yytnamerr_ (yytname_[tok]));
	    if (count < 5)
	      {
	        count = 0;
	        for (int x = yyxbegin; x < yyxend; ++x)
	          if (yycheck_[x + yyn] == x && x != yyterror_)
		    {
		      res.append (count++ == 0 ? ", expecting " : " or ");
		      res.append (yytnamerr_ (yytname_[x]));
		    }
	      }
	    return res.toString ();
          }
      }

    return "syntax error";
  }


  /* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
     STATE-NUM.  */
  private static final short yypact_ninf_ = -210;
  private static final short yypact_[] =
  {
       -33,   -11,    49,    16,  -210,  -210,  -210,    56,   103,  -210,
    -210,    79,  -210,   107,  -210,    87,   105,  -210,    -1,  -210,
    -210,   148,   122,   139,   158,  -210,  -210,  -210,  -210,   392,
     159,  -210,  -210,  -210,  -210,  -210,  -210,  -210,   392,   392,
    -210,  -210,   174,  -210,   197,   178,   345,  -210,  -210,    -5,
    -210,  -210,  -210,   392,  -210,  -210,   174,   511,  -210,  -210,
     195,  1223,  -210,    22,    22,  -210,    14,  1223,  1177,    46,
     190,  -210,  -210,  1232,  1232,  1223,    21,   577,  -210,  -210,
    -210,  -210,  -210,  -210,  -210,  -210,  -210,    65,  -210,     1,
     498,  -210,   101,   344,  -210,  -210,  -210,  -210,  -210,  -210,
    -210,   204,  -210,   216,  -210,   445,   169,   277,    22,  1223,
    1223,    58,   296,   216,  -210,   445,    97,  1232,  1232,   186,
     198,   295,   595,     2,    17,   186,  1232,  1232,    17,  -210,
     344,   344,   171,  1232,   214,  -210,  -210,     4,    46,    46,
    1223,  1223,  1223,  1223,  1223,  1223,  1223,   220,  -210,  1232,
    1232,  1232,  1232,  1232,  1232,  1223,   257,   198,  -210,  -210,
     661,    22,    22,  1223,  1223,  1223,  1223,  1223,  1223,   679,
      53,  -210,   344,   399,  -210,  -210,    22,  1223,    68,   208,
    1223,   156,  -210,   125,   210,   201,  -210,  -210,  -210,    17,
      17,  -210,   216,  -210,   445,  -210,  -210,  -210,  -210,  -210,
    -210,   217,   193,   264,    60,    10,    -6,  -210,  -210,  -210,
    -210,   745,    95,    95,  -210,  -210,  -210,  -210,  -210,  -210,
      22,   679,    -9,  -210,  1232,  -210,  -210,   227,   189,  -210,
     251,   811,  -210,  -210,  1232,  1232,  -210,   134,    -9,  1177,
    -210,   266,  -210,  1177,  1223,  1177,  -210,   877,   160,   188,
    1177,   270,  1177,  -210,   895,   245,  1177,  -210,  -210,  -210,
    1177,  -210,   961,  1232,  -210,  1027,     0,  -210,  1093,  -210,
    1159,  -210
  };

  /* YYDEFACT[S] -- default rule to reduce with in state S when YYTABLE
     doesn't specify something else to do.  Zero means the default is an
     error.  */
  private static final short yydefact_[] =
  {
       123,     0,     0,   126,   124,   125,     1,     0,   130,   127,
     129,     0,   128,   136,   131,     0,     0,   132,     3,   137,
     135,     0,     0,     0,     2,     4,   138,   133,   134,     0,
       0,     5,   141,   142,   145,   143,   146,   144,   153,   149,
     140,   139,     8,   154,     0,     0,     0,   150,    11,     0,
      10,   148,   155,     0,   147,   151,     0,     0,   152,     9,
       0,    72,   113,     0,     0,     7,    95,     0,     0,     0,
      98,    97,   114,     0,     0,     0,   100,     0,    22,    34,
      32,    33,    23,    24,    25,    26,    27,     0,    31,    83,
     112,    30,   101,     0,    28,    29,    35,    36,    37,   110,
      93,    71,    74,    76,    77,    75,   141,   142,     0,     0,
       0,     0,     0,    69,    70,    68,     0,    17,     0,   112,
       0,   101,     0,    83,    38,     0,    17,     0,   111,   109,
     116,   115,     0,    17,     0,     6,    21,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    92,     0,
       0,     0,     0,     0,     0,     0,     0,    70,    66,    67,
       0,     0,     0,     0,     0,     0,     0,     0,     0,    47,
       0,    19,    20,     0,    99,   102,     0,     0,     0,   109,
       0,     0,   119,     0,     0,    95,    98,    82,    94,    80,
      81,    78,    85,    86,    84,    79,    87,    88,    89,    90,
      91,     0,   107,   103,   104,   106,   105,   108,    73,    55,
      44,     0,    56,    57,    59,    58,    61,    62,    60,    63,
       0,    47,    52,    49,     0,    12,    96,     0,    78,    13,
       0,     0,   120,    14,    17,    17,    43,     0,    52,    54,
      48,     0,    18,     0,     0,   122,   118,     0,     0,     0,
      51,     0,    53,    46,     0,     0,   121,   117,    16,    15,
      50,    45,     0,     0,    40,     0,     0,    39,     0,    42,
       0,    41
  };

  /* YYPGOTO[NTERM-NUM].  */
  private static final short yypgoto_[] =
  {
      -210,  -210,  -210,   256,  -210,   238,   181,  -125,    73,   -17,
     127,  -210,  -210,  -210,  -210,  -210,    91,  -209,    90,   -58,
     267,  -210,  -210,   184,  -210,  -210,  -210,   129,  -210,   -66,
      32,   332,   151,   219,  -210,  -210,  -210,  -210,  -210,  -210,
     162,  -210,  -210,   333,  -210,   327,  -210,  -210,   326,   -29,
    -210,   300,  -210
  };

  /* YYDEFGOTO[NTERM-NUM].  */
  private static final short
  yydefgoto_[] =
  {
        -1,     2,    24,    25,    49,    50,    76,   170,   171,    77,
      78,    79,    80,    81,    82,    83,   222,   223,   241,   111,
     112,    84,   101,   102,    85,    86,    87,   191,    88,    89,
      90,    91,    92,    93,    94,    95,    96,    97,    98,   181,
     182,     3,     8,     9,    13,    14,    21,    18,    19,    99,
      46,    47,    44
  };

  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  private static final short yytable_ninf_ = -113;
  private static final short
  yytable_[] =
  {
        41,   178,   123,   239,     4,    23,   116,    60,   183,    43,
      45,   268,   149,   240,    56,    52,     1,    45,   149,   185,
     150,   151,   152,   153,    58,   140,   177,    57,   149,   240,
     106,   107,   105,   153,   115,   115,    34,    66,    35,  -111,
       5,  -111,  -111,  -111,  -111,   117,   115,    16,   133,     6,
     156,   122,   133,   108,   220,   186,    71,    36,   134,   154,
     118,    66,   134,   141,   141,   154,     7,    37,    38,   160,
      10,   187,   224,    70,    71,   154,   109,   110,   149,   115,
     115,   115,   152,   153,   137,   225,  -111,   224,    39,   138,
      40,   161,   162,   103,    15,   113,   113,    70,    71,   119,
     229,   125,    20,   212,   213,   119,   119,   113,   169,   248,
     249,   194,   194,   194,   194,   194,   194,   194,   227,  -109,
      22,  -109,  -109,  -109,  -109,   154,   105,   139,   161,   162,
     161,   162,   115,   115,   115,   115,   115,   115,   115,   115,
     113,   113,   113,   211,   224,   250,    29,   115,   194,   119,
     119,   115,   221,     7,    30,    16,    11,   233,   119,   119,
      11,    27,   237,    28,    23,   119,  -109,   161,   162,   188,
     125,   125,   192,   192,   192,   192,   192,   192,   192,   224,
     -64,   119,   119,   119,   119,   119,   119,   103,   -64,    48,
      42,   115,   258,   113,   113,   113,   113,   113,   113,   113,
     113,   -64,   -64,   -64,   136,    32,    33,   224,   113,   192,
     100,    34,   113,    35,   247,   115,   180,   231,   121,    53,
     259,   126,   252,   155,   129,   129,   254,   147,   256,   184,
     174,   180,    36,   260,  -112,   201,  -112,  -112,  -112,  -112,
     175,   234,    37,    38,    51,   265,   243,   118,   235,   136,
     124,   270,   113,   244,   128,   128,   119,   147,   154,   121,
     161,   162,   245,    39,   263,    40,   119,   119,   129,   129,
     195,   196,   197,   198,   199,   200,   113,   129,   179,   253,
      31,  -112,   149,   261,   129,   151,   152,   153,   -65,   209,
     161,   162,   130,   131,    59,   119,   -65,   242,   128,   128,
     129,   129,   129,   129,   129,   129,   228,   128,   128,   -65,
     -65,   -65,   238,  -109,   128,  -109,  -109,  -109,  -109,   189,
     190,   163,   164,   165,   166,   167,   168,   175,   251,   154,
     128,   128,   128,   128,   128,   128,   172,   173,   136,   208,
      17,    12,   132,   232,    26,   172,    55,     0,   136,     0,
       0,     0,   172,    32,    33,     0,     0,     0,     0,    34,
    -109,    35,   149,     0,   150,   151,   152,   153,   202,   203,
     204,   205,   206,   207,   136,   129,   158,   159,     0,   136,
      36,   136,     0,   136,     0,   129,   129,   136,     0,     0,
      37,    38,   136,   104,     0,   114,   114,   136,     0,   120,
      32,    33,     0,     0,     0,   128,    34,   114,    35,   154,
       0,    39,    54,    40,   129,   128,   128,   149,     0,   150,
     151,   152,   153,     0,     0,     0,     0,    36,     0,     0,
     214,   215,   216,   217,   218,   219,     0,    37,    38,     0,
     157,   114,   114,   172,   128,     0,   226,   230,     0,     0,
       0,     0,     0,   172,   172,     0,     0,     0,    39,     0,
      40,     0,     0,  -110,   154,  -110,  -110,  -110,  -110,     0,
       0,     0,   193,   193,   193,   193,   193,   193,   193,     0,
       0,     0,   266,     0,     0,     0,     0,   104,     0,     0,
       0,     0,     0,   114,   114,   114,   114,   114,   114,   114,
     114,     0,     0,     0,     0,     0,     0,     0,   114,   193,
    -110,   255,   114,     0,    60,    61,    62,   -94,    63,    32,
      33,    64,   -94,     0,    65,    34,    66,    35,     0,     0,
       0,     0,     0,     0,   142,   143,   144,   145,   146,   147,
       0,   148,    67,     0,     0,     0,    36,     0,     0,     0,
       0,     0,   114,    68,     0,    69,    37,    38,     0,     0,
     -94,     0,    70,    71,     0,     0,     0,    72,    73,    74,
      75,     0,     0,     0,     0,     0,   114,    39,     0,    40,
      60,    61,    62,     0,    63,    32,    33,    64,     0,     0,
     135,    34,    66,    35,     0,     0,     0,     0,    60,    61,
      62,     0,    63,    32,    33,    64,     0,     0,    67,    34,
      66,    35,    36,     0,   176,     0,     0,     0,     0,    68,
       0,    69,    37,    38,     0,     0,    67,     0,    70,    71,
      36,     0,     0,    72,    73,    74,    75,    68,     0,    69,
      37,    38,     0,    39,     0,    40,    70,    71,     0,     0,
       0,    72,    73,    74,    75,     0,     0,     0,     0,     0,
       0,    39,     0,    40,    60,    61,    62,     0,    63,    32,
      33,    64,     0,     0,   210,    34,    66,    35,     0,     0,
       0,     0,    60,    61,    62,     0,    63,    32,    33,    64,
       0,     0,    67,    34,    66,    35,    36,     0,     0,     0,
       0,     0,     0,    68,     0,    69,    37,    38,     0,     0,
      67,     0,    70,    71,    36,     0,     0,    72,    73,    74,
      75,    68,     0,    69,    37,    38,     0,    39,     0,    40,
      70,    71,     0,     0,     0,    72,    73,    74,    75,     0,
       0,     0,   220,     0,     0,    39,     0,    40,    60,    61,
      62,     0,    63,    32,    33,    64,     0,     0,   236,    34,
      66,    35,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,    67,     0,     0,     0,
      36,     0,     0,     0,     0,     0,     0,    68,     0,    69,
      37,    38,     0,     0,     0,     0,    70,    71,     0,     0,
       0,    72,    73,    74,    75,     0,     0,     0,     0,     0,
       0,    39,     0,    40,    60,    61,    62,     0,    63,    32,
      33,    64,     0,     0,   246,    34,    66,    35,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,    67,     0,     0,     0,    36,     0,     0,     0,
       0,     0,     0,    68,     0,    69,    37,    38,     0,     0,
       0,     0,    70,    71,     0,     0,     0,    72,    73,    74,
      75,     0,     0,     0,     0,     0,     0,    39,     0,    40,
      60,    61,    62,     0,    63,    32,    33,    64,     0,     0,
     257,    34,    66,    35,     0,     0,     0,     0,    60,    61,
      62,     0,    63,    32,    33,    64,   262,     0,    67,    34,
      66,    35,    36,     0,     0,     0,     0,     0,     0,    68,
       0,    69,    37,    38,     0,     0,    67,     0,    70,    71,
      36,     0,     0,    72,    73,    74,    75,    68,     0,    69,
      37,    38,     0,    39,     0,    40,    70,    71,     0,     0,
       0,    72,    73,    74,    75,     0,     0,     0,     0,     0,
       0,    39,     0,    40,    60,    61,    62,     0,    63,    32,
      33,    64,     0,     0,   264,    34,    66,    35,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,    67,     0,     0,     0,    36,     0,     0,     0,
       0,     0,     0,    68,     0,    69,    37,    38,     0,     0,
       0,     0,    70,    71,     0,     0,     0,    72,    73,    74,
      75,     0,     0,     0,     0,     0,     0,    39,     0,    40,
      60,    61,    62,     0,    63,    32,    33,    64,     0,     0,
     267,    34,    66,    35,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    67,     0,
       0,     0,    36,     0,     0,     0,     0,     0,     0,    68,
       0,    69,    37,    38,     0,     0,     0,     0,    70,    71,
       0,     0,     0,    72,    73,    74,    75,     0,     0,     0,
       0,     0,     0,    39,     0,    40,    60,    61,    62,     0,
      63,    32,    33,    64,     0,     0,   269,    34,    66,    35,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,    67,     0,     0,     0,    36,     0,
       0,     0,     0,     0,     0,    68,     0,    69,    37,    38,
       0,     0,     0,     0,    70,    71,     0,     0,     0,    72,
      73,    74,    75,     0,     0,     0,     0,     0,     0,    39,
       0,    40,    60,    61,    62,     0,    63,    32,    33,    64,
       0,     0,   271,    34,    66,    35,     0,     0,     0,     0,
      60,    61,    62,     0,    63,    32,    33,    64,     0,     0,
      67,    34,    66,    35,    36,     0,     0,     0,     0,     0,
       0,    68,     0,    69,    37,    38,     0,     0,    67,     0,
      70,    71,    36,     0,     0,    72,    73,    74,    75,    68,
       0,    69,    37,    38,     0,    39,     0,    40,    70,    71,
       0,    32,    33,    72,    73,    74,    75,    34,    66,    35,
      32,    33,     0,    39,     0,    40,    34,    66,    35,     0,
       0,     0,     0,     0,    67,     0,     0,     0,    36,     0,
       0,     0,     0,   127,     0,     0,     0,    36,    37,    38,
       0,     0,     0,     0,    70,    71,     0,    37,    38,     0,
       0,     0,     0,    70,    71,     0,     0,     0,     0,    39,
       0,    40,     0,     0,     0,     0,     0,     0,    39,     0,
      40
  };

  /* YYCHECK.  */
  private static final short
  yycheck_[] =
  {
        29,   126,    68,    12,    15,     6,    64,     3,   133,    38,
      39,    11,    18,   222,    19,    44,    49,    46,    18,    15,
      20,    21,    22,    23,    53,    24,    24,    32,    18,   238,
       8,     9,    61,    23,    63,    64,    14,    15,    16,    18,
      51,    20,    21,    22,    23,    31,    75,    48,    31,     0,
     108,    68,    31,    31,    63,    51,    52,    35,    41,    65,
      46,    15,    41,    62,    62,    65,    50,    45,    46,    11,
      14,   137,    19,    51,    52,    65,    54,    55,    18,   108,
     109,   110,    22,    23,    19,    32,    65,    19,    66,    24,
      68,    33,    34,    61,    15,    63,    64,    51,    52,    67,
      32,    69,    15,   161,   162,    73,    74,    75,    11,   234,
     235,   140,   141,   142,   143,   144,   145,   146,   176,    18,
      15,    20,    21,    22,    23,    65,   155,    62,    33,    34,
      33,    34,   161,   162,   163,   164,   165,   166,   167,   168,
     108,   109,   110,   160,    19,    11,    24,   176,   177,   117,
     118,   180,   169,    50,    15,    48,    53,    32,   126,   127,
      53,    13,   220,    15,     6,   133,    65,    33,    34,   137,
     138,   139,   140,   141,   142,   143,   144,   145,   146,    19,
      11,   149,   150,   151,   152,   153,   154,   155,    19,    15,
      31,   220,    32,   161,   162,   163,   164,   165,   166,   167,
     168,    32,    33,    34,    77,     8,     9,    19,   176,   177,
      15,    14,   180,    16,   231,   244,    60,    61,    67,    41,
      32,    31,   239,    19,    73,    74,   243,    41,   245,    15,
      32,    60,    35,   250,    18,    15,    20,    21,    22,    23,
      32,    31,    45,    46,    47,   262,    19,    46,    31,   122,
      69,   268,   220,    64,    73,    74,   224,    41,    65,   108,
      33,    34,    11,    66,    19,    68,   234,   235,   117,   118,
     141,   142,   143,   144,   145,   146,   244,   126,   127,    13,
      24,    65,    18,    13,   133,    21,    22,    23,    11,    32,
      33,    34,    73,    74,    56,   263,    19,   224,   117,   118,
     149,   150,   151,   152,   153,   154,   177,   126,   127,    32,
      33,    34,   221,    18,   133,    20,    21,    22,    23,   138,
     139,    25,    26,    27,    28,    29,    30,    32,   238,    65,
     149,   150,   151,   152,   153,   154,   117,   118,   211,   155,
      13,     8,    75,   181,    18,   126,    46,    -1,   221,    -1,
      -1,    -1,   133,     8,     9,    -1,    -1,    -1,    -1,    14,
      65,    16,    18,    -1,    20,    21,    22,    23,   149,   150,
     151,   152,   153,   154,   247,   224,   109,   110,    -1,   252,
      35,   254,    -1,   256,    -1,   234,   235,   260,    -1,    -1,
      45,    46,   265,    61,    -1,    63,    64,   270,    -1,    67,
       8,     9,    -1,    -1,    -1,   224,    14,    75,    16,    65,
      -1,    66,    67,    68,   263,   234,   235,    18,    -1,    20,
      21,    22,    23,    -1,    -1,    -1,    -1,    35,    -1,    -1,
     163,   164,   165,   166,   167,   168,    -1,    45,    46,    -1,
     108,   109,   110,   224,   263,    -1,    47,   180,    -1,    -1,
      -1,    -1,    -1,   234,   235,    -1,    -1,    -1,    66,    -1,
      68,    -1,    -1,    18,    65,    20,    21,    22,    23,    -1,
      -1,    -1,   140,   141,   142,   143,   144,   145,   146,    -1,
      -1,    -1,   263,    -1,    -1,    -1,    -1,   155,    -1,    -1,
      -1,    -1,    -1,   161,   162,   163,   164,   165,   166,   167,
     168,    -1,    -1,    -1,    -1,    -1,    -1,    -1,   176,   177,
      65,   244,   180,    -1,     3,     4,     5,    19,     7,     8,
       9,    10,    24,    -1,    13,    14,    15,    16,    -1,    -1,
      -1,    -1,    -1,    -1,    36,    37,    38,    39,    40,    41,
      -1,    43,    31,    -1,    -1,    -1,    35,    -1,    -1,    -1,
      -1,    -1,   220,    42,    -1,    44,    45,    46,    -1,    -1,
      62,    -1,    51,    52,    -1,    -1,    -1,    56,    57,    58,
      59,    -1,    -1,    -1,    -1,    -1,   244,    66,    -1,    68,
       3,     4,     5,    -1,     7,     8,     9,    10,    -1,    -1,
      13,    14,    15,    16,    -1,    -1,    -1,    -1,     3,     4,
       5,    -1,     7,     8,     9,    10,    -1,    -1,    31,    14,
      15,    16,    35,    -1,    19,    -1,    -1,    -1,    -1,    42,
      -1,    44,    45,    46,    -1,    -1,    31,    -1,    51,    52,
      35,    -1,    -1,    56,    57,    58,    59,    42,    -1,    44,
      45,    46,    -1,    66,    -1,    68,    51,    52,    -1,    -1,
      -1,    56,    57,    58,    59,    -1,    -1,    -1,    -1,    -1,
      -1,    66,    -1,    68,     3,     4,     5,    -1,     7,     8,
       9,    10,    -1,    -1,    13,    14,    15,    16,    -1,    -1,
      -1,    -1,     3,     4,     5,    -1,     7,     8,     9,    10,
      -1,    -1,    31,    14,    15,    16,    35,    -1,    -1,    -1,
      -1,    -1,    -1,    42,    -1,    44,    45,    46,    -1,    -1,
      31,    -1,    51,    52,    35,    -1,    -1,    56,    57,    58,
      59,    42,    -1,    44,    45,    46,    -1,    66,    -1,    68,
      51,    52,    -1,    -1,    -1,    56,    57,    58,    59,    -1,
      -1,    -1,    63,    -1,    -1,    66,    -1,    68,     3,     4,
       5,    -1,     7,     8,     9,    10,    -1,    -1,    13,    14,
      15,    16,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    31,    -1,    -1,    -1,
      35,    -1,    -1,    -1,    -1,    -1,    -1,    42,    -1,    44,
      45,    46,    -1,    -1,    -1,    -1,    51,    52,    -1,    -1,
      -1,    56,    57,    58,    59,    -1,    -1,    -1,    -1,    -1,
      -1,    66,    -1,    68,     3,     4,     5,    -1,     7,     8,
       9,    10,    -1,    -1,    13,    14,    15,    16,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    31,    -1,    -1,    -1,    35,    -1,    -1,    -1,
      -1,    -1,    -1,    42,    -1,    44,    45,    46,    -1,    -1,
      -1,    -1,    51,    52,    -1,    -1,    -1,    56,    57,    58,
      59,    -1,    -1,    -1,    -1,    -1,    -1,    66,    -1,    68,
       3,     4,     5,    -1,     7,     8,     9,    10,    -1,    -1,
      13,    14,    15,    16,    -1,    -1,    -1,    -1,     3,     4,
       5,    -1,     7,     8,     9,    10,    11,    -1,    31,    14,
      15,    16,    35,    -1,    -1,    -1,    -1,    -1,    -1,    42,
      -1,    44,    45,    46,    -1,    -1,    31,    -1,    51,    52,
      35,    -1,    -1,    56,    57,    58,    59,    42,    -1,    44,
      45,    46,    -1,    66,    -1,    68,    51,    52,    -1,    -1,
      -1,    56,    57,    58,    59,    -1,    -1,    -1,    -1,    -1,
      -1,    66,    -1,    68,     3,     4,     5,    -1,     7,     8,
       9,    10,    -1,    -1,    13,    14,    15,    16,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    31,    -1,    -1,    -1,    35,    -1,    -1,    -1,
      -1,    -1,    -1,    42,    -1,    44,    45,    46,    -1,    -1,
      -1,    -1,    51,    52,    -1,    -1,    -1,    56,    57,    58,
      59,    -1,    -1,    -1,    -1,    -1,    -1,    66,    -1,    68,
       3,     4,     5,    -1,     7,     8,     9,    10,    -1,    -1,
      13,    14,    15,    16,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    31,    -1,
      -1,    -1,    35,    -1,    -1,    -1,    -1,    -1,    -1,    42,
      -1,    44,    45,    46,    -1,    -1,    -1,    -1,    51,    52,
      -1,    -1,    -1,    56,    57,    58,    59,    -1,    -1,    -1,
      -1,    -1,    -1,    66,    -1,    68,     3,     4,     5,    -1,
       7,     8,     9,    10,    -1,    -1,    13,    14,    15,    16,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    31,    -1,    -1,    -1,    35,    -1,
      -1,    -1,    -1,    -1,    -1,    42,    -1,    44,    45,    46,
      -1,    -1,    -1,    -1,    51,    52,    -1,    -1,    -1,    56,
      57,    58,    59,    -1,    -1,    -1,    -1,    -1,    -1,    66,
      -1,    68,     3,     4,     5,    -1,     7,     8,     9,    10,
      -1,    -1,    13,    14,    15,    16,    -1,    -1,    -1,    -1,
       3,     4,     5,    -1,     7,     8,     9,    10,    -1,    -1,
      31,    14,    15,    16,    35,    -1,    -1,    -1,    -1,    -1,
      -1,    42,    -1,    44,    45,    46,    -1,    -1,    31,    -1,
      51,    52,    35,    -1,    -1,    56,    57,    58,    59,    42,
      -1,    44,    45,    46,    -1,    66,    -1,    68,    51,    52,
      -1,     8,     9,    56,    57,    58,    59,    14,    15,    16,
       8,     9,    -1,    66,    -1,    68,    14,    15,    16,    -1,
      -1,    -1,    -1,    -1,    31,    -1,    -1,    -1,    35,    -1,
      -1,    -1,    -1,    31,    -1,    -1,    -1,    35,    45,    46,
      -1,    -1,    -1,    -1,    51,    52,    -1,    45,    46,    -1,
      -1,    -1,    -1,    51,    52,    -1,    -1,    -1,    -1,    66,
      -1,    68,    -1,    -1,    -1,    -1,    -1,    -1,    66,    -1,
      68
  };

  /* STOS_[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
  private static final byte
  yystos_[] =
  {
         0,    49,    70,   110,    15,    51,     0,    50,   111,   112,
      14,    53,   112,   113,   114,    15,    48,   114,   116,   117,
      15,   115,    15,     6,    71,    72,   117,    13,    15,    24,
      15,    72,     8,     9,    14,    16,    35,    45,    46,    66,
      68,   118,    31,   118,   121,   118,   119,   120,    15,    73,
      74,    47,   118,    41,    67,   120,    19,    32,   118,    74,
       3,     4,     5,     7,    10,    13,    15,    31,    42,    44,
      51,    52,    56,    57,    58,    59,    75,    78,    79,    80,
      81,    82,    83,    84,    90,    93,    94,    95,    97,    98,
      99,   100,   101,   102,   103,   104,   105,   106,   107,   118,
      15,    91,    92,    99,   100,   118,     8,     9,    31,    54,
      55,    88,    89,    99,   100,   118,    88,    31,    46,    99,
     100,   101,    78,    98,    75,    99,    31,    31,    75,   101,
     102,   102,    89,    31,    41,    13,    79,    19,    24,    62,
      24,    62,    36,    37,    38,    39,    40,    41,    43,    18,
      20,    21,    22,    23,    65,    19,    88,   100,    89,    89,
      11,    33,    34,    25,    26,    27,    28,    29,    30,    11,
      76,    77,   102,   102,    32,    32,    19,    24,    76,   101,
      60,   108,   109,    76,    15,    15,    51,    98,    99,    75,
      75,    96,    99,   100,   118,    96,    96,    96,    96,    96,
      96,    15,   102,   102,   102,   102,   102,   102,    92,    32,
      13,    78,    88,    88,    89,    89,    89,    89,    89,    89,
      63,    78,    85,    86,    19,    32,    47,    88,    96,    32,
      89,    61,   109,    32,    31,    31,    13,    88,    85,    12,
      86,    87,    77,    19,    64,    11,    13,    78,    76,    76,
      11,    87,    78,    13,    78,    89,    78,    13,    32,    32,
      78,    13,    11,    19,    13,    78,   102,    13,    11,    13,
      78,    13
  };

  /* TOKEN_NUMBER_[YYLEX-NUM] -- Internal symbol number corresponding
     to YYLEX-NUM.  */
  private static final short
  yytoken_number_[] =
  {
         0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
     315,   316,   317,   318,   319,   320,   321,   322,   323
  };

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
  private static final byte
  yyr1_[] =
  {
         0,    69,    70,    71,    71,    71,    72,    72,    73,    73,
      73,    74,    75,    75,    75,    75,    75,    76,    76,    76,
      77,    78,    78,    79,    79,    79,    79,    79,    79,    79,
      79,    79,    79,    79,    79,    79,    79,    79,    80,    81,
      81,    82,    82,    83,    83,    84,    84,    85,    85,    85,
      86,    86,    87,    87,    87,    88,    88,    88,    88,    88,
      88,    88,    88,    88,    88,    88,    88,    88,    89,    89,
      89,    90,    90,    91,    91,    92,    92,    92,    93,    93,
      94,    94,    95,    95,    96,    96,    96,    97,    97,    97,
      97,    97,    97,    98,    98,    99,    99,    99,    99,   100,
     100,   100,   101,   101,   101,   101,   101,   101,   101,   102,
     102,   102,   102,   103,   104,   105,   106,   107,   107,   108,
     108,   109,   109,   110,   110,   110,   111,   111,   111,   112,
     113,   113,   113,   114,   115,   115,   116,   116,   116,   117,
     118,   118,   118,   118,   118,   118,   118,   118,   118,   119,
     119,   119,   120,   121,   121,   121
  };

  /* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
  private static final byte
  yyr2_[] =
  {
         0,     2,     5,     0,     1,     2,     7,     6,     0,     3,
       1,     1,     4,     4,     4,     6,     6,     0,     3,     1,
       1,     2,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     2,     9,
       8,    11,    10,     5,     4,     7,     6,     0,     2,     1,
       4,     3,     0,     2,     1,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     1,     1,     2,     2,     1,     1,
       1,     2,     1,     3,     1,     1,     1,     1,     3,     3,
       3,     3,     3,     1,     1,     1,     1,     3,     3,     3,
       3,     3,     2,     2,     1,     1,     4,     1,     1,     3,
       1,     1,     3,     3,     3,     3,     3,     3,     3,     1,
       1,     1,     1,     1,     1,     2,     2,     6,     5,     1,
       2,     4,     3,     0,     2,     2,     0,     1,     2,     2,
       0,     1,     2,     4,     2,     1,     0,     1,     2,     4,
       1,     1,     1,     1,     1,     1,     1,     3,     3,     0,
       1,     2,     3,     0,     1,     2
  };

  /* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
     First, the terminals, then, starting at \a yyntokens_, nonterminals.  */
  private static final String yytname_[] =
  {
    "$end", "error", "$undefined", "VAR_BEGIN", "RETURN", "BREAK", "FUNC",
  "WHILE", "FTRUE", "FFALSE", "IF", "THEN", "ELSE", "END",
  "STRING_DEFINITION", "IDENTIFIER", "NUMBER", "SINGLE_LINE_COMMENT",
  "DIVIDE_MOD", "ARG_SPLITTER", "PLUS", "MINUS", "DIVIDE", "MULTIPLY",
  "ASSIGN", "MORE", "LESS", "MORE_OR_EQUAL", "LESS_OR_EQUAL", "EQUAL",
  "NOT_EQUAL", "OPEN_BRACKET", "CLOSE_BRACKET", "AND", "OR", "FKFLOAT",
  "PLUS_ASSIGN", "MINUS_ASSIGN", "DIVIDE_ASSIGN", "MULTIPLY_ASSIGN",
  "DIVIDE_MOD_ASSIGN", "COLON", "FOR", "INC", "FAKE", "FKUUID",
  "OPEN_SQUARE_BRACKET", "CLOSE_SQUARE_BRACKET", "FCONST", "PACKAGE",
  "INCLUDE", "IDENTIFIER_DOT", "IDENTIFIER_POINTER", "STRUCT", "IS", "NOT",
  "CONTINUE", "YIELD", "SLEEP", "SWITCH", "CASE", "DEFAULT", "NEW_ASSIGN",
  "ELSEIF", "RIGHT_POINTER", "STRING_CAT", "OPEN_BIG_BRACKET",
  "CLOSE_BIG_BRACKET", "NULL", "$accept", "program", "body",
  "function_declaration", "function_declaration_arguments", "arg",
  "function_call", "function_call_arguments", "arg_expr", "block", "stmt",
  "fake_call_stmt", "for_stmt", "for_loop_stmt", "while_stmt", "if_stmt",
  "elseif_stmt_list", "elseif_stmt", "else_stmt", "cmp", "cmp_value",
  "return_stmt", "return_value_list", "return_value", "assign_stmt",
  "multi_assign_stmt", "var_list", "assign_value", "math_assign_stmt",
  "var", "variable", "expr", "math_expr", "expr_value", "break",
  "continue", "sleep", "yield", "switch_stmt", "switch_case_list",
  "switch_case_define", "package_head", "include_head", "include_define",
  "struct_head", "struct_define", "struct_mem_declaration", "const_head",
  "const_define", "explicit_value", "const_map_list_value",
  "const_map_value", "const_array_list_value", null
  };

  /* YYRHS -- A `-1'-separated list of the rules' RHS.  */
  private static final byte yyrhs_[] =
  {
        70,     0,    -1,   110,   111,   113,   116,    71,    -1,    -1,
      72,    -1,    71,    72,    -1,     6,    15,    31,    73,    32,
      78,    13,    -1,     6,    15,    31,    73,    32,    13,    -1,
      -1,    73,    19,    74,    -1,    74,    -1,    15,    -1,    15,
      31,    76,    32,    -1,    51,    31,    76,    32,    -1,    75,
      31,    76,    32,    -1,    99,    41,    15,    31,    76,    32,
      -1,    75,    41,    15,    31,    76,    32,    -1,    -1,    76,
      19,    77,    -1,    77,    -1,   102,    -1,    78,    79,    -1,
      79,    -1,    83,    -1,    84,    -1,    90,    -1,    93,    -1,
      94,    -1,   103,    -1,   104,    -1,   100,    -1,    97,    -1,
      81,    -1,    82,    -1,    80,    -1,   105,    -1,   106,    -1,
     107,    -1,    44,    75,    -1,    42,    78,    19,    88,    19,
      78,    11,    78,    13,    -1,    42,    78,    19,    88,    19,
      78,    11,    13,    -1,    42,    98,    24,    96,    64,    89,
      19,   102,    11,    78,    13,    -1,    42,    98,    24,    96,
      64,    89,    19,   102,    11,    13,    -1,     7,    88,    11,
      78,    13,    -1,     7,    88,    11,    13,    -1,    10,    88,
      11,    78,    85,    87,    13,    -1,    10,    88,    11,    85,
      87,    13,    -1,    -1,    85,    86,    -1,    86,    -1,    63,
      88,    11,    78,    -1,    63,    88,    11,    -1,    -1,    12,
      78,    -1,    12,    -1,    31,    88,    32,    -1,    88,    33,
      88,    -1,    88,    34,    88,    -1,    89,    26,    89,    -1,
      89,    25,    89,    -1,    89,    29,    89,    -1,    89,    27,
      89,    -1,    89,    28,    89,    -1,    89,    30,    89,    -1,
       8,    -1,     9,    -1,    54,    89,    -1,    55,    89,    -1,
     118,    -1,    99,    -1,   100,    -1,     4,    91,    -1,     4,
      -1,    91,    19,    92,    -1,    92,    -1,   118,    -1,    99,
      -1,   100,    -1,    98,    24,    96,    -1,    98,    62,    96,
      -1,    95,    24,    75,    -1,    95,    62,    75,    -1,    95,
      19,    98,    -1,    98,    -1,   118,    -1,    99,    -1,   100,
      -1,    99,    36,    96,    -1,    99,    37,    96,    -1,    99,
      38,    96,    -1,    99,    39,    96,    -1,    99,    40,    96,
      -1,    99,    43,    -1,     3,    15,    -1,    99,    -1,    15,
      -1,    15,    46,   102,    47,    -1,    52,    -1,    51,    -1,
      31,   100,    32,    -1,    75,    -1,   101,    -1,    31,   101,
      32,    -1,   102,    20,   102,    -1,   102,    21,   102,    -1,
     102,    23,   102,    -1,   102,    22,   102,    -1,   102,    18,
     102,    -1,   102,    65,   102,    -1,   101,    -1,   118,    -1,
      75,    -1,    99,    -1,     5,    -1,    56,    -1,    58,   102,
      -1,    57,   102,    -1,    59,    89,   108,    61,    78,    13,
      -1,    59,    89,   108,    61,    13,    -1,   109,    -1,   108,
     109,    -1,    60,    89,    11,    78,    -1,    60,    89,    11,
      -1,    -1,    49,    15,    -1,    49,    51,    -1,    -1,   112,
      -1,   111,   112,    -1,    50,    14,    -1,    -1,   114,    -1,
     113,   114,    -1,    53,    15,   115,    13,    -1,   115,    15,
      -1,    15,    -1,    -1,   117,    -1,   116,   117,    -1,    48,
      15,    24,   118,    -1,    68,    -1,     8,    -1,     9,    -1,
      16,    -1,    45,    -1,    14,    -1,    35,    -1,    66,   119,
      67,    -1,    46,   121,    47,    -1,    -1,   120,    -1,   119,
     120,    -1,   118,    41,   118,    -1,    -1,   118,    -1,   121,
     118,    -1
  };

  /* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
     YYRHS.  */
  private static final short yyprhs_[] =
  {
         0,     0,     3,     9,    10,    12,    15,    23,    30,    31,
      35,    37,    39,    44,    49,    54,    61,    68,    69,    73,
      75,    77,    80,    82,    84,    86,    88,    90,    92,    94,
      96,    98,   100,   102,   104,   106,   108,   110,   112,   115,
     125,   134,   146,   157,   163,   168,   176,   183,   184,   187,
     189,   194,   198,   199,   202,   204,   208,   212,   216,   220,
     224,   228,   232,   236,   240,   242,   244,   247,   250,   252,
     254,   256,   259,   261,   265,   267,   269,   271,   273,   277,
     281,   285,   289,   293,   295,   297,   299,   301,   305,   309,
     313,   317,   321,   324,   327,   329,   331,   336,   338,   340,
     344,   346,   348,   352,   356,   360,   364,   368,   372,   376,
     378,   380,   382,   384,   386,   388,   391,   394,   401,   407,
     409,   412,   417,   421,   422,   425,   428,   429,   431,   434,
     437,   438,   440,   443,   448,   451,   453,   454,   456,   459,
     464,   466,   468,   470,   472,   474,   476,   478,   482,   486,
     487,   489,   492,   496,   497,   499
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,    90,    90,    99,   102,   104,   108,   119,   132,   136,
     147,   160,   174,   188,   202,   216,   235,   257,   261,   272,
     285,   295,   306,   319,   325,   331,   337,   343,   349,   355,
     361,   367,   373,   379,   385,   391,   397,   403,   411,   424,
     438,   454,   469,   486,   498,   512,   526,   543,   551,   562,
     575,   587,   602,   610,   621,   634,   640,   653,   666,   679,
     692,   705,   718,   731,   744,   757,   770,   783,   798,   804,
     810,   818,   829,   842,   853,   866,   872,   878,   886,   899,
     914,   927,   942,   953,   966,   972,   978,   986,   999,  1012,
    1025,  1038,  1051,  1070,  1081,  1089,  1100,  1112,  1123,  1136,
    1142,  1148,  1156,  1162,  1175,  1188,  1201,  1214,  1227,  1242,
    1248,  1254,  1260,  1268,  1280,  1292,  1305,  1318,  1331,  1346,
    1357,  1370,  1382,  1397,  1400,  1406,  1415,  1418,  1420,  1424,
    1433,  1436,  1438,  1442,  1450,  1455,  1463,  1466,  1468,  1472,
    1480,  1491,  1502,  1513,  1525,  1537,  1549,  1561,  1573,  1589,
    1598,  1609,  1623,  1638,  1647,  1658
  };

  // Report on the debug stream that the rule yyrule is going to be reduced.
  private void yy_reduce_print (int yyrule, YYStack yystack)
  {
    if (yydebug == 0)
      return;

    int yylno = yyrline_[yyrule];
    int yynrhs = yyr2_[yyrule];
    /* Print the symbols being reduced, and their result.  */
    yycdebug ("Reducing stack by rule " + (yyrule - 1)
	      + " (line " + yylno + "), ");

    /* The symbols being reduced.  */
    for (int yyi = 0; yyi < yynrhs; yyi++)
      yy_symbol_print ("   $" + (yyi + 1) + " =",
		       yyrhs_[yyprhs_[yyrule] + yyi],
		       ((yystack.valueAt (yynrhs-(yyi + 1)))));
  }

  /* YYTRANSLATE(YYLEX) -- Bison symbol number corresponding to YYLEX.  */
  private static final byte yytranslate_table_[] =
  {
         0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    47,    48,    49,    50,    51,    52,    53,    54,
      55,    56,    57,    58,    59,    60,    61,    62,    63,    64,
      65,    66,    67,    68
  };

  private static final byte yytranslate_ (int t)
  {
    if (t >= 0 && t <= yyuser_token_number_max_)
      return yytranslate_table_[t];
    else
      return yyundef_token_;
  }

  private static final int yylast_ = 1300;
  private static final int yynnts_ = 53;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 6;
  private static final int yyterror_ = 1;
  private static final int yyerrcode_ = 256;
  private static final int yyntokens_ = 69;

  private static final int yyuser_token_number_max_ = 323;
  private static final int yyundef_token_ = 2;

/* User implementation code.  */

}


/* Line 879 of lalr1.ja  */
/* Line 1670 of "YYParser.y"  */



