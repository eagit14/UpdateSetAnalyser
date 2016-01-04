package ea.sn.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.CallFactory;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Call;
import com.twilio.sdk.resource.instance.Message;

public class TwilioPanel {
	JTextField toField;
	JTextArea msgArea;
	
	public TwilioPanel() {
	     System.out.println("Initizalizing twilio panel");
	     toField = new  JTextField();
	     toField.setText("+33629874490");
	     msgArea = new JTextArea() ;
	}

	public  JPanel buildPanel() {
		JPanel panel = new JPanel() ;

		panel.setBackground(new Color(0,240,0));
		panel.setLayout(new GridLayout(3, 2));

		panel.add(new JLabel("To"));
		panel.add(toField) ;
		panel.add(new JLabel("From"));
		panel.add(new JTextField()) ;
		panel.add(new JLabel("Message"));
		panel.add(msgArea) ;

		return panel;
	}

	public JPanel getPanel() {
		JPanel twilioPanel = new JPanel();
		twilioPanel.setLayout(new BorderLayout());
		twilioPanel.add(buildPanel(), BorderLayout.PAGE_START);
		twilioPanel.add(new JScrollPane(new JTextArea()), BorderLayout.CENTER);
		JButton twilioButton = new JButton("Send");
		twilioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					final String ACCOUNT_SID = "AC52e41438753f1093862f75533f188224"; 
					final String AUTH_TOKEN = "fd552123623a550c1438a7ecb8f2bcc3"; 

					//SMS
					TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN); 

					List<NameValuePair> params = new ArrayList<NameValuePair>(); 
					//params.add(new BasicNameValuePair("To", "+33629874490"));
					params.add(new BasicNameValuePair("To", toField.getText()));
					params.add(new BasicNameValuePair("From", "+13012460945")); 
					//params.add(new BasicNameValuePair("Body", "EA- My Java Test from Twilio"));
					params.add(new BasicNameValuePair("Body", msgArea.getText()));

					MessageFactory messageFactory = client.getAccount().getMessageFactory(); 
					Message message = messageFactory.create(params); 
					System.out.println(message.getSid()); 
					
					
					//CALL
//					TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN); 
//					List<NameValuePair> params = new ArrayList<NameValuePair>(); 
//					params.add(new BasicNameValuePair("To", "+33629874490")); 
//					params.add(new BasicNameValuePair("From", "+13012460945"));   
//					params.add(new BasicNameValuePair("Url", "www.google.com")); 
//					params.add(new BasicNameValuePair("Method", "GET"));  
//					params.add(new BasicNameValuePair("FallbackMethod", "GET"));  
//					params.add(new BasicNameValuePair("StatusCallbackMethod", "GET"));    
//					params.add(new BasicNameValuePair("Record", "false")); 
//					CallFactory callFactory = client.getAccount().getCallFactory(); 
//					Call call = callFactory.create(params); 
//					System.out.println(call.getSid()); 
				}
				catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
				);
		twilioPanel.add(twilioButton, BorderLayout.LINE_END);
		return twilioPanel;
	}
}
