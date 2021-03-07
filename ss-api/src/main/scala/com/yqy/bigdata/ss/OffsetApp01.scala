package com.yqy.bigdata.ss

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @author bahsk
 * @createTime 2021-03-05 21:42
 * @description
 */
object OffsetApp01 extends App {
  val checkpointDirectory = "ss-api/offset/checkpoints2"

  // 入口点
  val conf = new SparkConf()
    .setMaster("local[2]").setAppName("SSKafkaIntegrationApp")


  def functionToCreateContext(): StreamingContext = {

    // 指定5s一个批次
    val ssc = new StreamingContext(conf, Seconds(5))   // new context

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "master610:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "test-ss-kafka",
      "auto.offset.reset" -> "earliest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topics = Array("test")
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )

    stream.foreachRDD( rdd => {
      if(!rdd.isEmpty()) {
        println("rdd records:" + rdd.count())
      }
    })

    ssc.checkpoint(checkpointDirectory)   // set checkpoint directory
    ssc
  }

  val context = StreamingContext.getOrCreate(checkpointDirectory, functionToCreateContext _)
  context.start()
  context.awaitTermination()
}
