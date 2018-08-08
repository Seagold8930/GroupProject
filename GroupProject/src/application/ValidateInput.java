package application;

import db.DBConnection;

public class ValidateInput {
	private static final int USERNAME_MAX = 45, EMAIL_MAX = 45;
	
	/**
	 * public static boolean validateUsername( String input )
	 * Purpose: Used externally to execute usernameValidator
	 * Input: input as String
	 * Outputs: boolean
	 */
	public static boolean validateUsername( String input ) {
		return usernameValidator( input );
	}
	
	/**
	 * private static boolean usernameValidator( String input )
	 * Purpose: Compares a user input against a maximum length and a regular expression pattern
	 * Input: input as String
	 * Output: boolean
	 */
	private static boolean usernameValidator( String input ) {
		String pattern = "[a-zA-Z0-9_.@+-]+";
		
		if( input.length() > USERNAME_MAX || ! input.matches( pattern ) ) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * public static boolean validateEmail( String input )
	 * Purpose: Used externally to execute emailValidator
	 * Input: input as String
	 * Outputs: boolean
	 */
	public static boolean validateEmail( String input ) {
		return emailValidator( input );
	}
	
	/**
	 * private static boolean emailValidator( String input )
	 * Purpose: Compares a user input against a maximum length and a regular expression pattern
	 * Input: input as String
	 * Output: boolean
	 */
	private static boolean emailValidator( String input ) {
		String pattern = "^[\\w-\\+]+(\\.[\\w-\\+]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
		
		if ( input.length() > EMAIL_MAX || ! input.matches( pattern ) )
			return false;
		
		return true;
	}

	/**
	 * 
	 */
	public static boolean comparePasswords( String password, String cPassword ) {
		return password.equals( cPassword );
	}

	public static boolean compareEmails( String email, String cEmail ) {
		
		return email.equals( cEmail );
	}
	
	/**
	 * public static int validateAll( String username, String password, String cPassword, String email, String cEmail ) {
	 * Purpose: Validate all user inputs against regular expression patterns
	 * Input: 
	 */
	public static int validateAll( String username, String password, String cPassword, String email, String cEmail ) {
		
		if( ! validateUsername( username ) )
			return 3;
		else if( ! comparePasswords( password, cPassword ) )
			return 4;
		else if( ! compareEmails( email, cEmail ) )
			return 5;
		else if( ! validateEmail( email ) )
			return 6;
		else {
			User user = new User( username, password, email );
			DBConnection dbConnection = new DBConnection();
			
			return dbConnection.checkCredentialsConflict( user );
		}
	}
}
