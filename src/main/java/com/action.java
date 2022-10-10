package com;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

public class action {
    public static void main(String[] args) {

        String filePath = "src/main/resources/aaaa.xlsx";
        String company="sdi_mkh_event";
        //是否保密库。。。。。。。。。。。。。。
        int systmIndex = 1;
        int databaseIndex = 3;
        int tableIndex = 5;
        int columnIndex = 7;
        int isSenIndex = 10;
        String database="sdi_mkh_event";
        String prefix="sdi_";

        excelUtil excelUtil=new excelUtil();
        Map<String,String>map=  excelUtil.readExcel(filePath,company,systmIndex,databaseIndex,tableIndex,columnIndex,isSenIndex);
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
           // System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            StringBuffer sb=new StringBuffer();
            try{
                sb.append("insert overwrite table "+database+"."+ prefix+entry.getKey()+"(");
                //拆分脱敏字段
                List<String>desenList= Arrays.asList(entry.getValue().split(","));
                List<String>colList= hiveUtil.getFieldByTableName("sdi_mkh_event.sdi_"+entry.getKey());
                List<String>newList= new ArrayList<>();

                for(String col :colList){
                    if(desenList.contains(col)){
                        newList.add("null");

                    }else{
                        newList.add(col);

                    }
                }
                sb.append("select ");
                sb.append(newList.toString().replace("[","").replace("]",""));
                sb.append(" from "+database+"."+prefix+entry.getKey()+")");

                System.out.println(sb);
            }catch (Exception e){
                e.printStackTrace();
            }

        }



    }
}
