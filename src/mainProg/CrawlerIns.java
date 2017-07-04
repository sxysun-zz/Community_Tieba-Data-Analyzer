package mainProg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import util.Util;

public class CrawlerIns extends Thread{

	public String[] infoName;
	public int[] infoLevel;
	
	public void run(){
		if(WebCrawler.Name != null){
			infoName = getTiebaUserInfoName(WebCrawler.Name).split("\n");
			String[] infoLevelString = getTiebaUserInfoLevel(WebCrawler.Name).split("\n");
			int temp = infoLevelString.length;
			infoLevel = new int[temp];
			for(int i = 0; i < temp; i++){
				infoLevel[i] = (Integer.parseInt(infoLevelString[i]) + (temp - i));
			}
			
			for(int i = 0; i < infoName.length; i++){
				try{
					System.out.println(i + "-" + infoName[i] + "-" + infoLevel[i]);
					crawlTiebaTitle(0, infoLevel[i] * Config.getIns().getTiebaOffset(), infoName[i]);
				}catch(Exception e){
					
				}
			}
		}else{
			Util.errMessage("The tieba User Name is invalid");
		}
	}
	
	private String getTiebaUserInfoName(String ID){
		String userHome = "http://tieba.baidu.com/home/main?un=" + ID + "&fr=home";
		return catchText(userHome, "<div class=\"clearfix u-f-wrap\" id=\"forum_group_wrap\">", "<div class=\"right_aside\">"
				, "<span>", "</span>");
	}
	
	private String getTiebaUserInfoLevel(String ID){
		String userHome = "http://tieba.baidu.com/home/main?un=" + ID + "&fr=home";
		return catchText(userHome, "class=\"clearfix u-f-wrap\"", "<div class=\"right_aside\">"
				, "<span class=\"forum_level lv", "\"></span>");
	}
	
	private void crawlTiebaTitle(int minPageNum, int maxPageNumEx, String tiebaName){
		try {
			File output = new File(Config.getIns().getProgLocation() 
					+ Config.PATH_SEP + "titles_" + minPageNum + "-" + maxPageNumEx + "_" + tiebaName + ".txt");
			FileWriter writer = new FileWriter(output, false);
			int max = maxPageNumEx*50;
			int min = minPageNum*50;
			
			for(int i = min; i < max; i += 50){
				writer.write(
						catchText("https://tieba.baidu.com/f?kw=" + tiebaName + "&ie=utf-8&pn=" + i, 
								"threadlist_bright", 
						"thread_list_bottom", "class=\"j_th_tit \">", "</a>"));
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			Util.errMessage(e.getMessage());
		}
	}
	
	private String catchText(String url, String sTag, String eTag, String eleS, String eleE){
		try {
			URLConnection  connection = new URL(url).openConnection();
		    InputStream in = connection.getInputStream();  
		    
		    StringBuilder build = new StringBuilder();
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String line = "";
		    while ((line = br.readLine()) != null) {
		    	build.append(line);  
		    }
		    String page = build.toString();
		    in.close();
		    
		    if(page.indexOf(sTag) == -1){
		    	String withHttp = "http://" + url.substring(8);
		    	
		    	connection = new URL(withHttp).openConnection();
			    in = connection.getInputStream();  
			    
			    build = new StringBuilder();
			    br = new BufferedReader(new InputStreamReader(in));
			    line = "";
			    while ((line = br.readLine()) != null) {
			    	build.append(line);  
			    }
			    page = build.toString();
			    in.close();
			    
			    if(page.indexOf(sTag) == -1){
			    	Util.errMessage("This Tieba user has locked his private info");
			    	System.out.println(withHttp + "-" + sTag);
			    }
		    }
		    
			page = page.substring(page.indexOf(sTag), page.indexOf(eTag));
			
			int currentIndex = 0;
			String newsOutput = "";
			while((currentIndex = page.indexOf(eleS, currentIndex + 1)) > 0){
				newsOutput += (page.substring(currentIndex + eleS.length(), page.indexOf(eleE, currentIndex)) + "\n");
			}
			return newsOutput;
		} catch (IOException e) {
			e.printStackTrace();
			Util.errMessage(e.getMessage());
		}
		return "ERR_IOEXCEPTION";
	}
	
}
