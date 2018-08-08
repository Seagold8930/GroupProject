package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.ValidateInput;

class UsernameValidationTest {

	@Test
	void test() {
		String[] test = { "TestCase0", "Test.case", "test_Case2", "Test@Case.3", "test case 4", "Test;Case5", "test//case6", "teSt<cAsE7", "test-case-8", "012345678901234567890123456789012345678901234", "0123456789012345678901234567890123456789012345" };
		
		assertEquals( true, ValidateInput.validateUsername( test[ 0 ] ) );
		assertEquals( true, ValidateInput.validateUsername( test[ 1 ] ) );
		assertEquals( true, ValidateInput.validateUsername( test[ 2 ] ) );
		assertEquals( true, ValidateInput.validateUsername( test[ 3 ] ) );
		assertEquals( false, ValidateInput.validateUsername( test[ 4 ] ) );
		assertEquals( false, ValidateInput.validateUsername( test[ 5 ] ) );
		assertEquals( false, ValidateInput.validateUsername( test[ 6 ] ) );
		assertEquals( false, ValidateInput.validateUsername( test[ 7 ] ) );
		assertEquals( true, ValidateInput.validateUsername( test[ 8 ] ) );
		assertEquals( true, ValidateInput.validateUsername( test[ 9 ] ) );
		assertEquals( false, ValidateInput.validateUsername( test[ 10 ] ) );
		
	}

}
