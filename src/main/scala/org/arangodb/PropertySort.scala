package org.arangodb

case class PropertySort(
  var propertyContainers:List[PropertySort.Container] = List[PropertySort.Container]()
) {

  def sort(key:String, direction:Direction):PropertySort =
    PropertySort(
      propertyContainers :::
      List(new PropertySort.Container(key, direction))
    )
}

object PropertySort {

  class Container(
    key     : String,
    compare : Direction
  ) {
    def apply(key:String,direction:Direction) = {
      new Container(
        key.replace("\"", "\\\""),
        direction
      )
    }
  }
}