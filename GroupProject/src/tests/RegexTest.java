package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.ValidateInput;

class RegexTest {

	@Test
	void test() {
		String[] letters = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", 
							 "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		String[] numbers = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] validCharacters = { "_", ".", "-", "@" };
		String[] invalidCharacters = { "~", "`", "!", "#", "$", "%", "^", "&", "*", "(", ")", "+", "=", ",", ";", "'", ":", "\"" };
		
		for( int i = 0; i < letters.length; i++ ) {
			assertEquals( true, ValidateInput.validateUsername( letters[i] ) );
		}
		
		for( int i = 0; i < numbers.length; i++ ) {
			assertEquals( true, ValidateInput.validateUsername( numbers[i] ) );
		}
		
		for( int i = 0; i < validCharacters.length; i++ ) {
			assertEquals( true, ValidateInput.validateUsername( validCharacters[i] ) );
		}
		
		for( int i = 0; i < invalidCharacters.length; i++ ) {
			assertEquals( false, ValidateInput.validateUsername( invalidCharacters[i] ) );
		}
	}

}
