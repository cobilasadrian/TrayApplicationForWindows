package com.inther.ui;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.inther.main.JsonParser;
import com.inther.main.Message;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HeartbeatWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	//Read images from resources
	private BufferedImage led = ImageIO.read(new File("src/com/inther/resources/led.png"));
	private BufferedImage ledBW = ImageIO.read(new File("src/com/inther/resources/ledb&w.png"));
	
	private JPanel contentPane;
	private JLabel lblHeartbeat;
	private JLabel lblTimeReceived;
	private JLabel ledLabel;

	/**
	 * HeartbeatWindow constructor create the frame.
	 */
	public HeartbeatWindow(boolean isHeartbeat, String timeReceived) throws IOException {
		this.setVisible(true);
		this.setTitle("Heartbeat");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setSize(280, 120);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//Make a list of different sizes icons for the window
		ArrayList<Image> imageList = new ArrayList<Image>();
		imageList.add(ImageIO.read(new File("src/com/inther/resources/heartbeatIcons/icon16x16.png")));
		imageList.add(ImageIO.read(new File("src/com/inther/resources/heartbeatIcons/icon32x32.png")));
		imageList.add(ImageIO.read(new File("src/com/inther/resources/heartbeatIcons/icon48x48.png")));
		imageList.add(ImageIO.read(new File("src/com/inther/resources/heartbeatIcons/icon64x64.png")));
		this.setIconImages(imageList);
		
		//Make led in out state
		ledLabel = new JLabel(new ImageIcon(ledBW));
		
		//Create a JPanel and add it in GroupLayout with ledLabel
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(ledLabel))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(ledLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE))
		);
		//Set GroupLayout for contentPane
		contentPane.setLayout(gl_contentPane);
		//Set GridLayout for panel
		panel.setLayout(new GridLayout(3,2));
		
		//Set isHeartbeat value for lblHeartbeat
		lblHeartbeat = new JLabel("Heartbeat value is "+isHeartbeat+", received on:");
    	lblHeartbeat.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblHeartbeat.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblHeartbeat);
		
		//Set timeReceived value for lblHeartbeat
		lblTimeReceived = new JLabel(timeReceived);
		lblTimeReceived.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblTimeReceived.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblTimeReceived);
		
		//Execute periodic parsing operation and modifying data
		Timer time = new Timer(); //Instantiate Timer Object
		ScheduledTask st = new ScheduledTask(); //Instantiate SheduledTask class
		time.schedule(st, 0, 5000); //Create Repetitively task for every 5 secs
			
	}
	
	class ScheduledTask extends TimerTask {

		public void run() {
			Message message = new JsonParser().parseJsonFromUrl("http://172.17.41.117:8080/RESTService_v2/sensor/current/"); //Receive message from Json
			//Set isHeartbeat value for lblHeartbeat
			lblHeartbeat.setText("Heartbeat value is "+message.isHeartbeat()+", received on:");
			//Set timeReceived value for lblHeartbeat
		    lblTimeReceived.setText(message.getTimeReceived());
		    //make the LED blink for a second
			try {
				ledLabel.setIcon(new ImageIcon(led));
				Thread.sleep(1000);
				ledLabel.setIcon(new ImageIcon(ledBW));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}