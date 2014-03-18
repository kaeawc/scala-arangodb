package org.arangodb

import org.joda.time.DateTime
import org.specs2.mutable._
import play.api.libs.json._

class CollectionSpec extends Specification {

  "Collection" should {

    "have default values" in {

      val collection = Collection(
        "some-identifier",
        "some-name"
      )

      collection.state          mustEqual CollectionState.UNKNOWN
      collection.kind           mustEqual CollectionType.UNKNOWN
      collection.waitForSync    must beSome(false)
      collection.journalSize    must beSome(0)
      collection.isSystem       must beSome(false)
      collection.isVolatile     must beSome(false)
      collection.numberOfShards must beSome(1)
      collection.shardKeys      mustEqual Some(Nil)

    }
  }
}
