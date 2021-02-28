package com.yqy.bigdata.ss

import com.yqy.bigdata.util.MySQLUtils
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @author bahsk
 * @createTime 2021-02-28 20:45
 * @description 统计词频，并写入数据库
 */
object ForeachRDDWordCountApp extends App {
  // 入口点
  val conf = new SparkConf()
    .setMaster("local[2]").setAppName("NetworkWordCount")
  // 指定5s一个批次
  val ssc = new StreamingContext(conf, Seconds(5))

  /*
    * Checkpointing must be enabled for applications with any of the following requirements:
    * Usage of stateful transformations - If either updateStateByKey or reduceByKeyAndWindow (with inverse function)
    *     is used in the application,
    *     then the checkpoint directory must be provided to allow for periodic RDD checkpointing.
    * Recovering from failures of the driver running the application
    *   - Metadata checkpoints are used to recover with progress information.
    *
    * */
  val checkpointDirectory = "ss-api/"
  ssc.checkpoint(checkpointDirectory)

  // TODO... 对接网络数据
  // Create a DStream that will connect to hostname:port, like localhost:9999
  val lines = ssc.socketTextStream("master610", 9527)

  // TODO... 业务逻辑处理
  val result = lines.flatMap(_.split(",")).
    map((_,1)).reduceByKey(_+_).updateStateByKey[Int](updateFunction _)


  result.foreachRDD( rdd =>
    rdd.foreachPartition { partitionOfRecords =>
      val connection = MySQLUtils.getConnection()
      partitionOfRecords.foreach(

        record => {
          val sql = s"insert into wc2(word,cnt,data_status) values('${record._1}',${record._2})"
          connection.createStatement().execute(sql)
        }
      )
      connection.close()
    })

  ssc.start()
  ssc.awaitTermination()

  def updateFunction(newValues: Seq[Int], runningCount: Option[Int]): Option[Int] = {
    val newCount = newValues.sum
    val oldCount = runningCount.getOrElse(0)
    Some(newCount + oldCount)
  }
}
