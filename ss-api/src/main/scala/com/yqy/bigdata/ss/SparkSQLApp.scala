package com.yqy.bigdata.ss


import org.apache.spark.sql.SparkSession

/**
 * @author bahsk
 * @createTime 2021-02-28 21:19
 * @description
 */
object SparkSQLApp  {
    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder().appName(this.getClass.getSimpleName).master("local[*]").getOrCreate()
        val df = spark.read.format("json").load("ss-api/data/employees.json")
        df.show()

        df.createOrReplaceTempView("emp")
        spark.sql(
            """
              |select name,salary
              |from emp
              |where salary>3000
              |
              |""".stripMargin).show(false)

        spark.stop()
    }

}
