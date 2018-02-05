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
	
	
	static String subscriptionKey = "c3a369181f4b42a5abda2aaa5294e512";

    static String host = "https://api.microsofttranslator.com";
    static String path = "/V2/Http.svc/Translate";

	static HashMap<String, String> hm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					hm = new HashMap<String, String>();
					hm.put("English", "en-us");
					hm.put("French", "fr-fr");
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
				String source_language = comboBox1.getSelectedItem().toString();
				String target_language = comboBox2.getSelectedItem().toString();
				String translated_text = "Error";
				try {
					translated_text = Translate(user_text, hm.get(target_language), hm.get(source_language));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String old_screen_text = MainScreen.getText();
				// TODO : Add original text next to the translated text as well.
				if(old_screen_text != null){
					MainScreen.setText(old_screen_text+"\n"+"User 1: "+translated_text);
				}
				else{
					MainScreen.setText("User 1"+translated_text);
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
				String source_language = comboBox2.getSelectedItem().toString();
				String target_language = comboBox1.getSelectedItem().toString();
				System.out.println("source :"+source_language+" "+hm.get(source_language));
				System.out.println("target: "+target_language+" "+hm.get(target_language));
				String translated_text = "Error";
				try {
					translated_text = Translate(user_text, hm.get(target_language), hm.get(source_language));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String old_screen_text = MainScreen.getText();
				// TODO : Add original text next to the translated text as well.
				if(old_screen_text != null){
					MainScreen.setText(old_screen_text+"\n"+"User 2: "+translated_text);
					
				}
				else{
					MainScreen.setText("User 2"+translated_text);
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
	        //return response.toString();
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        InputSource is = new InputSource();
	        is.setCharacterStream(new StringReader(response.toString()));
	        Document doc = db.parse(is);
	        doc.getDocumentElement().normalize();
	        return doc.getElementsByTagName("string").item(0).getTextContent();
	        
	}
}
