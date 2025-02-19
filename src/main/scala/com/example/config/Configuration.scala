package com.example.config

import com.typesafe.config.ConfigFactory
import scala.jdk.CollectionConverters._

object Configuration {
    /* The config location can be overridden with -Dconfig.file=path/to/config-file */
    private lazy val defaultConfig = ConfigFactory.load("application.conf")
    private lazy val config = ConfigFactory.load().withFallback(defaultConfig)

    config.checkValid(ConfigFactory.defaultReference(), "default") //default mentioned in application.conf

    private lazy val appConfig = config.getConfig("default")
    val appName = appConfig.getString("appName")

    object Spark {
        private val spark = appConfig.getConfig("spark")
        private val _settings = spark.getConfig("settings")

        /* 
            _settings.entrySet(): Retrieves the key-value pairs from the ConfigObject.
            asScala: Converts the Java collection to Scala’s Map.
            entry.getValue.unwrapped(): Extracts the raw value from the ConfigValue, allowing you to handle it as a native Scala type (e.g., String, Int, etc.).
            .toString: Ensures that the value is converted into a string, so you have a consistent Map[String, String].
            .toMap: Converts the resulting collection into an immutable Scala Map.

        */
        lazy val settings: Map[String, String] = _settings.entrySet().asScala.map {case entry => entry.getKey -> entry.getValue.unwrapped().toString}.toMap
    }
  
}
