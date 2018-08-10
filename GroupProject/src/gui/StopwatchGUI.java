package gui;


import javax.swing.Timer; 
import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import application.RuntimeAttributes;
import application.StopWatch;
import application.Task;
import db.DBConnection;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JScrollPane;

public class StopwatchGUI extends JFrame {
	private static StopwatchGUI frame;
	private List<Task> taskList;
	private Task taskInProgress;
	private RuntimeAttributes attributes;
	private JPanel contentPane;
	private Timer timer;
	private long start;
//	private final Action action = new SwingAction();
//	private final Action action_1 = new SwingAction_1();
	private static final URL ICON_URL = StopwatchGUI.class.getResource( "/resources/images/clockIcon.png" );
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new StopwatchGUI( new RuntimeAttributes() ).setVisible(true);
			}
		});
	}

	public StopwatchGUI( RuntimeAttributes attributesIn ) throws HeadlessException {
		super("GoldRush - " + attributesIn.getUserToken());
		StopWatch stopwatch = new StopWatch();
		attributes = attributesIn;
		
		try {
			super.setIconImage(ImageIO.read( ICON_URL ));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		taskList = DBConnection.getTaskList( attributes.getUserToken() );
		
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                controlledClosing( attributes, stopwatch );
            }
        });
        
        setSize(480, 320);
        setLocationRelativeTo(null);
        
        JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFiles = new JMenu("Files");
		menuBar.add(mnFiles);
		
		JMenuItem mntmOpenProject = new JMenuItem("Create Task");
		mnFiles.add(mntmOpenProject);
		
//		JMenuItem mntmNewMenuItem = new JMenuItem("Save Project");
//		mnFiles.add(mntmNewMenuItem);
//		
//		JButton btnStopwatch = new JButton("Stopwatch");
//		menuBar.add(btnStopwatch);
//		btnStopwatch.setEnabled(false);
//		
//		JButton btnTimeScheme = new JButton("Time Scheme");
//		menuBar.add(btnTimeScheme);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JButton btnPause = new JButton("Pause");
		btnPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				if(!attributes.isConcluded() && attributes.isTaskSelected())
					if(btnPause.getText().equals("Pause")) {
						stopwatch.setPauseTime(System.currentTimeMillis());
						stopwatch.setRunning(false);
						timer.stop();
						btnPause.setText("Resume");
					} else {
						stopwatch.setRunning(true);
						btnPause.setText("Pause");
						stopwatch.setPauseTime(System.currentTimeMillis() - stopwatch.getPauseTime());
						timer.start();
					}
			}
		});
		
		JLabel lblTime = new JLabel("00:00:00");
		lblTime.setBounds(188, 56, 55, 20);
		contentPane.add(lblTime);
		
		btnPause.setBounds(172, 147, 89, 23);
		contentPane.add(btnPause);
		
		timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	lblTime.setText(stopwatch.stopwatch(start));
            }
        });
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!timer.isRunning() && !attributes.isChanged() && attributes.isTaskSelected()) {
                    attributes.setChanged(true);
                    stopwatch.setRunning(true);
                	start = System.currentTimeMillis();
                    timer.start();
                }
            }
        });
		
		btnStart.setBounds(172, 113, 89, 23);
		contentPane.add(btnStart);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( attributes.isTaskSelected() && stopwatch.isRunning() ) {
            		timer.stop();
            		stopwatch.setRunning(false);
	                attributes.setConcluded(true);
	                taskInProgress.setConcluded(true);
                }
            }
        });
        
		btnStop.setBounds(172, 181, 89, 23);
		contentPane.add(btnStop);
		
		JLabel lblNotes = new JLabel("Notes:");
		lblNotes.setBounds(287, 11, 70, 20);
		contentPane.add(lblNotes);
		
		JLabel lblStopwatch = new JLabel("Stopwatch");
		lblStopwatch.setBounds(188, 11, 73, 20);
		contentPane.add(lblStopwatch);
		
		JComboBox comboBox = new JComboBox(DBConnection.getTaskNameList( attributes.getUserToken() ).toArray());
		comboBox.setBounds(10, 30, 124, 32);
		//comboBox.setToolTipText("Test");
		contentPane.add(comboBox);
		
		JLabel lblTaskSelection = new JLabel("Task Selection:");
		lblTaskSelection.setBounds(10, 11, 124, 20);
		contentPane.add(lblTaskSelection);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JTextArea txtNotes = new JTextArea();
		txtNotes.setBounds(289, 35, 162, 161);
		txtNotes.setLineWrap(true);
		txtNotes.setBorder(BorderFactory.createLineBorder(Color.black));
		scroll.setBounds(289, 35, 162, 161);
		scroll.getViewport().add(txtNotes);
		contentPane.add(scroll);
		
		comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if( !attributes.isChanged() ) {
            		String selection = (String) comboBox.getSelectedItem();
	                for(Task t : taskList) {
	                	if( selection.equals(t.getTaskName()) ) {
	                		taskInProgress = t;
	                		attributes = new RuntimeAttributes( taskInProgress.getUsername(), taskInProgress.isConcluded() );
	                		txtNotes.setText(taskInProgress.getTaskNote());
	                		break;
	                	}
	                }
                } else {
                	JOptionPane.showMessageDialog(null, "Changes detected.\n Ensure stopwatch is not running and current task has been saved before selecting a new task.");
                }
            }
        }); 
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(331, 202, 70, 23);
		contentPane.add(btnSave);
		
		btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	if( attributes.isTaskSelected() && !stopwatch.isRunning() ) {
            		taskInProgress.setTaskNote(txtNotes.getText());
            		DBConnection.saveTaskToDatabase( taskInProgress );
            		attributes.setSaved(true);
            		attributes.setChanged(false);
            		attributes.setTaskSelected(false);
            	}
            }
		}); 
	}

	private void controlledClosing(RuntimeAttributes attributes, StopWatch stopwatch) {
		if ( stopwatch.isRunning() ) {
			JOptionPane.showOptionDialog(this, "Stopwatch is running.\nPlease stop and save prior to exit.", "Warning",
                    0, JOptionPane.WARNING_MESSAGE, null, null, null);
        } else if( attributes.isChanged() && ! attributes.isSaved() ) {
        	JOptionPane.showOptionDialog(this, "Unsaved changes detected.\nPlease save prior to exit.", "Warning",
                    JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
        } else
        	dispose();
	}

//	private class SwingAction extends AbstractAction {
//		public SwingAction() {
//			putValue(NAME, "SwingAction");
//			putValue(SHORT_DESCRIPTION, "Some short description");
//		}
//		public void actionPerformed(ActionEvent e) {
//		}
//	}
//	private class SwingAction_1 extends AbstractAction {
//		public SwingAction_1() {
//			putValue(NAME, "SwingAction_1");
//			putValue(SHORT_DESCRIPTION, "Some short description");
//		}
//		public void actionPerformed(ActionEvent e) {
//		}
//	}
}

//package gui;
//
//import java.awt.BorderLayout;
//import java.sql.Time;
//import java.util.Timer; 
//import java.util.TimerTask;
//import java.awt.EventQueue;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.border.EmptyBorder;
//
//import application.Project;
//import application.User;
//
//import javax.swing.JTextPane;
//import javax.swing.JMenuBar;
//import javax.swing.JMenu;
//import javax.swing.JMenuItem;
//import javax.swing.JRadioButtonMenuItem;
//import javax.swing.AbstractAction;
//import java.awt.event.ActionEvent;
//import javax.swing.Action;
//import javax.swing.BorderFactory;
//
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import javax.swing.JButton;
//import javax.swing.JList;
//import javax.swing.JTextField;
//import javax.swing.JComboBox;
//import javax.swing.JTextArea;
//import java.awt.Color;
//import javax.swing.JScrollBar;
//
//public class StopwatchGUI extends JFrame {
//
//	private JPanel contentPane;
//	private String userToken;
//	private Project project;
//	private final Action action = new SwingAction();
//	private final Action action_1 = new SwingAction_1();
//	
//	double secondsPassed = 0;	
//	boolean running = true;
//
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					StopwatchGUI frame = new StopwatchGUI();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//
//	public StopwatchGUI() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 477, 300);
//		
//		JMenuBar menuBar = new JMenuBar();
//		setJMenuBar(menuBar);
//		
//		JMenu mnFiles = new JMenu("Files");
//		menuBar.add(mnFiles);
//		
//		JMenuItem mntmOpenProject = new JMenuItem("Open Project");
//		mnFiles.add(mntmOpenProject);
//		
//		JMenuItem mntmNewMenuItem = new JMenuItem("Save Project");
//		mnFiles.add(mntmNewMenuItem);
//		
//		JButton btnStopwatch = new JButton("Stopwatch");
//		menuBar.add(btnStopwatch);
//		btnStopwatch.setEnabled(false);
//		
//		
//		JButton btnTimeScheme = new JButton("Time Scheme");
//		menuBar.add(btnTimeScheme);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
//		contentPane.setLayout(null);
//		
//
//		
//		JButton btnPause = new JButton("Pause");
//		btnPause.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) 
//			{
//				running = false;
//			}
//		});
//		
//		JTextPane timeText = new JTextPane();
//		timeText.setText(Double.toString(secondsPassed));
//		timeText.setBounds(188, 56, 47, 20);
//		contentPane.add(timeText);
//		
//		btnPause.setBounds(172, 147, 89, 23);
//		contentPane.add(btnPause);
//		
//		JButton btnStart = new JButton("Start");
//		btnStart.addMouseListener(new MouseAdapter() 
//		{
//			@Override
//			public void mouseClicked(MouseEvent arg0) 
//			{				
//				Timer myTimer = new Timer("StopWatchTime");
//				
//				TimerTask task = new TimerTask()
//				{
//					public void run() 
//					{
//						if(running == false)
//						{
//							btnStart.setEnabled(true);
//							myTimer.cancel();
//							running = true;
//						}
//						else
//						{
//							btnStart.setEnabled(false);
//							//System.out.println(running);
//							secondsPassed++;
//							timeText.setText(Double.toString(secondsPassed / 10));							
//							System.out.println(secondsPassed / 10 );	
//						}
//					}
//				};	
//				myTimer.scheduleAtFixedRate(task, 100, 100);
//			}
//		});
//		btnStart.setBounds(172, 113, 89, 23);
//		contentPane.add(btnStart);
//		
//		JButton btnStop = new JButton("Stop");
//		btnStop.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) 
//			{
//				running = false;
//				secondsPassed = 0;
//				timeText.setText(Double.toString(secondsPassed / 10));			
//			}
//		});
//		btnStop.setBounds(172, 181, 89, 23);
//		contentPane.add(btnStop);
//		
//
//		
//		JTextPane txtpnNotes = new JTextPane();
//		txtpnNotes.setText("Notes:");
//		txtpnNotes.setBounds(287, 11, 70, 20);
//		contentPane.add(txtpnNotes);
//		
//		JTextPane txtpnStopwatch = new JTextPane();
//		txtpnStopwatch.setText("Stopwatch");
//		txtpnStopwatch.setBounds(188, 11, 73, 20);
//		contentPane.add(txtpnStopwatch);
//		
//		String[] petStrings = { "Task1", "Task2", "Task3", "Task4", "Task5" };
//		
//		JComboBox comboBox = new JComboBox(petStrings);
//		comboBox.setBounds(10, 30, 124, 32);
//		//comboBox.setToolTipText("Test");
//		contentPane.add(comboBox);
//		
//		JTextPane txtpnTaskSelection = new JTextPane();
//		txtpnTaskSelection.setText("Task Selection:");
//		txtpnTaskSelection.setBounds(10, 11, 124, 20);
//		contentPane.add(txtpnTaskSelection);
//		
//		JTextArea txtrTest = new JTextArea();
//		txtrTest.setText("Test");
//		txtrTest.setBounds(289, 35, 162, 161);
//		contentPane.add(txtrTest);
//
//		txtrTest.setBorder(BorderFactory.createLineBorder(Color.black));
//		
//		JButton btnSave = new JButton("Save");
//		btnSave.setBounds(331, 202, 70, 23);
//		contentPane.add(btnSave);
//	}
//
//	private class SwingAction extends AbstractAction {
//		public SwingAction() {
//			putValue(NAME, "SwingAction");
//			putValue(SHORT_DESCRIPTION, "Some short description");
//		}
//		public void actionPerformed(ActionEvent e) {
//		}
//	}
//	private class SwingAction_1 extends AbstractAction {
//		public SwingAction_1() {
//			putValue(NAME, "SwingAction_1");
//			putValue(SHORT_DESCRIPTION, "Some short description");
//		}
//		public void actionPerformed(ActionEvent e) {
//		}
//	}
//}