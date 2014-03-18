package org.arangodb

import org.joda.time.DateTime
import org.specs2.mutable._
import play.api.libs.json._

class IndexSpec extends Specification {

  "Index" should {

    "have default settings" in {

      val index = Index("some-identifier")

      index.id            mustEqual "some-identifier"
      index.kind          mustEqual Index.TYPE_SKIPLIST
      index.unique        mustEqual false
      index.fields.length mustEqual 0

    }

    "be immutable" in {

      val index = Index("some-identifier")

      val newIndex = index.setType(Index.TYPE_SKIPLIST)

      newIndex.hashCode() mustEqual index.hashCode()
      newIndex mustEqual index

    }
  }
}
