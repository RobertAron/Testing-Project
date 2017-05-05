package JUnit4Printtokens2;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.junit.Test;

public class TokenTests {
	static String defaultFile = "./TestingFiles/Default_Test_File.txt";
	static String noTextFile = "./TestingFiles/Test_File_No_Text.txt";
	static String whiteSpaceFile = "./TestingFiles/Test_File_White_Space.txt";
	static String specialStartFile = "./TestingFiles/Test_File_Special_Start.txt";
	static String stringToEofFile = "./TestingFiles/Test_File_String_EOF.txt";
	static String commentNoEnd = "./TestingFiles/Test_File_Comment_Without_End.txt";
	static String immediateClosedStringFile = "./TestingFiles/Test_File_Immediate_Closed_String.txt";
	static String commentToSpecialFile = "./TestingFiles/Test_File_Comment_To_Special.txt";
	static String normalStringFile = "./TestingFiles/Test_File_Normal_String.txt";
	static String normalCommentFile = "./TestingFiles/Test_File_Normal_Comment.txt";
	static String normalToSpecial = "./TestingFiles/Test_File_Normal_To_Special.txt";
	static String normalToComment = "./TestingFiles/Test_File_Normal_To_Comment.txt";

	/*
	 * I'm not sure how to test two Buffered Readers with STD input Might Look
	 * Into This Later
	 * 
	 * @Test public void test_Open_Character_Stream_Null() {
	 * 
	 * 
	 * Printtokens2 myTokenTest = new Printtokens2(); BufferedReader
	 * nullExpected = new BufferedReader(new InputStreamReader(System.in));
	 * BufferedReader nullActual = myTokenTest.open_character_stream(null);
	 * 
	 * System.out.println(nullExpected.equals(nullActual));
	 * 
	 * assertEquals(nullExpected.toString(),nullActual.toString()); }
	 */
	@Test
	public void test_Open_Character_Stream_Does_Exsist() throws IOException {

		Printtokens2 myTokenTest = new Printtokens2();
		FileReader fr;

		fr = new FileReader(defaultFile);
		BufferedReader outputExpected = new BufferedReader(fr);
		BufferedReader outputActual = myTokenTest
				.open_character_stream(defaultFile);
		assertEquals(outputExpected.readLine(), outputActual.readLine());

	}

	@Test
	public void test_Open_Character_Stream_Doesnt_Exsist() {

		Printtokens2 myTokenTest = new Printtokens2();
		BufferedReader outputActual = myTokenTest.open_character_stream("");

		assertEquals(null, outputActual);
	}

	@Test
	public void test_get_char_no_error() throws IOException {
		FileReader fr;
		Printtokens2 myTokenTest = new Printtokens2();

		fr = new FileReader(defaultFile);
		BufferedReader myBr = new BufferedReader(fr);
		myBr.mark(4);
		int expectedOutput = myBr.read();
		myBr.reset();
		int actualOutput = myTokenTest.get_char(myBr);
		assertEquals(expectedOutput, actualOutput);

	}

	@Test
	public void test_get_char_error() throws IOException {
		Printtokens2 myTokenTest = new Printtokens2();
		FileReader fr;

		fr = new FileReader(noTextFile);
		BufferedReader brokenReader = new BufferedReader(fr);
		fr.close();
		int actualOutput = myTokenTest.get_char(brokenReader);
		int expectedOutput = -1;
		assertEquals(expectedOutput, actualOutput);

	}

	@Test
	public void test_unget_char_io_succsess() throws IOException {
		Printtokens2 myTokenTest = new Printtokens2();
		FileReader fr;

		fr = new FileReader(defaultFile);
		BufferedReader myBr = new BufferedReader(fr);
		myBr.mark(4);
		int actualOutput = myTokenTest.unget_char(0, myBr);
		int expectedOutput = 0;
		assertEquals(expectedOutput, actualOutput);

	}

	@Test
	public void test_unget_char_io_error() throws IOException {
		Printtokens2 myTokenTest = new Printtokens2();
		FileReader fr;

		fr = new FileReader(defaultFile);
		BufferedReader myBr = new BufferedReader(fr);
		int actualOutput = myTokenTest.unget_char(0, myBr);
		int expectedOutput = 0;
		assertEquals(expectedOutput, actualOutput);

	}

	@Test
	public void test_open_token_stream_not_null() throws IOException {
		Printtokens2 myTokenTest = new Printtokens2();
		BufferedReader actualReaderOutput;
		actualReaderOutput = myTokenTest.open_token_stream(defaultFile);

		FileReader fr = new FileReader(defaultFile);
		BufferedReader outputExpectedReader = new BufferedReader(fr);
		String outputExpected = outputExpectedReader.readLine();
		String actualOutput = actualReaderOutput.readLine();
		assertEquals(outputExpected, actualOutput);

	}

	// TODO: find out how to check if two buffered readers are pointing to
	// system.in

	@Test
	public void test_get_token_stream_white_space_file() {
		Printtokens2 myTokenTest = new Printtokens2();
		BufferedReader actualReaderOutput;
		actualReaderOutput = myTokenTest.open_token_stream(whiteSpaceFile);
		String actualOutput = myTokenTest.get_token(actualReaderOutput);
		assertEquals(null, actualOutput);

	}

	@Test
	public void test_get_token_special_start() {
		Printtokens2 myTokenTest = new Printtokens2();
		BufferedReader actualReaderOutput;
		actualReaderOutput = myTokenTest.open_token_stream(specialStartFile);
		String actualOutput = myTokenTest.get_token(actualReaderOutput);
		assertEquals("[", actualOutput);

	}

	@Test
	public void test_get_token_string_to_eof() {
		Printtokens2 myTokenTest = new Printtokens2();
		BufferedReader actualReaderOutput;
		actualReaderOutput = myTokenTest.open_token_stream(stringToEofFile);
		String actualOutput = myTokenTest.get_token(actualReaderOutput);
		assertEquals("\"", actualOutput);

	}

	@Test
	public void test_get_token_comment_without_close() {
		Printtokens2 myTokenTest = new Printtokens2();
		BufferedReader actualReaderOutput;
		actualReaderOutput = myTokenTest.open_token_stream(commentNoEnd);
		String actualOutput = myTokenTest.get_token(actualReaderOutput);
		assertEquals(";  ", actualOutput);

	}

	@Test
	public void test_get_token_immidiate_closed_string() {
		Printtokens2 myTokenTest = new Printtokens2();
		BufferedReader actualReaderOutput;
		actualReaderOutput = myTokenTest.open_token_stream(immediateClosedStringFile);
		String actualOutput = myTokenTest.get_token(actualReaderOutput);
		assertEquals("\"\"", actualOutput);

	}

	@Test
	public void test_get_token_normal_string() {
		Printtokens2 myTokenTest = new Printtokens2();
		BufferedReader actualReaderOutput;
		actualReaderOutput = myTokenTest.open_token_stream(normalStringFile);
		String actualOutput = myTokenTest.get_token(actualReaderOutput);
		assertEquals("\"Hello World\"", actualOutput);

	}

	@Test
	public void test_get_token_normal_comment() {
		Printtokens2 myTokenTest = new Printtokens2();
		BufferedReader actualReaderOutput;
		actualReaderOutput = myTokenTest.open_token_stream(normalCommentFile);
		String actualOutput = myTokenTest.get_token(actualReaderOutput);
		assertEquals(";Hello World", actualOutput);

	}

	@Test
	public void test_get_token_normal_to_special() {
		Printtokens2 myTokenTest = new Printtokens2();
		BufferedReader actualReaderOutput;
		actualReaderOutput = myTokenTest.open_token_stream(normalToSpecial);
		String actualOutput = myTokenTest.get_token(actualReaderOutput);
		assertEquals("NormalToSpecial", actualOutput);

	}

	@Test
	public void test_get_token_normal_to_comment() {
		Printtokens2 myTokenTest = new Printtokens2();
		BufferedReader actualReaderOutput;
		actualReaderOutput = myTokenTest.open_token_stream(normalToComment);
		String actualOutput = myTokenTest.get_token(actualReaderOutput);
		assertEquals("NormalToComment", actualOutput);

	}

	@Test
	public void test_get_token_broken_buffer() throws IOException {
		Printtokens2 myTokenTest = new Printtokens2();
		BufferedReader actualReaderOutput;
		actualReaderOutput = myTokenTest.open_token_stream(normalToComment);
		actualReaderOutput.close();
		String actualOutput = myTokenTest.get_token(actualReaderOutput);
		assertEquals(null, actualOutput);

	}

	@Test
	public void test_token_type_keyword() {
		assertEquals(1, Printtokens2.token_type("and"));
		assertEquals(1, Printtokens2.token_type("or"));
		assertEquals(1, Printtokens2.token_type("if"));
		assertEquals(1, Printtokens2.token_type("xor"));
		assertEquals(1, Printtokens2.token_type("lambda"));
		assertEquals(1, Printtokens2.token_type("=>"));

	}
	
	@Test
	public void test_token_type_spec() {
		assertEquals(2, Printtokens2.token_type("("));
		assertEquals(2, Printtokens2.token_type(")"));
		assertEquals(2, Printtokens2.token_type("["));
		assertEquals(2, Printtokens2.token_type("]"));
		assertEquals(2, Printtokens2.token_type("'"));
		assertEquals(2, Printtokens2.token_type("`"));
		assertEquals(2, Printtokens2.token_type(","));


	}
	
	@Test
	public void test_token_type_identifier() {
		assertEquals(3, Printtokens2.token_type("ab"));
	}
	@Test
	public void test_token_type_num_constant() {
		assertEquals(41, Printtokens2.token_type("123"));
	}
	
	@Test
	public void test_token_type_str_constant() {
		assertEquals(42, Printtokens2.token_type("\"Test Const\""));
		assertEquals(42, Printtokens2.token_type("\"Test Const"));
	}
	
	@Test
	public void test_token_type_char_constant() {
		assertEquals(43, Printtokens2.token_type("#a12"));
	}
	
	@Test
	public void test_token_type_comment() {
		assertEquals(5, Printtokens2.token_type(";123"));
	}
	@Test
	public void test_token_type_error() {
		assertEquals(-1, Printtokens2.token_type("#123"));
	}
	
	@Test
	public void test_print_token() throws IOException {
		Printtokens2 myTokenTest = new Printtokens2();
		
		//error
		{
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
		myTokenTest.print_token("#123");
		assertEquals("error,\"" + "#123" + "\".\n", outContent.toString());
	    System.setOut(null);
	    System.setErr(null);
		}
	    
		//keyword
		{
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
		myTokenTest.print_token("and");
		assertEquals("keyword,\"" + "and" + "\".\n", outContent.toString());
	    System.setOut(null);
	    System.setErr(null);
		}
		
		//spec
		{
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
		myTokenTest.print_token("(");
		assertEquals("lparen.\n", outContent.toString());
	    System.setOut(null);
	    System.setErr(null);
		}
		
		//identifier
		{
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
		myTokenTest.print_token("ab");
		assertEquals("identifier,\"" + "ab" + "\".\n", outContent.toString());
	    System.setOut(null);
	    System.setErr(null);
		}
		
		
		//num_const
		{
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
		myTokenTest.print_token("123");
		assertEquals("numeric," + "123" + ".\n", outContent.toString());
	    System.setOut(null);
	    System.setErr(null);
		}
		
		//String
		{
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
		myTokenTest.print_token("\"String\"");
		assertEquals("string," + "\"String\"" + ".\n", outContent.toString());
	    System.setOut(null);
	    System.setErr(null);
		}
		
		//char_constant
		{
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
		myTokenTest.print_token("#a12");
		assertEquals("character,\"" + "#a12".charAt(1) + "\".\n", outContent.toString());
	    System.setOut(null);
	    System.setErr(null);
		}
		
		
		
		
		
		
	    System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
	    System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));
	}
	
	
	
	@Test
	public void test_main_no_entry() throws IOException {
		String systemInData = ";JustAComment";
		InputStream testInput = new ByteArrayInputStream( systemInData.getBytes("UTF-8") );
		System.setIn(testInput);
		String[] args= new String[0];
		Printtokens2.main(args);
		
		
		
	}
	
	
	
	@Test
	public void test_main_one_entry() throws IOException {
		String[] args= new String[1];
		args[0] = normalToComment;
		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
		Printtokens2.main(args);
		
		
		
	}
	
	@Test
	public void test_main_two_entry() throws IOException {
		String[] args= new String[2];
		args[0] = normalToComment;
		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
		Printtokens2.main(args);
		
		
		
	}
	
	
	@Test
	public void test_print_spec_symbol(){
		Printtokens2 myTokenTest = new Printtokens2();
		
		
		//)
		{
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
		myTokenTest.print_spec_symbol("(");
		assertEquals("lparen.\n", outContent.toString());
	    System.setOut(null);
	    System.setErr(null);
		}
		
		//)
		{
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	    myTokenTest.print_spec_symbol(")");
		assertEquals("rparen.\n", outContent.toString());
	    System.setOut(null);
	    System.setErr(null);
		}
		
		//[
		{
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
		myTokenTest.print_spec_symbol("[");
		assertEquals("lsquare.\n", outContent.toString());
	    System.setOut(null);
	    System.setErr(null);
		}
		
		//]
		{
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
		myTokenTest.print_spec_symbol("]");
		assertEquals("rsquare.\n", outContent.toString());
	    System.setOut(null);
	    System.setErr(null);
		}
		//)
		{
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
		myTokenTest.print_spec_symbol("'");
		assertEquals("quote.\n", outContent.toString());
	    System.setOut(null);
	    System.setErr(null);
		}
		
		//1
		{
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
		myTokenTest.print_spec_symbol("`");
		assertEquals("bquote.\n", outContent.toString());
	    System.setOut(null);
	    System.setErr(null);
		}
		
		//,
		{
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
		myTokenTest.print_spec_symbol(",");
		assertEquals("comma.\n", outContent.toString());
	    System.setOut(null);
	    System.setErr(null);
		}
		
		
		
		
		
		
		
		
	    System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
	    System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));
		
	}


}
