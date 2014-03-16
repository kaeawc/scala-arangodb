package org.arangodb

import play.api.libs.json._

trait JsonModel[A] {

  implicit val jsonFormat:OFormat[A]

  def parseJson(json:String):Option[A] = {
    try {
      fromJson(Json.parse(json))
    } catch {
      case e:Exception => {
        println("parseJson")
        println(json)
        println(e.getMessage())
        None
      }
    }
  }
  
  def fromJson(json:JsValue):Option[A] = {
    try {
      Some(Json.fromJson(json).get)
    } catch {
      case e:Exception => {
        println("fromJson")
        println(json)
        println(e.getMessage())
        None
      }
    }
  }
  
  def toJson(data:A):JsValue =
    Json.toJson(data)
}