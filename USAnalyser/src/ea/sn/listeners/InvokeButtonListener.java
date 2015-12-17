package ea.sn.listeners;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ea.sn.loggers.ELogger;
import ea.sn.manager.ESoapManager;
import ea.sn.ui.EFrame;


public class InvokeButtonListener implements ActionListener{
	EFrame lFrame ; 
    ELogger logger ;
    
	public InvokeButtonListener(ELogger log) {
		System.out.println("*** ButtonListener *** ");
		logger = log;
	}

	public void logAnswer(String soapAnswer, String sFilter) {
		if (!sFilter.isEmpty()) {
			//String filteredAnswer = "<answer>\n";
			String filteredAnswer = "";
			String[] keys = sFilter.split(",");	
			Scanner scanner = new Scanner(soapAnswer);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				int i=0;
				boolean found = false;
				while ( i< keys.length && !found) {
					if (line.contains(keys[i]) || line.contains("Response from end point")) { 
						filteredAnswer = filteredAnswer + line + "\n"  ;
						found = true;
					}
					i++;
				}
			}
			scanner.close();

			//filteredAnswer += "</answer>";
			//EFrame.getMainArea().setText(filteredAnswer);
			EFrame.getMainAreaOutput().setText(filteredAnswer);
		} else {
			//EFrame.getMainArea().setText(soapAnswer);
			EFrame.getMainAreaOutput().setText(soapAnswer);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		String end = EFrame.getJendPoint().getText() ;
		String action = EFrame.getjAction().getText() ;
		String par1Name = EFrame.getParam1Name().getText() ;
		String par1Value = EFrame.getParam1Value().getText() ;
		String par2Name = EFrame.getParam2Name().getText() ;
		String par2Value = EFrame.getParam2Value().getText() ;
		String par3Name = EFrame.getParam3Name().getText() ;
		String par3Value = EFrame.getParam3Value().getText() ;
		String sEnvelope = EFrame.getMainArea().getText();
		String sProxyHost = EFrame.getProxyHost().getText();
		String sProxyPort = EFrame.getProxyPort().getText();
		String sFilter = EFrame.getFilterValue().getText();
		String instances = EFrame.getInstances().getText();
		
		String sUser =  EFrame.getUsername().getText() ;
		char[] sPassword =  EFrame.getPassword().getPassword();
		
		ESoapManager soap = new ESoapManager(end , action , sEnvelope);
		soap.setLogger(logger);
		ESoapManager.setParam(1, par1Name, par1Value);
		ESoapManager.setParam(2, par2Name, par2Value);
		ESoapManager.setParam(3, par3Name, par3Value);
		ESoapManager.setSoapProxyHost(sProxyHost);
		ESoapManager.setSoapProxyPort(sProxyPort);
		ESoapManager.setSoapPassword(sPassword);
		ESoapManager.setSoapUserName(sUser);

		String soapAnswer = soap.sendSoapRequest(EFrame.getPostMethod().getSelectedIndex());
		if (instances.length() > 0) {
			List<String> instanceArray = Arrays.asList(instances.split("\\s*,\\s*"));
		
			for (int i=0; i < instanceArray.size(); i++) {
				String table = end.substring(end.lastIndexOf("/"), end.length());
				String newEndPoint = "https://" + instanceArray.get(i) + ".service-now.com" + table;
				
				ESoapManager.setEndPoint(newEndPoint);
				soapAnswer+= soap.sendSoapRequest(EFrame.getPostMethod().getSelectedIndex());
				//soapAnswer += newEndPoint + "\n";
			}
		}

		logAnswer( soapAnswer, sFilter);
		
	}

}
