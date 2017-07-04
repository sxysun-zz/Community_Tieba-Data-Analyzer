package mainProg;

import java.util.HashMap;
import java.util.Map;

public class SFTPConstants {

	public static final String SFTP_REQ_HOST = "host";
    public static final String SFTP_REQ_PORT = "port";
    public static final String SFTP_REQ_USERNAME = "username";
    public static final String SFTP_REQ_PASSWORD = "password";
    public static final int SFTP_DEFAULT_PORT = 22;
    public static final String SFTP_REQ_LOC = "location";
	
    public static Map<String, String> connectInfo;
    
    public static void configConnectInfo(){
    	connectInfo = new HashMap<String, String>();
    	connectInfo.put(SFTP_REQ_HOST, "***");
    	connectInfo.put(SFTP_REQ_LOC, "**");
    	connectInfo.put(SFTP_REQ_PASSWORD, "**");
    	connectInfo.put(SFTP_REQ_PORT, "**");
    	connectInfo.put(SFTP_REQ_USERNAME, "**");
    }
}
