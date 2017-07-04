package mainProg;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.Util;

public class EncryptTool {
	
	public void paintEncrypt(JPanel _panel){
		JButton en = new JButton("Encrypt");
		JButton de = new JButton("Decrypt");
		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
		JTextField input = new JTextField(20);
		JTextField output = new JTextField(20);
		JTextField code  = new JTextField(20);
		_panel.add(panel, BorderLayout.CENTER);
		_panel.add(panel2, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(4,2));
		panel.add(new JLabel("Input File Location :"));
		panel.add(input);
		panel.add(new JLabel("Output File Location :"));
		panel.add(output);
		panel.add(new JLabel("Encryptiong Code :"));
		panel.add(code);
		panel2.add(en);
		panel2.add(de);
		en.addActionListener(event -> encrypt(input.getText(),output.getText(),stringToCode(code.getText())));
		de.addActionListener(event -> decrypt(input.getText(),output.getText(),stringToCode(code.getText())));
	}
	
	private int stringToCode(String s){
		char[] temp = s.toCharArray();
		char res = temp[0];
		for(char c : temp){
			if(c < res)
				res = c;
		}
		return res;
	}

	public void encrypt(String m_input, String m_output, int code){
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
				Util.errMessage(e.getMessage());
			}
		
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Util.errMessage(e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
				Util.errMessage(e1.getMessage());
			}
	}
	
	public void decrypt(String m_input, String m_output, int code){
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
				Util.errMessage(e.getMessage());
			}
		
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Util.errMessage(e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
				Util.errMessage(e1.getMessage());
			}
	}
	
}
