package JUnit4Printtokens2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

public class Printtokens2 {
	static int error = -1;
	static int keyword = 1;
	static int spec_symbol = 2;
	static int identifier = 3;
	static int num_constant = 41;
	static int str_constant = 42;
	static int char_constant = 43;
	static int comment = 5;
	
	/***********************************************/
	/* NMAE:	open_character_stream          */
	/* INPUT:       a filename                     */
	/* OUTPUT:      a BufferedReader */
	/* DESCRIPTION: when not given a filename,     */
	/*              open stdin,otherwise open      */
	/*              the existed file               */
	/***********************************************/
	BufferedReader open_character_stream(String fname) {
		BufferedReader br = null;
		if (fname == null) {
			br = new BufferedReader(new InputStreamReader(System.in));
		} else {
			try {
				FileReader fr = new FileReader(fname);
				br = new BufferedReader(fr);
			} catch (FileNotFoundException e) {
				System.out.print("The file " + fname +" doesn't exists\n");
				e.printStackTrace();
			}
		}
		
		return br;
	}
	
	/**********************************************/
	/* NAME:	get_char                      */
	/* INPUT:       a BufferedReader      */
	/* OUTPUT:      a character              */
	/**********************************************/
	int get_char(BufferedReader br){ 
		//TODO: should return -1
            int ch = error;
	    try {
	    	br.mark(4);
		ch= br.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return ch;
	}
	
	/***************************************************/
	/* NAME:      unget_char                           */
	/* INPUT:     a BufferedReader,a character */
	/* OUTPUT:    a character                          */
	/* DESCRIPTION: move backward  */
	/***************************************************/
	char unget_char (int ch,BufferedReader br) { 
	  try {
		br.reset();
	} catch (IOException e) {
		e.printStackTrace();
	}
		 return 0;
	}
	
	/********************************************************/
	/* NAME:	open_token_stream                       */
	/* INPUT:       a filename                              */
	/* OUTPUT:      a BufferedReader             */
	/* DESCRIPTION: when filename is EMPTY,choice standard  */
	/*              input device as input source            */
	/********************************************************/
	BufferedReader open_token_stream(String fname) {
		BufferedReader br;
		//TODO fname cannot equal null, changed to ""
		if (fname.equals(""))
			br = open_character_stream(null);
		else
			br = open_character_stream(fname);
		return br;
	}
	
	/********************************************************/
	/* NAME :	get_token                               */
	/* INPUT: 	a BufferedReader          */
	/* OUTPUT:      a token string                                */
	/* DESCRIPTION: according the syntax of tokens,dealing  */
	/*              with different case  and get one token  */
	/********************************************************/
	String get_token(BufferedReader br) {
		int i = 0, j;
		int id = 0;
		int res = 0;
		char ch = '\0';
		// TODO: Try catch methods not needed here
		StringBuilder sb = new StringBuilder();

		res = get_char(br);

		if (res == error) {
			return null;
		}

		ch = (char) res;
		while (ch == ' ' || ch == '\n' || ch == '\r') /*
													 * strip all blanks until
													 * meet characters
													 */
		{
			res = get_char(br);
			ch = (char) res;
		}

		if (res == error)
			return null;
		sb.append(ch);
		if (is_spec_symbol(ch) == true)// ()[]'`, basically any file starting
										// with these
			return sb.toString();
		if (ch == '"')
			// TODO: swapped from 2 to 1
			id = 1; /* prepare for string */
		if (ch == 59)
			// TODO: swapped from 1 to 2
			id = 2; /* prepare for comment */

		res = get_char(br);
		if (res == error) {// EOF on second or third non space character
			unget_char(ch, br);
			return sb.toString();
		}
		ch = (char) res;

		while (is_token_end(id, res) == false)/* until meet the end character */
		{
			sb.append(ch);
			// TODO: next line not needed - it is already done in the get_char()
			// method
			// br.mark(4);
			res = get_char(br);
			if (res == error) {// EOF after start
				break;
			}
			ch = (char) res;
		}

		if (res == error) /* if end character is eof token after close of special */
		{
			unget_char(ch, br); /* then put back eof on token_stream */
			return sb.toString();
		}
		if (is_spec_symbol(ch) == true) /*
										 * if end character is special_symbol
										 */
		{
			unget_char(ch, br); /* then put back this character */
			return sb.toString();
		}
		if (id == 1) /* if end character is " and is string */
		{
			sb.append(ch);
			return sb.toString();
		}
		if (id == 0 && ch == 59)
		/* when not in string or comment,meet ";" */
		{
			unget_char(ch, br); /* then put back this character */
			return sb.toString();
		}

		return sb.toString(); /* return nomal case token */
	}
	
	/*******************************************************/
	/* NAME:	is_token_end                           */
	/* INPUT:       a character,a token status             */
	/* OUTPUT:	a BOOLEAN value                        */
	/*******************************************************/
	static boolean is_token_end(int str_com_id, int res)
	{
	 if(res==-1)return(true); /* is eof token? */
	 char ch = (char)res;
	 if(str_com_id==1)          /* is string token */
	    { if(ch=='"' | ch=='\n' || ch == '\r')   /* for string until meet another " */
	         return true;
	      else
	         return false;
	    }

	 if(str_com_id==2)    /* is comment token */
	   { if(ch=='\n' || ch == '\r' || ch=='\t')     /* for comment until meet end of line */ 
	        return true;
	      else
	        return false;
	   }

	 if(is_spec_symbol(ch)==true) return true; /* is special_symbol? */
	 if(ch ==' ' || ch=='\n'|| ch=='\r' || ch==59) return true; 
	               
	 return false;               /* other case,return FALSE */
	}
	
	/****************************************************/
	/* NAME :	token_type                          */
	/* INPUT:       a token              */
	/* OUTPUT:      an integer value                    */
	/* DESCRIPTION: the integer value is corresponding  */
	/*              to the different token type         */
	/****************************************************/
	static int token_type(String tok)
	{ 
	 if(is_keyword(tok))return(keyword);
	 if(is_spec_symbol(tok.charAt(0)))return(spec_symbol);
	 if(is_identifier(tok))return(identifier);
	 if(is_num_constant(tok))return(num_constant);
	 if(is_str_constant(tok))return(str_constant);
	 if(is_char_constant(tok))return(char_constant);
	 if(is_comment(tok))return(comment);
	 return(error);                    /* else look as error token */
	}
	
	/****************************************************/
	/* NAME:	print_token                             */
	/* INPUT:	a token                                 */
	/****************************************************/
	void print_token(String tok) {
		int type;
		type = token_type(tok);
		if (type == error) {
			System.out.print("error,\"" + tok + "\".\n");
		}

		if (type == keyword) {
			System.out.print("keyword,\"" + tok + "\".\n");
		}

		if (type == spec_symbol)
			print_spec_symbol(tok);
		if (type == identifier) {
			System.out.print("identifier,\"" + tok + "\".\n");
		}
		// TODO: these outputs do not currently match the rest?
		if (type == num_constant) {
			System.out.print("numeric," + tok + ".\n");
		}
		if (type == str_constant) {
			System.out.print("string," + tok + ".\n");
		}
		//TODO: char at 1? Is this expected?
		if (type == char_constant) {
			System.out.print("character,\"" + tok.charAt(1) + "\".\n");
		}

	}

	/* the code for tokens judgment function */

	
	/*************************************/
	/* NAME:	is_comment           */
	/* INPUT: 	a token */
	/* OUTPUT:      a BOOLEAN value      */
	/*************************************/
	static boolean is_comment(String ident)
	{
	  if( ident.charAt(0) ==59 )   /* the char is 59   */
	     return true;
	  else
	     return false;
	}
	
	/*************************************/
	/* NAME:	is_keyword           */
	/* INPUT: 	a token */
	/* OUTPUT:      a BOOLEAN value      */
	/*************************************/
	static boolean is_keyword(String str)
	{ 
	 if (str.equals("and") || str.equals("or") || str.equals("if") ||
			 str.equals("xor")||str.equals("lambda")||str.equals("=>"))
	      return true;
	  else 
	      return false;
	}
	
	/*************************************/
	/* NAME:	is_char_constant     */
	/* INPUT: 	a token */
	/* OUTPUT:      a BOOLEAN value      */
	/*************************************/
	static boolean is_char_constant(String str)
	{
	  if (str.length() >= 2 && str.charAt(0)=='#' && Character.isLetter(str.charAt(1)))
	     return true;
	  else  
	     return false;
	}
	
	/*************************************/
	/* NAME:	is_num_constant      */
	/* INPUT: 	a token */
	/* OUTPUT:      a BOOLEAN value      */
	/*************************************/
	static boolean is_num_constant(String str)
	{
	  int i=1;
	  
	  if ( Character.isDigit(str.charAt(0))) 
	    {
	    while ( i < str.length() && str.charAt(i) != '\0' )   /* until meet token end sign */
	      {
	    	//TODO not i+1, just i
	       if(Character.isDigit(str.charAt(i)))	
	         i++;
	       else
	         return false;
	      }                         /* end WHILE */
	    return true;
	    }
	  else
	   return false;               /* other return FALSE */
	}
	
	/*************************************/
	/* NAME:	is_str_constant      */
	/* INPUT: 	a token */
	/* OUTPUT:      a BOOLEAN value      */
	/*************************************/
	static boolean is_str_constant(String str)
	{
	  int i=1;
	 
	  if ( str.charAt(0) =='"')
	     { while (i < str.length() && str.charAt(0)!='\0')  /* until meet the token end sign */
	         { if(str.charAt(i)=='"')
	             return true;        /* meet the second '"'           */
	           else
	           i++;
	         }               /* end WHILE */
	     return true;	
	    }
	  else
	    return false;       /* other return FALSE */
	}
	
	/*************************************/
	/* NAME:	is_identifier         */
	/* INPUT: 	a token */
	/* OUTPUT:      a BOOLEAN value      */
	/*************************************/
	static boolean is_identifier(String str)
	{
	  int i=1;

	  if ( Character.isLetter(str.charAt(0)) ) 
	     {
	        while(i < str.length() && str.charAt(i) !='\0' )   /* unti meet the end token sign */
	           { 
	            if(Character.isLetter(str.charAt(i)) || Character.isDigit(str.charAt(i)))   
	               i++;
	            else
	               return false;
	           }      /* end WHILE */
	     return true;
	     }
	  else
	     return false;
	}
	
	/******************************************/
	/* NAME:	unget_error               */
	/* INPUT:      a BufferedReader */
	/* OUTPUT: 	print error message       */
	/******************************************/
	static void unget_error(BufferedReader br)
	{
		System.out.print("It can not get charcter\n");
	}
	
	/*************************************************/
	/* NAME:        print_spec_symbol                */
	/* INPUT:       a spec_symbol token */
	/* OUTPUT :     print out the spec_symbol token  */
	/*              according to the form required   */
	/*************************************************/
	static void print_spec_symbol(String str)
	{
	    if      (str.equals("("))
	    {
	         
	             System.out.print("lparen.\n");
	             return;
	    } 
	    if (str.equals(")"))
	    {
	      
	             System.out.print("rparen.\n");
	             return;
	    }
	    if (str.equals("["))
	    {
	             System.out.print("lsquare.\n");
	             return;
	    }
	    if (str.equals("]"))
	    {
	       
	             System.out.print("rsquare.\n");
	             return;
	    }
	    if (str.equals("'"))
	    {
	             System.out.print("quote.\n");
	             return;
	    }
	    if (str.equals("`"))
	    {
	 
	             System.out.print("bquote.\n");
	             return;
	    }
	    
	    System.out.print("comma.\n");
	}
	
	/*************************************/
	/* NAME:        is_spec_symbol       */
	/* INPUT:       a token */
	/* OUTPUT:      a BOOLEAN value      */
	/*************************************/
	static boolean is_spec_symbol(char c)
	{
	    if (c == '(')
	    {  
	        return true;
	    }
	    if (c == ')')
	    {
	        return true;
	    }
	    if (c == '[')
	    {
	        return true;
	    }
	    if (c == ']')
	    {
	        return true;
	    }
	    if (c == '\'')
	    {
	        return true;
	    }
	    if (c == '`')
	    {
	        return true;
	    }
	    if (c == ',')
	    {
	        return true;
	    }
	    return false;     /* others return FALSE */
	}
	
	public static void main(String[] args) throws IOException {
		String fname = null;
		if (args.length == 0) {	/* if not given filename,take as '""' */
			fname = new String();
		} else if (args.length == 1) {
			//TODO args[1] should be args[0]
			fname = args[0];
		} else {
			System.out.print("Error!,please give the token stream\n");
			return;
			//Not Sure how to test this.
			//System.exit(0);
		}
		Printtokens2 t = new Printtokens2();
		BufferedReader br = t.open_token_stream(fname);	/* open token stream */
		String tok = t.get_token(br);
		while (tok != null) {	/* take one token each time until eof */
			t.print_token(tok);
			tok = t.get_token(br);
		}
		//Not sure how to test this.
		//System.exit(0);
	}
}