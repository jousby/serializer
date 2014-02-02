package com.test.serialization

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import java.io.ByteArrayOutputStream
import java.io.ByteArrayInputStream


class MsgPackSerializationTest extends FlatSpec with Matchers {
 
  "To and from MsgPack Serialisation" should "handle a simple object" in {
    val person = Person(1, "James", 78, true)
    
    // serialize
    val outStream = new ByteArrayOutputStream    
    
    val msgPackSerializer = new MsgPackSerializer(outStream)    
    person.writeObject(msgPackSerializer)  
    msgPackSerializer.close    
    
    // deserialize
    val inStream = new ByteArrayInputStream(outStream.toByteArray())
    
    val msgPackDeserializer = new MsgPackDeserializer(inStream)      
    val deserializedPerson = Person.readObject(msgPackDeserializer)
    msgPackDeserializer.close
     
    deserializedPerson shouldEqual (person)
  }
}