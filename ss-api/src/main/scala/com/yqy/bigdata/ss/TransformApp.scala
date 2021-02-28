package com.yqy.bigdata.ss

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}


/**
 * @author bahsk
 * @createTime 2021-02-28 17:22
 * @description
 */
object TransformApp {

  def main(args: Array[String]): Unit = {
    // 入口点
    val conf = new SparkConf()
      .setMaster("local[2]").setAppName("NetworkWordCount")
    // 指定5s一个批次
    val ssc = new StreamingContext(conf, Seconds(5))




    // TODO... 对接网络数据
    val lines = ssc.socketTextStream("master610", 9527)

    //要过滤掉的数据类型  data001
    val data = List("data001")
    val dataRDD = ssc.sparkContext.parallelize(data).map((_, true))

    //transform 练习  传入的数据类型  20210228,data003    传出的数据类型   data003,20210228
    lines.map(x => (x.split(",")(1), x)).transform(rdd => {
      val filterRDD =  rdd.leftOuterJoin(dataRDD)
      filterRDD.filter(x => x._2._2.getOrElse(false) != true).map(x => (x._1,x._2._1))
    }).print()





    ssc.start()
    ssc.awaitTermination()
  }



}


