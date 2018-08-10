package gui;

import java.awt.EventQueue;
import java.awt.Image;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import application.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class TrackerGUI extends JFrame {

	private static final URL ICON_URL = TrackerGUI.class.getResource("/resources/images/clockIcon.png");
	private static final String LOGO = "/resources/images/clock_small.png";
	private static final String README = "/DOCUMENTATION/README_test.txt"; // TODO can't find file location??
	
	private JPanel contentPane;
	private JTable tblTracking;
	private static String userToken;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrackerGUI frame = new TrackerGUI(userToken);
					frame.setVisible(true);
					frame.setIconImage(ImageIO.read( ICON_URL));	
					frame.setTitle("GoldRush");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TrackerGUI(String userToken) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Menu");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmStopwatch = new JMenuItem("StopWatch");
		mntmStopwatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadStopWatch();
			}
		});
		mnNewMenu.add(mntmStopwatch);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});
		
		JMenuItem mntmCreateTask = new JMenuItem("Create Task");
		mntmCreateTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadCreateTask();
			}
		});
		mnNewMenu.add(mntmCreateTask);
		mnNewMenu.add(mntmLogout);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmReadme = new JMenuItem("README");
		mntmReadme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openREADME();
			}
		});
		mnHelp.add(mntmReadme);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox<String> cbProjectSelect = new JComboBox<String>();
		cbProjectSelect.setBounds(282, 39, 124, 20);
		contentPane.add(cbProjectSelect);
		addProjectsToCB(cbProjectSelect);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(10, 11, 100, 100);
		Image logo = new ImageIcon(this.getClass().getResource(LOGO)).getImage();
		lblLogo.setIcon(new ImageIcon(logo));
		contentPane.add(lblLogo);
		
		JLabel lblProjectTracker = new JLabel("Project Tracker");
		lblProjectTracker.setFont(new Font("Calibri", Font.PLAIN, 20));
		lblProjectTracker.setBounds(120, 11, 124, 38);
		contentPane.add(lblProjectTracker);

		DefaultTableModel projTblModel = new DefaultTableModel();
		tblTracking = new JTable(projTblModel);	
		tblTracking.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tblTracking.setBounds(255, 70, 170, 159);
		contentPane.add(tblTracking);
		
		// ----- START events
		cbProjectSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				populateProjectTable(cbProjectSelect.getSelectedItem().toString(), projTblModel);
			}
		});
		// ----- END events
	}
	/**
	 * select a project from the available projects assigned to the username
	 * @param cbProjectSelect
	 */
	public void addProjectsToCB(JComboBox<String> cbProjectSelect) {
		// ----- TEMP objects for testing purposes while no access to DB
		Project project1 = new Project(01, "username", "project 1");
		Task task1 = new Task(01, 01, "task 1");
		Task task2 = new Task(02, 01, "task 2");
		Note note1 = new Note(01, 01, "note 1 task 1", "note 1 desc");
		Note note2 = new Note(02, 01, "note 2 task 1", "note 2 desc");
		Note note3 = new Note(01, 02, "note 1 task 2", "note 1 desc");
		
		task1.addNoteList(note1); task1.addNoteList(note2); task2.addNoteList(note3);
		project1.addTaskList(task1); project1.addTaskList(task2);
		// -----
		// TODO add DB project/task retrieval and object instantiation
		
		cbProjectSelect.addItem("select project");
		cbProjectSelect.addItem(project1.getProjectName());
			
	}
	/**
	 * method populates the table with the tasks from the selected project
	 * @param project 		- the select project
	 * @param projTblModel 	- the table
	 */
	public void populateProjectTable(/*Project project*/String project, DefaultTableModel projTblModel) {
		projTblModel.setRowCount(0);
		if(project.equals("select project")) {
			projTblModel.setRowCount(0);
		} else {	
			// ----- TEMP while no access to DB
			projTblModel.addRow(new Object[] {"task1", "2"});
			projTblModel.addRow(new Object[] {"task2", "1"});
			// -----
			/* ----- CHANGE TO THIS when data retrieved from DB, probably wont work
			for(String obj : project.taskList) {
				projTblModel.addRow(new Object[] {obj.getTaskName(), 
						(for(String obj : task.noteList) {
							obj.getDescription();
						}));
			}
			*/
		}
	}
	/**
	 * 
	 * @param component
	 * @param popup
	 */
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	/**
	 * Method opens README file in Notepad
	 */
	private void openREADME() {
        try {
        	ProcessBuilder pb = new ProcessBuilder("Notepad.exe", README);
        	pb.start();             
        } catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * method will close this window and sign the user out of the system.
	 * This method will then call the LoginGUI frame
	 */
	private void logout() {
		// TODO
	}
	/**
	 * this method closes the current window and loads the StopWatchGUI frame
	 */
	private void loadStopWatch() {
		// TODO
	}
	/**
	 * this method closes the current window and loads the CreateTaskGUI frame
	 */
	private void loadCreateTask() {
		// TODO
	}
}
