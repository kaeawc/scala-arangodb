package org.arangodb

import play.api.libs.json._

case class Cursor(
  id             : String,
  name           : String
) {

  def toJson:JsValue = Cursor.toJson(this)

  def toMap:Map[String,Any] = {

    Map[String,Any]() + ("name" -> name)

  }

}

object Cursor extends ((
  String,
  String
) => Cursor)
with JsonModel[Cursor] {

  val CURSOR_PATH = "/_api/cursor"
  
  implicit val jsonFormat = Json.format[Cursor]

}