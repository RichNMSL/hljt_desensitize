package com;

import java.io.File;
import java.nio.file.Files;

public class action {
    public static void main(String[] args) {

        String filePath = "src/main/resources/aaaa.xlsx";
        String company="hlgf";

        int systmIndex = 1;
        int databaseIndex = 3;
        int tableIndex = 5;
        int columnIndex = 7;
        int isSenIndex = 12;
        excelUtil excelUtil = new excelUtil();
        excelUtil.readExcel(filePath,company,systmIndex,databaseIndex,tableIndex,columnIndex,isSenIndex);
    }
}
