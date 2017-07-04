package mainProg;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.Util;

public class ClientWindow {

	private JFrame mainFrame;
	private JButton toEncrypt;
	private JButton toChat;
	private JButton toCompress;
	private JButton toSetting;
	private JButton toHelp;
	private JPanel mainPanel;
	private JPanel toolPanel;
	
	public void appOnGUI(){
		
		//Tool Panel Setup
		toolPanel = new JPanel();
		toEncrypt = new JButton("Encrypt");
		toCompress = new JButton("Compress");
		toChat = new JButton("Chatroom");
		toSetting = new JButton("WebCrawler");
		toHelp = new JButton("Help");
		toolPanel.add(toEncrypt);
		toolPanel.add(toCompress);
		toolPanel.add(toChat);
		toolPanel.add(toSetting);
		toolPanel.add(toHelp);
		toEncrypt.addActionListener(event -> sceneEncrypt(mainPanel));
		toCompress.addActionListener(event -> sceneCompress(mainPanel));
		toChat.addActionListener(event -> sceneChat(mainPanel));
		toSetting.addActionListener(event -> sceneCrawler(mainPanel));
		toHelp.addActionListener(event -> sceneHelp(mainPanel));
		
		//Main Panel Setup
		mainPanel = new JPanel();
		sceneHelp(mainPanel);
		
		//Main Frame Setup
		mainFrame = new JFrame("ConicWare");
		mainFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainFrame.getContentPane().add(toolPanel, BorderLayout.NORTH);
		mainFrame.setLocation(Config.getIns().getWindowXLocation(), Config.getIns().getWindowYLocation());
		mainFrame.setDefaultCloseOperation(3);
		mainFrame.setSize(Config.getIns().getWindowWidth(), Config.getIns().getWindowHeight());
		mainFrame.setResizable(true);
		mainFrame.setVisible(true);
	}
	
//SCENE MANAGEMENT
	
	private static void sceneHelp(JPanel _panel){
		Util.clearScene(_panel);
		_panel.add(new JLabel(Util.noReturns(Config.getIns().getWelcomeLabelText())));
	}
	
	private static void sceneEncrypt(JPanel _panel){
		Util.clearScene(_panel);
		new EncryptTool().paintEncrypt(_panel);
	}
	
	private static void sceneCompress(JPanel _panel){
		Util.clearScene(_panel);
		new CompressTool().paintCompress();
	}
	
	private static void sceneChat(JPanel _panel){
		Util.clearScene(_panel);
		try {
			new ChatTool().paintChat(_panel);
		} catch (Exception e) {
			e.printStackTrace();
			Util.errMessage(e.getMessage());
		}
	}
	
	private static void sceneCrawler(JPanel _panel){
		Util.clearScene(_panel);
		new WebCrawler().paintCrawler(_panel);
	}
//SCENE MANAGEMENT
}

	
