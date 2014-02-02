package com.test.serialization

import com.test.serializer._
import javax.naming.OperationNotSupportedException

case class Person(id: Int, name: String, age: Int, active: Boolean) extends Serializable[Person] {
  
  override def writeObject(out: Serializer): Unit = {
    out.writeInt    (0, "id", id)
    out.writeString (1, "name", name)
    out.writeInt    (2, "age", age)
    out.writeBoolean(3, "active", active)
  }
  
}

object Person extends Deserializable[Person] {
  
  override def readObject(in: Deserializer): Person = {
    Person(
      in.readInt("id"),
      in.readString("name"),
      in.readInt("age"),
      in.readBoolean("active")
    )
  }
  
  override def readObject(in: Deserializer, fromVersion: Int): Person = 
    throw new OperationNotSupportedException
  
}

