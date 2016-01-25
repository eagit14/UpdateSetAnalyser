/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ea.sn.comparator;

import ea.sn.listeners.CompareButtonListener;
import ea.sn.loggers.ELogger;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author elie.abdelnour
 */
public class Comparator {

    JTextField inst1;
    JTextField inst2;
    JTextField inst3;

    public Comparator() {
        System.out.println("Initizalizing Comparator");
        inst1 = new JTextField();
        inst2 = new JTextField();
        inst3 = new JTextField();
    }

    public JPanel buildInstPanel() {
        JPanel panel = new JPanel();

        panel.setBackground(new Color(0, 240, 0));
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("Instance1 (ex: fill DEV for DEV.service-now.com)"));
        panel.add(inst1);
        panel.add(new JLabel("Instance2 (ex: fill QUA for QUA.service-now.com)"));
        panel.add(inst2);
        panel.add(new JLabel("Instance3 (ex: fill PRD for PRD.service-now.com)"));
        panel.add(inst3);

        return panel;
    }

    public JPanel buildGridPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 4));
        panel.add(new JButton("Update Set"));
        panel.add(new JButton("I1"));
        panel.add(new JButton("I2"));
        panel.add(new JButton("I3"));

        return panel;
    }


    
    
    public JPanel getPanel() {
        
        JPanel ComPanel = new JPanel();
        ComPanel.setLayout(new BorderLayout());
        ComPanel.add(buildInstPanel(), BorderLayout.PAGE_START);
        ComPanel.add(buildGridPanel(), BorderLayout.CENTER);
        
        ELogger usLogger = new ELogger();
        JButton compButton = new JButton("Compare");
        compButton.addActionListener(new CompareButtonListener(usLogger));
        
        ComPanel.add(compButton, BorderLayout.LINE_END);
        return ComPanel;
    }

    public static void main(String[] args) {
        System.out.println("Comparator");
    }

}
