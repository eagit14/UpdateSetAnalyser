/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ea.sn.listeners;

import ea.sn.comparator.UpdateSet;
import ea.sn.loggers.ELogger;
import ea.sn.manager.ESoapManager;
import ea.sn.ui.EFrame;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;

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
    List<String> env4List;

    List<UpdateSet> env1USList;
    List<UpdateSet> env2USList;
    List<UpdateSet> env3USList;
    List<UpdateSet> env4USList;

    List<String> fullList;
    List<String> fullListUniq;

    public CompareButtonListener(ELogger log) {
        System.out.println("*** CompareListener *** ");

        env1List = new ArrayList<String>();
        env2List = new ArrayList<String>();
        env3List = new ArrayList<String>();
        env4List = new ArrayList<String>();

        env1USList = new ArrayList<UpdateSet>();
        env2USList = new ArrayList<UpdateSet>();
        env3USList = new ArrayList<UpdateSet>();
        env4USList = new ArrayList<UpdateSet>();

        fullList = new ArrayList<String>();
        fullListUniq = new ArrayList<String>();

        logger = log;
    }

    private String getTag(String line, String tagName) {
        int index1, index2;
        String rValue = "";
        System.out.println("Tag" + tagName + " , Line is : " + line);
        index1 = line.indexOf("<" + tagName + ">");
        index2 = line.indexOf("</" + tagName + ">");

        rValue = line.substring(index1 + tagName.length() + 2, index2);
        System.out.println("rValue " + rValue);
        return rValue;
    }

    public void getUS(String snInstance, List<String> envList, List<UpdateSet> envUSList) {
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
        //Should change to always put Param
        String soapAnswer = soap.sendSoapRequest(EFrame.getPostMethod().getSelectedIndex());

        //Displaying result
        Scanner scanner = new Scanner(soapAnswer);
        UpdateSet updateSet = null;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String name = "";
            String state = "";
            String sys_id = "";
            if (line.contains("<name>")) {
                name = getTag(line.trim(), "name");
                envList.add(name);

                updateSet = new UpdateSet();
                updateSet.setName(name);
                //filteredAnswer = filteredAnswer + value + "\n";
            }

            if (line.contains("<state>")) {
                state = getTag(line.trim(), "state");
                updateSet.setState(state);
            }

            if (line.contains("<sys_id>")) {
                sys_id = getTag(line.trim(), "sys_id");
                updateSet.setSysid(sys_id);
                envUSList.add(updateSet);
            }
        }
        scanner.close();
        //System.out.println(filteredAnswer);
    }

    public JButton JRedButton(String str) {
        JButton button = new JButton(str);
        button.setBackground(Color.red);
        button.setOpaque(true);
        button.setSize(new Dimension(50, 20));
        //button.setToolTipText("");
        return button;
    }

    public JButton JGreenButton(String str, String sysID , String name, String instance) {
        //https://silvadev.service-now.com/sys_update_set.do?sys_id=8d82fb5d37341200aefa8d2754990ea0
        JButton button = new JButton(str);
        button.setBackground(Color.green);
        button.setOpaque(true);
        button.setToolTipText(name + " / " + sysID);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                URI uri;
                try {
                    uri = new URI(instance.replace("SOAP", "sys_id=" + sysID));
                    Desktop dt = Desktop.getDesktop();
                    dt.browse(uri.resolve(uri));
                } catch (URISyntaxException ex) {
                    Logger.getLogger(CompareButtonListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(CompareButtonListener.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        return button;
    }

    public UpdateSet getUSObject(List<UpdateSet> array, String key) {
        for (UpdateSet usIterator : array) {
            if (usIterator.getName().equals(key)) {
                return usIterator;
            }
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("********* In COMPARE Listener");
        EFrame.getgridComparisonPanel().removeAll();
        EFrame.getgridComparisonPanel().revalidate();

        getUS(EFrame.getUsEndPoint1().getText(), env1List, env1USList);
        getUS(EFrame.getUsEndPoint2().getText(), env2List, env2USList);
        getUS(EFrame.getUsEndPoint3().getText(), env3List, env3USList);
        getUS(EFrame.getUsEndPoint4().getText(), env4List, env4USList);

        //merge all update sets
        fullList.addAll(env1List);
        fullList.addAll(env2List);
        fullList.addAll(env3List);
        fullList.addAll(env4List);

        //Get Uniq Values
        HashSet<String> uniqueValues = new HashSet<>(fullList);
        for (String uniqValue : uniqueValues) {
            fullListUniq.add(uniqValue);
        }

        String result = "";
        int gridLines = fullListUniq.size() + 1;
        EFrame.getgridComparisonPanel().setLayout(new GridLayout(gridLines, 5));

        EFrame.getgridComparisonPanel().add(new JLabel("Update Set Name"));
        EFrame.getgridComparisonPanel().add(new JLabel("Instance 1"));
        EFrame.getgridComparisonPanel().add(new JLabel("Instance 2"));
        EFrame.getgridComparisonPanel().add(new JLabel("Instance 3"));
        EFrame.getgridComparisonPanel().add(new JLabel("Instance 4"));

        int counter = 1;
        for (String uniqListValue : fullListUniq) {

            String foundIn1 = "|N";
            String foundIn2 = "|N";
            String foundIn3 = "|N";
            String foundIn4 = "|N";
            EFrame.getgridComparisonPanel().add(new JButton(uniqListValue));
            if (env1List.contains(uniqListValue)) {
                foundIn1 = "|Y";
                UpdateSet us = getUSObject(env1USList, uniqListValue);
                JButton gButton = JGreenButton("FOUND (" + us.getState() + ")", us.getSysid() , us.getName(), EFrame.getUsEndPoint1().getText());
                EFrame.getgridComparisonPanel().add(gButton);
            } else {
                EFrame.getgridComparisonPanel().add(JRedButton("NOT FOUND"));
            }

            if (env2List.contains(uniqListValue)) {
                foundIn2 = "|Y";
                UpdateSet us = getUSObject(env2USList, uniqListValue);
                JButton gButton = JGreenButton("FOUND (" + us.getState() + ")", us.getSysid() , us.getName(), EFrame.getUsEndPoint2().getText());
                EFrame.getgridComparisonPanel().add(gButton);
            } else {
                EFrame.getgridComparisonPanel().add(JRedButton("NOT FOUND"));
            }

            if (env3List.contains(uniqListValue)) {
                foundIn3 = "|Y";
                UpdateSet us = getUSObject(env3USList, uniqListValue);
                JButton gButton = JGreenButton("FOUND (" + us.getState() + ")", us.getSysid() , us.getName(), EFrame.getUsEndPoint3().getText());
                EFrame.getgridComparisonPanel().add(gButton);
            } else {
                EFrame.getgridComparisonPanel().add(JRedButton("NOT FOUND"));
            }

            if (env4List.contains(uniqListValue)) {
                foundIn4 = "|Y";
                UpdateSet us = getUSObject(env4USList, uniqListValue);
                JButton gButton = JGreenButton("FOUND (" + us.getState() + ")", us.getSysid(), us.getName(), EFrame.getUsEndPoint4().getText());
                EFrame.getgridComparisonPanel().add(gButton);
            } else {
                EFrame.getgridComparisonPanel().add(JRedButton("NOT FOUND"));
            }
            //System.out.println("US -> " + uniqListValue + foundIn1 + foundIn2 + foundIn3 );
            result += counter + " | " + uniqListValue + foundIn1 + foundIn2 + foundIn3 + foundIn4 + "\n";
            counter++;
        }
        EFrame.getgridComparisonPanel().revalidate();
        //EFrame.getgridComparisonArea().setText(result);
        System.out.println(result);

    }
}
