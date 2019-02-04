package com.HsunTzu.hdfs


import com.HsunTzu.core.HdfsUntar
import com.HsunTzu.hdfs.HdfsCodec.{bZip2Code, defaultCode, deflateCode, gzipCode, lz4Code, snappyCode}
//import com.typesafe.scalalogging.Logger
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.io.compress._
import org.apache.log4j.Logger
import org.slf4j.LoggerFactory

class HdfsCodec {

}

object HdfsCodec{

  val logger: Logger = Logger.getLogger(HdfsCodec.getClass)
//  private [this] val logger =Logger(LoggerFactory.getLogger(classOf[HdfsCodec]))

  val deflateCode:String="DEFLATE"
  val snappyCode:String="SNAPPY"
  val gzipCode:String= "GZIP"
  val lz4Code:String="LZO"
  val bZip2Code:String="BZIP2"
  val defaultCode:String="DEFAULT"

  /**
    * 判断 压缩文件的 后缀名 与 压缩codec 是否一致
    * @param file
    * @param codec
    * @return
    */
  def boolCompresfileExtension(file:String,codec:CompressionCodec):Boolean={
    val extension=codec.getDefaultExtension
    if(file.endsWith(extension)){
      return  true
    }else{
      return  false
    }
  }
  /**
    * 压缩格式 数字信号 到压缩格式 的映射
    *
    * @param signal
    * @return
    */
  def codecSignalToCodec(signal: String): String = signal.trim match {
    case "0" => deflateCode
    case "1" => snappyCode
    case "2" => gzipCode
    case "3" => lz4Code
    case "4" => bZip2Code
    case "5" => defaultCode
    case _ =>  deflateCode
  }

  /**
    * 根据 codec 字符串 创建 codec格式
    * @param codecStr
    * @return
    */
  def codecStrToCodec(codecStr:String):CompressionCodec=codecStr.trim match {
    case "SNAPPY" => new SnappyCodec()
    case "GZIP"   => new GzipCodec()
    case "LZO"    => new Lz4Codec()
    case  "BZIP2" => new BZip2Codec()
    case "DEFLATE" => new DeflateCodec()
    case "DEFAULT" => new DefaultCodec()
    case _ =>  new DeflateCodec()
  }

  /**
    * 根据 压缩格式 设置 conf
    * @param codecStr
    * @param codecTrait
    * @param conf
    */
  def codecTosetConf(codecStr:String,codecTrait:CompressionCodec,conf:Configuration):Unit=codecStr.trim match {
    case "SNAPPY" => codecTrait.asInstanceOf[SnappyCodec].setConf(conf)
    case "GZIP"   =>  codecTrait.asInstanceOf[GzipCodec].setConf(conf)
    case "LZO"    => codecTrait.asInstanceOf[Lz4Codec].setConf(conf)
    case "BZIP2"  => codecTrait.asInstanceOf[BZip2Codec].setConf(conf)
    case "DEFLATE" => codecTrait.asInstanceOf[DeflateCodec].setConf(conf)
    case "DEFAULT" => codecTrait.asInstanceOf[DefaultCodec].setConf(conf)
    case  _         => codecTrait.asInstanceOf[DeflateCodec].setConf(conf)
  }

}