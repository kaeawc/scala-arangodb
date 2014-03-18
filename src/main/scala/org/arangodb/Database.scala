package org.arangodb



import play.api.libs.json._

import scala.concurrent.{Future,ExecutionContext}
import ExecutionContext.Implicits.global

case class Database(
  name : String = ""
) {

  def toJson:JsValue = Database.toJson(this)

  def buildPath(relativePath:String):String = {
    if (name.isEmpty) relativePath
    else Database.DB_PREFIX + name + relativePath
  }

  def toMap = Writeable.writeableMap(Map("name" -> name))

  def getVersion(implicit db:ArangoDb):Future[Option[Version]] = {
    db.request(buildPath(Database.VERSION_PATH)) map {
      case Some(json:JsValue) => Version.fromJson(json)
      case _ => None
    }
  }

  def getServerRole(implicit db:ArangoDb):Future[Option[ServerRole]] = {
    db.request(buildPath(Database.ROLE_PATH)) map {
      case Some(json:JsValue) => ServerRole.fromJson(json)
      case _ => None
    }
  }
}

object Database extends ((
  String
) => Database)
with JsonModel[Database] {

  /**
   * Database prefix
   */
  val DB_PREFIX:String       = "/_db/";

  /**
   * Path to document api
   */
  val DOCUMENT_PATH:String   = "/_api/document/";
  
  /**
   * Path to version api
   */
  val VERSION_PATH:String    = "/_api/version";
  
  /**
   * Path to server role api
   */
  val ROLE_PATH:String       = "/_admin/server/role";

  /**
   * Path to collection api
   */
  val COLLECTION_PATH:String = "/_api/collection";
  
  /**
   * Path to database api
   */
  val DATABASE_PATH:String   = "/_api/database";

  /**
   * Path to index api
   */
  val INDEX_PATH:String      = "/_api/index";

  implicit val jsonFormat = Json.format[Database]

  def create(name:String)(implicit arango:ArangoDb):Future[Option[Version]] = {

    val db = new Database(name)

    WS.url(arango.address + "/_db/_system" + DATABASE_PATH).post(db.toMap) map {
      case response:WSResponse => Version.parseJson(response.body)
      case _ => None
    }
  }
}