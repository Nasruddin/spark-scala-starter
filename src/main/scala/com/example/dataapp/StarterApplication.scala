package com.example.dataapp

import com.example.dataapp.Analysis.calculateAverageTipByPickupLocation
import org.apache.spark.sql.SparkSession

object StarterApplication extends SparkMainApplication {

    private val (inputPath, outputPath) = setInputOutputPath(args)

    val data = sparkSession.read.parquet(s"${inputPath}/*.parquet")
    private val analysisResult = calculateAverageTipByPickupLocation(data)

    analysisResult.write.mode("overwrite").option("header", "true").csv(outputPath)

    override def setInputOutputPath(args: Array[String]): (String, String) = {
        val parsedArgs = args.grouped(2).map(arg => arg(0) -> arg(1)).toMap
        (parsedArgs("--input-path"), parsedArgs("--output-path"))
    }

    override def validateConfig()(implicit sparkSession: SparkSession): Boolean = {
        true
    }

    override def run(): Unit = {
        super.run()
    }

    run()
}
