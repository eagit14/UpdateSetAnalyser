package ea.sn.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import ea.sn.listeners.MenuListener;
import ea.sn.loggers.ELogger;

public class MyMenu {
	JMenuBar menuBar;
    JComponent parentComponent ; 
    ELogger logger;
    
	public MyMenu(JComponent comp , ELogger log) {
		menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255,255,255));
		parentComponent = comp;
		logger = log;
		createMenuBar();
	}
	
	public JComponent getParentComponent() {
		return parentComponent;
	}

	public void setParentComponent(JComponent parentComponent) {
		this.parentComponent = parentComponent;
	}

	public JMenuBar createMenuBar() {

		JMenuItem menuItem;
		MenuListener mm = new MenuListener();
		mm.setMainComponent(parentComponent);
		mm.setLogger(logger);
		
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		menuItem = new JMenuItem("Show favourites",KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK ));
		menuItem.addActionListener(mm);
		menu.add(menuItem);
		menuItem = new JMenuItem("Quit",KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK ));
		menuItem.addActionListener(mm);
		menu.add(menuItem);

		menu = new JMenu("Window");
		menuBar.add(menu);
		menuItem = new JMenuItem("Clear main panel",KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));
		menuItem.addActionListener(mm);
		menu.add(menuItem);
		
		return menuBar;
	}


	public JMenuBar getMenuBar() {
		return menuBar;
	}


	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	
}