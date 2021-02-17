package com.yqy.bigdata.ss

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @author bahsk
 * @createTime 2021-02-16 22:28
 * @description 基于SS的 wordcount
 *              数据源 : netcat nc -lk 9527
 */
object NetWorkCountApp extends App {


  // 入口点
  val conf = new SparkConf()
    .setMaster("local[2]").setAppName("NetworkWordCount")
  // 指定5s一个批次
  val ssc = new StreamingContext(conf, Seconds(5))

  // TODO... 对接网络数据
  // Create a DStream that will connect to hostname:port, like localhost:9999
  val lines = ssc.socketTextStream("master610", 9527)

  // TODO... 业务逻辑处理
  val result = lines.flatMap(_.split(",")).map((_,1)).reduceByKey(_+_)

  result.print()

  ssc.start()
  ssc.awaitTermination()

}
