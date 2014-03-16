package org.arangodb

import play.api.libs.json._

case class Version(
  var version : String,
  var server  : String
) {

  def toJson:JsValue = Version.toJson(this)

}

object Version extends ((String,String) => Version)
with JsonModel[Version] {
  
  implicit val jsonFormat = Json.format[Version]

}