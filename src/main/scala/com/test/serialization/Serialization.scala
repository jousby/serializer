package com.test.serialization

import java.io.OutputStream

  
sealed trait SerializableFormat
case object JsonFormat extends SerializableFormat
case object MsgPackFormat extends SerializableFormat
case object LegacyXmlFormat extends SerializableFormat


trait Serializable[A] {
  /**
   * Writes this object to a stream using the current fields and field orders.
   */
  def writeObject(out: Serializer): Unit
}


trait Deserializable[A] {
  
  /**
   * Loads this object expecting the input stream wrapped by the deserializer to 
   * be compatible with the current version of this object.
   */
  def readObject(in: Deserializer): A
  
  /**
   * Attempts to load this object from a stream created from a previous version of 
   * this object. ie. Assuming the current object is at version '2' and there is 
   * a serialized binary created using version '1' you could attempt to load it with
   * <code> x.readObject(in, 1) </code> assuming the magic sauce of version converters
   * has been added. 
   * 
   * TODO Insert some sort of pluggable step version converters magic.
   */
  def readObject(in: Deserializer, fromVersion: Int): A
  
}


trait Serializer {
  def writeBoolean(fieldNo: Int, propertyName: String, value: Boolean): Unit
  def writeString(fieldNo: Int, propertyName: String, value: String): Unit
  def writeInt(fieldNo: Int, propertyName: String, value: Int): Unit
  def writeFloat(fieldNo: Int, propertyName: String, value: Float): Unit
  def writeArray(fieldNo: Int, propertyName: String, value: Array[Object]): Unit
  def writeMap(fieldNo: Int, propertyName: String, value: Map[String, Object]): Unit
  def writeObject(fieldNo: Int, propertyName: String, value: Object): Unit
  def close(): Unit
}


trait Deserializer {
  def readBoolean(propertyName: String): Boolean
  def readString(propertyName: String): String
  def readInt(propertyName: String): Int
  def readFloat(propertyName: String): Float
  def readArray(propertyName: String): Array[Object]
  def readMap(propertyName: String): Map[String, Object]
  def readObject(propertyName: String): Object
  def close(): Unit
}



