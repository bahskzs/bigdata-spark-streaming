package com.yqy.bigdata.ss

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @author bahsk
 * @createTime 2021-02-27 20:47
 * @description 完成基于SS进行词频统计分析
 *   数据源 : file (特点 ： windows上不能直接用复制操作，只能用cp等操作)
 *
 *   SS编程范式：
 *  1) SSC <= sparkConf
 *  2) 业务逻辑
 *  3) 启动作业
 *   */
object FileWordCountApp extends App {

  // 入口点
  val conf = new SparkConf()
    .setMaster("local[*]").setAppName("NetworkWordCount")
  // 指定5s一个批次
  val ssc = new StreamingContext(conf, Seconds(5))

  // TODO... 对接网络数据
  // Create a DStream that will connect to hostname:port, like localhost:9999
  val lines = ssc.textFileStream("D:\\skills-learn\\bigdata\\test\\")

  // TODO... 业务逻辑处理
  val result = lines.flatMap(_.split(",")).map((_,1)).reduceByKey(_+_)

  result.print()

  ssc.start()
  ssc.awaitTermination()
}
