package JUnit4Printtokens2;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

import org.junit.Test;

public class TokenTests {
	
	
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
	
	
	@Test
	public void test_Open_Character_Stream_Does_Exsist() {
		
		
		Printtokens2 myTokenTest = new Printtokens2();
		FileReader fr;
		try {
			fr = new FileReader("./TestingFiles/Open_Stream_Does_Exsist.txt");
			BufferedReader outputExpected = new BufferedReader(fr);
			BufferedReader outputActual = myTokenTest.open_character_stream("./TestingFiles/Open_Stream_Does_Exsist.txt");
			assertEquals(outputExpected,outputActual);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	*/
	@Test
	public void test_Open_Character_Stream_Doesnt_Exsist() {
		
		
		Printtokens2 myTokenTest = new Printtokens2();
		BufferedReader outputActual = myTokenTest.open_character_stream("");
		
		
		assertEquals(null,outputActual);
	}
	
	

}
