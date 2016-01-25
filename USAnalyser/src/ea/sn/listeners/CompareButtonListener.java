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
import java.util.Collections;
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
        index1 = line.indexOf("<" + tagName + ">");
        index2 = line.indexOf("</" + tagName + ">");

        rValue = line.substring(index1 + tagName.length() + 2, index2);
        return rValue;
    }

    public void getUS(String snInstance, List<String> envList, List<UpdateSet> envUSList) {
        //String sUser = EFrame.getUsername().getText();
        //char[] sPassword = EFrame.getPassword().getPassword();
        String sUser = EFrame.getUSUsername().getText();
        char[] sPassword = EFrame.getUSPassword().getPassword();
        String soapAction = "getRecords";
        String filteredAnswer = "";

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
            String name = "", state = "", sys_id = "", createdOn = "", createdBy = "";
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

            if (line.contains("<sys_created_on>")) {
                createdOn = getTag(line.trim(), "sys_created_on");
                updateSet.setCreatedOn(createdOn);
                envUSList.add(updateSet);
            }

            if (line.contains("<sys_created_by>")) {
                createdBy = getTag(line.trim(), "sys_created_by");
                updateSet.setCreatedBy(createdBy);
                envUSList.add(updateSet);
            }

        }
        scanner.close();
    }

    public JButton JRedButton(String str) {
        JButton button = new JButton(str);
        button.setBackground(Color.red);
        button.setOpaque(true);
        button.setSize(new Dimension(50, 20));
        //button.setToolTipText("");
        return button;
    }

    public JButton JGreenButton(String str, String sysID, String tooltip, String instance) {
        JButton button = new JButton(str);
        button.setBackground(Color.green);
        button.setOpaque(true);
        button.setToolTipText(tooltip);
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

    public JButton renderButton(List<String> arrayList, List<UpdateSet> arrayUSList, String endPoint, String uniqListValue) {
        String foundInArray = "|N";
        JButton newButton;
        if (arrayList.contains(uniqListValue)) {
            foundInArray = "|Y";
            UpdateSet us = getUSObject(arrayUSList, uniqListValue);
            String tooltip = us.getName() + " / " + us.getSysid() + " / " + us.getCreatedBy() + " / " + us.getCreatedOn();
            String buttonName = "FOUND (" + us.getState() + ")";
            newButton = JGreenButton(buttonName, us.getSysid(), tooltip, endPoint);
        } else {
            newButton = JRedButton("NOT FOUND");
        }
        return newButton;
    }

    public void addButtonsToFrame(List<JButton> allButtons) {
        int gridLines = (allButtons.size() / 5) + 1;
        /*Because there are 5 columns */

        EFrame.getgridComparisonPanel().setLayout(new GridLayout(gridLines, 5));

        EFrame.getgridComparisonPanel().add(new JLabel("Update Set Name"));
        EFrame.getgridComparisonPanel().add(new JLabel("Instance 1"));
        EFrame.getgridComparisonPanel().add(new JLabel("Instance 2"));
        EFrame.getgridComparisonPanel().add(new JLabel("Instance 3"));
        EFrame.getgridComparisonPanel().add(new JLabel("Instance 4"));

        for (JButton buttonIterator : allButtons) {
            EFrame.getgridComparisonPanel().add(buttonIterator);
        }
    }

    public void emptyAllLists(){
        env1List.clear();
        env2List.clear();
        env3List.clear();
        env4List.clear();
        env1USList.clear();
        env2USList.clear();
        env3USList.clear();
        env4USList.clear();
        fullList.clear();
        fullListUniq.clear();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        EFrame.getgridComparisonPanel().removeAll();
        EFrame.getgridComparisonPanel().revalidate();
        List<JButton> allButtons = new ArrayList<>();
        emptyAllLists(); 
        
        String inst1 = EFrame.getUsEndPoint1().getText();
        String inst2 = EFrame.getUsEndPoint2().getText();
        String inst3 = EFrame.getUsEndPoint3().getText();
        String inst4 = EFrame.getUsEndPoint4().getText();
        String result = "";

        if (inst1 != null && !inst1.isEmpty()) {
            getUS(inst1, env1List, env1USList);
        }
        if (inst2 != null && !inst2.isEmpty()) {
            getUS(inst2, env2List, env2USList);
        }
        if (inst3 != null && !inst3.isEmpty()) {
            getUS(inst3, env3List, env3USList);
        }
        if (inst4 != null && !inst4.isEmpty()) {
            getUS(inst4, env4List, env4USList);
        }

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

        Collections.sort(fullListUniq);
        Collections.reverse(fullListUniq);

        int counter = 1;
        Boolean cond = true;
        for (String uniqListValue : fullListUniq) {

            Boolean foundIn1 = false , foundIn2 = false , foundIn3 = false , foundIn4 = false ;
            JButton inst1Button, inst2Button, inst3Button, inst4Button;

            JButton nameButton = new JButton(uniqListValue);
            nameButton.setToolTipText("Update set number : " + counter);

            inst1Button = renderButton(env1List, env1USList, inst1, uniqListValue);
            inst2Button = renderButton(env2List, env2USList, inst2, uniqListValue);
            inst3Button = renderButton(env3List, env3USList, inst3, uniqListValue);
            inst4Button = renderButton(env4List, env4USList, inst4, uniqListValue);
            
            foundIn1 = inst1Button.getToolTipText() != null && !inst1Button.getToolTipText().isEmpty() ;
            foundIn2 = inst2Button.getToolTipText() != null && !inst2Button.getToolTipText().isEmpty() ;
            foundIn3 = inst3Button.getToolTipText() != null && !inst3Button.getToolTipText().isEmpty() ;
            foundIn4 = inst4Button.getToolTipText() != null && !inst4Button.getToolTipText().isEmpty() ;
                    
            if (EFrame.getFilterGreen().isSelected() == true) {
                cond = (inst1Button.getToolTipText() == null || inst1Button.getToolTipText().isEmpty())
                        || (inst2Button.getToolTipText() == null || inst2Button.getToolTipText().isEmpty())
                        || (inst3Button.getToolTipText() == null || inst3Button.getToolTipText().isEmpty())
                        || (inst4Button.getToolTipText() == null || inst4Button.getToolTipText().isEmpty());
            }

            if (cond) {
                allButtons.add(nameButton);
                allButtons.add(inst1Button);
                allButtons.add(inst2Button);
                allButtons.add(inst3Button);
                allButtons.add(inst4Button);
                result += counter + " | " + uniqListValue + "|" + foundIn1 + "|" + foundIn2 + "|" + foundIn3 + "|" + foundIn4 + "\n";
                counter++;
            }
            
        }

        addButtonsToFrame(allButtons);

        EFrame.getgridComparisonPanel().revalidate();
        logger.log(result, true);
        
    }
}
