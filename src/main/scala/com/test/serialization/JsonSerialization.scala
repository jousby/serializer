package com.test.serialization

import com.fasterxml.jackson.core._
import java.io.OutputStream
import java.io.InputStream
import java.io.IOException
import javax.naming.OperationNotSupportedException


class JsonSerializer(out: OutputStream, prettyPrint: Boolean = false) extends Serializer {
  
  val jsonFactory = new JsonFactory
  val jsonOut = 
    if (prettyPrint) 
      jsonFactory.createGenerator(out, JsonEncoding.UTF8).useDefaultPrettyPrinter()
    else 
      jsonFactory.createGenerator(out, JsonEncoding.UTF8)       
  jsonOut.writeStartObject();         
  
  
  override def writeBoolean(fieldNo: Int, propertyName: String, value: Boolean): Unit = 
    jsonOut.writeBooleanField(propertyName, value)
  
  override def writeString(fieldNo: Int, propertyName: String, value: String): Unit = 
    jsonOut.writeStringField(propertyName, value)
  
  override def writeInt(fieldNo: Int, propertyName: String, value: Int): Unit = 
    jsonOut.writeNumberField(propertyName, value)
  
  override def writeFloat(fieldNo: Int, propertyName: String, value: Float): Unit = 
    jsonOut.writeNumberField(propertyName, value)   

  override def writeArray(fieldNo: Int, propertyName: String, value: Array[Object]): Unit = 
    throw new OperationNotSupportedException
  
  override def writeMap(fieldNo: Int, propertyName: String, value: Map[String, Object]): Unit = 
    throw new OperationNotSupportedException
  
  override def writeObject(fieldNo: Int, propertyName: String, value: Object): Unit = 
    throw new OperationNotSupportedException
  
  
  override def close(): Unit = {    
    jsonOut.writeEndObject();  
    jsonOut.close  
  }
}

class JsonDeserializer(in: InputStream) extends Deserializer {
  
  val jsonFactory = new JsonFactory
  val jsonIn = jsonFactory.createParser(in)  
  jsonIn.nextToken // Hack TODO confirm if a startObject / endObject method is best solution
  
  override def readBoolean(propertyName: String): Boolean = {
    propertyNameCheck(propertyName)
    jsonIn.getBooleanValue()
  }
  
  override def readString(propertyName: String): String = {
    propertyNameCheck(propertyName)
    jsonIn.getText()
  }
  
  override def readInt(propertyName: String): Int = {
    propertyNameCheck(propertyName)
    jsonIn.getIntValue()    
  }
  
  override def readFloat(propertyName: String): Float = {
    propertyNameCheck(propertyName)
    jsonIn.getFloatValue()  
  }
  
  override def readArray(propertyName: String): Array[Object] = 
    throw new OperationNotSupportedException
    
  override def readMap(propertyName: String): Map[String, Object] = 
    throw new OperationNotSupportedException
    
  override def readObject(propertyName: String): Object = 
    throw new OperationNotSupportedException 
  
    
  override def close(): Unit = { 
    jsonIn.close  
  }
  
  private def propertyNameCheck(expectedPropertyName: String): Unit = {   
    // advance token pointer to point at the property name 
    jsonIn.nextToken
    val currentPropertyName = jsonIn.getCurrentName()
    
    if (currentPropertyName != expectedPropertyName)
      throw new IOException(s"Expected property $expectedPropertyName but found $currentPropertyName")
    
    // after reading the property name move the token pointer to the field containing the value
    jsonIn.nextToken
  }
}