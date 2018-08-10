package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import application.Task;
import db.DBConnection;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class CreateTaskGUI extends JFrame {

	private static final URL ICON_URL = CreateTaskGUI.class.getResource("/resources/images/clockIcon.png");
	private static final String LOGO = "/resources/images/clock_small.png";
	private static final String README = "/DOCUMENTATION/README_test.txt"; // can't find file location??
	
	private JPanel contentPane;
	private JTextField txtTaskName;
	private static String userToken;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateTaskGUI frame = new CreateTaskGUI(userToken);
					frame.setVisible(true);
					frame.setIconImage(ImageIO.read( ICON_URL));	
					frame.setTitle("GoldRush - "/*TODO insert username here*/);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CreateTaskGUI(String userToken) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		JMenuItem mntmStopwatch = new JMenuItem("StopWatch");
		mntmStopwatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadStopWatch();
			}
		});
		mnMenu.add(mntmStopwatch);
		
		JMenuItem mntmTaskTracker = new JMenuItem("Task Tracker");
		mntmTaskTracker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadTaskTracker();
			}
		});
		mnMenu.add(mntmTaskTracker);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});
		mnMenu.add(mntmLogout);
		
		JMenu mnNewMenu = new JMenu("Help");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmReadme = new JMenuItem("README");
		mnNewMenu.add(mntmReadme);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCreateANew = new JLabel("Create a Task");
		lblCreateANew.setFont(new Font("Calibri", Font.PLAIN, 20));
		lblCreateANew.setBounds(120, 11, 122, 38);
		contentPane.add(lblCreateANew);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(10, 11, 100, 100);
		Image logo = new ImageIcon(this.getClass().getResource(LOGO)).getImage();
		lblLogo.setIcon(new ImageIcon(logo));
		contentPane.add(lblLogo);
		
		txtTaskName = new JTextField();
		txtTaskName.setBounds(221, 88, 176, 20);
		contentPane.add(txtTaskName);
		txtTaskName.setColumns(10);
		
		JLabel lblTaskName = new JLabel("Task Name");
		lblTaskName.setBounds(120, 94, 75, 14);
		contentPane.add(lblTaskName);
		
		JLabel lblTaskDescription = new JLabel("Task Description");
		lblTaskDescription.setBounds(120, 125, 122, 14);
		contentPane.add(lblTaskDescription);
		
		JTextArea txtaTaskDesc = new JTextArea();
		txtaTaskDesc.setBounds(221, 120, 176, 63);
		txtaTaskDesc.setBorder(BorderFactory.createLineBorder(Color.black));
		txtaTaskDesc.setLineWrap(true);
		contentPane.add(txtaTaskDesc);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createTask(userToken, txtTaskName, txtaTaskDesc);
			}
		});
		btnSubmit.setBounds(221, 194, 75, 23);
		contentPane.add(btnSubmit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetFields(txtTaskName, txtaTaskDesc);
			}
		});
		btnCancel.setBounds(322, 194, 75, 23);
		contentPane.add(btnCancel);
	}
	public void createTask(String userToken, JTextField tName, JTextArea tDesc) {
		DBConnection.CreateTask(tName.getText(), tDesc.getText());
	}
	public void resetFields(JTextField tName, JTextArea tDesc) {
		tName.setText("");
		tDesc.setText("");
	}
	/**
	 * method will close this window and sign the user out of the system.
	 * This method will then call the LoginGUI gui.
	 */
	private void logout() {
		// TODO
	}
	/**
	 * this method closes the current window and loads the StopWatchGUI
	 */
	private void loadStopWatch() {
		// TODO
	}
	/**
	 * this method closes the current window and loads the TaskTrackerGUI
	 */
	private void loadTaskTracker() {
		// TODO
	}
}
