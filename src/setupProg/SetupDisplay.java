package setupProg;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mainProg.ClientWindow;
import mainProg.Config;
import util.Util;

public class SetupDisplay {

	private JFrame setupFrame;
	private JTextField email;
	private JTextField name;
	private JPanel finishRegister;
	private JPanel setupPanel;
	private JPanel inputPanel;
	private JButton check;
	private JLabel intro;
	private JButton finish;
	private boolean go = false;
	private boolean hasRunned = false;
	
	public void setupOnGUI(){
		
		try {
			go = checkRegistrationID();
		} catch (Exception e) {
			e.printStackTrace();
			Util.errMessage(e.getMessage());
		}
		
		if(go == true){
			runMainProg();
		}
		
		setupFrame = new JFrame("Setup Programe for ConicWare");
		email = new JTextField(15);
		name = new JTextField(15);
		intro = new JLabel(setupReadFile(Config.getIns().getProgLocation() + Config.PATH_SEP + "intro_label.html"));
		setupPanel = new JPanel();
		inputPanel = new JPanel();
		check = new JButton("CHECK!");
		finish = new JButton("Finish Registration and Go!");
		finishRegister = new JPanel();
		
		setupPanel.add(intro);
		inputPanel.setLayout(new GridLayout(3,2));
		inputPanel.add(new JLabel("     Email Address* :"));
		inputPanel.add(email);
		inputPanel.add(new JLabel("     Preferred Name* :"));
		inputPanel.add(name);
		inputPanel.add(new JLabel("     Click this when you finish :"));
		inputPanel.add(check);
		
		check.addActionListener(event -> {
			try {
				generateSetupFile(email, name);
			} catch (UnknownHostException e) {
				e.printStackTrace();
				Util.errMessage(e.getMessage());
			}
		});
		
		finishRegister.setLayout(new GridLayout(2,1));
		finishRegister.add(new JLabel("          Please Copy the file emailed to you to the same directory as this program"));
		finishRegister.add(finish);
		finish.addActionListener(event -> {try {
			go = checkRegistrationID();
			if(go == true)
				runMainProg();
		} catch (Exception e) {
			e.printStackTrace();
			Util.errMessage(e.getMessage());
		}});		
		
		if(go == false){
			setupFrame.getContentPane().add(inputPanel, BorderLayout.CENTER);
			setupFrame.getContentPane().add(setupPanel, BorderLayout.NORTH);
			setupFrame.getContentPane().add(finishRegister, BorderLayout.SOUTH);
			setupFrame.setLocation(300, 100);
			setupFrame.setDefaultCloseOperation(3);
			setupFrame.setSize(600, 500);
			setupFrame.setResizable(false);
			setupFrame.setVisible(true);
		}
	}
	
	private void runMainProg(){
		ClientWindow window = new ClientWindow();
		window.appOnGUI();
		hasRunned = true;
	}
	
	private boolean checkRegistrationID() throws NoSuchAlgorithmException, UnsupportedEncodingException, Exception{
		File f = new File(Config.getIns().getProgLocation() + Config.PATH_SEP + "ID.txt");
		boolean temp = false;
		
		if(f.exists()){
			byte[] readChar = setupReadFile(Config.getIns().getProgLocation() + Config.PATH_SEP + "ID.txt").getBytes();
			byte[] regChar = RegisterID.getRegisterID().getBytes();
			int count = 0;
			for(int i = 0; i < 10; i++){
				if(readChar[i] == regChar[i]){
					count ++;
				}
			}
			if(count == 10)
				temp = true;
		}
		
		if((temp == true) && (!hasRunned)){
			if(setupFrame != null){
				for(int i = 0; i < setupFrame.getComponentCount(); i++){
					setupFrame.getComponent(i).invalidate();
					setupFrame.getComponent(i).setVisible(false);
				}
				setupFrame.setVisible(false);
			}
		}
		
		return temp;
	}
	
	private void generateSetupFile(JTextField t1, JTextField t2) throws UnknownHostException{
		if((t1.getText() != null) && (t2.getText() != null)){
			
			String str = createClientInfoString(t1, t2);
			
			byte[] ns = str.getBytes();
			byte[] ret = new byte[ns.length];
			for(int i = 0; i < ns.length; i++){
				ret[i] = (byte) (ns[i] + 4);
			}
			str = new String(ret);
			
			//String to File
			byte[] tempCharRead = str.getBytes();
			try{
				FileOutputStream write = new FileOutputStream(Config.getIns().getProgLocation() + Config.PATH_SEP + "emailMe.cstudio");
				for(int i = 0; i < tempCharRead.length; i ++){
					write.write((tempCharRead[i]));
				}
				write.close();	
			}catch(IOException e){
				e.printStackTrace();
				Util.errMessage(e.getMessage());
			}
		}else{
			t1.setText("You have to fillout this section!");
			t2.setText("You have to fillout this section!");
		}
	}
	
	private String createClientInfoString(JTextField t1, JTextField t2) throws UnknownHostException{
		String content = "";
		content += "email:";
		content += t1.getText();
		content += (Config.LINE_SEP + "name:");
		content += t2.getText();
		content += Config.LINE_SEP;
		
		Properties props = System.getProperties();
		content += (Config.LINE_SEP + "Operating System Name :"+props.getProperty("os.name")); 
		content += (Config.LINE_SEP + "Operating System Architecture :"+props.getProperty("os.arch")); 
		content += (Config.LINE_SEP + "OS Version :"+props.getProperty("os.version")); 
		content += (Config.LINE_SEP + "User Name :"+props.getProperty("user.name")); 
		content += (Config.LINE_SEP + "User Home Directory :"+props.getProperty("user.home")); 
		content += (Config.LINE_SEP + "Now Directory :"+props.getProperty("user.dir"));
		content += (Config.LINE_SEP + "Computer Name :" +InetAddress.getLocalHost().getHostName());
		content += (Config.LINE_SEP + "Local IP Address :" +InetAddress.getLocalHost().getHostAddress());
		
		try {
			content += (Config.LINE_SEP + "Registration ID :" + RegisterID.getRegisterIDRaw());
		} catch (NoSuchAlgorithmException e) {
			Util.errMessage(e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			Util.errMessage(e.getMessage());
			e.printStackTrace();
		}
		return content;
	}
	
	private String setupReadFile(String _location){
		File input = new File(_location);
		String output = "";
		try {
			FileReader reader = new FileReader(input);
			try {
				char[] charRead = new char[(int)input.length()];
				reader.read(charRead);
				for(char c: charRead){
					output += c;
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			Util.errMessage(e.getMessage());
			e.printStackTrace();
		}
		return output;
	}
}
