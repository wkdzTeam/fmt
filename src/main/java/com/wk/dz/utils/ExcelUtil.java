package com.wk.dz.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelUtil
{
  public static String[][] readData(File file, int ignoreRows)
    throws FileNotFoundException, IOException
  {
    List result = new ArrayList();
    int rowSize = 0;
    BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

    POIFSFileSystem fs = new POIFSFileSystem(in);
    HSSFWorkbook wb = new HSSFWorkbook(fs);
    HSSFCell cell = null;
    for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
      HSSFSheet st = wb.getSheetAt(sheetIndex);

      for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
        HSSFRow row = st.getRow(rowIndex);
        if (row != null)
        {
          int tempRowSize = row.getLastCellNum() + 1;
          if (tempRowSize > rowSize) {
            rowSize = tempRowSize;
          }
          String[] values = new String[rowSize];
          Arrays.fill(values, "");
          boolean hasValue = false;
          for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex = (short)(columnIndex + 1)) {
            String value = "";
            cell = row.getCell(columnIndex);
            if (cell != null)
            {
              switch (cell.getCellType()) {
              case 1:
                value = cell.getStringCellValue();
                break;
              case 0:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                  java.util.Date date = cell.getDateCellValue();
                  if (date != null)
                    value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                  else
                    value = "";
                }
                else {
                  value = new DecimalFormat("##########0.00").format(cell.getNumericCellValue());
                }
                break;
              case 2:
                if (!cell.getStringCellValue().equals(""))
                  value = cell.getStringCellValue();
                else {
                  value = cell.getNumericCellValue() + "";
                }
                break;
              case 3:
                break;
              case 5:
                value = "";
                break;
              case 4:
                value = cell.getBooleanCellValue() == true ? "Y" : "N";
                break;
              default:
                value = "";
              }
            }
            values[columnIndex] = value.trim();
            hasValue = true;
          }

          if (hasValue) {
            result.add(values);
          }
        }
      }
    }
    in.close();
    String[][] returnArray = new String[result.size()][rowSize];
    for (int i = 0; i < returnArray.length; i++) {
      returnArray[i] = ((String[])(String[])result.get(i));
    }
    return returnArray;
  }

  public static void exportExcel(List<Object[]> data, String fileName)
    throws IOException
  {
    HSSFWorkbook wb = new HSSFWorkbook();

    Sheet sheet = wb.createSheet();
    Font font0 = createFonts(wb, (short)700, "宋体", false, (short)200);

    for (int i = 0; i < data.size(); i++) {
      Row rowData = sheet.createRow(i);
      int j = 0;
      for (Object value : (Object[])data.get(i)) {
        createCell(wb, rowData, j++, value, i == 0 ? font0 : null);
      }
    }
    FileOutputStream fos = new FileOutputStream(fileName);
    wb.write(fos);
    fos.flush();
    fos.close();
  }

  private static void exportExcel(List<Object[]> data, OutputStream os)
    throws IOException
  {
    HSSFWorkbook wb = new HSSFWorkbook();

    Sheet sheet = wb.createSheet();
    // 设置表格默认列宽度为20个字节
    sheet.setDefaultColumnWidth((short) 20);
    Font font0 = createFonts(wb, (short)700, "宋体", false, (short)200);

    for (int i = 0; i < data.size(); i++) {
      Row rowData = sheet.createRow(i);
      int j = 0;
      for (Object value : (Object[])data.get(i)) {
        createCell(wb, rowData, j++, value, i == 0 ? font0 : null);
      }
    }
    wb.write(os);
    os.flush();
  }

  public static void downloadExcel(String filename, HttpServletResponse response, List<Object[]> data)
  {
    OutputStream os = null;
    try {
      response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("UTF-8"), "ISO-8859-1"));
      response.setCharacterEncoding("UTF-8");
      response.setContentType("application/octet-stream");
      os = response.getOutputStream();
      exportExcel(data, os);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (os != null) os.close(); 
      } catch (Exception e) { e.printStackTrace(); }

    }
  }

  private static void createStringCell(Workbook wb, Row row, int column, String value, Font font)
  {
    if ((value == null) || (value.equals("null")))
      value = "";
    if (value.matches("\\d+\\-\\d+\\-\\d+ \\d+:\\d+:\\d+\\.0"))
      value = value.substring(0, value.length() - 2);
    Cell cell = row.createCell(column);
    cell.setCellValue(value);
    if (font != null) {
      CellStyle cellStyle = wb.createCellStyle();
      cellStyle.setFont(font);
      cellStyle.setFillBackgroundColor((short)60);
      cellStyle.setFillForegroundColor((short)9);
      cell.setCellStyle(cellStyle);
    }
  }

  private static void createCell(Workbook wb, Row row, int column, Object value, Font font)
  {
    if ((value instanceof Long)) {
      Cell cell = row.createCell(column);
      if (value != null) {
        cell.setCellValue(((Long)value).doubleValue());
      }
    }
    else if ((value instanceof Integer)) {
      Cell cell = row.createCell(column);
      if (value != null) {
        cell.setCellValue(((Integer)value).doubleValue());
      }
    }
    else if ((value instanceof Float)) {
      Cell cell = row.createCell(column);
      if (value != null) {
        cell.setCellValue(((Float)value).doubleValue());
      }
    }
    else if ((value instanceof Boolean)) {
      Cell cell = row.createCell(column);
      cell.setCellValue(((Boolean)value).booleanValue());
    }
    else {
      createStringCell(wb, row, column, value + "", font);
    }
  }

  private static Font createFonts(Workbook wb, short bold, String fontName, boolean isItalic, short hight)
  {
    Font font = wb.createFont();
    font.setFontName(fontName);
    font.setBoldweight(bold);
    font.setItalic(isItalic);
    font.setFontHeight(hight);
    return font;
  }

  public static void main(String[] args)
    throws Exception
  {
    Object[] header = { "序号", "资产名称", "资产编号", "借用人", "借用人卡号", "使用地点", "借用时间", "预计归还时间", "实际归还时间", "状态", "操作员", "操作" };
    Object[] dataRow1 = { Integer.valueOf(1), "羽毛球拍", Long.valueOf(80128411111L), "王强", "2009001", "初一（5）班", Timestamp.valueOf("2014-02-17 22:52:00"), java.sql.Date.valueOf("2014-02-24"), "逾期未还", "超级管理员", "归还   取消借用 变更" };
    Object[] dataRow2 = { Integer.valueOf(2), "羽毛球拍", Long.valueOf(80128412222L), "王强", "2009001", "高三年级", Timestamp.valueOf("2014-02-12 23:48:01"), java.sql.Date.valueOf("2014-02-19"), "2012-09-04 21:25", "正常归还", "超级管理员" };
    Object[] dataRow3 = { Integer.valueOf(3), "羽毛球拍", Long.valueOf(80128413333L), "王强", "2009001", "初二（2）班", Timestamp.valueOf("2012-09-06 00:23:03"), java.sql.Date.valueOf("2012-09-13"), "2014-02-12 22:16", "正常归还", "超级管理员" };
    Object[] dataRow4 = { Integer.valueOf(4), "羽毛球拍", Long.valueOf(80128414444L), "王强", "2009001", "初二年级", Timestamp.valueOf("2012-09-04 21:23:04"), java.sql.Date.valueOf("2012-09-11"), "2012-09-04 21:25", "正常归还", "超级管理员" };
    List list = Arrays.asList(new Object[][] { header, dataRow1, dataRow2, dataRow3, dataRow4 });
    exportExcel(list, "D:\\test.xls");
  }
}
