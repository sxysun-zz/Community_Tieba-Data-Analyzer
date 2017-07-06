package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import test.Util;

public class RegisterID {
	
	public static String getCPUNumber() throws IOException{
		Process process = Runtime.getRuntime().exec(
		new String[] { "wmic", "cpu", "get", "ProcessorId" });
		process.getOutputStream().close();
		Scanner sc = new Scanner(process.getInputStream());
		String serial = sc.next();
		sc.close();
		return serial;
	}
	
	public static String getMotherboardNumber() {
		  String result = "";
		  try {
		   File file = File.createTempFile("realhowto", ".vbs");
		   file.deleteOnExit();
		   FileWriter fw = new java.io.FileWriter(file);
		   String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
		     + "Set colItems = objWMIService.ExecQuery _ \n"
		     + "   (\"Select * from Win32_BaseBoard\") \n"
		     + "For Each objItem in colItems \n"
		     + "    Wscript.Echo objItem.SerialNumber \n"
		     + "    exit for  ' do the first cpu only! \n" + "Next \n";
		   fw.write(vbs);
		   fw.close();
		   Process p = Runtime.getRuntime().exec(
		     "cscript //NoLogo " + file.getPath());
		   BufferedReader input = new BufferedReader(new InputStreamReader(p
		     .getInputStream()));
		   String line;
		   while ((line = input.readLine()) != null) {
		    result += line;
		   }
		   input.close();
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		  return result.trim();
		 }
	
	public static String getRegisterIDRaw() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		String i = "";
		try {
			i += getCPUNumber();
			i += getMotherboardNumber();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Util.EncoderByMd5(Util.stringEncrypt(i));		
	}
	
	public static byte[] encryptSHA(byte[] data) throws Exception {    
        MessageDigest sha = MessageDigest.getInstance("SHA");  
        sha.update(data);  
        return sha.digest();  
    }  
	
	public static String getRegisterID(String raw) throws NoSuchAlgorithmException, UnsupportedEncodingException, Exception{
		String ret = new String(encryptSHA(Util.EncoderByMd5(Util.stringEncrypt(raw)).getBytes()));
		byte[] temp = ret.getBytes();
		for(byte c: temp){
			if(c <= 0x1F)
				c += 0x21;
		}
		return new String(temp);
	}
}
