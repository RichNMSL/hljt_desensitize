package com;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class excelUtil {


    public void readExcel(String filePath, String company,int systmIndex, int databaseIndex, int tableIndex, int columnIndex, int isSenIndex) {
        try {

            File excel = new File(filePath);
            if (excel.isFile() && excel.exists()) {
                Workbook wb = null;
                if (filePath.endsWith(".xls")) {
                    wb = new HSSFWorkbook(Files.newInputStream(Paths.get(filePath)));

                } else if (filePath.endsWith(".xlsx")) {
                    wb = new XSSFWorkbook(filePath);
                }
                Sheet sheet = wb.getSheetAt(0);

                for (Row row : sheet) {
                    //系统名称
                    String systm = row.getCell(systmIndex).toString();
                    //数据库名称
                    String database = row.getCell(databaseIndex).toString();
                    //表名称
                    String table = row.getCell(tableIndex).toString();
                    //字段名称
                    String column = row.getCell(columnIndex).toString();
                    //是否脱敏
                    String isSen = row.getCell(isSenIndex).toString();

                    if(isSen!=null&&isSen.equals("是")){
                        System.out.println( "update sdi_"+company+"_"+systm+"_"+database+"."+table+" set "+column+"=null;");
                        System.out.println( "update sdi_"+company+"_"+systm+"_"+database+"_secret."+table+" set "+column+"=null;");
                    }

                }

            } else {
                System.out.println("找不到此文件" + filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
