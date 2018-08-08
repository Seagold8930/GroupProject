package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import application.User;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jbcrypt.BCrypt;

public class SignUpGUI {
	private static JFrame frame;
	private static JFXPanel fxPanel;
	private static final URL ICON_URL = LoginGUI.class.getResource( "/resources/images/clockIcon.png" );
	private static final URL LOGO_URL = LoginGUI.class.getResource( "/resources/images/clock.png" );
	private static final String STYLE = "GoldRush.css";
	private static TextField txtUsername;
	private static PasswordField pwdPassword;
	private static PasswordField pwdConfirmPassword;
	private static TextField txtEmail;
	private static TextField txtConfirmEmail;
	private static Label lblWarning;
	
	/**
	 * 
	 */
	public void initAndShowSignUpGUI() {
        frame = new JFrame( "GoldRush" );
        
        try {
			frame.setIconImage( ImageIO.read( ICON_URL ) );
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        fxPanel = new JFXPanel();
        frame.add( fxPanel );
        frame.setSize( 700, 400 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation( dimension.width / 2 - frame.getSize().width  /2, dimension.height / 2 - frame.getSize().height / 2 );
        frame.setVisible( true );
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	initAndShowSignUpGUI( fxPanel );
            }
       });
    }
	
	/**
	 * 
	 * @param fxPanel
	 */
	private static void initAndShowSignUpGUI( JFXPanel fxPanel ) {
        fxPanel.setScene( createSignUpScene() );
    }
	
	/**
	 * 
	 * @return
	 */
	private static Scene createSignUpScene() {
		GridPane sGrid = new GridPane();
        sGrid.setAlignment( Pos.CENTER );
        sGrid.setHgap( 10 );
        sGrid.setVgap( 10 );
        sGrid.setPadding( new Insets( 25, 25, 25, 25 ) );       

        Scene scene = new Scene( sGrid, 700, 400 );
        scene.getStylesheets().add( SignUpGUI.class.getResource( STYLE ).toExternalForm() );
        
        try {
			ImageView myImage = new ImageView();
			myImage.setImage( SwingFXUtils.toFXImage( ImageIO.read( LOGO_URL ), null ) );
			sGrid.add( myImage, 0, 0 );
			
		} catch ( IOException e ) {
			e.printStackTrace();
		}
        
        txtUsername = new TextField();
        txtUsername.prefWidth( 400 );
        txtUsername.setPromptText( "Username" );
        txtUsername.setId( "custom-field" );
        
        pwdPassword = new PasswordField();
        pwdPassword.prefWidth( 400 );
        pwdPassword.setPromptText( "Password ");
        pwdPassword.setId( "custom-field" );
        
        pwdConfirmPassword = new PasswordField();
        pwdConfirmPassword.prefWidth( 400 );
        pwdConfirmPassword.setPromptText( "Confirm Password ");
        pwdConfirmPassword.setId( "custom-field" );
        
        txtEmail =  new TextField();
        txtEmail.prefWidth( 400 );
        txtEmail.setPromptText( "Email" );
        txtEmail.setId( "custom-field" );
        
        txtConfirmEmail =  new TextField();
        txtConfirmEmail.prefWidth( 400 );
        txtConfirmEmail.setPromptText( "Confirm Email" );
        txtConfirmEmail.setId( "custom-field" );
        
        lblWarning = new Label();
        lblWarning.setId( "warning-label" );
        
        VBox vbox = new VBox( 10 );
        vbox.setPrefWidth( 400 );
        vbox.getChildren().addAll( txtUsername, pwdPassword, pwdConfirmPassword, txtEmail, txtConfirmEmail, lblWarning );
        sGrid.add( vbox, 1, 0 );
        
        Button btnCancel = new Button( "Cancel" );
        btnCancel.setId( "custom-button" );
        Button btnRegister = new Button( "Register" );
        btnRegister.setId( "custom-button" );
        
        HBox hbButtons = new HBox( 10 );
        hbButtons.setAlignment( Pos.BOTTOM_RIGHT );
        hbButtons.getChildren().addAll( btnCancel, btnRegister );
        sGrid.add( hbButtons, 1, 1 );
        
        btnCancel.setOnAction( new EventHandler< ActionEvent >() {
            @Override
            public void handle( ActionEvent event ) {
            	frame.setVisible( false );
            	LoginGUI obj = new LoginGUI();
            	obj.showLoginGUI();
            }
        });
        
        btnRegister.setOnAction( new EventHandler< ActionEvent >() {
            @Override
            public void handle( ActionEvent event ) {
            	lblWarning.setText( null );
            	evaluateResult( ValidateInput.validateAll( txtUsername.getText(), pwdPassword.getText(), 
                                                           pwdConfirmPassword.getText(), txtEmail.getText(), 
                                                           txtConfirmEmail.getText() ) );
            }
        });
        
        return scene;
    }

	private static void evaluateResult( int result ) {
		switch( result ) {
			case 1 :
				lblWarning.setText( "Username already registered" );
				break;
			case 2 :
				lblWarning.setText( "Email already registered" );
				break;
			case 3 :
				lblWarning.setText( "Invalid username" );
				break;
			case 4 :
				lblWarning.setText( "Password mismatch" );
				break;
			case 5 :
				lblWarning.setText( "Email mismatch" );
				break;
			case 6 :
				lblWarning.setText( "Invalid email" );
				break;
			case 0 :
				if ( insertUserCredentials() )
					alertAndDisplayLogin();
				else
					lblWarning.setText( "Error connecting to the Database" );
				
				break;
		}
		
	}

	private static boolean insertUserCredentials() {
		User user = new User( txtUsername.getText(), BCrypt.hashpw( pwdPassword.getText(), BCrypt.gensalt() ), txtEmail.getText() );
		DBConnection obj = new DBConnection();
		
		clearAllFields();
		return obj.createUser( user );
	}

	private static void clearAllFields() {
		txtUsername.clear();
		pwdPassword.clear();
		pwdConfirmPassword.clear();
		txtEmail.clear();
		txtConfirmEmail.clear();
	}

	private static void alertAndDisplayLogin() {
		Alert alertSuccess = new Alert( AlertType.INFORMATION );
		alertSuccess.setTitle( "GoldRush Registration" );
		alertSuccess.setHeaderText( null );
		alertSuccess.setContentText( "Registration completed successfully" );
		
		alertSuccess.showAndWait();
		
		displayLogin();
	}

	private static void displayLogin() {
		frame.setVisible( false );
		LoginGUI obj = new LoginGUI();
    	obj.showLoginGUI();
	}
}
