package mainProg;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.Util;

public class WebCrawler {

	public static String Name;
	
	public void paintCrawler(JPanel _panel) {
		//Define all new Components GUI
		JPanel crawlerPanel = new JPanel();
		JPanel configPanel = new JPanel();
		JButton crawl = new JButton(" Crawl ");
		JTextField name = new JTextField(25);
		name.setText("userNameID");
		JButton random = new JButton("Random");
				
		//Add action Listener
		crawl.addActionListener(event -> crawlInfo(name.getText()));
//		random.addActionListener();
		        
		//add to pane and manage layout
		_panel.add(configPanel, BorderLayout.CENTER);
		_panel.add(crawlerPanel, BorderLayout.SOUTH);
		crawlerPanel.add(crawl);
		configPanel.add(name);
		crawlerPanel.add(random);
	}
	
	//take in a splitted file and give out a sorted one
	private String calculateTimes(String str){
		HashMap<String, Integer> has = new HashMap<String, Integer>();
		String[] slist = str.split("/");
		int l = slist.length;
		for(int i = 0; i < l; i++){
			if(!has.containsKey(slist[i])){
				has.put(slist[i], 1);
			}else{
				has.put(slist[i], has.get(slist[i])+1);
			}
		}
		return sort(has);
	}
 
	private static String sort(HashMap<String,Integer> map){
		StringBuffer sb = new StringBuffer();
	    List<HashMap.Entry<String, Integer>> infoIds = new ArrayList<HashMap.Entry<String, Integer>>(map.entrySet());   
	    Collections.sort(infoIds, new Comparator<HashMap.Entry<String, Integer>>() {     
	        public int compare(HashMap.Entry<String, Integer> o1, HashMap.Entry<String, Integer> o2) {     
	            return (o2.getValue() - o1.getValue());     
	        }
	    });
	    for (int i = 0; i < infoIds.size(); i++) {
	    	Entry<String, Integer> id = infoIds.get(i);
//	    	sb.append(id.getKey()).append(" --- ").append(id.getValue()).append("\n");
	    	sb.append(id.getValue()).append("\n");
	    }
	    return sb.toString();
	}  
	
/*
	private void ASyncCrawlText(int _pages, String _name){
//		_t.setText(catchText(_in.getText(), "<div id=\"middlew8\">", "<div id=\"bottomw8\">", "title=\"", "\" >"));
		Name = _name;
		CrawlerIns crawler1 = new CrawlerIns();
		CrawlerIns crawler2 = new CrawlerIns();
		CrawlerIns crawler3 = new CrawlerIns();
		CrawlerIns crawler4 = new CrawlerIns();
		CrawlerIns crawler5 = new CrawlerIns();
		CrawlerIns crawler6= new CrawlerIns();
		CrawlerIns crawler7 = new CrawlerIns();
		
		//add the number of pages to download
		
		crawler1.start();
		crawler2.start();
		crawler3.start();
		crawler4.start();
		crawler5.start();
		crawler6.start();
		crawler7.start();
	}
*/
	private void crawlInfo(String _ID){
		Name = _ID;
		CrawlerIns crawler1 = new CrawlerIns();
		crawler1.start();
	}
}
