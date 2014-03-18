package org.arangodb

import play.api.libs.json._

case class ArangoDbDocument(
  id  : String, // document id
  key : String, // document key
  rev : String  // document revision
) {

  def toJson:JsValue = ArangoDbDocument.toJson(this)

}

object ArangoDbDocument extends ((
  String,
  String,
  String
) => ArangoDbDocument)
with JsonModel[ArangoDbDocument] {

  private val serialVersionUID:Long = 7047000745465609916L

  implicit val jsonFormat = Json.format[ArangoDbDocument]

}