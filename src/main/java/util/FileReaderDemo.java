package util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class FileReaderDemo {  
    public static void main(String[] args) {  
        characters();  
    }  
  
  
/*****************字符数组进行读取*********************/  
    private static void characters() {  
  
        try {  
  //"D:\\testExcel3.xls"
            FileReader fr = new FileReader("D:\\aa.xls");  
            char []  buf = new char[6];   
            //将Denmo中的文件读取到buf数组中。  
            int num = 0;      
            StringBuffer vv;
            FileWriter fw = new FileWriter("D:\\aa3.xls",true);  
            while((num = fr.read(buf))!=-1) {  
  
                //String(char[] value , int offest,int count) 分配一个新的String,包含从offest开始的count个字符  
                sop(new String(buf,0,num));  
                fw.write(new String(buf,0,num));  
               // vv = vv.append(new String(buf,0,num));
                
                
            }  
           // sop('\n');  
            fr.close();  
            
            //传递一个参数,代表不覆盖已有的数据。并在已有数据的末尾进行数据续写  
           // fw.write(vv);  
            fw.close();  
        }  
        catch (IOException e) {  
            sop(e.toString());  
        }  
    }  
  
  
  
  
  
  
/*****************单个字母读取*************************/  
    private static void singleReader() {  
          
        try {  
  
            //创建一个文件读取流对象，和指定名称的文件关联。  
            //要保证文件已经存在，否则会发生异常：FileNotFoundException  
            FileReader fr = new FileReader("Demo.txt");  
  
          
            //如何调用读取流对象的read方法？  
            //read()方法，一次读取一个字符，并且自动往下读。如果到达末尾则返回-1  
            int ch = 0;  
            while ((ch=fr.read())!=-1) {  
                sop((char)ch);  
            }  
            sop('\n');  
            fr.close();  
  
  
            /*int ch = fr.read(); 
            sop("ch=" + (char)ch); 
 
            int ch2 = fr.read(); 
            sop("ch2=" + (char)ch2); 
 
            //使用结束注意关闭流 
            fr.close(); */    
              
  
  
        }  
        catch (IOException e) {  
            sop(e.toString());  
        }  
      
    }  
  
  
/**********************Println************************/  
    private static void sop(Object obj) {  
        System.out.print(obj);  
    }  
  
}  
