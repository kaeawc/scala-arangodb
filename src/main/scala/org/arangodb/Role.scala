package org.arangodb

sealed trait Role {

  case object COORDINATOR extends Role
  case object DBSERVER extends Role
  case object UNKNOWN extends Role
  case object UNDEFINED extends Role

}

object Role
extends Role
with Enum[Role]
with EnumJson[Role] {

  val values = List(
    COORDINATOR,
    DBSERVER,
    UNKNOWN,
    UNDEFINED
  )
}
