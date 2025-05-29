package com.hnv.api.service.tool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.hnv.data.json.JSONArray;
import com.hnv.data.json.JSONObject;

public class ToolCSV {
	public static JSONArray reqData (String fPath, int lineBegin, String separ) throws Exception{
		List<String> 	data 	= reqReadFile( fPath,  lineBegin);
		JSONArray 		lst		= reqData (data, separ);
		return lst;
	}
	
	private static List<String> reqReadFile(String fPath, int lineBegin) throws Exception{ 
		List<String> 	data		= new ArrayList<String>();
		String 			line 		= "";  
		
		try{  
			BufferedReader br = new BufferedReader(new FileReader(fPath));  
			int count = 0;
			while (count < lineBegin ) {
				line = br.readLine();
				count++;
			}
			
			while ((line = br.readLine()) != null){    //returns a Boolean value  
				data.add(line);
			}  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		return data;
	}
	
	private static JSONArray reqData (List<String> data, String separ) throws Exception{
		JSONArray 	lst		= new JSONArray();
		for (String line: data) {
			try {
				String[] tok = reqTok (line, separ);
				JSONObject dt = new JSONObject();
				for (int i=0;i<tok.length;i++) {
					dt.put(i, tok[i]);
				}
				lst.add (dt);
			}catch(Exception e) {
				e.printStackTrace();  
			}
		}
		
		return lst;
	}
	
	private static String[] reqTok(String s, String separ){
		int count = 0;
		Hashtable <String , String> tab = new Hashtable<String, String>();

		int p01, p02;
		while ( (p01 = s.indexOf("\"")) >0) {
			count ++;
			p02 		= s.indexOf("\"", p01+1);
			String grp 	= s.substring(p01, p02+1);
			String rep 	= "hnv_csv_"+count;
			s 			= s.replace(grp, rep); 
			
			grp 		= grp.replace("\"", ""); 
			tab.put(rep, grp);
		}
		
		String[] tok = s.split(separ);
		for (int i=0;i<tok.length;i++) {
			tok[i] = tok[i].trim();
			if (tok[i].startsWith("hnv_csv")) tok[i] = tab.get(tok[i]);
		}
		
		return tok;
	}
	
	public static void main(String []a) throws Exception{
		String path = "C:\\tmp\\HD.csv";
		JSONArray lst = reqData (path, 1, ";");
		System.out.println(lst);
	}
}
