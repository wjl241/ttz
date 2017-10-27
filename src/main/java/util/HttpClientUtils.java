package util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;



/**
 * HttpClient4.3工具类
 * 
 * @author tech
 * @date 2016-03-29
 *
 */
public class HttpClientUtils {
	private static Logger logger = LoggerFactory
			.getLogger(HttpClientUtils.class); // 日志记录

	
	/**
	 * post请求传输json参数
	 * 
	 * @param url
	 *            url地址
	 * @param json
	 *            参数
	 * @return
	 */
	public static JSONObject httpPost(String url, JSONObject jsonParam) {
		// post请求返回结果
		CloseableHttpClient httpClient = HttpClients.createDefault();
		JSONObject jsonResult = null;
		HttpPost httpPost = new HttpPost(url);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpPost.setConfig(requestConfig);
		try {
			if (null != jsonParam) {
				// 解决中文乱码问题
				StringEntity entity = new StringEntity(jsonParam.toString(),
						"utf-8");
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				httpPost.setEntity(entity);
			}
			CloseableHttpResponse result = httpClient.execute(httpPost);
			//请求发送成功，并得到响应
			if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String str = "";
				try {
					//读取服务器返回过来的json字符串数据 
					str = EntityUtils.toString(result.getEntity(), "utf-8");
					//把json字符串转换成json对象 
					jsonResult = JSONObject.parseObject(str);
				} catch (Exception e) {
					logger.error("post请求提交失败:" + url, e);
				}
			}
		} catch (IOException e) {
			logger.error("post请求提交失败:" + url, e);
		} finally {
			httpPost.releaseConnection();
		}
		return jsonResult;
	}
	
	
	/**
	 * post请求传输String参数
	 * 例如：name=Jack&sex=1&type=2
	 * Content-type:application/x-www-form-urlencoded
	 * @param url
	 *            url地址
	 * @param strParam
	 *            参数
	 * @param noNeedResponse
	 *            不需要返回结果
	 * @return
	 */
	public static JSONObject httpPost(String url, String strParam) {
		// post请求返回结果
		CloseableHttpClient httpClient = HttpClients.createDefault();
		JSONObject jsonResult = null;
		HttpPost httpPost = new HttpPost(url);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpPost.setConfig(requestConfig);
		try {
			if (null != strParam) {
				// 解决中文乱码问题
				StringEntity entity = new StringEntity(strParam,"utf-8");
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/x-www-form-urlencoded");
				httpPost.setEntity(entity);
			}
			CloseableHttpResponse result = httpClient.execute(httpPost);
			//请求发送成功，并得到响应
			if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String str = "";
				try {
					//读取服务器返回过来的json字符串数据
					str = EntityUtils.toString(result.getEntity(), "utf-8");
					//把json字符串转换成json对象
					jsonResult = JSONObject.parseObject(str);
				} catch (Exception e) {
					logger.error("post请求提交失败:" + url, e);
				}
			}
		} catch (IOException e) {
			logger.error("post请求提交失败:" + url, e);
		} finally {
			httpPost.releaseConnection();
		}
		return jsonResult;
	}

	/**
	 * 发送get请求
	 * 
	 * @param url
	 *            路径
	 * @return
	 */
	public static JSONObject httpGet(String url,String cookie) {
		// get请求返回结果
		JSONObject jsonResult = null;
		CloseableHttpClient client = HttpClients.createDefault();
		// 发送get请求
		HttpGet request = new HttpGet(url);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(2000).setConnectTimeout(2000).build();
		request.setConfig(requestConfig);
		request.addHeader("Cookie",cookie);
		try {
			CloseableHttpResponse response = client.execute(request);

			//请求发送成功，并得到响应
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				//读取服务器返回过来的json字符串数据
				HttpEntity entity = response.getEntity();
				String strResult = EntityUtils.toString(entity, "utf-8");
				//把json字符串转换成json对象
				jsonResult = JSONObject.parseObject(strResult);
			} else {
				logger.error("get请求提交失败:" + url);
			}
		} catch (IOException e) {
			logger.error("get请求提交失败:" + url, e);
		} finally {
			request.releaseConnection();
		}
		return jsonResult;
	}
	
	
	
	/**
	 * 发送get请求,专门获取淘宝的订单详情
	 * 
	 * @param url
	 *            路径
	 * @return
	 */
	public static String httpGet2(String url,String cookie,String path) {
		// get请求返回结果
		CloseableHttpClient client = HttpClients.createDefault();
		// 发送get请求
		HttpGet request = new HttpGet(url);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(2000).setConnectTimeout(2000).build();
		request.setConfig(requestConfig);
		request.addHeader("Cookie",cookie);
		try {
			CloseableHttpResponse response = client.execute(request);

			//请求发送成功，并得到响应
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				//读取服务器返回过来的json字符串数据
				HttpEntity entity = response.getEntity();
				ByteArrayOutputStream swapStream = new ByteArrayOutputStream(); 
				byte[] buff = new byte[100]; //buff用于存放循环读取的临时数据 
				int rc = 0; 
				while ((rc = entity.getContent().read(buff, 0, 100)) > 0) { 
					swapStream.write(buff, 0, rc); 
				} 
				//String strResult = EntityUtils.toString(entity,"GBK");
				
				
				try {
					outPutStream(swapStream.toByteArray(),path);
					
					} catch (Exception e) {
						logger.error("记录轻淘客日志出错", e);
						e.printStackTrace();
					}
				
				//weiteExcel(strResult);
				return "";
			} else {
				logger.error("get请求提交失败:" + url);
			}
		} catch (IOException e) {
			logger.error("get请求提交失败:" + url, e);
		} finally {
			request.releaseConnection();
		}
		return "";
	}
	public static void main(String[] args) {
		
	}
	
	
	
	public static String doPost(String url,Map<String,String> map,String charset){  
        HttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = null;  
        try{  
            httpClient = new SSLClient();  
            httpPost = new HttpPost(url);  
            //设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            Iterator iterator = map.entrySet().iterator();  
            while(iterator.hasNext()){  
                Entry<String,String> elem = (Entry<String, String>) iterator.next();  
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
            }  
            if(list.size() > 0){  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
                httpPost.setEntity(entity);  
            }  
            //System.err.println("33"+new Date());
            HttpResponse response = httpClient.execute(httpPost);  
           // System.err.println("44"+new Date());
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
        return result;  
    }  
	
	
	public static void weiteExcel(String info) {
		try {
			
			  FileWriter out = new FileWriter("D:\\testExcel.xls", true); 
		        //往文件写入
		        out.write(info);
		        //换行
		        out.write("\r\n");
		        //刷新IO内存流
		        out.flush();
		        //关闭
		        out.close();
			
			} catch (Exception e) {
				logger.error("记录轻淘客日志出错", e);
				e.printStackTrace();
			}
	}
	
	
	/**
	 * 用于数出字节流
	 * @param bytesArray
	 * @param path
	 */
	public static void outPutStream(byte[] bytesArray,String path) {
		FileOutputStream fos = null;  
	    File file;  
	    //mycontent = "This is my Data which needs to be written into the file.";  
	    try {  
	      // specify the file path  
	      file = new File(path);  //"D:\\aa1112.xls"
	      fos = new FileOutputStream(file);  
	      /* This logic will check whether the file exists or not. 
	      if the file is not found at the specified location it would create 
	      a new file 
	       */  
//	      if (!file.exists()) {  
//	        file.createNewFile();  
//	      }  
	      /* String content cannot be directly written into a file. 
	      It needs to be converted into bytes 
	       */  
	      //byte[] bytesArray = mycontent.getBytes();  
	      fos.write(bytesArray);  
	      fos.flush();  
	      System.out.println("File Written Successfully");  
	    } catch (FileNotFoundException e) {  
	      e.printStackTrace();  
	    } catch (IOException e) {  
	      e.printStackTrace();  
	    } finally {  
	      try {  
	        if (fos != null) {  
	          fos.close();  
	        }  
	      } catch (IOException ioe) {  
	        System.out.println("Error in closing the Stream");  
	      }  
	    }  
	  }  
	public static String doPost2(String url,Map<String,String> map,String charset){  
        HttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = null;  
        try{  
            httpClient = new SSLClient();  
            httpPost = new HttpPost(url);  
            //设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            Iterator iterator = map.entrySet().iterator();  
            while(iterator.hasNext()){  
                Entry<String,String> elem = (Entry<String, String>) iterator.next();  
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
            }  
            if(list.size() > 0){  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
                httpPost.setEntity(entity);  
            }  
            //System.err.println("33"+new Date());
            HttpResponse response = httpClient.execute(httpPost);  
           // System.err.println("44"+new Date());
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
        return result;  
    }  
	
  
}