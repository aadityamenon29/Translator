import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.List;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.text.DefaultCaret;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.JScrollPane;

public class Translator {
	
	JTextArea textAreaUser1;
	JTextArea textAreaUser2;
	JButton btnSend1;
	JButton btnSend2;
	JTextArea MainScreen;
	JComboBox comboBox1;
	JComboBox comboBox2;
	private JFrame frame;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Translator window = new Translator();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Translator() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, -32, 1258, 736);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		comboBox1 = new JComboBox();
		comboBox1.setFont(new Font("Yu Gothic", Font.PLAIN, 20));
		comboBox1.setModel(new DefaultComboBoxModel(new String[] {"English", "Spanish", "French", "German", "Italian"}));
		comboBox1.setBounds(277, 419, 152, 54);
		frame.getContentPane().add(comboBox1);
		
		comboBox2 = new JComboBox();
		comboBox2.setModel(new DefaultComboBoxModel(new String[] {"English", "Spanish", "French", "German", "Italian"}));
		comboBox2.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 20));
		comboBox2.setBounds(788, 417, 152, 54);
		frame.getContentPane().add(comboBox2);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setBounds(548, 419, 108, 247);
		frame.getContentPane().add(verticalStrut);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(156, 514, 380, 97);
		frame.getContentPane().add(scrollPane_1);
		
		textAreaUser1 = new JTextArea();
		scrollPane_1.setViewportView(textAreaUser1);
		
		textAreaUser1.setForeground(Color.BLACK);
		textAreaUser1.setFont(new Font("Monospaced", Font.PLAIN, 15));
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(663, 514, 380, 97);
		frame.getContentPane().add(scrollPane_2);
		
		textAreaUser2 = new JTextArea();
		scrollPane_2.setViewportView(textAreaUser2);
		
		btnSend1 = new JButton("Send");
		
		btnSend1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String user_text = textAreaUser1.getText();
				String old_screen_text = MainScreen.getText();
				if(old_screen_text != null){
					MainScreen.setText(old_screen_text+"\n"+"User 1: "+user_text);
				}
				else{
					MainScreen.setText("User 1"+user_text);
				}
				textAreaUser1.setText("");
			}
		});
		btnSend1.setBounds(304, 624, 97, 25);
		frame.getContentPane().add(btnSend1);
		
		btnSend2 = new JButton("Send");
		btnSend2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user_text = textAreaUser2.getText();
				String old_screen_text = MainScreen.getText();
				if(old_screen_text != null){
					MainScreen.setText(old_screen_text+"\n"+"User 2: "+user_text);
					
				}
				else{
					MainScreen.setText("User 2"+user_text);
				}
				textAreaUser2.setText("");
				
			}
		});
		btnSend2.setBounds(829, 624, 97, 25);
		frame.getContentPane().add(btnSend2);
		//caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(152, 63, 891, 327);
		frame.getContentPane().add(scrollPane);
		
		MainScreen = new JTextArea();
		scrollPane.setViewportView(MainScreen);
		MainScreen.setFont(new Font("Monospaced", Font.BOLD, 16));
		MainScreen.setEditable(false);
	}
}
