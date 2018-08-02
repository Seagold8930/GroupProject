package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
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
import javafx.scene.layout.VBox;

public class SignUpGUI {
	private static JFrame frame;
	private static JFXPanel fxPanel;
	private static final URL ICON_URL = LoginGUI.class.getResource( "/resources/images/clockIcon.png" );
	private static final URL LOGO_URL = LoginGUI.class.getResource( "/resources/images/clock.png" );
	private static final String STYLE = "GoldRush.css";
	private static Label lblWarning;
	
	/**
	 * 
	 */
	public void initAndShowSignUpGUI() {
        frame = new JFrame( "GoldRush" );
        
        try {
			BufferedImage icon = ImageIO.read( ICON_URL );
			frame.setIconImage( icon );
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
        Scene signUpScene = createSignUpScene();
        fxPanel.setScene( signUpScene );
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
			BufferedImage logo = ImageIO.read( LOGO_URL );
			Image image = SwingFXUtils.toFXImage( logo, null );
			ImageView myImage = new ImageView();
			myImage.setImage( image );
			sGrid.add( myImage, 0, 0 );
			
		} catch ( IOException e ) {
			e.printStackTrace();
		}
        
        TextField txtUsername = new TextField();
        txtUsername.prefWidth( 400 );
        txtUsername.setPromptText( "Username" );
        txtUsername.setId( "custom-field" );
        
        PasswordField pwdPassword = new PasswordField();
        pwdPassword.prefWidth( 400 );
        pwdPassword.setPromptText( "Password ");
        pwdPassword.setId( "custom-field" );
        
        PasswordField pwdConfirmPassword = new PasswordField();
        pwdConfirmPassword.prefWidth( 400 );
        pwdConfirmPassword.setPromptText( "Confirm Password ");
        pwdConfirmPassword.setId( "custom-field" );
        
        TextField txtEmail =  new TextField();
        txtEmail.prefWidth( 400 );
        txtEmail.setPromptText( "Email" );
        txtEmail.setId( "custom-field" );
        
        TextField txtConfirmEmail =  new TextField();
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
        
        return scene;
    }
}
