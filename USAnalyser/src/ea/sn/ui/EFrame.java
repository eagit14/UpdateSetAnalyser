package ea.sn.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import jxl.write.WriteException;
import ea.sn.listeners.InvokeButtonListener;
import ea.sn.loggers.ELogger;
import ea.sn.loggers.Utils;
import ea.sn.manager.ExcelManager;

public class EFrame {
	int sizeX ; 
	int sizeY ;
	int locationX ;
	int locationY;
	JFrame frame; 
	static File chosenFile;
	static ELogger logger;
	public static boolean RIGHT_TO_LEFT = false;

	static JTextField jendPoint ;
	static JTextField jAction ;
	static JTextField param1Name;

	static JTextField param1Value;
	static JTextField param2Name;
	static JTextField param2Value;
	static JTextField param3Name;
	static JTextField param3Value;
	static JTextField filterValue;
	static JTextField instances;
	static JTextField username;
	static JTextField proxyHost;
	static JTextField proxyPort;
	static JTextField outputDir;
	static JPasswordField password;
	static JTextArea mainArea;
	static JTextArea mainAreaOutput;
	static JTextArea updateSetArea;

	static JComboBox<String> postMethod ;
	static Color leftPanelColor ;
	static Color mainAreaColor ;
	static Color bottomPanelColor ;
	static Color topPanelColor ;
	
	public static JTextField getParam2Name() {
		return param2Name;
	}


	public static void setParam2Name(JTextField param2Name) {
		EFrame.param2Name = param2Name;
	}


	public static JTextField getParam2Value() {
		return param2Value;
	}


	public static void setParam2Value(JTextField param2Value) {
		EFrame.param2Value = param2Value;
	}


	public static JTextField getParam3Name() {
		return param3Name;
	}


	public static void setParam3Name(JTextField param3Name) {
		EFrame.param3Name = param3Name;
	}


	public static JTextField getParam3Value() {
		return param3Value;
	}


	public static void setParam3Value(JTextField param3Value) {
		EFrame.param3Value = param3Value;
	}

	public static JTextField getFilterValue() {
		return filterValue;
	}
	
	
	public static JTextField getInstances() {
		return instances;
	}
	
	public EFrame() {
		System.out.println("*** NEW EFRAME ***");
		boolean initialize = true;
		frame = new JFrame("Web Now 1.0!");
		sizeX = 1000;
		sizeY = 600;
		locationX = 400;
		locationY = 50;
		leftPanelColor = new Color(230,230,230);
		mainAreaColor = new Color(255,255,255);
		bottomPanelColor = new Color(10,10,200);
		topPanelColor = new Color(240,0,0);

		logger = new ELogger();
		logger.log("Building Main Frame");
		String[] postChoices = { "Params", "Envelope" };
		postMethod = new JComboBox<>(postChoices);
		postMethod.setSelectedIndex(0);
		jendPoint = new JTextField("https://empeabdelnour.service-now.com/em_event.do?SOAP") ;
		
		
			
		postMethod.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        mainAreaOutput.setText("Changing post method");
		    }
		});
		
		jAction = new JTextField("getRecords") ;
		param1Name = new JTextField() ;
		param1Value = new JTextField() ;
		param2Name = new JTextField() ;
		param2Value = new JTextField() ;
		param3Name = new JTextField() ;
		param3Value = new JTextField() ;
		filterValue = new JTextField() ;
		instances = new JTextField() ;
		outputDir = new JTextField("/Users/elie.abdelnour/Tools/") ;
		
		if (initialize) {
			username = new JTextField("elie.abdelnour.admin") ;
			password = new JPasswordField("") ;
			proxyHost = new JTextField("") ;
			proxyPort = new  JTextField("") ;
		} else {
			username = new JTextField("") ;
			password = new JPasswordField("") ;
			proxyHost = new JTextField("") ;
			proxyPort = new  JTextField("") ;
		}
		mainArea = new JTextArea(); 
		mainArea.setBackground(mainAreaColor);
		mainArea.setLineWrap(true);
		
		mainAreaOutput = new JTextArea();
		mainAreaOutput.setBackground(mainAreaColor);
		mainAreaOutput.setLineWrap(true);
		
		updateSetArea = new JTextArea(); 
		updateSetArea.setBackground(mainAreaColor);
		updateSetArea.setLineWrap(true);
						
		MyMenu mm = new MyMenu(mainArea , logger);
		frame.setJMenuBar(mm.getMenuBar());
	}


	public static JTextField getUsername() {
		return username;
	}


	public static void setUsername(JTextField username) {
		EFrame.username = username;
	}


	public static JPasswordField getPassword() {
		return password;
	}


	public static void setPassword(JPasswordField password) {
		EFrame.password = password;
	}


	public static JTextField getProxyHost() {
		return proxyHost;
	}


	public static void setProxyHost(JTextField proxyHost) {
		EFrame.proxyHost = proxyHost;
	}


	public static JTextField getProxyPort() {
		return proxyPort;
	}


	public static void setProxyPort(JTextField proxyPort) {
		EFrame.proxyPort = proxyPort;
	}


	public static JComboBox<String> getPostMethod() {
		return postMethod;
	}


	public static void setPostMethod(JComboBox<String> postMethod) {
		EFrame.postMethod = postMethod;
	}


	public static JTextArea getMainArea() {
		return mainArea;
	}

	public static JTextArea getMainAreaOutput() {
		return mainAreaOutput;
	}


	public static JTextArea getUpdateSetArea() {
		return updateSetArea;
	}


	public static void setUpdateSetArea(JTextArea updateSetArea) {
		EFrame.updateSetArea = updateSetArea;
	}

	public static void appendText(String newString) {
		String currentString = getUpdateSetArea().getText();
		String finalString = currentString + "\n" + newString ;
		getUpdateSetArea().setText(finalString);
	}
	
	public static void setMainArea(JTextArea mainArea) {
		EFrame.mainArea = mainArea;
	}

	public static void setMainAreaOutput(JTextArea mainAreaOutput) {
		EFrame.mainAreaOutput = mainAreaOutput;
	}

	public static JTextField getJendPoint() {
		return jendPoint;
	}


	public static void setJendPoint(JTextField jendPoint) {
		EFrame.jendPoint = jendPoint;
	}


	public static JTextField getjAction() {
		return jAction;
	}


	public static void setjAction(JTextField jAction) {
		EFrame.jAction = jAction;
	}


	public JFrame getFrame() {
		return frame;
	}


	public void setFrame(JFrame frame) {
		this.frame = frame;
	}


	public static JTextField getParam1Name() {
		return param1Name;
	}


	public static void setParam1Name(JTextField param1Name) {
		EFrame.param1Name = param1Name;
	}


	public static JTextField getParam1Value() {
		return param1Value;
	}


	public static void setParam1Value(JTextField param1Value) {
		EFrame.param1Value = param1Value;
	}


	public void buildAndShowFrame() {
		frame.setPreferredSize(new Dimension(sizeX, sizeY));
		frame.setMinimumSize(new Dimension(sizeX, sizeY));
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		//frame.setLocation(locationX,locationY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLayout(new BorderLayout());
		addComponentsToPane(frame.getContentPane());

		frame.pack();
		frame.setVisible(true);
	}

	public static JPanel buildCenterPanel() {
		JPanel cPanel = new JPanel() ;
		cPanel.setLayout(new GridLayout(2, 2));

		cPanel.add(new JScrollPane(mainArea)) ;
		cPanel.add(new JScrollPane(mainAreaOutput)) ;
		
		return cPanel;
	}
	
	public static JPanel buildTopPanel() {
		JPanel panel = new JPanel() ;
		panel.setBackground(topPanelColor);
		panel.setLayout(new GridLayout(3, 4));

		JLabel jlabel = new JLabel("SOAP Endpoint") ;
		
		panel.add(jlabel) ;
		panel.add(jendPoint) ;
		panel.add(new JLabel("")) ;
		panel.add(new JLabel("")) ;
		
		panel.add(new JLabel("Username")) ;
		panel.add(username) ;
		panel.add(new JLabel("Password")) ;
		panel.add(password) ;

		panel.add(new JLabel("Proxy host")) ;
		panel.add(proxyHost) ;
		panel.add(new JLabel("Proxy port")) ;
		panel.add(proxyPort) ;
		
		
		return panel;

	}

	public static JPanel buildTab2TopPanel() {
		JPanel panel = new JPanel() ;
		panel.setBackground(new Color(0,0,240));
		panel.setLayout(new GridLayout(1, 4));

		panel.add(new JLabel("Output directory"));
		
		panel.add(outputDir) ;
		
		panel.add(new JLabel(""));
		
		JButton browseButton = new JButton("Browse");
		browseButton.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser= new JFileChooser();
			    int choice = chooser.showOpenDialog(chooser);
			    if (choice != JFileChooser.APPROVE_OPTION) return;
			    chosenFile = chooser.getSelectedFile();
				
			}
		});
		panel.add(browseButton) ;
		
		return panel;

	}
	
	public static JPanel buildLeftPanel() {
		JPanel panel = new JPanel() ;
		panel.setBackground(leftPanelColor);;
		panel.setLayout(new GridLayout(0, 2));

		panel.add(new JLabel("POST Method ")) ;
		panel.add(postMethod) ;

		panel.add(new JLabel("SOAP Action")) ;
		panel.add(jAction) ;

		for (int i = 0 ; i<2 ; i++) {
			panel.add(new JLabel("")) ;
		}

		panel.add(new JLabel("Param1 name ")) ;
		panel.add(param1Name) ;
		panel.add(new JLabel("Param1 value ")) ;
		panel.add(param1Value); 

		panel.add(new JLabel("Param2 name ")) ;
		panel.add(param2Name) ;
		panel.add(new JLabel("Param2 value ")) ;
		panel.add(param2Value) ;

		panel.add(new JLabel("Param3 name ")) ;
		panel.add(param3Name) ;
		panel.add(new JLabel("Param3 value ")) ;
		panel.add(param3Value) ;

		for (int i = 0 ; i<4 ; i++) {
			panel.add(new JLabel("")) ;
		}

		panel.add(new JLabel("Filter on")) ;
		panel.add(filterValue) ;
		
		panel.add(new JLabel("Other instances if any")) ;
		panel.add(instances) ;
		
		
		for (int i = 0 ; i<14 ; i++) {
			panel.add(new JLabel("")) ;
		}

		return panel;

	}

	public static void addComponentsToPane(Container pane) {

		if (!(pane.getLayout() instanceof BorderLayout)) {
			pane.add(new JLabel("Container doesn't use BorderLayout!"));
			return;
		}

		if (RIGHT_TO_LEFT) {
			pane.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
		}

		//Webservice panel
		JPanel webservicePanel = new JPanel();
		webservicePanel.setLayout(new BorderLayout());
		webservicePanel.add(buildTopPanel(), BorderLayout.PAGE_START);
		//webservicePanel.add(new JScrollPane(mainArea), BorderLayout.CENTER);
		webservicePanel.add(buildCenterPanel(), BorderLayout.CENTER);
		webservicePanel.add(buildLeftPanel(), BorderLayout.LINE_START);
		JLabel bottomLabel = new JLabel("Should you need any assistance, please contact elie.abdelnour@service.com ");
		webservicePanel.add(bottomLabel, BorderLayout.PAGE_END);
		JButton sendButton = new JButton(">> Invoke");
		sendButton.addActionListener(new InvokeButtonListener(logger));   
		webservicePanel.add(sendButton, BorderLayout.LINE_END);
			
		//Update set panel
		JPanel updateSetPanel = new JPanel(new BorderLayout());
		updateSetPanel.add(buildTab2TopPanel(), BorderLayout.PAGE_START);
		updateSetPanel.add(new JScrollPane(updateSetArea), BorderLayout.CENTER);
	  	
	  	JButton analyzeButton = new JButton("Analyze!");
	  	analyzeButton.addActionListener(new ActionListener() {
	  		public void actionPerformed(ActionEvent e) {

	  			try {
	  				Desktop d= Desktop.getDesktop();
	  				String filename = null;

	  				//d.browse(new URI("https://lafamrdev.service-now.com/u_labor_type_list.do?XML"));
	  				
	  				if (chosenFile == null) { 
	  					System.out.println("Please choose file!");
	  					updateSetArea.setText("Please choose file");
	  				}
	  				else { 
	  					String path = chosenFile.toString() ;
	  					System.out.println(path);	
	  					updateSetArea.setText("Analyzing file " + path + ", please wait... ");
	  					try {

	  						ExcelManager emt = new ExcelManager();
	  						emt.setInputFile(path);
	  						filename = outputDir.getText() + "UpdateSetAnalysisResult-" + new Utils().getDateTime() + ".xls" ;
	  						emt.setOutputFile(filename);
	  						//emt.setOutputFile("/Users/elie.abdelnour/Tools/UpdateSetAnalysisResult-" + new Utils().getDateTime() + ".xls");
	  						emt.setArea(updateSetArea);
	  						emt.write();
	  					} catch (WriteException e1) {
	  						e1.printStackTrace();
	  					} catch (IOException e1) {
	  						e1.printStackTrace();
	  					}
	  					appendText("Analyzing file " + path + " DONE");
	  					d.open(new File(filename));
	  					
//		  				String mailTo = "elie.abdelnour@servicenow.com";
//		  				String subject = "Logs from WebNow";
//		  				String body = updateSetArea.getText().replaceAll(" ", "%20").replaceAll("\n", "%0D%0A");
//		  				final String mailURIStr = String.format("mailto:%s?subject=%s&cc=%s&body=%s",mailTo, subject.replaceAll(" ", "%20"), mailTo, body);
//		  				final URI mailURI = new URI(mailURIStr);
//		  				d.mail(mailURI);
	  				}
	  			} catch (Exception e1) {
	  				e1.printStackTrace();
	  			}
	  		}
	});  

	  	updateSetPanel.add(analyzeButton, BorderLayout.LINE_END);
	  	
		//Tabbed pane
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setOpaque(false);
		tabbedPane.addTab("Web Service", null, webservicePanel,"SOAP Webservice");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		tabbedPane.addTab("Update set Analyzer", null, updateSetPanel, "Analyze update set and generate excel");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
//		tabbedPane.addTab("Twilio Integration", null, twilioPanel, "Run commands");
		tabbedPane.addTab("Twilio Integration", null, new TwilioPanel().getPanel(), "Run commands");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		tabbedPane.setSelectedIndex(0);
		//tabbedPane.getRootPane().setDefaultButton(sendButton);
		pane.add(tabbedPane);

	}

}
