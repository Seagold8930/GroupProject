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
import java.util.ArrayList;
import java.util.List;

import application.*;
import jbcrypt.BCrypt;

public class DBConnection {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/projectschema";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";
	
	/**
	 * public int checkCredentialsConflict( User user )
	 * Purpose: Searches the database for an existing entry which should be unique. The method returns 0 if no match is found,
	 * 			1 if a conflict is found for username, and 2 if a conflict is found for email
	 * Input: User object
	 * Outputs: integer
	 */
	public static int checkCredentialsConflict( User user ) {
		try {
			Connection connection = DriverManager.getConnection( DB_URL, DB_USERNAME, DB_PASSWORD );
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
	 * Input: User object
	 * Outputs: boolean
	 */
	public static boolean createUser( User user ) {
		try {
			Connection connection = DriverManager.getConnection( DB_URL, DB_USERNAME, DB_PASSWORD );
			PreparedStatement prepStatement = connection.prepareStatement( "insert into usercredentials ( username, password, email ) values ( ?, ?, ? )" );
			
			prepStatement.setString( 1, user.getUsername() );
			prepStatement.setString( 2, user.getPassword() );
			prepStatement.setString( 3, user.getEmail() );
			
			prepStatement.execute();
			
			prepStatement.close();
			connection.close();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * public boolean login( String handle, String pass )
	 * Purpose: Connects to the database and searches for a table entry - either username or email address - matching
	 * 			the handle input. If both handle and password matches the values retrieved from the database the method 
	 * 			returns true, otherwise the method returns false
	 * Input: handle and password as String
	 * Outputs: boolean
	 */
	public static boolean login( String handle, String password ) {
		try {
			Connection connection = DriverManager.getConnection( DB_URL, DB_USERNAME, DB_PASSWORD );
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
	
//	public List reportProject( String userToken ) {
//		List<Project> projectList = new ArrayList<>();
//		
//		try {
//			Connection connection = DriverManager.getConnection( DB_URL, DB_USERNAME, DB_PASSWORD );
//			PreparedStatement prepStatement = connection.prepareStatement( "select * from projects where username = ?" );
//			
//			prepStatement.setString( 1, userToken );
//			ResultSet resultSet = prepStatement.executeQuery();
//			
//			while( resultSet.next() ) {
//				Project project = new Project( resultSet.getString( "username" ), resultSet.getString( "projectname" ), resultSet.getString( "description" ) );
//				projectList.add( project );
//			}
//				
//			connection.close();
//			prepStatement.close();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return projectList;
//	}
	
	public static List getTaskNameList(String username) {
		List<String> taskList = new ArrayList<>();
		
		try {
			Connection connection = DriverManager.getConnection( DB_URL, DB_USERNAME, DB_PASSWORD );
			PreparedStatement prepStatement = connection.prepareStatement( "select * from tasks where username = ?" );
			
			prepStatement.setString( 1, username );
			
			ResultSet resultSet = prepStatement.executeQuery();
			
			while( resultSet.next() ) {
				String taskName = resultSet.getString( "taskName" );
				taskList.add( taskName );
			}
				
			connection.close();
			prepStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return taskList;
	}

	public static List<Task> getTaskList(String userToken) {
		List<Task> taskList = new ArrayList<>();
		
		try {
			Connection connection = DriverManager.getConnection( DB_URL, DB_USERNAME, DB_PASSWORD );
			PreparedStatement prepStatement = connection.prepareStatement( "select * from tasks where username = ?" );
			
			prepStatement.setString( 1, userToken );
			
			ResultSet resultSet = prepStatement.executeQuery();
			
			while( resultSet.next() ) {
				taskList.add(new Task(resultSet.getInt( "taskID" ), resultSet.getString( "username" ), resultSet.getString( "taskName" ), 
						resultSet.getString( "description" ), resultSet.getString( "notes" ), resultSet.getBoolean("concluded")));
			}
				
			connection.close();
			prepStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return taskList;
	}

	public static void saveTaskToDatabase(Task task) {
		try {
			Connection connection = DriverManager.getConnection( DB_URL, DB_USERNAME, DB_PASSWORD );
			PreparedStatement prepStatement = connection.prepareStatement( "update tasks set notes = ?, concluded = ? where taskID = ?" );
			
			prepStatement.setString( 1, task.getTaskNote() );
			prepStatement.setBoolean( 2, task.isConcluded() );
			prepStatement.setInt( 3, task.getTaskID() );
			
			prepStatement.execute();
				
			connection.close();
			prepStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void createTask( String username, String taskName, String description ) {
		Task task = new Task( username, taskName, description );
		
		try {
			Connection connection = DriverManager.getConnection( DB_URL, DB_USERNAME, DB_PASSWORD );
			PreparedStatement prepStatement = connection.prepareStatement( "insert into tasks ( username, taskName, description, notes, concluded ) values ( ?, ?, ?, ?, ? )" );
			
			prepStatement.setString( 1, task.getUsername() );
			prepStatement.setString( 2, task.getTaskName() );
			prepStatement.setString( 3, task.getTaskDescription() );
			prepStatement.setString( 4, task.getTaskNote() );
			prepStatement.setBoolean( 5, task.isConcluded() );
			
			prepStatement.execute();
				
			connection.close();
			prepStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	public static Task getTask(Task task) {
//		Task dbTask;
//		try {
//			Connection connection = DriverManager.getConnection( DB_URL, DB_USERNAME, DB_PASSWORD );
//			PreparedStatement prepStatement = connection.prepareStatement( "select * from tasks where username = ? and taskName = ?" );
//			
//			prepStatement.setString( 1, attributes.getUserToken() );
//			//prepStatement.setString( 2, attributes.getProjectName() );
//			prepStatement.setString( 2, selection );
//			
//			ResultSet resultSet = prepStatement.executeQuery();
//			
//			while( resultSet.next() ) {
//				task = new Task(resultSet.getInt( "taskID" ), resultSet.getString( "username" ), resultSet.getString( "taskName" ), 
//						resultSet.getString( "description" ), resultSet.getString( "notes" ), resultSet.getBoolean("concluded"));
//			}
//				
//			connection.close();
//			prepStatement.close();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return dbTask;
//	}
}
