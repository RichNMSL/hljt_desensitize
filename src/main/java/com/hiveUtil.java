package com;

import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class hiveUtil {

    //获取表字段
    public static List<String> getFieldByTableName(String dbTableName) throws SQLException, ClassNotFoundException {
        Connection conn = jdbcUtil.getConn();
        System.out.println("创建连接成功");
        Statement stat = jdbcUtil.getStmt(conn);
        System.out.println("创建命令成功");
        List<String> fields = new ArrayList<>();
        ResultSet res = stat.executeQuery("desc " + dbTableName);
        while (res.next()) {
            String col_name = res.getString("col_name");
            if (StringUtils.isNotBlank(col_name) && !col_name.contains("#")) {
                fields.add(col_name);
            }
        }
        jdbcUtil.closeFunc(conn,stat);
        return fields;
    }



}

