// package org.arangodb

// import org.joda.time.DateTime
// import org.specs2.mutable._
// import play.api.libs.json._

// class VersionSpec extends Specification {

//   "Version.version" should {

//     "should be mutable" in {

//       val v = Version("2.0","localhost")
//       v.version = "2.1"
//       v.version mustEqual "2.1"

//     }
//   }

//   "Version.server" should {

//     "should be mutable" in {

//       val v = Version("2.0","localhost")
//       v.server = "dev.local"
//       v.server mustEqual "dev.local"

//     }
//   }

//   "Version.toJson" should {

//     "serialize a Version object" in {

//       val json = Version.toJson(Version("2.0","localhost"))

//       json mustEqual Json.obj(
//         "version" -> "2.0",
//         "server" -> "localhost"
//       )

//     }
//   }

//   "Version.fromJson" should {

//     "deserialize JSON to a Version object" in {

//       val json = Json.obj(
//         "version" -> "2.0",
//         "server" -> "localhost"
//       )

//       Version.fromJson(json) match {
//         case Some(version:Version) => {
//           version.version mustEqual "2.0"
//           version.server  mustEqual "localhost"
//           success
//         }
//         case _ => failure("Could not create a Version from valid JSON.")
//       }
//     }
//   }

//   "Version.parseJson" should {

//     "deserialize JSON to a Version object" in {

//       val json = """{
//         "version" : "2.0",
//         "server"  : "localhost"
//       }"""

//       Version.parseJson(json) match {
//         case Some(version:Version) => {
//           version.version mustEqual "2.0"
//           version.server  mustEqual "localhost"
//           success
//         }
//         case _ => failure("Could not create a Version from valid JSON.")
//       }
//     }
//   }
// }
