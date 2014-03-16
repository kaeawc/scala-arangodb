package org.arangodb

case class PropertyFilter(
  var propertyContainers:List[PropertyFilter.Container] = List[PropertyFilter.Container]()
) {

  def has(
    key     : String,
    value   : Object,
    compare : Compare
  ) = {

    val filter = new PropertyFilter()

    filter.propertyContainers = propertyContainers ::: List(new PropertyFilter.Container(
      key,
      value,
      compare
    ))
  }

  def getFilterString():String = {
    if (propertyContainers.isEmpty) {
      ""
    } else {

      val sb = new StringBuffer()
      sb.append(" FILTER ")
            
      var i = 1 
      
      for (container <- propertyContainers) {

        if (i > 1) sb.append("&& ")
        
        sb.append(" x.`")
        sb.append(container.key)
        sb.append("` ")

        import Compare._
    
        val operator = container.compare match {
          case EQUAL => "=="
          case NOT_EQUAL => "!="
          case GREATER_THAN => ">"
          case LESS_THAN => "<"
          case GREATER_THAN_EQUAL => ">="
          case LESS_THAN_EQUAL => "<="
          case _ => throw new Exception("Unknown Comparison")
        }

        sb.append(operator)

        sb.append(" @value" + i + " ")

        i = i + 1
      }
      
      sb.toString()
    }
  }

  def getBindVars() = {

    var i = 1

    propertyContainers.foldLeft(Map[String,Any]()) {
      (map,container) =>
        val key = "value" + i
        i = i + 1
        map + (key -> container.value)
    }
  }
}

object PropertyFilter {
  
  class Container(
    keyString   : String,
    val value   : Object,
    val compare : Compare
  ) {

    val key = keyString.replace("\"", "\\\"")
  }
}