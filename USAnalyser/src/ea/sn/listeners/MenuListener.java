package ea.sn.listeners;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ea.sn.loggers.ELogger;

public class MenuListener  extends Properties  implements ActionListener{

	private static final long serialVersionUID = 1L;
	JComponent mainComponent ;
	ELogger logger;
	
        public MenuListener() {
            //Init menu
        }
    
    
	public JComponent getMainComponent() {
		return mainComponent;
	}

	public void setMainComponent(JComponent mainFrame) {
		this.mainComponent = mainFrame;
	}


	public ELogger getLogger() {
		return logger;
	}


	public void setLogger(ELogger logger) {
		this.logger = logger;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem)(e.getSource());
		String act = source.getActionCommand();
		try {
			if (act.equals("Show favourites")) {
				String filename = "config/favourites.properties" ; 

				load(new FileInputStream(filename));

				JFrame frame = new JFrame("My Favouries");
				frame.setPreferredSize(new Dimension(800,300));
				frame.getContentPane().setMinimumSize(new Dimension(800, 400));
				frame.getContentPane().setLayout(new GridLayout(10, 2));
				frame.getContentPane().add(new JLabel("soap.endpoint.1"));
				frame.getContentPane().add(new JTextField(getProperty("soap.endpoint.1") ));
				frame.getContentPane().add(new JLabel("soap.endpoint.2"));
				frame.getContentPane().add(new JTextField(getProperty("soap.endpoint.2") ));
				frame.getContentPane().add(new JLabel("soap.endpoint.3"));
				frame.getContentPane().add(new JTextField(getProperty("soap.endpoint.3") ));
				frame.getContentPane().add(new JLabel("soap.proxyHost.1"));
				frame.getContentPane().add(new JTextField(getProperty("soap.proxyHost.1") ));
				frame.getContentPane().add(new JLabel("soap.proxyPort.1"));
				frame.getContentPane().add(new JTextField(getProperty("soap.proxyPort.1") ));
				frame.getContentPane().add(new JLabel("soap.envelop.1"));
				frame.getContentPane().add(new JTextField(getProperty("soap.envelop.1") ));
				frame.getContentPane().add(new JLabel("soap.envelop.2"));
				frame.getContentPane().add(new JTextField(getProperty("soap.envelop.2") ));
				frame.pack();
				frame.setVisible(true);
			} else if (act.equals("Clear main panel")) {
				 ((JTextArea)mainComponent).setText("");
			} else if (act.equals("Quit")) {
		    	logger.close();
		    	System.exit(0); 
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
