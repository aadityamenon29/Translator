import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	
	// TODO : Error logging and exception handling
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
	String last_user_1_text;
	String last_user_1_translated_text;
	String last_user_2_text;
	String last_user_2_translated_text;
	static Writer writer;
	private JFrame frame;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	
	
	static String subscriptionKey = "b01a7efbbf04482d9517536345339c97";

    static String host = "https://api.microsofttranslator.com";
    static String path = "/V2/Http.svc/Translate";

	static HashMap<String, String> hm;
	private JScrollPane scrollPane;
	private JButton btnReportError1;
	private JButton btnNewButton;

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
					hm.put("Arabic", "ar");
					hm.put("German", "de");
					hm.put("Danish", "da");
					hm.put("Hebrew", "he");
					hm.put("Italian", "it");
					hm.put("Japanese", "ja");
					hm.put("Korean", "ko");
					hm.put("Portuguese", "pt");
					hm.put("Spanish", "es");
					hm.put("Russian", "ru");
					hm.put("Thai", "th");
					hm.put("Simplified Chinese", "zh-CHS");
					hm.put("Hindi", "hi");
					writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Log.txt"), "utf-8"));
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
		comboBox1.setModel(new DefaultComboBoxModel(new String[] {"Arabic", "Danish", "English", "French", "German", "Hebrew", "Hindi", "Italian", "Japanese", "Korean", "Portuguese", "Russian", "Spanish", "Thai", "Simplified Chinese"}));
		comboBox1.setBounds(277, 419, 152, 54);
		frame.getContentPane().add(comboBox1);
		
		comboBox2 = new JComboBox();
		comboBox2.setModel(new DefaultComboBoxModel(new String[] {"Arabic", "Danish", "English", "French", "German", "Hebrew", "Hindi", "Italian", "Japanese", "Korean", "Portuguese", "Russian", "Spanish", "Thai", "Simplified Chinese"}));
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
        Style style3 = textPane.addStyle("C", null);
        StyleConstants.setForeground(style3, Color.black);
        StyleConstants.setItalic(style3, true);
        
		btnSend1 = new JButton("Send");
		btnSend1.setForeground(new Color(0, 102, 102));
		btnSend1.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		btnSend1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String user_text = textAreaUser1.getText();
				String source_language = comboBox1.getSelectedItem().toString();
				String target_language = comboBox2.getSelectedItem().toString();
				String translated_text = "Error";
				try {
					translated_text = Translate(user_text, hm.get(target_language), hm.get(source_language));
					last_user_1_text = user_text;
					last_user_1_translated_text = translated_text;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				// TODO : Add original text next to the translated text as well.
				
		
					try {
						doc.insertString(doc.getLength(), "\n"+"User 1: "+translated_text,style1);
						doc.insertString(doc.getLength(), " (o.g text: "+user_text+")", style3);
						
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				textAreaUser1.setText("");
			}
		});
		btnSend1.setBounds(395, 624, 97, 25);
		frame.getContentPane().add(btnSend1);
		
		btnSend2 = new JButton("Send");
		btnSend2.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSend2.setForeground(new Color(0, 102, 102));
		btnSend2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user_text = textAreaUser2.getText();
				//System.out.println(user_text);
				String source_language = comboBox2.getSelectedItem().toString();
				String target_language = comboBox1.getSelectedItem().toString();
				String translated_text = "Error";
				try {
					translated_text = Translate(user_text, hm.get(target_language), hm.get(source_language));
					last_user_2_text = user_text;
					last_user_2_translated_text = translated_text;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				// TODO : Add original text next to the translated text as well.
				
					
					try {
						doc.insertString(doc.getLength(), "\n"+"User 2: "+translated_text,style2);
						doc.insertString(doc.getLength(), " (o.g text: "+user_text+")", style3);
						//System.out.println(translated_text);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				
				textAreaUser2.setText("");
				
			}
		});
		btnSend2.setBounds(906, 624, 97, 25);
		frame.getContentPane().add(btnSend2);
		
		btnReportError1 = new JButton("Report Error!");
		btnReportError1.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnReportError1.setForeground(Color.RED);
		btnReportError1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				//System.out.println("Error reported by user 1. User 1 possibly didn't understand translated text: \'"+last_user_2_translated_text+"\'. "+"Original text sent by User 2 was : \'"+last_user_2_text+"\' at time: "+dateFormat.format(date)+"\n");
				try {
					writer.write("Error reported by user 1. User 1 possibly didn't understand translated text: \'"+last_user_2_translated_text+"\'. "+"Original text sent by User 2 was : \'"+last_user_2_text+"\' at time: "+dateFormat.format(date)+"\n");
					writer.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnReportError1.setBounds(192, 624, 136, 25);
		frame.getContentPane().add(btnReportError1);
		
		btnNewButton = new JButton("Report Error!");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setForeground(Color.RED);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				//System.out.println("Error reported by user 2. User 2 possibly didn't understand translated text: \'"+last_user_1_translated_text+"\'. "+"Original text sent by User 1 was : \'"+last_user_1_text+"\' at time: "+dateFormat.format(date)+"\n");
				try {
					writer.write("Error reported by user 2. User 2 possibly didn't understand translated text: \'"+last_user_1_translated_text+"\'. "+"Original text sent by User 1 was : \'"+last_user_1_text+"\' at time: "+dateFormat.format(date)+"\n");
					writer.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(692, 624, 136, 25);
		frame.getContentPane().add(btnNewButton);
		
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
	        String text_return =  doc.getElementsByTagName("string").item(0).getTextContent();
	        
	        return text_return;
	        
	}
}
