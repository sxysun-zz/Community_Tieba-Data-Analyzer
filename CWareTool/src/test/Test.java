package test;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

//import java.awt.BorderLayout;
//import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Test {

	private static JFrame frame;
	private static JButton en;
	private static JButton de;
	private static JPanel panel;
	private static JPanel panel2;
	private static JTextField input;
	private static JTextField output;
	private static JTextField code;
	private static JPanel panel3;
	private static JButton trans;
	private static JTextField codeIn;
	
	public static void main(String args[]){
		onGUI();
	}
	
	private static void encrypt(String m_input, String m_output, int code){
		try {
			File input = new File(m_input);
		
			FileInputStream d = new FileInputStream(input);
		
			long charSum = input.length();
			byte[] tempCharRead = new byte[(int)charSum];
			d.read(tempCharRead);
			d.close();
			
			try{
				FileOutputStream write = new FileOutputStream(m_output);
				for(int i = 0; i < tempCharRead.length; i ++){
					write.write((tempCharRead[i] + code));
				}
				write.close();	
			}catch(IOException e){
				e.printStackTrace();
			}
		
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	}
	
	private static void decrypt(String m_input, String m_output, int code){
		try {
			File input = new File(m_input);
		
			FileInputStream d = new FileInputStream(input);
		
			long charSum = input.length();
			byte[] tempCharRead = new byte[(int)charSum];
			d.read(tempCharRead);
			d.close();
			
			try{
				FileOutputStream write = new FileOutputStream(m_output);
				for(int i = 0; i < tempCharRead.length; i ++){
					write.write((tempCharRead[i] - code));
				}
				write.close();	
			}catch(IOException e){
				e.printStackTrace();
			}
		
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	}
	
	private static void onGUI(){
		frame = new JFrame("Encrypter");
		en = new JButton("Encrypt");
		de = new JButton("Decrypt");
		panel = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		input = new JTextField("input file this is");
		output = new JTextField(10);
		code  = new JTextField(5);
		codeIn = new JTextField("Enter the Registration ID Raw");
		trans = new JButton("Get the Real Registration ID");
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		frame.getContentPane().add(panel2, BorderLayout.CENTER);
		frame.getContentPane().add(panel3, BorderLayout.SOUTH);
		panel.add(input);
		panel.add(output);
		panel.add(code);
		panel2.add(en);
		panel2.add(de);
		panel3.setLayout(new GridLayout(2,1));
		panel3.add(codeIn);
		panel3.add(trans);
		trans.addActionListener(event -> getRealID(codeIn.getText()));
		en.addActionListener(event -> encrypt(input.getText(),output.getText(),Integer.parseInt(code.getText())));
		de.addActionListener(event -> decrypt(input.getText(),output.getText(),Integer.parseInt(code.getText())));
		frame.setLocation(1366/3, 768/3);
		frame.setDefaultCloseOperation(3);
		frame.setSize(500, 300);
		frame.setVisible(true);
	}
	
	private static void getRealID(String raw){
		try {
			Util.StringToFile(RegisterID.getRegisterID(raw), System.getProperties().getProperty("user.dir") + "\\ID.txt");;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
