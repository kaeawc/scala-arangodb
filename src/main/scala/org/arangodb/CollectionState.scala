package org.arangodb

sealed trait CollectionState {

  case object UNLOADED extends CollectionState
  case object LOADED extends CollectionState
  case object UNLOADING extends CollectionState
  case object DELETED extends CollectionState
  case object LOADING extends CollectionState
  case object UNKNOWN extends CollectionState

}

object CollectionState
extends CollectionState
with Enum[CollectionState]
with EnumJson[CollectionState] {

  val values = List(
    UNLOADED,
    LOADED,
    UNLOADING,
    DELETED,
    LOADING,
    UNKNOWN
  )
}
