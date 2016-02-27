package com.inther.ui;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.inther.main.JsonParser;
import com.inther.model.Message;

import static com.inther.model.AppConfig.SENZOR_CURRENT_STATE_URL;
import static com.inther.model.AppConfig.GET_HB_FREQUENCY_URL;
import static com.inther.model.AppConfig.GET_LIGHT_THRESHOLD_URL;

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

public class SensorsStateWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	//Read images from resources
	private BufferedImage motion = ImageIO.read(new File("src/com/inther/resources/motion.png"));
	private BufferedImage no_motion = ImageIO.read(new File("src/com/inther/resources/no_motion72x64.png"));
	private BufferedImage light = ImageIO.read(new File("src/com/inther/resources/light.png"));
	private BufferedImage lightBW = ImageIO.read(new File("src/com/inther/resources/no_light72x64.png"));
	private BufferedImage greenLed = ImageIO.read(new File("src/com/inther/resources/green_led.png"));
	private BufferedImage redLed = ImageIO.read(new File("src/com/inther/resources/red_led.png"));
	private BufferedImage ledBW = ImageIO.read(new File("src/com/inther/resources/ledb&w.png"));
	
	private JPanel contentPane;
	private JPanel panel;
	private JLabel lblMotionImg;
	private JLabel lblLightImg;
	private JLabel lblState1;
	private JLabel lblState2;
	private JLabel ledLabel;
	private JLabel lblPresence;
	
	/**
	 * SensorsStateWindow constructor create the frame.
	 */
	public SensorsStateWindow() throws IOException {
		this.setVisible(true);
		this.setTitle("Sensors state");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(260, 240);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//Make a list of different sizes icons for the window
		ArrayList<Image> imageList = new ArrayList<Image>();
		imageList.add(ImageIO.read(new File("src/com/inther/resources/sensorsStateIcons/icon16x16.png")));
		imageList.add(ImageIO.read(new File("src/com/inther/resources/sensorsStateIcons/icon24x24.png")));
		imageList.add(ImageIO.read(new File("src/com/inther/resources/sensorsStateIcons/icon32x32.png")));
		imageList.add(ImageIO.read(new File("src/com/inther/resources/sensorsStateIcons/icon48x48.png")));
		imageList.add(ImageIO.read(new File("src/com/inther/resources/sensorsStateIcons/icon64x64.png")));
		imageList.add(ImageIO.read(new File("src/com/inther/resources/sensorsStateIcons/icon128x128.png")));
		imageList.add(ImageIO.read(new File("src/com/inther/resources/sensorsStateIcons/icon256x256.png")));
		this.setIconImages(imageList);
		
		//Make led in out state
		ledLabel = new JLabel("Led");
		lblPresence = new JLabel("Nobody is in the room");
		
		//Create a JPanel and add it in GroupLayout with ledLabel
		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblPresence)
					.addPreferredGap(ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
					.addComponent(ledLabel)
					.addContainerGap())
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPresence)
						.addComponent(ledLabel))
					.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE))
		);
		//Set GroupLayout for contentPane
		contentPane.setLayout(gl_contentPane);
		//Set GridLayout for panel
		panel.setLayout(new GridLayout(3,2));
		
		JLabel lblMotion = new JLabel("Motion");
		lblMotion.setHorizontalAlignment(SwingConstants.CENTER);
		lblMotion.setFont(new Font("Segoe UI", Font.BOLD, 18));
		panel.add(lblMotion);
		
		JLabel lblLight = new JLabel("Light");
		lblLight.setHorizontalAlignment(SwingConstants.CENTER);
		lblLight.setFont(new Font("Segoe UI", Font.BOLD, 18));
		panel.add(lblLight);
	
		lblMotionImg = new JLabel(new ImageIcon(no_motion));
		lblMotionImg.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblMotionImg);
		
		lblLightImg = new JLabel(new ImageIcon(lightBW));
		lblLightImg.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblLightImg);
		
		lblState1 = new JLabel("not detected");
		lblState1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblState1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblState1);
		
		lblState2 = new JLabel("not detected");
		lblState2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblState2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblState2);

		//Execute periodic parsing operation and modifying data
		Timer time = new Timer(); //Instantiate Timer Object
		ScheduledTask st = new ScheduledTask(); //Instantiate SheduledTask class
		time.schedule(st, 0, 2000); //Create Repetitively task for every 2 secs	
			
	}	
	
	private class ScheduledTask extends TimerTask {
		public void run() {
			Message message = new JsonParser().getSensorCurrentData(SENZOR_CURRENT_STATE_URL); //Receive message from Json
			int lightThreshold = new JsonParser().getSensorSettings(GET_LIGHT_THRESHOLD_URL,"lightThreshold"); //Receive lightThreshold from Json
			int HBFrequency = new JsonParser().getSensorSettings(GET_HB_FREQUENCY_URL,"HBFrequency"); //Receive HBFrequency from Json
			
			if((message !=null)&&(lightThreshold!=0)){
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

				//Check if somebody is in the room
				if((message.getPirSensorVal()==true)||(message.getLightSensorVal()>lightThreshold)){
					lblPresence.setText("Anyone is in the room");
				} else{
					lblPresence.setText("Nobody is in the room");
				}
				
				//Set lblMotionImg and lblState1 value depending on the pirSensorVal received 
				if(message.getPirSensorVal()==true){
					lblMotionImg.setIcon(new ImageIcon(motion));
					lblState1.setText("is detected");	
				}
				else {
					lblMotionImg.setIcon(new ImageIcon(no_motion));
					lblState1.setText("not detected");	
				}
				
				//Set lblLightImg and lblState2 value depending on the lightSensorVal received 
				if(message.getLightSensorVal()<lightThreshold){
					lblLightImg.setIcon(new ImageIcon(lightBW));
					lblState2.setText("not detected");
				}
				else {
					lblLightImg.setIcon(new ImageIcon(light));
					lblState2.setText("is detected");
				}
				
			} else{
				//Set window components in disabled state
				contentPane.setBackground(Color.LIGHT_GRAY);
				panel.setBackground(Color.LIGHT_GRAY);
				ledLabel.setIcon(new ImageIcon(ledBW));
				lblMotionImg.setIcon(new ImageIcon(no_motion));
				lblState1.setText("not detected");
				lblLightImg.setIcon(new ImageIcon(lightBW));
				lblState2.setText("not detected");
				lblPresence.setText("Nobody is in the room");
			}
		}
	}
}
