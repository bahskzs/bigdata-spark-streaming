package com.yqy.bigdata.ss

import org.apache.spark.{SparkConf, SparkContext}
import scala.collection.mutable.ListBuffer
/**
 * @author bahsk
 * @createTime 2021-02-28 16:35
 * @description DStreams 和 RDD之间的交互 ，数据批量累计demo
 */
object SparkCoreJoinApp extends App {

    //such as "local" to run locally with one thread, local[*] 全部线程,spark://master:7077" to run on a Spark standalone cluster
      /**
       * Set a name for your application. Shown in the Spark web UI.
      *   def setAppName(name: String): SparkConf = {
      *   set("spark.app.name", name)
      *   }
      *   setAppName中实际上是为参数 "spark.app.name" 赋值
      */
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName(this.getClass.getSimpleName)

    val sc = new SparkContext(sparkConf)

    //要过滤掉的数据
    val list = new ListBuffer[(String,Boolean)]()

    list.append(("data001",true))  //true 代表本条数据是要被过滤掉的

    list.append(("data004",true))

    //tuple 转换为 rdd
    val listRDD = sc.parallelize(list)

    //准备数据
    val input = new ListBuffer[(String,String)]()
    input.append(("data001","20210228"))
    input.append(("data002","20210228"))
    input.append(("data003","20210228"))
    input.append(("data004","20210228"))

    val inputRDD = sc.parallelize(input)

    val filterRDD = inputRDD.leftOuterJoin(listRDD)

    /** getOrElse 的作用 <==> 等价于 case
     * Returns the option's value if the option is nonempty, otherwise
     * return the result of evaluating `default`.
     *
     * This is equivalent to:
     * {{{
     * option match {
     *   case Some(x) => x
     *   case None    => default
     * }
     * }}}
     *
     */
    filterRDD.filter(x => x._2._2.getOrElse(false) != true).map(x => (x._1,x._2._1)).foreach(println)
    /*
    * @note Only one `SparkContext` should be active per JVM. You must `stop()` the
    *   active `SparkContext` before creating a new one.
    * */
    sc.stop()

}
