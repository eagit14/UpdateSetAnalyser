/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ea.sn.listeners;

import ea.sn.loggers.ELogger;
import ea.sn.manager.ESoapManager;
import ea.sn.ui.EFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import javax.swing.JButton;

/**
 *
 * @author elie.abdelnour
 */
public class CompareButtonListener implements ActionListener {

    ELogger logger;
    String endPoint1;
    String endPoint2;
    String endPoint3;
    List<String> env1List;
    List<String> env2List;
    List<String> env3List;
    List<String> fullList;
    List<String> fullListUniq;

    public CompareButtonListener(ELogger log) {
        System.out.println("*** CompareListener *** ");

        env1List = new ArrayList<String>();
        env2List = new ArrayList<String>();
        env3List = new ArrayList<String>();
        fullList = new ArrayList<String>();
        fullListUniq = new ArrayList<String>();
        logger = log;
    }

    private String getTag(String line, String tagName) {
        int index1, index2;
        index1 = line.indexOf("<" + tagName + ">");
        index2 = line.indexOf("</" + tagName + ">");

        return line.substring(index1 + tagName.length() + 2, index2);
    }

    public void getUS(String snInstance, List<String> envList) {
        String sUser = EFrame.getUsername().getText();
        char[] sPassword = EFrame.getPassword().getPassword();
        String soapAction = "getRecords";
        String filteredAnswer = "";

        System.out.println("Starting Compare : Username= " + sUser + " , pass = " + sPassword + " end = " + snInstance);

        //Sending SOAP request
        ESoapManager soap = new ESoapManager(snInstance, soapAction, null);
        soap.setLogger(logger);
        ESoapManager.setSoapPassword(sPassword);
        ESoapManager.setSoapUserName(sUser);
        String soapAnswer = soap.sendSoapRequest(EFrame.getPostMethod().getSelectedIndex());

        //Displaying result
        Scanner scanner = new Scanner(soapAnswer);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("name")) {
                String value = getTag(line.trim(), "name");
                envList.add(value);
                filteredAnswer = filteredAnswer + value + "\n";
            }
        }
        scanner.close();
        //System.out.println(filteredAnswer);
    }

    public JButton JRedButton(String str) {
        JButton button = new JButton(str);
        button.setBackground(Color.red);
        button.setOpaque(true);
        button.setSize(new Dimension(50,20)); 
        return button;
    }

    public JButton JGreenButton(String str) {
        JButton button = new JButton(str);
        button.setBackground(Color.green);
        button.setOpaque(true);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("********* In COMPARE Listener");
//        getUS("https://silvadev.service-now.com/sys_update_set.do?SOAP", env1List);
//        getUS("https://silvaft.service-now.com/sys_update_set.do?SOAP", env2List);
//        getUS("https://silvastg.service-now.com/sys_update_set.do?SOAP", env3List);

        getUS(EFrame.getUsEndPoint1().getText(), env1List);
        getUS(EFrame.getUsEndPoint2().getText(), env2List);
        getUS(EFrame.getUsEndPoint3().getText(), env3List);
        
        //merge all update sets
        fullList.addAll(env1List);
        fullList.addAll(env2List);
        fullList.addAll(env3List);

        //Get Uniq Values
        HashSet<String> uniqueValues = new HashSet<>(fullList);
        for (String uniqValue : uniqueValues) {
            fullListUniq.add(uniqValue);
        }

        String result = "";
        int gridLines = fullListUniq.size();
        EFrame.getgridComparisonPanel().setLayout(new GridLayout(gridLines, 4));

        for (String uniqListValue : fullListUniq) {
 
                String foundIn1 = "N";
                String foundIn2 = "N";
                String foundIn3 = "N";
                EFrame.getgridComparisonPanel().add(new JButton(uniqListValue));
                if (env1List.contains(uniqListValue)) {
                    foundIn1 = "Y";
                    EFrame.getgridComparisonPanel().add(JGreenButton("FOUND"));
                } else {
                    EFrame.getgridComparisonPanel().add(JRedButton("NOT FOUND"));
                }

                if (env2List.contains(uniqListValue)) {
                    foundIn2 = "Y";
                    EFrame.getgridComparisonPanel().add(JGreenButton("FOUND"));
                } else {
                    EFrame.getgridComparisonPanel().add(JRedButton("NOT FOUND"));
                }

                if (env3List.contains(uniqListValue)) {
                    foundIn3 = "Y";
                    EFrame.getgridComparisonPanel().add(JGreenButton("FOUND"));
                } else {
                    EFrame.getgridComparisonPanel().add(JRedButton("NOT FOUND"));
                }
           
            //System.out.println("US -> " + uniqListValue + foundIn1 + foundIn2 + foundIn3 );
            //result += uniqListValue + " --> " + foundIn1 + foundIn2 + foundIn3 + "\n";
 
            //EFrame.getgridComparisonPanel().add(new JButton("uniqListValue + \" --> \"+ foundIn1 + foundIn2 + foundIn3"));
        }
        EFrame.getgridComparisonPanel().revalidate();
        //EFrame.getgridComparisonArea().setText(result);

    }
}
