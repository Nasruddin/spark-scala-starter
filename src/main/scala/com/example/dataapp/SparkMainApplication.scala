package com.example.dataapp

import com.example.config.Configuration
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

trait SparkMainApplication extends App {

    // private val appName = Configuration.appName
    private val appName = DEFAULT_APP_NAME
    println(s"App Name :: ${appName}")
    private lazy val sparkConf: SparkConf = {
    val coreConf = new SparkConf().setAppName(appName)
    // merge if missing
    Configuration.Spark.settings.foreach(tuple => coreConf.setIfMissing(tuple._1, tuple._2))
    coreConf
  }

  lazy implicit val sparkSession: SparkSession = {
    SparkSession.builder()
        .config(sparkConf)
        .getOrCreate()
  }

  def validateConfig()(implicit sparkSession: SparkSession): Boolean

  def setInputOutputPath(args: Array[String]): (String, String)

  def run(): Unit = {
      validateConfig()
  }

}
