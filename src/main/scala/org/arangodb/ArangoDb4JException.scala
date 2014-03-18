package org.arangodb

import play.api.libs.json._

case class ArangoDb4JException(
  private val message:String,
  private val statusCode:Int = 0,
  private val errorNum:Int = 0
) extends Exception(message) {
  
  def getErrorNumber():Int = {
    this.errorNum
  }

  def getStatusCode():Int = {
    this.statusCode
  }
}

object ArangoDb4JException extends ((
  String,
  Int,
  Int
) => ArangoDb4JException)
with JsonModel[ArangoDb4JException] {
  
  implicit val jsonFormat = Json.format[ArangoDb4JException]

  private val serialVersionUID:Long = -5301373222706274918L;

}