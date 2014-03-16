package org.arangodb

import play.api.libs.json._

case class ServerRole(
  role  : Role,
  error : Boolean,
  code  : Int
) {

  def toJson:JsValue = ServerRole.toJson(this)

}

object ServerRole extends ((
  Role,
  Boolean,
  Int
) => ServerRole)
with JsonModel[ServerRole] {
  
  implicit val jsonFormat = Json.format[ServerRole]

  def fromString(name:String):Role = {
    name match {
      case "COORDINATOR" => Role.COORDINATOR
      case "DBSERVER"    => Role.DBSERVER
      case "UNKNOWN"     => Role.UNKNOWN
      case "UNDEFINED"   => Role.UNDEFINED
    }
  }
}