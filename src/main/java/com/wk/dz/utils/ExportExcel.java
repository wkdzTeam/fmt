package com.wk.dz.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExportExcel
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ExportExcel.class);
	public static byte[] exportExcel(List<Object> dataset) 
    {
		return  exportExcel("交易明细excel", null, dataset, "yyyy-MM-dd");
    }

    public static byte[] exportExcel(String[] headers, List<Object> dataset) 
    {
    	return exportExcel("交易明细excel", headers, dataset, "yyyy-MM-dd");
    }
    public static byte[] exportExcel(String title,String[] headers, List<Object> dataset) 
    {
    	return exportExcel(title, headers, dataset, "yyyy-MM-dd");
    }

    public static byte[] exportExcel(String[] headers, List<Object> dataset, String pattern) 
    {
       return  exportExcel("交易明细excel", headers, dataset, pattern);
    }

    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     * @param <T>
     * 
     * @param title
     *            表格标题名
     * @param headers
     *            表格属性列名数组
     * @param dataset
     *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out
     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern
     *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    @SuppressWarnings("unchecked")
    public static byte[] exportExcel(String title, String[] headers,List<Object> dataset,String pattern) 
    {
    	// 声明一个工作薄
    	HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFFont font = workbook.createFont();// 生成一个字体
        HSSFFont font2 = workbook.createFont(); // 生成另一个字体
        HSSFFont font3 = workbook.createFont();// 生成另一个字体
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
//	        // 定义注释的大小和位置,详见文档
//	        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
//	                0, 0, 0, (short) 4, 2, (short) 6, 5));
//	        // 设置注释内容
//	        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
//	        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
//	        comment.setAuthor("leno");

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        if(null != headers && headers.length > 0)
        {
        	for (short i = 0; i < headers.length; i++) 
            {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }
        }
        

        // 遍历集合数据，产生数据行
        Iterator<Object> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            Object t = (Object) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < fields.length; i++) 
            {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style2);
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName,new Class[] {});
                    Object value = getMethod.invoke(t, new Object[] {});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    // if (value instanceof Integer) {
                    // int intValue = (Integer) value;
                    // cell.setCellValue(intValue);
                    // } else if (value instanceof Float) {
                    // float fValue = (Float) value;
                    // textValue = new HSSFRichTextString(
                    // String.valueOf(fValue));
                    // cell.setCellValue(textValue);
                    // } else if (value instanceof Double) {
                    // double dValue = (Double) value;
                    // textValue = new HSSFRichTextString(
                    // String.valueOf(dValue));
                    // cell.setCellValue(textValue);
                    // } else if (value instanceof Long) {
                    // long longValue = (Long) value;
                    // cell.setCellValue(longValue);
                    // }
                    if(null == value)
                    {
                    	value = "-";
                    }
                    else if (value instanceof Date) 
                    {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } 
                    else if (value instanceof byte[]) 
                    {
                        // 有图片时，设置行高为60px;
                        row.setHeightInPoints(60);
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算
                        sheet.setColumnWidth(i, (short) (35.7 * 80));
                        // sheet.autoSizeColumn(i);
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                                1023, 255, (short) 6, index, (short) 6, index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor, workbook.addPicture(
                                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } 
                    else 
                    {
                        // 其它数据类型都当作字符串简单处理
                        textValue = value.toString();
                    }
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null) 
                    {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) 
                        {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } 
                        else 
                        {
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            font3.setColor(HSSFColor.BLUE.index);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                } 
                catch (SecurityException e) 
                {
                	LOGGER.error("====================exception{}",e);
                } 
                catch (NoSuchMethodException e) 
                {
                	LOGGER.error("====================exception{}",e);
                } 
                catch (IllegalArgumentException e) 
                {
                	LOGGER.error("====================exception{}",e);
                }
                catch (IllegalAccessException e) 
                {
                	LOGGER.error("====================exception{}",e);
                } 
                catch (InvocationTargetException e) 
                {
                	LOGGER.error("====================exception{}",e);
                } 
                finally 
                {
                    // 清理资源
                }
            }

        }
        ByteArrayOutputStream  out = new ByteArrayOutputStream();
        try 
        {
            workbook.write(out);
        } 
        catch (IOException e) 
        {
        	LOGGER.error("====================exception{}",e);
        }
        return out.toByteArray();
    }
    
//    public static void main(String[] args) 
//    {
//    	List<Object> cls = new ArrayList<Object>();
//    	Person p = null;
//    	for(int i=0;i<50000;i++)
//    	{
//    		p = new Person();
//    		p.setName("第"+i+"条记录");
//    		p.setValue(i+"");
//    		cls.add(p);
//    	}
//    	
//    	OutputStream out;
//		try {
//			out = new FileOutputStream(new File("D://001.xls"));
//			out.write(ExportExcel.exportExcel(cls));
//			out.flush();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	
//	}
//    
//    static class Person
//    {
//    	private String name;
//    	private String value;
//		public Person() 
//		{
//			super();
//			// TODO Auto-generated constructor stub
//		}
//		public String getName() {
//			return name;
//		}
//		public void setName(String name) {
//			this.name = name;
//		}
//		public String getValue() {
//			return value;
//		}
//		public void setValue(String value) {
//			this.value = value;
//		}
//    	
//    }
}
