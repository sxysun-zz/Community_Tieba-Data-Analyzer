package mainProg;

import java.io.File;

import util.Util;

//
//
//This Class Should be Singleton
//
//

public class Config {
	
	private static Config conf;

	private String APP_LOC;
	private String USR_PREF;
	private int ENCRYPT_CODE;
	private String WEL_LABEL;
	private int WIN_WIDTH;
	private int WIN_HEIGHT;
	private int LOC_X;
	private int LOC_Y;
	private String CHAT_LOC;
	private String URL_CHAT;
	private int TIEBA_OFFSET;
	
	public static String PATH_SEP = File.separator;
	public static String LINE_SEP = System.getProperties().getProperty("line.separator");
	
	private static String[] configSeq = {"WEL_LABEL", "ENCRYPT_CODE",
			"WIN_WIDTH", "WIN_HEIGHT", "LOC_X", "LOC_Y", "CHAT_LOC", "URL_CHAT"};
	
	public void Setup(){
		APP_LOC = System.getProperties().getProperty("user.dir")  + PATH_SEP + "res";
		String configIn = Util.noReturns(Util.stringDecrypt(Util.readFileChar(APP_LOC + PATH_SEP + "config_info.conf")));
		WEL_LABEL = PATH_SEP + getConfigIni(0, configIn);
		ENCRYPT_CODE = Integer.parseInt(getConfigIni(1, configIn));
		WIN_WIDTH = Integer.parseInt(getConfigIni(2, configIn));
		WIN_HEIGHT = Integer.parseInt(getConfigIni(3, configIn));
		LOC_X = Integer.parseInt(getConfigIni(4, configIn));
		LOC_Y = Integer.parseInt(getConfigIni(5, configIn));
		CHAT_LOC = PATH_SEP + getConfigIni(6, configIn);
		URL_CHAT = getConfigIni(7, configIn);
		TIEBA_OFFSET = 100;
	}
	
//BORiNG GETTER METHODS
	public String getChatFileLocOnServer(){
		return URL_CHAT;
	}
	
	public int getTiebaOffset(){
		return TIEBA_OFFSET;
	}
	
	public String getChatFileLocNative(){
		return APP_LOC + CHAT_LOC;
	}
	
	public String getUserPreference(){
		return APP_LOC + USR_PREF;
	}
	
	public String getProgLocation(){
		return APP_LOC;
	}
	
	public int getEncryptCode(){
		return ENCRYPT_CODE;
	}
	
	public String getWelcomeLabelLoc(){
		return APP_LOC + WEL_LABEL;
	}
	
	public String getWelcomeLabelText(){
		return Util.stringDecrypt(Util.readFileChar(getWelcomeLabelLoc()));
	}
	
	public int getWindowHeight(){
		return WIN_HEIGHT;
	}
	
	public int getWindowWidth(){
		return WIN_WIDTH;
	}
	
	public int getWindowXLocation(){
		return LOC_X;
	}
	
	public int getWindowYLocation(){
		return LOC_Y;
	}
	
	public static Config getIns(){
		if(conf == null){
			conf = new Config();
		}
		return conf;
	}
	
	private Config(){
		
	}
	
	private String getConfigIni(int i, String _confIn){
		if(i < configSeq.length -1){
			return _confIn.substring(_confIn.indexOf(configSeq[i]) + configSeq[i].length() + 1, _confIn.indexOf(configSeq[i+1]));
		}else{
			return _confIn.substring(_confIn.indexOf(configSeq[i]) + configSeq[i].length() + 1);
		}
		
	}
}
