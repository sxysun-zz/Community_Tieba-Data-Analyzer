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
    	connectInfo.put(SFTP_REQ_HOST, "128.199.122.193");
    	connectInfo.put(SFTP_REQ_LOC, "/var/www/html");
    	connectInfo.put(SFTP_REQ_PASSWORD, "Conic2017&01#");
    	connectInfo.put(SFTP_REQ_PORT, "6911");
    	connectInfo.put(SFTP_REQ_USERNAME, "root");
    }
}
