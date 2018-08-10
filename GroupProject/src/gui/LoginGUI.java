package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import application.RuntimeAttributes;
import application.ValidateInput;
import db.DBConnection;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginGUI {
	private static JFrame frame;
	private static JFXPanel fxPanel;
	private static final URL ICON_URL = LoginGUI.class.getResource( "/resources/images/clockIcon.png" );
	private static final URL LOGO_URL = LoginGUI.class.getResource( "/resources/images/clock.png" );
	private static final String STYLE = "GoldRush.css";
	private static Label lblWarning;
	
	/**
	 * private static void initAndShowGUI()
	 * Purpose: Creates the frame, sets its attributes, and centers the display position
	 * Input: NULL
	 * Outputs: NULL
	 */
	private static void initAndShowGUI() {
        frame = new JFrame( "GoldRush" );
        
        try {
        	frame.setIconImage( ImageIO.read( ICON_URL ) );
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        fxPanel = new JFXPanel();
        frame.add( fxPanel );
        frame.setSize( 300, 600 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation( dimension.width / 2 - frame.getSize().width  /2, dimension.height / 2 - frame.getSize().height / 2 );
        frame.setVisible( true );
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	initAndShowLoginGUI( fxPanel );
            }
       });
    }
	
	/**
	 * private static void initAndShowLoginGUI( JFXPanel fxPanel )
	 * Purpose: Sets the Scene into the JavaFX Panel
	 * Input: fxPanel
	 * Outputs: NULL
	 */
	private static void initAndShowLoginGUI( JFXPanel fxPanel ) {
        fxPanel.setScene( createLoginScene() );
    }
	
	/**
	 * private static Scene createLoginScene()
	 * Purpose: Scene builder for the login user interface
	 * Input: NULL
	 * Outputs: Scene
	 */
	private static Scene createLoginScene() {
		final GridPane lGrid = new GridPane();
        lGrid.setAlignment( Pos.CENTER );
        lGrid.setHgap( 10 );
        lGrid.setVgap( 10 );
        lGrid.setPadding( new Insets( 25, 25, 25, 25 ) );       

        Scene scene = new Scene( lGrid, 300, 600 );
        scene.getStylesheets().add( LoginGUI.class.getResource( STYLE ).toExternalForm() );
        
        try {
			ImageView myImage = new ImageView();
			myImage.setImage( SwingFXUtils.toFXImage( ImageIO.read( LOGO_URL ), null ) );
			lGrid.add( myImage, 0, 0 );
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        TextField txtUsername = new TextField();
        txtUsername.setPromptText( "Username" );
        txtUsername.setId( "custom-field" );
        
        PasswordField pwdPassword = new PasswordField();
        pwdPassword.setPromptText( "Password ");
        pwdPassword.setId( "custom-field" );
        
        lblWarning = new Label();
        lblWarning.setId( "warning-label" );
        
        VBox vbox = new VBox( 10 );
        vbox.setAlignment( Pos.CENTER );
        vbox.getChildren().addAll( txtUsername, pwdPassword, lblWarning );
        lGrid.add( vbox, 0, 3 );
        
        Button btnLogin = new Button( "Login" );
        btnLogin.setId( "custom-button" );
        Button btnSingup = new Button( "Sign-up" );
        btnSingup.setId( "custom-button" );
        
        HBox hbButtons = new HBox( 10 );
        hbButtons.setAlignment( Pos.CENTER );
        hbButtons.getChildren().addAll( btnLogin, btnSingup );
        lGrid.add( hbButtons, 0, 5 );
        
        btnLogin.setOnAction( new EventHandler< ActionEvent >() {
        	@Override
        	public void handle( ActionEvent event ) {
                lblWarning.setText( null );
                
                if( ValidateInput.validateUsername( txtUsername.getText() ) ) {
                	if( authenticate( txtUsername.getText(), pwdPassword.getText() ) ) {
	                	String token = txtUsername.getText();
                		txtUsername.clear();
	                    pwdPassword.clear();
                		transitionToStopwatch( token );
	                } else {
	                	lblWarning.setText( "Invalid username/password" );
	                }
                } else {
                	lblWarning.setText( "Invalid username" );
                }
            }
        });
        
        btnSingup.setOnAction( new EventHandler< ActionEvent >() {
            @Override
            public void handle( ActionEvent event ) {
            	frame.setVisible( false );
            	SignUpGUI obj = new SignUpGUI();
            	obj.initAndShowSignUpGUI();
            }
        });
        
        return scene;
    }	

	protected static void transitionToStopwatch(String token) {
        frame.setVisible( false );
        new StopwatchGUI( new RuntimeAttributes( token ) ).setVisible(true);
	}

	/**
	 * private static boolean authenticate( String handle, String password )
	 * Purpose: sends user input for authentication
	 * Input: handle and password as String
	 * Outputs: boolean
	 */
	private static boolean authenticate( String handle, String password ) {
		return DBConnection.login( handle, password );
	}
	
	/**
	 * public void showLoginGUI()
	 * Purpose: Used by other classes to display the login user interface
	 * Input: NULL
	 * Outputs: NULL
	 */
	public void showLoginGUI() {
		initAndShowGUI();
	}
	
	/**
	 * main method
	 */
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initAndShowGUI();
            }
        });
    }
}
