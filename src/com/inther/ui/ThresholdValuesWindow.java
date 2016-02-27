package com.inther.ui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.inther.main.EditThresholds;
import com.inther.main.JsonParser;
import static com.inther.model.AppConfig.GET_LIGHT_THRESHOLD_URL;
import static com.inther.model.AppConfig.GET_HB_FREQUENCY_URL;
import static com.inther.model.AppConfig.SET_LIGHT_THRESHOLD_URL;
import static com.inther.model.AppConfig.SET_HB_FREQUENCY_URL;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTextField;
import javax.swing.JButton;

public class ThresholdValuesWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblStatus1;
	private JLabel lblStatus2; 
	private JLabel lblVal1;
	private JLabel lblVal2;
	private JButton btnEditHeartbeatFrequency;
	private JButton btnEditLightThreshold;

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public ThresholdValuesWindow() throws IOException {
		this.setVisible(true);
		this.setTitle("Threshold Values");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(235, 220);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//Make a list of different sizes icons for the window
		ArrayList<Image> imageList = new ArrayList<Image>();
		imageList.add(ImageIO.read(new File("src/com/inther/resources/thresholdValues/icon16x16.png")));
		imageList.add(ImageIO.read(new File("src/com/inther/resources/thresholdValues/icon24x24.png")));
		imageList.add(ImageIO.read(new File("src/com/inther/resources/thresholdValues/icon32x32.png")));
		imageList.add(ImageIO.read(new File("src/com/inther/resources/thresholdValues/icon48x48.png")));
		imageList.add(ImageIO.read(new File("src/com/inther/resources/thresholdValues/icon64x64.png")));
		imageList.add(ImageIO.read(new File("src/com/inther/resources/thresholdValues/icon128x128.png")));
		this.setIconImages(imageList);
		
		JLabel lblLightThresholdValue = new JLabel("Light Threshold Value: ");
		lblVal1 = new JLabel("null");
	
		JLabel lblHeartbeatFrequency = new JLabel("Heartbeat Frequency:");
		lblVal2 = new JLabel("null");
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		
		JLabel lblEditLightThreshold = new JLabel("Edit:");
		JLabel lblEditHeartbeatFrequency = new JLabel("Edit:");
		
		lblStatus1 = new JLabel();
		lblStatus2 = new JLabel();
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(8)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblEditLightThreshold)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblStatus1))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblLightThresholdValue)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblVal1))
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblEditHeartbeatFrequency)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblStatus2))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblHeartbeatFrequency)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblVal2))
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(15, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(8)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLightThresholdValue)
						.addComponent(lblVal1))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEditLightThreshold)
						.addComponent(lblStatus1))
					.addGap(4)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblVal2)
						.addComponent(lblHeartbeatFrequency))
					.addGap(8)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEditHeartbeatFrequency)
						.addComponent(lblStatus2))
					.addGap(4)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(15, Short.MAX_VALUE))
		);
		panel_1.setLayout(new GridLayout(1,2));
		
		textField_1 = new JTextField();
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		btnEditHeartbeatFrequency = new JButton("Edit");
		btnEditHeartbeatFrequency.setActionCommand("btnEditHeartbeatFrequency");
		btnEditHeartbeatFrequency.addActionListener(new ButtonClickListener()); 
		btnEditHeartbeatFrequency.setEnabled(false);
		panel_1.add(btnEditHeartbeatFrequency);
		panel.setLayout(new GridLayout(1,2));
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		btnEditLightThreshold = new JButton("Edit");
		btnEditLightThreshold.setActionCommand("btnEditLightThreshold");
		btnEditLightThreshold.addActionListener(new ButtonClickListener());
		btnEditLightThreshold.setEnabled(false);
		panel.add(btnEditLightThreshold);
		contentPane.setLayout(gl_contentPane);
		
		//Execute periodic parsing operation and modifying data
		Timer time = new Timer(); //Instantiate Timer Object
		ScheduledTask st = new ScheduledTask(); //Instantiate SheduledTask class
		time.schedule(st, 0, 2000); //Create Repetitively task for every 2 secs
	}
	
	private class ButtonClickListener implements ActionListener{
	      public void actionPerformed(ActionEvent e) {
	         String command = e.getActionCommand();  
	         if(command.equals("btnEditLightThreshold")){
	        	 //Edit Light Threshold value
	        	 EditThresholds editThresholds = new EditThresholds();
	        	 int status = editThresholds.sendPostRequest(SET_LIGHT_THRESHOLD_URL,"{\"lightThreshold\":"+textField.getText()+"}");
	        	 if(status == 200){
	        		 lblStatus1.setText("success");
	        	 } else{
	        		 lblStatus1.setText("fail");
	        	 }
	         }
	         else if(command.equals("btnEditHeartbeatFrequency")){
	        	//Edit HBFrequency value
	        	 EditThresholds editThresholds = new EditThresholds();
	        	 int status = editThresholds.sendPostRequest(SET_HB_FREQUENCY_URL,"{\"HBFrequency\":"+textField_1.getText()+"}");
	        	 if(status == 200){
	        		 lblStatus2.setText("success");
	        	 } else{
	        		 lblStatus2.setText("fail");
	        	 }
	         }
	      }		
	   }
	
	private class ScheduledTask extends TimerTask {
		public void run() {
			int lightThreshold = new JsonParser().getSensorSettings(GET_LIGHT_THRESHOLD_URL,"lightThreshold"); //Receive lightThreshold from Json
			int HBFrequency = new JsonParser().getSensorSettings(GET_HB_FREQUENCY_URL,"HBFrequency"); //Receive HBFrequency from Json
			//Set lightThreshold and HBFrequency values if if they are not null
			if((lightThreshold!=0) && (HBFrequency!=0)){
				lblVal1.setText(Integer.toString(lightThreshold));
				lblVal2.setText(Integer.toString(HBFrequency));
				contentPane.setBackground(Color.WHITE);
				btnEditHeartbeatFrequency.setEnabled(true);
				btnEditLightThreshold.setEnabled(true);
			}else{
				//Set window components in disabled state
				lblVal1.setText("null");
				lblVal2.setText("null");
				contentPane.setBackground(Color.LIGHT_GRAY);
				btnEditHeartbeatFrequency.setEnabled(false);
				btnEditLightThreshold.setEnabled(false);
			}	
		}
	}
}
