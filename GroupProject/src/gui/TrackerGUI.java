package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import application.*;

import javax.swing.JComboBox;

public class TrackerGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrackerGUI frame = new TrackerGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TrackerGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 561, 368);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		JComboBox cbProjectSelection = new JComboBox();
		cbProjectSelection.setBounds(341, 11, 194, 20);
		contentPane.add(cbProjectSelection);
			
		// temp variables for testing purposes, retrieve from db data later
		int noteID1_1=01,noteID1_2=02,noteID2_1=01,taskID1_1=01,taskID1_2=02,projectID=01; 
		String username="user1",description1="description about task 1_1",description2="description about task 2_1",pName="project 1";
		// temp objects for testing purposes
		Note note1 = new Note(noteID1_1, description1);
		Note note2 = new Note(noteID1_2, "");
		Note note3 = new Note(noteID2_1, description2);	
		Task task1 = new Task(taskID1_1);
		Task task2 = new Task(taskID1_2);		
		Project project1 = new Project(projectID, username, pName);
		//add objects to objects
		task1.setProjectID(projectID); task2.setProjectID(projectID); 	//add both task to project
		note1.setTaskID(taskID1_1); task1.addNoteList(note1);			//add note1 to task1
		note2.setTaskID(taskID1_1); task1.addNoteList(note2);			//add note2 to task1
		note3.setTaskID(taskID1_2); task2.addNoteList(note3);			//add note3 to task2
		project1.addTaskList(task1); project1.addTaskList(task2);		//add tasks to project
		
		cbProjectSelection.addItem("select project");
		cbProjectSelection.addItem(project1.getProjectName());
		
		//String value = cbProjectSelection.getSelectedItem().toString();
	}
}
