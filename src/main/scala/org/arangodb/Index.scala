package org.arangodb

import play.api.libs.json._

case class Index(
  id     : String,
  kind   : String = Index.TYPE_SKIPLIST,
  unique : Boolean = false,
  fields : List[String] = List[String]()
) {

  def toJson:JsValue = Index.toJson(this)

  def toMap = {
    Map(
      "kind" -> kind,
      "unique" -> unique,
      "fields" -> fields
    )
  }

  def setId(id:String) =
    Index(
      id,
      kind,
      unique,
      fields
    )

  def setType(kind:String) =
    Index(
      id,
      kind,
      unique,
      fields
    )

  def setUnique(unique:Boolean) =
    Index(
      id,
      kind,
      unique,
      fields
    )

  def setFields(fields:List[String]) =
    Index(
      id,
      kind,
      unique,
      fields
    )

}

object Index extends ((
  String,
  String,
  Boolean,
  List[String]
) => Index)
with JsonModel[Index] {

  val TYPE_SKIPLIST = "skiplist"
  val TYPE_HASH     = "hash"
  val TYPE_FULLTEXT = "fulltext"
  val TYPE_PRIMARY  = "primary"

  implicit val jsonFormat = Json.format[Index]

}