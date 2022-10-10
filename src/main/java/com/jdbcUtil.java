package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbcUtil {
    static final String DriverName="org.apache.hive.jdbc.HiveDriver";
    static final String url="jdbc:hive2://10.155.0.14:10000";
    static final String user="hive";
    static final String pass="Hzhl@2022";


    public static Connection getConn() throws ClassNotFoundException, SQLException {
        Class.forName(DriverName);
        Connection connection = DriverManager.getConnection(url,user,pass);
        return connection;
    }

    /**
     * 创建命令
     * @param connection
     * @return
     * @throws SQLException
     */
    public static Statement getStmt(Connection connection) throws SQLException {
        return connection.createStatement();
    }

    /**
     * 关闭连接
     * @param connection
     * @param statement
     * @throws SQLException
     */
    public static void closeFunc(Connection connection,Statement statement) throws SQLException {
        statement.close();
        connection.close();
    }
}
