package org.arangodb




import play.api.libs.json._
import com.ning.http.client._

import scala.concurrent.{Future,ExecutionContext}
import ExecutionContext.Implicits.global

case class ArangoDb(
  host       : String  = "localhost",
  port       : Int     = 8529,
  sslEnabled : Boolean = false
) {

  lazy val protocol = if (sslEnabled) "https" else "http"

  lazy val address = protocol + "://" + host + ":" + port

  def request[A](uri:String):Future[Option[JsValue]] = {

    WS.url(address + uri).get map {
      case response:WSResponse =>
        try {
          // println(response.body)
          
          val json = Json.parse(response.body)


          val errors = (json \ "error").asOpt[Boolean]

          if (errors.isDefined && errors.get) {
            val error = ArangoDb4JException.fromJson(json)
            println(error)
            None
          } else {
            Some(json)
          }
        } catch {
          case e:Exception => {
            println("Could not parse response as JSON.")
            None
          }
        }
      case _ => {
        println("Failed to get any response")
        None
      }
    }
  }
}
