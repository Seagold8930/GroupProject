/**
 * Copyright© 2018 - A. Perry, A. Sirvid, D. Mota
 * 
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHORS DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.*;
import jbcrypt.BCrypt;

public class DBConnection {
	private String dbURL = "jdbc:mysql://localhost:3306/projectschema";
	private String dbUsername = "root";
	private String dbPassword = "";
	
	/**
	 * public int checkCredentialsConflict( User user )
	 * Purpose: Searches the database for an existing entry which should be unique. The method returns 0 if no match is found,
	 * 			1 if a conflict is found for username, and 2 if a conflict is found for email
	 * @param user
	 * @return int
	 */
	public int checkCredentialsConflict( User user ) {
		try {
			Connection connection = DriverManager.getConnection( dbURL, dbUsername, dbPassword );
			PreparedStatement prepStatement = connection.prepareStatement( "select * from usercredentials where username = ? or email = ?" );
			
			prepStatement.setString( 1, user.getUsername() );
			prepStatement.setString( 2, user.getEmail() );
			
			ResultSet resultSet = prepStatement.executeQuery();
			
			while( resultSet.next() ) {
				
				if( user.getUsername().equals( resultSet.getString( "username" ) ) ) {
					connection.close();
					prepStatement.close();
					
					return 1;
				} else if( user.getEmail().equals( resultSet.getString( "email" ) ) ) {
					connection.close();
					prepStatement.close();
					
					return 2;
				}
			}
			
			connection.close();
			prepStatement.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	 * public void createUser( User user )
	 * Purpose: Connects to the database and inserts a new entry into the usercredentials table
	 * @param user
	 */
	public void createUser( User user ) {
		try {
			Connection connection = DriverManager.getConnection( dbURL, dbUsername, dbPassword );
			PreparedStatement prepStatement = connection.prepareStatement( "insert into usercredentials ( username, password, email ) values ( ?, ?, ? )" );
			
			prepStatement.setString( 1, user.getUsername() );
			prepStatement.setString( 2, user.getPassword() );
			prepStatement.setString( 3, user.getEmail() );
			
			prepStatement.execute();
			
			prepStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * public boolean login( String handle, String pass )
	 * Purpose: Connects to the database and searches for a table entry - either username or email address - matching
	 * 			the handle input. If both handle and password matches the values retrieved from the database the method 
	 * 			returns true, otherwise the method returns false
	 * @param handle
	 * @param password
	 * @return true/false
	 */
	public boolean login( String handle, String password ) {
		try {
			Connection connection = DriverManager.getConnection( dbURL, dbUsername, dbPassword );
			PreparedStatement prepStatement = connection.prepareStatement( "select * from usercredentials where username = ? or email = ?" );
			
			prepStatement.setString( 1, handle );
			prepStatement.setString( 2, handle );
			
			ResultSet resultSet = prepStatement.executeQuery();
			
			while( resultSet.next() ) {
				String dbUsername = resultSet.getString( "username" );
				String dbPassword = resultSet.getString( "password" );
				String dbEmail = resultSet.getString( "email" );
				
				if( ( handle.equals( dbUsername ) && BCrypt.checkpw( password, dbPassword ) ) || 
					( handle.equals( dbEmail ) && BCrypt.checkpw( password, dbPassword ) ) ) {
					
					connection.close();
					prepStatement.close();
					return true;
				}
			}
			
			connection.close();
			prepStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 *  
	 * @param user
	 * @param newPassword
	 */
	public void updatePassword( User user, String newPassword ) {
		//TO DO
	}
	
	/**
	 * 
	 * @param project
	 */
	public void createProject( Project project ) {
		//TO DO
	}
	
	/**
	 * 
	 * @param project
	 */
	public void updateProject( Project project ) {
		//TO DO
	}
	
	
}
