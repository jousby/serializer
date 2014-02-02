package com.test.serialization

import org.scalatest.Matchers
import org.scalatest.FlatSpec
import java.io.ByteArrayInputStream


class JsonDeserializerTest extends FlatSpec with Matchers {

  "A JsonDeserializer" should "handle a simple object" in {
    val json = 
      """|{
         |  "id" : 1,
         |  "name" : "James",
         |  "age" : 78,
         |  "active" : true
         |}""".stripMargin
    val expectedPerson = Person(1, "James", 78, true)
    
    val inStream = new ByteArrayInputStream(json.getBytes())
    
    val jsonDeserializer = new JsonDeserializer(inStream)    
    val person = Person.readObject(jsonDeserializer)
    jsonDeserializer.close
        
    person shouldEqual (expectedPerson)
  }
}