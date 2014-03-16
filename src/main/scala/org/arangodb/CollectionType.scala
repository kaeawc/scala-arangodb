package org.arangodb

sealed trait CollectionType {

  case object EDGE extends CollectionType
  case object DOCUMENT extends CollectionType
  case object UNKNOWN extends CollectionType

}

object CollectionType
extends CollectionType
with Enum[CollectionType]
with EnumJson[CollectionType] {

  val values = List(
    EDGE,
    DOCUMENT,
    UNKNOWN
  )
}
