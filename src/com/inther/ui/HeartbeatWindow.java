package com.inther.ui;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.inther.main.JsonParser;
import com.inther.model.Message;

import static com.inther.model.AppConfig.GET_HB_FREQUENCY_URL;
import static com.inther.model.AppConfig.SENZOR_CURRENT_STATE_URL;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class HeartbeatWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	//Read images from resources
	private BufferedImage greenLed = ImageIO.read(new File("src/com/inther/resources/green_led.png"));
	private BufferedImage redLed = ImageIO.read(new File("src/com/inther/resources/red_led.png"));
	private BufferedImage ledBW = ImageIO.read(new File("src/com/inther/resources/ledb&w.png"));
	
	private JPanel contentPane;
	private JPanel panel;
	private JLabel lblHeartbeat;
	private JLabel lblTimeReceived;
	private JLabel ledLabel;

	/**
	 * HeartbeatWindow constructor create the frame.
	 */
	public HeartbeatWindow() throws IOException {
		this.setVisible(true);
		this.setTitle("Heartbeat");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(280, 120);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
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
		ledLabel = new JLabel("Led");
		
		//Create a JPanel and add it in GroupLayout with ledLabel
		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(237, Short.MAX_VALUE)
					.addComponent(ledLabel)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(ledLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 64, Short.MAX_VALUE)
					.addContainerGap())
		);
		//Set GroupLayout for contentPane
		contentPane.setLayout(gl_contentPane);
		//Set GridLayout for panel
		panel.setLayout(new GridLayout(3,2));
		
		//Set isHeartbeat value for lblHeartbeat
		lblHeartbeat = new JLabel("Heartbeat value is null, received on");
    	lblHeartbeat.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblHeartbeat.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblHeartbeat);
		
		//Set timeReceived value for lblHeartbeat
		lblTimeReceived = new JLabel("0000-00-00 00:00:00");
		lblTimeReceived.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblTimeReceived.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblTimeReceived);
		
		//Execute periodic parsing operation and modifying data
		Timer time = new Timer(); //Instantiate Timer Object
		ScheduledTask st = new ScheduledTask(); //Instantiate SheduledTask class
		time.schedule(st, 0, 2000); //Create Repetitively task for every 2 secs
			
	}
	
	private class ScheduledTask extends TimerTask {
		public void run() {
			Message message = new JsonParser().getSensorCurrentData(SENZOR_CURRENT_STATE_URL); //Receive message from Json
			int HBFrequency = new JsonParser().getSensorSettings(GET_HB_FREQUENCY_URL,"HBFrequency"); //Receive HBFrequency from Json
			
			if(message !=null){
				contentPane.setBackground(Color.WHITE);
				panel.setBackground(Color.WHITE);				
				//make the LED blink for a second
				try {
					SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date timeReceived = parser.parse(message.getTimeReceived());
					timeReceived.setTime(timeReceived.getTime() + HBFrequency*1000);
					Date now = new Date();
					//if last HBFrequency seconds did not receive any messages then make the red LED else green
					if(timeReceived.before(now)){
						ledLabel.setIcon(new ImageIcon(redLed));
					}else{
						ledLabel.setIcon(new ImageIcon(greenLed));
					}
					Thread.sleep(1000);
					ledLabel.setIcon(new ImageIcon(ledBW));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//Set isHeartbeat value for lblHeartbeat
				lblHeartbeat.setText("Heartbeat value is "+message.isHeartbeat()+", received on");
				//Set timeReceived value for lblHeartbeat
			    lblTimeReceived.setText(message.getTimeReceived());
			} else{
				//Set window components in disabled state
				contentPane.setBackground(Color.LIGHT_GRAY);
				panel.setBackground(Color.LIGHT_GRAY);
				ledLabel.setIcon(new ImageIcon(ledBW));
				//Set isHeartbeat value for lblHeartbeat
				lblHeartbeat.setText("Heartbeat value is null, received on");
				//Set timeReceived value for lblHeartbeat
			    lblTimeReceived.setText("0000-00-00 00:00:00");
			}
		}
	}
}