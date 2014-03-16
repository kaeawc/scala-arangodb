package org.arangodb

sealed trait Direction {

  case object ASCENDING extends Direction
  case object DESCENDING extends Direction

}

object Direction
extends Direction
with Enum[Direction]
with EnumJson[Direction] {

  val values = List(
    ASCENDING,
    DESCENDING
  )
}
