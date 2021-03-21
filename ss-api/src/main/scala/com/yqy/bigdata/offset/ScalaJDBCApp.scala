package com.yqy.bigdata.offset


import scalikejdbc.{DB, scalikejdbcSQLInterpolationImplicitDef}
import scalikejdbc.config.DBs

/**
 * @author bahsk
 * @createTime 2021-03-21 22:05
 * @description ScalikeJDBC 练习
 */
object ScalaJDBCApp extends App {

  //用于加载配置文件
  DBs.setupAll()
  query()

  //更新组号
  val groupId = 10
  //update(groupId)

  val offsets = Offset.apply("test","10",4,10)
  insert(offsets)
  queryAll()

  def query() : Unit = {
    DB readOnly { implicit session =>
      sql"select * from offsets_storage".map(rs => rs.long("offset")).list.apply()
    }
  }.foreach(println)


  def queryAll() : Unit = {
    DB readOnly { implicit session =>
      sql"select * from offsets_storage".map(
        rs => Offset(
          rs.string("topic"),
          rs.string("groupid"),
          rs.int("partitions"),
          rs.long("offset")
        )
      ).list.apply()
    }
  }.foreach(println)

  def update(groupId : Int) = {
    DB localTx { implicit session =>
      sql"update offsets_storage set groupid = ${groupId} ".update.apply()
    }
  }

  def insert(offsets : Offset) = {
    //insert into offsets_storage values("test","test-group",2,12);
    DB localTx { implicit session =>
      sql"""
           insert into offsets_storage values (${offsets.topic}, ${offsets.groupId}, ${offsets.partitionId}, ${offsets.offset})
           """
        .update.apply()
      /*
      * 相同写法
      * SQL("insert into user values (?,?)").bind(123, "Alice").update.apply()
      * */
    }
  }

  case class Offset(topic: String, groupId: String, partitionId: Int, offset: Long)

}
