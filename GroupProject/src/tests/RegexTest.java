package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gui.LoginGUI;

class RegexTest {

	@Test
	void test() {
		String[] test = { "DanMota0", "Dan.Mota1", "Dan_Mota2", "Dan@Mota3", "Dan Mota 4", "Dan;Mota5", "Dan/Mota6", "Dan<Mota7" };
		
		LoginGUI obj = new LoginGUI();
		
		assertEquals( true, obj.validate( test[ 0 ] ) );
		assertEquals( true, obj.validate( test[ 1 ] ) );
		assertEquals( true, obj.validate( test[ 2 ] ) );
		assertEquals( true, obj.validate( test[ 3 ] ) );
		assertEquals( false, obj.validate( test[ 4 ] ) );
		assertEquals( false, obj.validate( test[ 5 ] ) );
		assertEquals( false, obj.validate( test[ 6 ] ) );
		assertEquals( false, obj.validate( test[ 7 ] ) );
		
	}

}
