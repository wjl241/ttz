package util;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * xls工具类
 * 
 * @author hjn
 * 
 */
public class XlsUtil {
	
	public static void read(String filePath) throws IOException {
		String fileType = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
		InputStream stream = new FileInputStream(filePath);
		Workbook wb = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook(stream);
		} else if (fileType.equals("xlsx")) {
			//wb = new XSSFWorkbook(stream);
		} else {
			System.out.println("您输入的excel格式不正确");
		}
		Sheet sheet1 = wb.getSheetAt(0);
		for (Row row : sheet1) {
			for (Cell cell : row) {
				System.out.print(cell.getStringCellValue() + "  ");
			}
			System.out.println();
		}
	}
	
	

	public static boolean write(String outPath) throws Exception {
		String fileType = outPath.substring(outPath.lastIndexOf(".") + 1, outPath.length());
		System.out.println(fileType);
		// 创建工作文档对象
		Workbook wb = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook();
		} else if (fileType.equals("xlsx")) {
			//wb = new XSSFWorkbook();
		} else {
			System.out.println("您的文档格式不正确！");
			return false;
		}
		// 创建sheet对象
		Sheet sheet1 = (Sheet) wb.createSheet("sheet1");
		// 循环写入行数据
		for (int i = 0; i < 5; i++) {
			Row row = (Row) sheet1.createRow(i);
			// 循环写入列数据
			for (int j = 0; j < 8; j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue("测试" + j);
			}
		}
		// 创建文件流
		OutputStream stream = new FileOutputStream(outPath);
		// 写入数据
		wb.write(stream);
		// 关闭文件流
		stream.close();
		return true;
	}


	public static void main(String[] args) {
//		try {
//			XlsUtil.write("D:" + File.separator + "out.xls");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			XlsUtil.readExcel("D:" + File.separator + "test1.xls","aaa");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		List<String> ret = XlsUtil.readExcel("D:" + File.separator + "test1.xls");
	}
	
	public static void read2(String filePath) throws IOException {
		String fileType = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
		InputStream stream = new FileInputStream(filePath);
		Workbook wb = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook(stream);
		} else if (fileType.equals("xlsx")) {
			//wb = new XSSFWorkbook(stream);
		} else {
			System.out.println("您输入的excel格式不正确");
		}
		Sheet sheet1 = wb.getSheetAt(0);
		for (Row row : sheet1) {
			for (Cell cell : row) {
				System.out.println(cell.getStringCellValue() + "  ");
			}
			System.out.println();
		}
	}
	
	
	/**
	 * 通用获取excel内容
	 * @param filePath
	 * @return
	 */
	 public static List<String> readExcel(String filePath) {
		 
	       // Map<String, String> map = new HashMap<String, String>();
		 	List<String> ret = new ArrayList<String>();
	        // 创建对Excel工作簿文件的引用
	        try {
	        	//filePath是文件地址。
	            HSSFWorkbook wookbook = new HSSFWorkbook(new FileInputStream(filePath));
	            // 在Excel文档中，第一张工作表的缺省索引是0
	 
	            HSSFSheet sheet = wookbook.getSheetAt(0);
	 
	            // 获取到Excel文件中的所有行数
	            int rows = sheet.getPhysicalNumberOfRows();
	            int max_cells = 0;
	            // 获取最长的列，在实践中发现如果列中间有空值的话，那么读到空值的地方就停止了。所以我们需要取得最长的列。<br>　　　　　　　　　　　　　　//如果每个列正好都有一个空值得话，通过这种方式获得的列长会比真实的列要少一列。所以我自己会在将要倒入数据库中的EXCEL表加一个表头<br>　　　　　　　　　　　　　　//防止列少了，而插入数据库中报错。
	            for (int i = 0; i < rows; i++) {
	                HSSFRow row = sheet.getRow(i);
	                if (row != null) {
	                    int cells = row.getPhysicalNumberOfCells();
	                    if (max_cells < cells) {
	                        max_cells = cells;
	                    }
	 
	                }
	            }
	            System.out.println(max_cells);
	            // 遍历行
	            for (int i = 1; i < rows; i++) {
	                // 读取左上端单元格
	                HSSFRow row = sheet.getRow(i);
	                // 行不为空
	                if (row != null) {
	                    String value = "";
	                    // 遍历列
	                    String b_id = null;
	                    for (int j = 0; j < max_cells; j++) {
	                        // 获取到列的值
	                        HSSFCell cell = row.getCell(j);
	                      // System.err.println(cell.getColumnIndex()); 
	                      //把所有是空值的都换成NULL
	                        if (cell == null) {
	                            value += "NULL,";
	                        } else {
	                            switch (cell.getCellType()) {//如果是公式的话，就读取得出的值
	                            case HSSFCell.CELL_TYPE_FORMULA:
	                                try {
	                                    value += "'" +String.valueOf(cell.getNumericCellValue()).replaceAll("'", "")+ "',";
	                                } catch (IllegalStateException e) {
	                                    value += "'" +String.valueOf(cell.getRichStringCellValue()).replaceAll("'", "")+ "',";
	                                }
	                                break;
	                            case HSSFCell.CELL_TYPE_NUMERIC:
	                                // 如果有日期的话，那么就读出日期格式
	                                // 如果是数字的话，就写出数字格式
	                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
	                                    SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                                    Date date2 = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
	                                    String date1 = dff.format(date2);
	                                    value += "'" + date1.replaceAll("'", "") + "',";
	 
	                                } else {
	                                    value += "'" +  cell.getNumericCellValue() + "',";
	                                }
	                                break;
	                            case HSSFCell.CELL_TYPE_STRING:
	                                String ss = cell.getStringCellValue().replaceAll("'", "");//如果文本有空值的话，就把它写成null
	                                
	                                if (ss == null || "".equals(ss)) {
	                                    value += "NULL,";
	                                } else {
	                                    value += "'" + cell.getStringCellValue().replaceAll("'", "") + "',";
	                                }
	 
	                                break;
	                            default:
	                                value += "NULL,";
	                                break;
	                            }
	                        }
	                         if (j == 0) {
	                         
	                         b_id = value.substring(1, value.length() - 2);
	                         }
	 
	                    }
	 
	                    String valueresult = value.substring(0, value.length() - 1);
	                    ret.add(valueresult);
	                   // System.err.println(valueresult);
	                    
	                }
	            }
	 
	            wookbook.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 
	 
	 
	        return ret;
	    }
}

