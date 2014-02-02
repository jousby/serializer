package com.test.serialization

import com.fasterxml.jackson.core._
import java.io.OutputStream
import java.io.InputStream
import java.io.IOException
import javax.naming.OperationNotSupportedException
import org.msgpack.MessagePack


class MsgPackSerializer(out: OutputStream) extends Serializer {
  
  val packer = new MessagePack().createPacker(out)
  
  
  override def writeBoolean(fieldNo: Int, propertyName: String, value: Boolean): Unit = 
    packer.write(value)
  
  override def writeString(fieldNo: Int, propertyName: String, value: String): Unit = 
    packer.write(value)
  
  override def writeInt(fieldNo: Int, propertyName: String, value: Int): Unit = 
    packer.write(value)
  
  override def writeFloat(fieldNo: Int, propertyName: String, value: Float): Unit = 
    packer.write(value)  

  override def writeArray(fieldNo: Int, propertyName: String, value: Array[Object]): Unit = 
    throw new OperationNotSupportedException
  
  override def writeMap(fieldNo: Int, propertyName: String, value: Map[String, Object]): Unit = 
    throw new OperationNotSupportedException
  
  override def writeObject(fieldNo: Int, propertyName: String, value: Object): Unit = 
    throw new OperationNotSupportedException
  
  
  override def close(): Unit = packer.close
  
}

class MsgPackDeserializer(in: InputStream) extends Deserializer {
  
  val unpacker = new MessagePack().createUnpacker(in)
  
  
  override def readBoolean(propertyName: String): Boolean = unpacker.readBoolean
  
  override def readString(propertyName: String): String = unpacker.readString
  
  override def readInt(propertyName: String): Int = unpacker.readInt
  
  override def readFloat(propertyName: String): Float = unpacker.readFloat
  
  override def readArray(propertyName: String): Array[Object] = 
    throw new OperationNotSupportedException
    
  override def readMap(propertyName: String): Map[String, Object] = 
    throw new OperationNotSupportedException
    
  override def readObject(propertyName: String): Object = 
    throw new OperationNotSupportedException 
  
    
  override def close(): Unit = unpacker.close
  
}