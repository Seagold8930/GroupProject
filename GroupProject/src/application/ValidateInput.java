package application;

public class ValidateInput {
	private static final int USERNAME_MAX = 45;
	
	public static boolean validateUsername( String input ) {
		return valUsername( input );
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	private static boolean valUsername( String input ) {
		String pattern = "[a-zA-Z0-9_.@-]+{45}";
		
		if( ! input.matches( pattern )  || input.length() > USERNAME_MAX ) {
			return false;
		}
		
		return true;
	}
}
