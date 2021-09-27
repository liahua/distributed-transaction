package com.chenahua.distributedtransaction;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.*;
import java.util.concurrent.locks.ReentrantLock;

public class MybatisTest {
    @Test
    void mybatisTest() throws SQLException, ClassNotFoundException {
        Class<?> driver = Class.forName("com.mysql.cj.jdbc.Driver");

        Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.4:3306", "root", "root");

        PreparedStatement preparedStatement = conn.prepareStatement("select * from liahua.table_01;");
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("resultSet = " + resultSet);
    }

    @Test
    void mybatisTestManual() {
        AnnotationConfigApplicationContext content = new AnnotationConfigApplicationContext();
        Object testOrderAspect = content.getBean("TestOrderAspect");
    }

    @Test
    void testLock() {
        new ReentrantLock(false);
    }


}
