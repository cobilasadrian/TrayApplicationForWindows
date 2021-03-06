package com.inther.main;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import com.inther.ui.HeartbeatWindow;
import com.inther.ui.SensorsStateWindow;
import com.inther.ui.ThresholdValuesWindow;

public class TrayApp{
	
	/**
	 * imageForTray() method adapts main icon to different sizes
	 * @systemTray the systemTray of the system
	 */
	private static Image imageForTray(SystemTray systemTray){
	    Image trayImage = Toolkit.getDefaultToolkit().getImage("src/com/inther/resources/trayIcon.png");
	    Dimension trayIconSize = systemTray.getTrayIconSize();
	    trayImage = trayImage.getScaledInstance(trayIconSize.width, trayIconSize.height, Image.SCALE_SMOOTH);
	    return trayImage;
	}
	
	//start of main method
	public static void main(String []args){
	    //checking for support
	    if(!SystemTray.isSupported()){
	        System.out.println("System tray is not supported.");
	        return ;
	    }
	    //get the systemTray of the system
	    SystemTray systemTray = SystemTray.getSystemTray();
	    
	    //get image 
	    Image image = imageForTray(systemTray);
	
	    //popupmenu
	    PopupMenu trayPopupMenu = new PopupMenu();
	
	    //1t menuitem for popupmenu
	    MenuItem sensorsState = new MenuItem("Sensors state");
	    sensorsState.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {	                	
        		try {
					new SensorsStateWindow(); //open Sensors state window	
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}            	
	        }
	    });     
	    trayPopupMenu.add(sensorsState);
	   
	    //2nd menuitem for popupmenu
	    MenuItem heartbeat = new MenuItem("Heartbeat");
	    heartbeat.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            try {
	            	new HeartbeatWindow(); //open Heartbeat window
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}        
	        }
	    });     
	    trayPopupMenu.add(heartbeat);
	
	    //3rd menuitem of popupmenu
	    MenuItem thresholdValues = new MenuItem("Threshold Values");
	    thresholdValues.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	try {
					new ThresholdValuesWindow(); //open ThresholdValues window
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} //open ThresholdValues window
	        	
	        }
	    });
	    trayPopupMenu.add(thresholdValues);
	    
	    //4th menuitem of popupmenu
	    MenuItem close = new MenuItem("Close");
	    close.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            System.exit(0);             
	        }
	    });
	    trayPopupMenu.add(close);
	
	    //setting tray icon
	    TrayIcon trayIcon = new TrayIcon(image, "Sensors current state (right click)", trayPopupMenu);
	    //adjust to default size as per system recommendation
	    trayIcon.setImageAutoSize(true);
	
	    try{
	        systemTray.add(trayIcon);
	    }catch(AWTException awtException){
	        awtException.printStackTrace();
	    }
	}
}