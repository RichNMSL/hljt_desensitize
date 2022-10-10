package com;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class excelUtil {


    public  Map readExcel(String filePath, String company, int systmIndex, int databaseIndex, int tableIndex, int columnIndex, int isSenIndex) {
        Map<String,String> map=new<String,String>HashMap();
        try {

            File excel = new File(filePath);
            if (excel.isFile() && excel.exists()) {
                Workbook wb = null;
                if (filePath.endsWith(".xls")) {
                    wb = new HSSFWorkbook(Files.newInputStream(Paths.get(filePath)));

                } else if (filePath.endsWith(".xlsx")) {
                    wb = new XSSFWorkbook(filePath);
                }
                Sheet sheet = wb.getSheetAt(1);

//                for(int i=1;i<sheet.getLastRowNum()+1;i++){
//                    String tableA = null;
//
//                    if (isMergedRegion(sheet, i, tableIndex)) {
//                        tableA = getMergedRegionValue(sheet, i, tableIndex);
//
//                    } else {
//                        tableA = sheet.getRow(i).getCell(tableIndex).toString();
//
//                    }
//                    //StringBuffer sb=new StringBuffer();
//                    System.out.println(tableA);
//                    //sb.append(tableA);
//
//                    for(int j=2;j<sheet.getLastRowNum();j++){
//                        String tableB = null;
//
//                        if (isMergedRegion(sheet, j, tableIndex)) {
//                            tableB = getMergedRegionValue(sheet, j, tableIndex);
//
//                        } else {
//                            tableB = sheet.getRow(j).getCell(tableIndex).toString();
//
//                        }
//                        if(tableA!=tableB){
//                            break;
//                        }else{
//                            //字段名称
//                            String column = sheet.getRow(j).getCell(columnIndex).toString();
//                            //是否脱敏
//                            String isSen = sheet.getRow(j).getCell(isSenIndex).toString();
//                            if (isSen != null && isSen.equals("是")) {
//                                //sb.append("~"+column );
//                                System.out.println(column);
//                            }
//                        }
//
//
//
//                    }
//                }
                for (Row row : sheet) {
                    String table = null;
                    if (isMergedRegion(sheet, row.getRowNum(), tableIndex)) {
                        table = getMergedRegionValue(sheet, row.getRowNum(), tableIndex);

                    } else {
                        table = row.getCell(tableIndex).toString();

                    }
                    //是否脱敏
                    String isSen = row.getCell(isSenIndex).toString();
                    if (isSen != null && isSen.equals("是")) {
                        map.put(table,"");
                    }

                }


                for (Row row : sheet) {
                    //系统名称
                    String systm = row.getCell(systmIndex).toString();
                    //数据库名称
                    String database = row.getCell(databaseIndex).toString();
                    //表名称
                    String table = null;
                    if (isMergedRegion(sheet, row.getRowNum(), tableIndex)) {
                        table = getMergedRegionValue(sheet, row.getRowNum(), tableIndex);

                    } else {
                        table = row.getCell(tableIndex).toString();

                    }
                    //字段名称
                    String column=null;
                    if(row.getCell(columnIndex)!=null){
                         column = row.getCell(columnIndex).toString();

                    }

                    //是否脱敏
                    String isSen = row.getCell(isSenIndex).toString();
                    if (isSen != null && isSen.equals("是")&&column!=null) {
                        String str=map.get(table);
                        map.put(table,str+column+",");
                    }

                }

            } else {
                System.out.println("找不到此文件" + filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return map;
        }
    }

    //判断是否合并单元格
    private static boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

    //获取合并单元格的值
    public String getMergedRegionValue(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return fCell.getStringCellValue();
                }
            }
        }
        return null;
    }

}
