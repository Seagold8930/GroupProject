package gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class LoginGUI {
	private static JFrame frame;
	private static JFXPanel fxPanel;
	private static URL iconURL = LoginGUI.class.getResource( "/resources/images/clockIcon.png" );
	private static URL logoURL = LoginGUI.class.getResource( "/resources/images/clock.png" );
	private static final String STYLE = "GoldRush.css";
	private static Label lblWarning;
	
	private static void initAndShowGUI() {
        frame = new JFrame( "GoldRush" );
        
        try {
			BufferedImage icon = ImageIO.read( iconURL );
			frame.setIconImage( icon );
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        fxPanel = new JFXPanel();
        frame.add( fxPanel );
        frame.setSize( 300, 600 );
        frame.setVisible( true );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	initAndShowGUI( fxPanel );
            }
       });
    }
	
	private static void initAndShowGUI( JFXPanel fxPanel ) {
        Scene loginScene = createLoginScene();
        fxPanel.setScene( loginScene );
    }
	
	private static Scene createLoginScene() {
		final GridPane lGrid = new GridPane();
        lGrid.setAlignment( Pos.CENTER );
        lGrid.setHgap( 10 );
        lGrid.setVgap( 10 );
        lGrid.setPadding( new Insets( 25, 25, 25, 25 ) );       

        Scene scene = new Scene( lGrid, 300, 600 );
        scene.getStylesheets().add( LoginGUI.class.getResource( STYLE ).toExternalForm() );
        
        try {
			BufferedImage logo = ImageIO.read( logoURL );
			Image image = SwingFXUtils.toFXImage( logo, null );
			ImageView myImage = new ImageView();
			myImage.setImage( image );
			lGrid.add( myImage, 0, 0 );
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        TextField txtUsername = new TextField();
        txtUsername.setPromptText( "Username" );
        txtUsername.setId( "custom-field" );
        HBox hbUser = new HBox( 10 );
        hbUser.setAlignment( Pos.CENTER );
        hbUser.getChildren().add( txtUsername );
        lGrid.add( hbUser, 0, 2 );
        
        PasswordField pwdPassword = new PasswordField();
        pwdPassword.setPromptText( "Password ");
        pwdPassword.setId( "custom-field" );
        HBox hbPass = new HBox( 10 );
        hbPass.setAlignment( Pos.CENTER );
        hbPass.getChildren().add( pwdPassword );
        lGrid.add( hbPass, 0, 3 );
        
        lblWarning = new Label();
        lblWarning.setId( "warning-label" );
        HBox hbLblPass = new HBox( 10 );
        hbLblPass.setAlignment( Pos.CENTER );
        hbLblPass.getChildren().add( lblWarning );
        lGrid.add( hbLblPass, 0, 4 );
        
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
                
                if( authenticate( txtUsername.getText(), pwdPassword.getText() ) ) {
                	txtUsername.clear();
                    pwdPassword.clear();
                    //TODO
                } else {
                	lblWarning.setText( "Invalid username/password" );
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
	
	private static boolean authenticate( String handle, String password ) {
		DBConnection obj = new DBConnection();
		
		if ( obj.login(handle, password) ) {
			return true;
		}
		
		return false;
	}

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initAndShowGUI();
            }
        });
    }
}
