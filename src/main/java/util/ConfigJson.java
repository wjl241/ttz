package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ConfigJson {
	/**
	 * 读取jar包文件
	 * @return
	 */
	public   String getConfigKeyJar() {
	    String keyVal = "";  
        try {
	           InputStream is=ConfigJson.class.getClass().getResourceAsStream("/util/config.json");  
	           if(is == null) {
	        	   System.err.println("找不到配置文件/util/config.json");
	        	   return "";
	           }
	           BufferedReader br=new BufferedReader(new InputStreamReader(is));  
		       String str=null;
		       StringBuffer buf = new StringBuffer();
		       while((str=br.readLine())!=null){
		           buf.append(str);
		           buf.append("\r\n");
		       }
		       System.out.println(buf.toString());
		       keyVal = buf.toString();
		       br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
        return keyVal;  
    
	}
}
