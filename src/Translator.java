import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.*;
import org.xml.sax.InputSource;
import org.w3c.dom.*;
import java.awt.EventQueue;
import java.util.HashMap;
import javax.swing.JFrame;
import java.awt.List;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
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
import javax.swing.JTextPane;

public class Translator {
	
	// TODO : Error logging
	// TODO : Could coding of user 1 and user 2
	// TODO : BUtton for error logging, file writing possibly 
	// TODO : should also see original source text next to translated text
	JTextArea textAreaUser1;
	JTextArea textAreaUser2;
	JButton btnSend1;
	JButton btnSend2;
	JComboBox comboBox1;
	JComboBox comboBox2;
	JTextPane textPane;
	private JFrame frame;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	
	
	static String subscriptionKey = "b01a7efbbf04482d9517536345339c97";

    static String host = "https://api.microsofttranslator.com";
    static String path = "/V2/Http.svc/Translate";

	static HashMap<String, String> hm;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					hm = new HashMap<String, String>();
					hm.put("English", "en");
					hm.put("French", "fr");
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
		comboBox1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 20));
		comboBox1.setModel(new DefaultComboBoxModel(new String[] {"English", "Spanish", "French", "German", "Italian"}));
		comboBox1.setBounds(277, 419, 152, 54);
		frame.getContentPane().add(comboBox1);
		
		comboBox2 = new JComboBox();
		comboBox2.setModel(new DefaultComboBoxModel(new String[] {"English", "Spanish", "French", "German", "Italian"}));
		comboBox2.setFont(new Font("Yu Gothic UI", Font.PLAIN, 20));
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

        textPane = new JTextPane();
        textPane.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        scrollPane = new JScrollPane();
		scrollPane.setBounds(156, 49, 887, 324);
		frame.getContentPane().add(scrollPane);
		
		scrollPane.setViewportView(textPane);
		StyledDocument doc = textPane.getStyledDocument();
        Style style1 = textPane.addStyle("A", null);
        StyleConstants.setForeground(style1, Color.red);
        Style style2 = textPane.addStyle("B", null);
        StyleConstants.setForeground(style2, Color.blue);
        
		btnSend1 = new JButton("Send");
		
		btnSend1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String user_text = textAreaUser1.getText();
				String source_language = comboBox1.getSelectedItem().toString();
				String target_language = comboBox2.getSelectedItem().toString();
				String translated_text = "Error";
				try {
					translated_text = Translate(user_text, hm.get(target_language), hm.get(source_language));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				// TODO : Add original text next to the translated text as well.
				
		
					try {
						doc.insertString(doc.getLength(), "\n"+"User 1: "+translated_text,style1);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
				System.out.println(user_text);
				String source_language = comboBox2.getSelectedItem().toString();
				String target_language = comboBox1.getSelectedItem().toString();
				String translated_text = "Error";
				try {
					translated_text = Translate(user_text, hm.get(target_language), hm.get(source_language));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				// TODO : Add original text next to the translated text as well.
				
					
					try {
						doc.insertString(doc.getLength(), "\n"+"User 2: "+translated_text,style2);
						System.out.println(translated_text);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				
				textAreaUser2.setText("");
				
			}
		});
		btnSend2.setBounds(829, 624, 97, 25);
		frame.getContentPane().add(btnSend2);
		
		
		
		
//		StyledDocument doc = textPane.getStyledDocument();
//		Style style1 = textPane.addStyle("A", null);
//		Style style2 = textPane.addStyle("B", null);
		
		
//        try {
//			doc.insertString(doc.getLength(), "RED ",style1);
//		} catch (BadLocationException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//        try {
//        	doc.insertString(doc.getLength(), "\n", null);
//			doc.insertString(doc.getLength(), "BLUE",style2);
//		} catch (BadLocationException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
	}
	
	private String Translate(String text, String target, String from) throws Exception{
		//return text;
		 	String encoded_query = URLEncoder.encode (text, "UTF-8");
	        String params = "?to=" + target + "&text=" + encoded_query + "&from=" + from;
	        URL url = new URL (host + path + params);

	        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);
	        connection.setDoOutput(true);

	        StringBuilder response = new StringBuilder ();
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(connection.getInputStream()));
	        String line;
	        while ((line = in.readLine()) != null) {
	            response.append(line);
	        }
	        in.close();

	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        InputSource is = new InputSource();
	        is.setCharacterStream(new StringReader(response.toString()));
	        Document doc = db.parse(is);
	        doc.getDocumentElement().normalize();
	        return doc.getElementsByTagName("string").item(0).getTextContent();
	        
	}
}
