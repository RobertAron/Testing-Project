package JUnit4Printtokens2;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
	public void test_get_token_white_space_file() {
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
		actualReaderOutput = myTokenTest
				.open_token_stream(immediateClosedStringFile);
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

}
