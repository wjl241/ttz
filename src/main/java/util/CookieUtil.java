package util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Base64;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CookieUtil {
	static Logger logger = LoggerFactory.getLogger(CookieUtil.class);  
	public static void main(String[] args) {
		//think_decrypt("MDAwMDAwMDAwMIlnmNaNsatyrLLJb4G6Zpx_y3iwtJ-MaX93uGQ");
		think_decrypt("MDAwMDAwMDAwMIlnmNaNsatyrLLJb4G6Zpx_y3iwtJ-MaX93uGQ");
		//decode2("W34rXyFAIyh7KX1dMjAxNiY1ODU4");
	}
//PHP解密过程
//	function think_decrypt($data, $key = '')
//	{
//	    $key = md5(empty($key) ? config('data_auth_key') : $key);
//	    $data = str_replace(array('-', '_'), array('+', '/'), $data);
//	    $mod4 = strlen($data) % 4;
//	    if ($mod4) {
//	        $data .= substr('====', $mod4);
//	    }
//	    $data = base64_decode($data);
//	    $expire = substr($data, 0, 10);
//	    $data = substr($data, 10);
//
//	    if ($expire > 0 && $expire < time()) {
//	        return '';
//	    }
//	    $x = 0;
//	    $len = strlen($data);
//	    $l = strlen($key);
//	    $char = $str = '';
//
//	    for ($i = 0; $i < $len; $i++) {
//	        if ($x == $l) $x = 0;
//	        $char .= substr($key, $x, 1);
//	        $x++;
//	    }
//
//	    for ($i = 0; $i < $len; $i++) {
//	        if (ord(substr($data, $i, 1)) < ord(substr($char, $i, 1))) {
//	            $str .= chr((ord(substr($data, $i, 1)) + 256) - ord(substr($char, $i, 1)));
//	        } else {
//	            $str .= chr(ord(substr($data, $i, 1)) - ord(substr($char, $i, 1)));
//	        }
//	    }
//	    return base64_decode($str);
//	}
	/**
	 *解密cookie .token中的密码  解密内容形如 最后为user id [~+_!@#({)}]2016&5858
	 */
	public static  String think_decrypt(String data) {
		String key = toMD5("+_*/@ZKkj@2016");//默认解密参数，写死
		data = data.replace('-', '+');
		data = data.replace('_', '/');
		int mod4 = data.length() % 4;
		if(mod4 != 0) {
			data = data + "====".substring(mod4);
		}
		
		byte[] a1 =BASE64.decode(data.toCharArray());
		
		data = new String(a1);
		
		String expire = data.substring(0, 10);
		int expire2 = Integer.valueOf(expire);
		  if (expire2 > 0 && expire2 < System.currentTimeMillis()/1000) {
		  return "error#有效期失效";
		}
		 
		byte[] data2 = new byte[a1.length-10];
		for(int i=10;i<a1.length;i++) {
			data2[i-10] = a1[i];
		}
		int x = 0;
		int len = data2.length;
		int l = key.length();
		String char1 ="";
		String str="";
		for(int i=0;i<len+1;i++) {
			if(x==l) {
				x=0;
			}
			char1 = char1+key.substring(x, x+1);
			x++;
		}
		int a;
		int b;
		int c;
		String ret ="";
		char d;
		for(int i=0;i<len;i++) {
			a = data2[i];
			if(a<0) {
				a = 256+a;
			}
			b = ord(char1.substring(i, i+1));
			if(a<b) {
				c =  (a+Byte.valueOf("256") - b);
			}else {
				c =  (a - b );
			}
			d = (char) c;
			ret = ret + d;
		}
		ret = decode2(ret);
		System.err.println("ret2:"+ret);
		return ret;
	}
	
	
	public static String toMD5(String plainText) {
		try {
			// 生成实现指定摘要算法的 MessageDigest 对象。
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 使用指定的字节数组更新摘要。
			md.update(plainText.getBytes());
			// 通过执行诸如填充之类的最终操作完成哈希计算。
			byte b[] = md.digest();
			// 生成具体的md5密码到buf数组
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// System.out.println("32位: " + buf.toString());// 32位的加密
			// System.out.println("16位: " + buf.toString().substring(8, 24));//
			// 16位的加密，其实就是32位加密后的截取
			return buf.toString();

		} catch (Exception e) {
			e.printStackTrace();
			//logger.error("MD5解析错误:"+plainText,e);
			return "";
		}
	}
	
	
	  public static String decode(String str){    
		byte[] bt = null;
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			bt = decoder.decodeBuffer(str);
			System.err.println(bt);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String aa = "";
		try {
			aa = new String(bt, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return aa;
	}
	  
	public static String decode2(String str) {
		byte[] asBytes = Base64.getDecoder().decode(str);

		try {
			System.out.println(new String(asBytes, "utf-8"));
			return new String(asBytes, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	  
	  public static String byteArrayToHexStr(byte[] byteArray) {
		    if (byteArray == null){
		        return null;
		    }
		    char[] hexArray = "0123456789ABCDEF".toCharArray();
		    char[] hexChars = new char[byteArray.length * 2];
		    for (int j = 0; j < byteArray.length; j++) {
		        int v = byteArray[j] & 0xFF;
		        hexChars[j * 2] = hexArray[v >>> 4];
		        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		    }
		    return new String(hexChars);
		}
	  
	  private static int ord(String s){
		  int rt=s.toCharArray()[0];
		  return rt;
		  }
	  
	  public static String stringToAscii(String value)  
	  {  
	      StringBuffer sbu = new StringBuffer();  
	      char[] chars = value.toCharArray();   
	      for (int i = 0; i < chars.length; i++) {  
	          if(i != chars.length - 1)  
	          {  
	              sbu.append((int)chars[i]).append(",");  
	          }  
	          else {  
	              sbu.append((int)chars[i]);  
	          }  
	      }  
	      return sbu.toString();  
	  }  
	  
	  /**
		 * 根据cookie token获取userid
		 * @param request
		 * @param response
		 * @return
		 */
		public static int getUserId(HttpServletRequest request,HttpServletResponse response) {
			if(request.getCookies() ==null) {
				return -1;
			}
			Cookie[] cookies = request.getCookies();
			if(cookies ==null || cookies.length<=0) {
				responseWriteInfo("cookies信息为空",response);
				return -1;
			}
			Cookie token;
			token = cookies[0];
			boolean isContinue = false;
			for(int i =0;i<cookies.length;i++) {
				token = cookies[i];
				if(token.getName().equals("token")) {
					isContinue =true;
					break;
				}
			}
			if(!isContinue) {
				responseWriteInfo("cookies未包含有效用户信息",response);
				return -1;
			}
			
			if(token ==null || token.getValue() ==null ) {
				responseWriteInfo("token信息为空信息为空",response);
				return -1;
			}
			//解析tokenInfo
			String tokenInfo = CookieUtil.think_decrypt(token.getValue());
			if(tokenInfo ==null ||tokenInfo.equals("") ) {
				responseWriteInfo("解析tokenInfo为空",response);
				return -1;
			}
			if(tokenInfo.contains("error#")) {
				responseWriteInfo(tokenInfo.substring(5),response);
				return -1;
			}
			if(!tokenInfo.contains("&")) {
				responseWriteInfo("tokenInfo未包含&，无法解析",response);
				return -1;
			}
			String[] token_str = tokenInfo.split("&");
			if(token_str == null || token_str.length<=1) {
				responseWriteInfo("tokenInfo未包含用户信息",response);
				return -1;
			}
			String userid = (String) token_str[1];
			int userId = Integer.valueOf(userid);
			return userId;
		}
		
		public static HttpServletResponse responseWriteInfo(String info,HttpServletResponse response) {
			try {
				response.getWriter().write(info);
				return response;
			} catch (IOException e) {
				logger.error("response.getWriter().write错误：", e);
			}
			return response;
		}
}
