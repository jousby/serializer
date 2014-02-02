package com.test.serialization

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import java.io.ByteArrayOutputStream


class JsonSerializerTest extends FlatSpec with Matchers {
 
  "A JsonSerializer" should "handle a simple object" in {
    val person = Person(1, "James", 78, true)
    val expectedJson = 
      """|{
         |  "id" : 1,
         |  "name" : "James",
         |  "age" : 78,
         |  "active" : true
         |}""".stripMargin
    
    val outStream = new ByteArrayOutputStream    
    
    val jsonSerializer = new JsonSerializer(outStream, true)    
    person.writeObject(jsonSerializer)  
    jsonSerializer.close    
    
    val jsonString = outStream.toString()    
    jsonString shouldEqual (expectedJson)
  }
}