package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JPanel;

import sun.misc.BASE64Encoder;

public class Util {
	
	public static void clearScene(JPanel _panel){
		for(int i = 0; i < _panel.getComponentCount(); i++){
			_panel.getComponent(i).invalidate();
			_panel.getComponent(i).setVisible(false);
		}
		_panel = new JPanel();
    }

	public static String readFile(String _location){
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
			e.printStackTrace();
		}
		return output;
	}
	
	public static void StringToFile(String content, String loc){
		byte[] tempCharRead = content.getBytes();
		try{
			FileOutputStream write = new FileOutputStream(loc);
			for(int i = 0; i < tempCharRead.length; i ++){
				write.write((tempCharRead[i]));
			}
			write.close();	
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static String noReturns(String _s){
		char[] schar = _s.toCharArray();
		for(int i = 0; i < schar.length; i++){
			if((schar[i] == 0x0D) || (schar[i] == 0x0A))
				schar[i] = 0x00;
		}
		String ret = "";
		for(char c: schar){
			if(c != 0x00)
				ret += c;
		}
		return ret;
	}
	
	public static String stringEncrypt(String _s){
		byte[] ns = _s.getBytes();
		byte[] ret = new byte[ns.length];
		for(int i = 0; i < ns.length; i++){
			ret[i] = (byte) (ns[i] + 2);
		}
		return new String(ret);
	}
	
	public static String stringDecrypt(String _s){
		byte[] ns = _s.getBytes();
		byte[] ret = new byte[ns.length];
		for(int i = 0; i < ns.length; i++){
			ret[i] = (byte) (ns[i] - 2);
		}
		return new String(ret);
	}
	
	public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }
}
