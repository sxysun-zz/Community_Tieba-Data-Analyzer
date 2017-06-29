package mainProg;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;

import util.Util;
import util.SFTPChannel;

public class ChatTool {
	
	public void paintChat(JPanel _panel) throws Exception{
		//Define all new Components GUI
		JTextArea _tex = new JTextArea(17,42);
		JPanel chatPanel = new JPanel();
		JButton refresh = new JButton("Refresh");
		JButton send = new JButton("Send");
		JTextField input = new JTextField(25);
		JScrollPane scroll = new JScrollPane(_tex);
		
		//Setup Connection to Server
		SFTPChannel channelIns = new SFTPChannel();
		channelIns.closeChannel();
		SFTPConstants.configConnectInfo();
		ChannelSftp channel = channelIns.getChannel(SFTPConstants.connectInfo, 5000);
		
		//Add action Listener
		refresh.addActionListener(event -> refreshText(_tex));
		send.addActionListener(event -> sendText(input, channel));
        
		//add to pane and manage layout
        _panel.add(scroll, BorderLayout.CENTER);
        _panel.add(chatPanel, BorderLayout.SOUTH);
        chatPanel.add(refresh);
        chatPanel.add(input);
        chatPanel.add(send);
        
        //set components properties
        _tex.setEditable(false);
        scroll.setHorizontalScrollBarPolicy( 
        		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
        scroll.setVerticalScrollBarPolicy( 
        		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
	}
	
	private void sendText(JTextField _input, ChannelSftp _channel){
		InetAddress ia = null;
		try {
			ia= InetAddress.getLocalHost();
			String localname=ia.getHostName();
			String localip=ia.getHostAddress();
			String prefix = localip + "_" + localname + " : ";
			String output = Util.readFileChar(Config.getIns().getChatFileLocNative());
			
			
			
			//Vip Usr Joke
			File vip = new File(System.getProperties().getProperty("user.dir") + Config.PATH_SEP + "VIP");
			if(vip.exists())
				prefix = Config.LINE_SEP + "VIP_USR_IOTA : ";
			
			output += Config.LINE_SEP;
			output += (prefix + _input.getText());
			//Change Native File
			char[] tempCharRead = output.toCharArray();
			FileOutputStream write = new FileOutputStream(Config.getIns().getChatFileLocNative());
			for(int i = 0; i < tempCharRead.length; i ++){
				write.write((tempCharRead[i]));
			}
			write.close();
		} catch (Exception e) {
			e.printStackTrace();
			Util.errMessage(e.getMessage());
		}
		
		
		
//		Upload Native File to Server
		try {
			uploadFile("/var/www/html/chat/op_ch_file/ch_his_web.caudep", Config.getIns().getChatFileLocNative(), _channel);
		} catch (IOException e) {
			e.printStackTrace();
			Util.errMessage(e.getMessage());
		}

		_input.setText("");
		
	}
	
	private void refreshText(JTextArea _text){
		defultDownload();
        _text.setText(Util.readFileChar(Config.getIns().getChatFileLocNative()));
	}
	
	public static void defultDownload(){
		try {   
    		  String urlPath = Config.getIns().getChatFileLocOnServer();
            URL url = new URL(urlPath);   
            downloadFile(url, Config.getIns().getChatFileLocNative());   
         } catch (IOException e) {
          e.printStackTrace();   
          Util.errMessage(e.getMessage());
         }
	}
	
	public static void downloadFile(URL _url, String _output) throws IOException{
		URLConnection  connection = _url.openConnection();
	    InputStream in = connection.getInputStream();  
	    FileOutputStream os = new FileOutputStream(_output); 
	      byte[] buffer = new byte[4 * 1024];  
	      int read;  
	      while ((read = in.read(buffer)) > 0) {  
	          os.write(buffer, 0, read);  
	      }  
	    os.close();  
	    in.close();
	}
	
	public static void uploadFile(String _server, String _input, ChannelSftp channel) throws IOException{
		//Read Native File
		File input = new File(_input);
		FileInputStream d = new FileInputStream(input);
		long charSum = input.length();
		byte[] tempCharRead = new byte[(int)charSum];
		d.read(tempCharRead);
		d.close();
		
		//Connect to Server
		
		try {
			if(!channel.isConnected())
				channel.connect();
			channel.put(_input, _server);
		} catch (JSchException e) {
			e.printStackTrace();
			Util.errMessage(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			Util.errMessage(e.getMessage());
		}
		
	}
}
