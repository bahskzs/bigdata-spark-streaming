package com.yqy.bigdata.util

import java.sql.{Connection, DriverManager}

/**
 * @author bahsk
 * @createTime 2021-02-28 20:47
 * @description MySQL 工具类
 */
object MySQLUtils {
  def getConnection() = {
    Class.forName("com.mysql.cj.jdbc.Driver")
    DriverManager.getConnection("jdbc:mysql://localhost:3306/spark?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC","root","QAZwsx123")
  }


  def close(connection:Connection): Unit = {
    if(null != connection) {
      connection.close()
    }
  }

//  def main(args: Array[String]): Unit = {
//    val conn = MySQLUtils.getConnection() ;
//    if(conn != null){
//      val rs = conn.createStatement().executeQuery("")
//      while (rs.next()) {
//        println(rs.getString(0))
//      }
//    }
//    conn.close()
//  }
}
