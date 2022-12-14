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
//                            //????????????
//                            String column = sheet.getRow(j).getCell(columnIndex).toString();
//                            //????????????
//                            String isSen = sheet.getRow(j).getCell(isSenIndex).toString();
//                            if (isSen != null && isSen.equals("???")) {
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
                    //????????????
                    String isSen = row.getCell(isSenIndex).toString();
                    if (isSen != null && isSen.equals("???")) {
                        map.put(table,"");
                    }

                }


                for (Row row : sheet) {
                    //????????????
                    String systm = row.getCell(systmIndex).toString();
                    //???????????????
                    String database = row.getCell(databaseIndex).toString();
                    //?????????
                    String table = null;
                    if (isMergedRegion(sheet, row.getRowNum(), tableIndex)) {
                        table = getMergedRegionValue(sheet, row.getRowNum(), tableIndex);

                    } else {
                        table = row.getCell(tableIndex).toString();

                    }
                    //????????????
                    String column=null;
                    if(row.getCell(columnIndex)!=null){
                         column = row.getCell(columnIndex).toString();

                    }

                    //????????????
                    String isSen = row.getCell(isSenIndex).toString();
                    if (isSen != null && isSen.equals("???")&&column!=null) {
                        String str=map.get(table);
                        map.put(table,str+column+",");
                    }

                }

            } else {
                System.out.println("??????????????????" + filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return map;
        }
    }

    //???????????????????????????
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

    //???????????????????????????
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
