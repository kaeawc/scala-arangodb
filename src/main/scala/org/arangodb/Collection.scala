package org.arangodb

import play.api.libs.json._

case class Collection(
  id             : String,
  name           : String,
  state          : CollectionState      = CollectionState.UNKNOWN,
  kind           : CollectionType       = CollectionType.UNKNOWN,
  waitForSync    : Option[Boolean]      = Some(false), // (optional, default: false): If true then the data is synchronised to disk before returning from a create or update of an document.
  journalSize    : Option[Int]          = Some(0),     // (optional, default is a configuration parameter): The maximal size of a journal or datafile. Note that this also limits the maximal size of a single object. Must be at least 1MB.
  isSystem       : Option[Boolean]      = Some(false), // (optional, default is false): If true, create a system collection. In this case collection-name should start with an underscore. End users should normally create non-system collections only. API implementors may be required to create system collections in very special occasions, but normally a regular collection will do.
  isVolatile     : Option[Boolean]      = Some(false), // (optional, default is false): If true then the collection data is kept in-memory only and not made persistent. Unloading the collection will cause the collection data to be discarded. Stopping or re-starting the server will also cause full loss of data in the collection. Setting this option will make the resulting collection be slightly faster than regular collections because ArangoDB does not enforce any synchronisation to disk and does not calculate any CRC checksums for datafiles (as there are no datafiles).
  numberOfShards : Option[Int]          = Some(1),     // (optional, default is 1): The number of shards of the collection. The option is not used in a single-server setup.
  shardKeys      : Option[List[String]] = Some(Nil)    // (optional): The shard key attributes of the collection. If empty, this defaults to the "_key" attribute in a cluster setup. The option is not used in a single-server setup.
) {

  def toJson:JsValue = Collection.toJson(this)

  def toMap:Map[String,Any] = {

    var result = Map[String,Any]()

    result = result + ("name" -> name)

    if (CollectionType.EDGE == kind)
      result = result + ("type" -> 3)
    else
      result = result + ("type" -> 2) 
    
    if (null != waitForSync)
      result = result + ("waitForSync" -> waitForSync)

    if (null != journalSize)
      result = result + ("journalSize" -> journalSize)

    if (null != isSystem)
      result = result + ("isSystem" -> isSystem)

    if (null != isVolatile)
      result = result + ("isVolatile" -> isVolatile)

    if (null != numberOfShards)
      result = result + ("numberOfShards" -> numberOfShards)

    if (null != shardKeys)
      result = result + ("shardKeys" -> shardKeys)


    result
  }

  def setWaitForSync(waitForSync:Boolean):Collection =
    Collection(
      id,
      name,
      state,
      kind,
      Some(waitForSync),
      journalSize,
      isSystem,
      isVolatile,
      numberOfShards,
      shardKeys
    )

  def setJournalSize(journalSize:Integer):Collection =
    Collection(
      id,
      name,
      state,
      kind,
      waitForSync,
      Some(journalSize),
      isSystem,
      isVolatile,
      numberOfShards,
      shardKeys
    )

  def setIsSystem(isSystem:Boolean):Collection =
    Collection(
      id,
      name,
      state,
      kind,
      waitForSync,
      journalSize,
      Some(isSystem),
      isVolatile,
      numberOfShards,
      shardKeys
    )

  def setIsVolatile(isVolatile:Boolean):Collection =
    Collection(
      id,
      name,
      state,
      kind,
      waitForSync,
      journalSize,
      isSystem,
      Some(isVolatile),
      numberOfShards,
      shardKeys
    )

  def setNumberOfShards(numberOfShards:Integer):Collection =
    Collection(
      id,
      name,
      state,
      kind,
      waitForSync,
      journalSize,
      isSystem,
      isVolatile,
      Some(numberOfShards),
      shardKeys
    )

  def setShardKeys(shardKeys:List[String]):Collection =
    Collection(
      id,
      name,
      state,
      kind,
      waitForSync,
      journalSize,
      isSystem,
      isVolatile,
      numberOfShards,
      Some(shardKeys)
    )

  def setId(id:String):Collection =
    Collection(
      id,
      name,
      state,
      kind,
      waitForSync,
      journalSize,
      isSystem,
      isVolatile,
      numberOfShards,
      shardKeys
    )

  def setName(name:String):Collection =
    Collection(
      id,
      name,
      state,
      kind,
      waitForSync,
      journalSize,
      isSystem,
      isVolatile,
      numberOfShards,
      shardKeys
    )

  def setState(state:CollectionState):Collection =
    Collection(
      id,
      name,
      state,
      kind,
      waitForSync,
      journalSize,
      isSystem,
      isVolatile,
      numberOfShards,
      shardKeys
    )

  def setType(kind:CollectionType):Collection =
    Collection(
      id,
      name,
      state,
      kind,
      waitForSync,
      journalSize,
      isSystem,
      isVolatile,
      numberOfShards,
      shardKeys
    )

}

object Collection extends ((
  String,
  String,
  CollectionState,
  CollectionType,
  Option[Boolean],
  Option[Int],
  Option[Boolean],
  Option[Boolean],
  Option[Int],
  Option[List[String]]
) => Collection)
with JsonModel[Collection] {
  
  implicit val jsonFormat = Json.format[Collection]

}