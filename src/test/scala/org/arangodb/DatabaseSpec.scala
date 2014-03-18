package org.arangodb

import org.joda.time.DateTime
import org.specs2.mutable._
import play.api.libs.json._

import scala.concurrent.{Future,ExecutionContext}
import ExecutionContext.Implicits.global

class DatabaseSpec extends Specification {

  "Database" should {

    "have a name" in {

      Database("default").name mustEqual "default"

    }
  }

  "Database.buildPath" should {

    "use name when one is given" in {

      val path = Database("default").buildPath("/awesome")

      path mustEqual Database.DB_PREFIX + "default/awesome"

    }

    "use just the relative path when there is no name" in {

      val path = Database().buildPath("/awesome")

      path mustEqual "/awesome"

    }
  }

  "Database.toMap" should {

    "make a simple Map from a Database object" in {

      Database("default").toMap mustEqual Map("name" -> "default")

    }
  }
  
  implicit val db = ArangoDb()

  "Database.getVersion" should {

    "return server's version" in {

      waitFor {
        Database("_system").getVersion.map {
          case Some(version:Version) => {
            version.server mustEqual "arango"
            success
          }
          case _ => failure("The version wasn't returned or the JSON was malformed.")
        }
      }
    }
  }

  "Database.getServerRole" should {

    "return server's role" in {

      waitFor {
        Database("_system").getServerRole.map {
          case Some(server:ServerRole) => {
            server.role mustEqual Role.UNDEFINED
            server.error mustEqual false
            server.code mustEqual 200
            success
          }
          case _ => failure("The version wasn't returned or the JSON was malformed.")
        }
      }
    }
  }
}
