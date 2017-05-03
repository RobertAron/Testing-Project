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
	static String onlyReturnsFile = "./TestingFiles/Test_File_Non_Characters.txt";
	static String specialCharFile = "./TestingFiles/Test_File_Special_Char_Start.txt";
	/*
	 * I'm not sure how to test two Buffered Readers with STD input
	 * Might Look Into This Later
	@Test
	public void test_Open_Character_Stream_Null() {
		
		
		Printtokens2 myTokenTest = new Printtokens2();
		BufferedReader nullExpected = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader nullActual = myTokenTest.open_character_stream(null);
		
		System.out.println(nullExpected.equals(nullActual));
		
		assertEquals(nullExpected.toString(),nullActual.toString());
	}
	
	*/
	@Test
	public void test_Open_Character_Stream_Does_Exsist() {
		
		
		Printtokens2 myTokenTest = new Printtokens2();
		FileReader fr;
		try {
			fr = new FileReader(defaultFile);
			BufferedReader outputExpected = new BufferedReader(fr);
			BufferedReader outputActual = myTokenTest.open_character_stream(defaultFile);
			assertEquals(outputExpected.readLine(),outputActual.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	@Test
	public void test_Open_Character_Stream_Doesnt_Exsist() {
		
		
		Printtokens2 myTokenTest = new Printtokens2();
		BufferedReader outputActual = myTokenTest.open_character_stream("");
		
		
		assertEquals(null,outputActual);
	}
	
	@Test
	public void test_get_char_no_error(){
		FileReader fr;
		Printtokens2 myTokenTest = new Printtokens2();
		
		try {
			fr = new FileReader(defaultFile);
			BufferedReader myBr = new BufferedReader(fr);
			myBr.mark(4);
			int expectedOutput = myBr.read();
			myBr.reset();
			int actualOutput = myTokenTest.get_char(myBr);
			assertEquals(expectedOutput,actualOutput);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
		
	@Test
	public void test_get_char_error(){
		Printtokens2 myTokenTest = new Printtokens2();
		FileReader fr;
		try {
			fr = new FileReader(noTextFile);
			BufferedReader brokenReader = new BufferedReader(fr);
			fr.close();
			int actualOutput = myTokenTest.get_char(brokenReader);
			int expectedOutput = 0;
			assertEquals(expectedOutput,actualOutput);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void test_unget_char_io_succsess(){
		Printtokens2 myTokenTest = new Printtokens2();
		FileReader fr;
		try {
			fr = new FileReader(defaultFile);
			BufferedReader myBr = new BufferedReader(fr);
			myBr.mark(4);
			int actualOutput = myTokenTest.unget_char(0, myBr);
			int expectedOutput = 0;
			assertEquals(expectedOutput,actualOutput);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	@Test
	public void test_unget_char_io_error(){
		Printtokens2 myTokenTest = new Printtokens2();
		FileReader fr;
		try {
			fr = new FileReader(defaultFile);
			BufferedReader myBr = new BufferedReader(fr);
			int actualOutput = myTokenTest.unget_char(0, myBr);
			int expectedOutput = 0;
			assertEquals(expectedOutput,actualOutput);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void test_open_token_stream_not_null(){
		Printtokens2 myTokenTest = new Printtokens2();
		BufferedReader actualReaderOutput;
		actualReaderOutput = myTokenTest.open_token_stream(defaultFile);

		try {
			FileReader fr = new FileReader(defaultFile);
			BufferedReader outputExpectedReader = new BufferedReader(fr);
			String outputExpected = outputExpectedReader.readLine();
			String actualOutput = actualReaderOutput.readLine();
			assertEquals(outputExpected,actualOutput);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	//TODO: find out how to check if two buffered readers are pointing to system.in
		
	
	@Test
	public void test_get_token_empty_file(){
		Printtokens2 myTokenTest = new Printtokens2();
		BufferedReader actualReaderOutput;
		actualReaderOutput = myTokenTest.open_token_stream(noTextFile);
		
		String actualOutput = myTokenTest.get_token(actualReaderOutput);
		assertEquals(null,actualOutput);
		
	}
	
	@Test
	public void test_get_token_default_file(){
		Printtokens2 myTokenTest = new Printtokens2();
		BufferedReader actualReaderOutput;
		actualReaderOutput = myTokenTest.open_token_stream(defaultFile);
		
		String actualOutput = myTokenTest.get_token(actualReaderOutput);
		assertEquals(null,actualOutput);
		
	}
	
	@Test
	public void test_get_token_return_characters(){
		Printtokens2 myTokenTest = new Printtokens2();
		BufferedReader actualReaderOutput;
		actualReaderOutput = myTokenTest.open_token_stream(onlyReturnsFile);
		
		String actualOutput = myTokenTest.get_token(actualReaderOutput);
		assertEquals(null,actualOutput);
		
	}
	
	@Test
	public void test_get_token_special_char(){
		Printtokens2 myTokenTest = new Printtokens2();
		BufferedReader actualReaderOutput;
		actualReaderOutput = myTokenTest.open_token_stream(specialCharFile);
		
		String actualOutput = myTokenTest.get_token(actualReaderOutput);
		assertEquals(null,actualOutput);
		
	}
	
		
}
	

	
	

