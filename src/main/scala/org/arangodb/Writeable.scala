package org.arangodb

import play.api.libs.json._

case class Writeable[-A](
  transform   : (A => Array[Byte]),
  contentType : Option[String] = None
) {

  def map[B](f: B => A): Writeable[B] = Writeable(b => transform(f(b)), contentType)

}

/**
 * Helper utilities for `Writeable`.
 */
object Writeable extends DefaultWriteables

/**
 * Default Writeable.
 */
trait DefaultWriteables {

  /**
   * `Writeable` for `NodeSeq` values - literal Scala XML.
   */
  implicit def writeableOf_NodeSeq[C <: scala.xml.NodeSeq](implicit codec: Codec): Writeable[C] = {
    Writeable(xml => codec.encode(xml.toString))
  }

  /**
   * `Writeable` for `NodeBuffer` values - literal Scala XML.
   */
  implicit def writeableOf_NodeBuffer(implicit codec: Codec): Writeable[scala.xml.NodeBuffer] = {
    Writeable(xml => codec.encode(xml.toString))
  }

  /**
   * `Writeable` for `urlEncodedForm` values
   */
  implicit def writeableOf_urlEncodedForm(implicit codec: Codec): Writeable[Map[String, Seq[String]]] = {
    import java.net.URLEncoder
    Writeable(formData =>
      codec.encode(formData.map(item => item._2.map(c => item._1 + "=" + URLEncoder.encode(c, "UTF-8"))).flatten.mkString("&"))
    )
  }

  /**
   * `Writeable` for `urlEncodedForm` values
   */
  implicit def writeableMap(implicit codec: Codec): Writeable[Map[String, String]] = {
    import java.net.URLEncoder
    Writeable(formData =>
      codec.encode(formData.map(item => item._1 + "=" + URLEncoder.encode(item._2, "UTF-8")).flatten.mkString("&"))
    )
  }

  /**
   * `Writeable` for `JsValue` values - Json
   */
  implicit def writeableOf_JsValue(implicit codec: Codec): Writeable[JsValue] = {
    Writeable(jsval => codec.encode(jsval.toString))
  }

  /**
   * Straightforward `Writeable` for String values.
   */
  implicit def wString(implicit codec: Codec): Writeable[String] = Writeable[String](str => codec.encode(str))

  /**
   * Straightforward `Writeable` for Array[Byte] values.
   */
  implicit val wBytes: Writeable[Array[Byte]] = Writeable(identity)

}

