package util;

/*
/java FileReader的第二种读取方式read(char[])
现将读取的字符存到字符数组，然后在一起打印

*/


import java.io.*;
//import java.util.*;
public class FileReaderDemo2 {


/**
* @param args
*/
	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		FileReader fr = null;
//		char[] buf = new char[1024];
//		try {
//			// 定义一个字符数组，用于存储读到的字符
//			// 该read(char[])返回的是读到的字符个数int
//			// 一个字符两个字节，1024
//			fr = new FileReader("D:\\\\aa.xls");
//			int num = 0;
//			while ((num = fr.read(buf)) != -1) {
//				// 继续读还是往buf里面装所以前面的会被后面进来的替代了
//				System.out.print(new String(buf, 0, num));
//				/*
//				 * 因为read(char[])返回的是字符的个数，当打印长度不够1024时，则直接返回的是个数，因为我们是将字符数组buf打印成字符串，
//				 * 所以不足1024的时候得使用new String(char[] ,0,num);(从哪开始到哪结束)
//				 */
//			}
//
//			// System.out.println("num"+num+"..."+new String(buf));//Arrays.toString(buf)
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				fr.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		bb();
	}
	
	public static void aa() {
		OutputStream out=System.out;
		try {
		byte[] bs="本实例使用OutputStream输出流，在控制台输出字符串\n".getBytes();
		out.write(bs);
		bs="输出内容：\n".getBytes();
		out.write(bs);
		bs="明日科技有限公司，祝愿天下学子，学业有成。".getBytes();
		out.write(bs);
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
	
	
	public static void bb() {
		FileOutputStream fos = null;  
	    File file;  
	    String mycontent = "This is my Data which needs to be written into the file.";  
	    try {  
	      // specify the file path  
	      file = new File("D:\\aa111.txt");  
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
	      byte[] bytesArray = mycontent.getBytes();  
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
	}

