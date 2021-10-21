import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.sql.functions.{col, current_date, current_timestamp, date_format, lit, when}
import org.apache.spark.sql.types.{DateType, DoubleType, IntegerType, StringType, StructField, StructType}

import java.io._

object SparkTraining {

  val schema = StructType(
    List(
      StructField("OrderLine", IntegerType, false),
      StructField("Orderid", IntegerType, false),
      StructField("Productid", IntegerType, false),
      StructField("Shipdate", DateType, false),
      StructField("Billdate", DateType, false),
      StructField("Unitprice", DoubleType, false),
      StructField("numunits", IntegerType, false),
      StructField("TotalPrice", DoubleType, false)
    )
  )

  def main( args : Array[String]) : Unit = {

    val hiveDir = new File("/D:system/hive")


    val ss = SparkSession.builder()
      .appName("Mon application Spark")
      .master("local[4]")
      .config("spark.sql.crossJoin.enabled", "true")
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .config("spark.shuffle.compress", "true")
      .config("spark.sql.warehouse.dir", hiveDir.getAbsolutePath)
     // .enableHiveSupport()
      .getOrCreate()

    val sc = ss.sparkContext.parallelize(Seq("juvenal", "boubdallah", "bruno", "vincent", "taylor"))
    sc.foreach{e => println(e)}

    val rdd2 = ss.sparkContext.parallelize(List(34, 56, 89, 209, 30))
    rdd2.map(e => e * 2).foreach{i => println(i)}

    val rdd3 = ss.sparkContext.textFile("C:\\Users\\Administrateur\\Desktop\\data")
      .flatMap(t => t.split(" "))

    val rd = rdd3.take(3)

    import ss.implicits._
    val df1 = rdd3.toDF()
    df1.show(10)

    val df_orders = ss.read
      .format("com.databricks.spark.csv")
      .schema(schema)
      .option("header", "true")
      .option("delimiter", "\t")
      .option("inferSchema", "false")
      .load("C:\\Users\\Administrateur\\Desktop\\data\\orderline.txt")

   // df_orders.show(15)
    df_orders.printSchema()  // permet de lire le schéma des données.

    df_orders.col("OrderLine")

    val listeCols = df_orders.columns.take(4)

    // selection des colonnes
   val df_prix =  df_orders.select(
      col("Unitprice"),
      col("num".concat("units")),
      col("totalprice"),
      col("OrderLine").cast(StringType)
    //  col("OrderLine").as("Order Line ID").cast(StringType)
   )

    val df_prix2 =  df_orders.select(
      $"Unitprice".as("Unit Price"),
      $"numunits",
     $"totalprice",
      $"OrderLine"
    ).withColumn("numunits", col("numunits").cast(StringType))

    val df_prix3 = df_orders.selectExpr(listeCols:_*)
    df_prix3.printSchema()

    val df_ordersTaxes = df_orders
      .withColumn("numunits", col("numunits").cast(StringType))
      .withColumn("Shipdate", date_format(col("Shipdate"), "yyyy-mm-dd"))
      .withColumn("taxes", col("numunits") * col("totalprice") * lit(0.20))
      .withColumn("processingDate", when(col("Shipdate").isNull, current_date()).otherwise(col("Shipdate")))

    val df_orderfiltre = df_ordersTaxes.filter(col("numunits") === lit(2) )
      .filter(col("numunits") > lit(2) ||  !col("taxes") == lit(0))

    val df_join = df_orders.join(df_ordersTaxes, Seq("orderline"), "inner" )
   //   .join(df_ordersTaxes, df_ordersTaxes("orderlineId") === df_orders("orderLine") && df_ordersTaxes("taxeId") === df_orders("taxeName") , "inner" )

  //  val df_kpi = df_union.groupBy(col("")).sum("totalprices")

  //  val df_hive =  ss.sql("SELECT * FROM hiveTable_Client WHERE city = 'Paris'")

    df_orders.createOrReplaceTempView("table_commandes")

    val df_sql =  ss.sql("SELECT * FROM table_commandes LIMIT 100")
   // df_sql.show(15)

    val df_mysql = ss.read
      .format("jdbc")
      .option("url", "jdbc:mysql://127.0.0.1:3306")
      .option("user", "consultant")
      .option("password", "pwd#86")
      .option("dbtable", "(select state, city, sum(round(numunits * totalprice)) as commandes_totales from jea_db.orders group by state, city) requete")
      .load()

    df_mysql.show()

   /* df_mysql
      .repartition(1)
      .write
      .partitionBy("city")
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .mode(SaveMode.Overwrite)
      .csv("C:\\Users\\Administrateur\\Desktop\\data\\tableMySQL2") */



  }


}
