import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.List;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class Translator {

	private JFrame frame;

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
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Yu Gothic", Font.PLAIN, 20));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"English", "Spanish", "French", "German", "Italian"}));
		comboBox.setBounds(277, 419, 152, 54);
		frame.getContentPane().add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"English", "Spanish", "French", "German", "Italian"}));
		comboBox_1.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 20));
		comboBox_1.setBounds(788, 417, 152, 54);
		frame.getContentPane().add(comboBox_1);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setBounds(548, 419, 108, 247);
		frame.getContentPane().add(verticalStrut);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(156, 514, 380, 97);
		frame.getContentPane().add(textArea);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(663, 514, 380, 97);
		frame.getContentPane().add(textArea_1);
		
		JButton btnSend = new JButton("Send");
		btnSend.setBounds(304, 624, 97, 25);
		frame.getContentPane().add(btnSend);
		
		JButton btnSend_1 = new JButton("Send");
		btnSend_1.setBounds(829, 624, 97, 25);
		frame.getContentPane().add(btnSend_1);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setEditable(false);
		textArea_2.setBounds(152, 63, 891, 327);
		frame.getContentPane().add(textArea_2);
	}
}
