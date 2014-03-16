package org.arangodb

sealed trait Compare {

  case object EQUAL extends Compare
  case object NOT_EQUAL extends Compare
  case object GREATER_THAN extends Compare
  case object GREATER_THAN_EQUAL extends Compare
  case object LESS_THAN extends Compare
  case object LESS_THAN_EQUAL extends Compare

}

object Compare
extends Compare
with Enum[Compare]
with EnumJson[Compare] {

  val values = List(
    EQUAL,
    NOT_EQUAL,
    GREATER_THAN,
    GREATER_THAN_EQUAL,
    LESS_THAN,
    LESS_THAN_EQUAL
  )
}
