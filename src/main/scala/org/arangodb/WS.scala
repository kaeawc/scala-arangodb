package org.arangodb

import java.io.ByteArrayOutputStream
import com.ning.http.client._
import com.ning.http.util.AsyncHttpProviderUtils

import scala.concurrent._
import ExecutionContext.Implicits.global

object WS {

  def url(location:String) = WS(location)

}

case class WS(url:String) {

  val client = new AsyncHttpClient()

  private def prepare(method:String):WSRequest = {

    val builder = new RequestBuilder(method)
      .setUrl(url)
      
    WSRequest(client,method,builder)
  }

  private def prepare(method:String,data:Array[Byte]):WSRequest = {

    val builder = new RequestBuilder(method)
      .setUrl(url)
      .setBody(data)

    WSRequest(client,method,builder)
  }
  
  def get:Future[WSResponse] =
    prepare("GET").execute
  
  def post:Future[WSResponse] =
    prepare("POST").execute

  def post(data:Array[Byte]):Future[WSResponse] =
    prepare("POST",data).execute

}

case class WSRequest(
  client: AsyncHttpClient,
  method: String,
  builder: RequestBuilder
) {

  def execute:Future[WSResponse] = {
    import com.ning.http.client.AsyncCompletionHandler
    var result = Promise[WSResponse]()
    client.executeRequest(builder.build(), new AsyncCompletionHandler[Response]() {
      override def onCompleted(response: Response) = {
        result.success(WSResponse(response))
        response
      }

      override def onThrowable(t: Throwable) = {
        result.failure(t)
      }
    })
    result.future
  }
}

case class WSResponse(response: Response) {

  import scala.xml._
  import play.api.libs.json._


  /**
   * @return The underlying response object.
   */
  def underlying[T] = response.asInstanceOf[T]

  /**
   * The response status code.
   */
  def status: Int = response.getStatusCode

  /**
   * The response status message.
   */
  def statusText: String = response.getStatusText

  /**
   * Get a response header.
   */
  def header(key: String): Option[String] = Option(response.getHeader(key))

  /**
   * The response body as String.
   */
  lazy val body: String = {
    // RFC-2616#3.7.1 states that any text/* mime type should default to ISO-8859-1 charset if not
    // explicitly set, while Plays default encoding is UTF-8.  So, use UTF-8 if charset is not explicitly
    // set and content type is not text/*, otherwise default to ISO-8859-1
    val contentType = Option(response.getContentType).getOrElse("application/octet-stream")
    val charset = Option(AsyncHttpProviderUtils.parseCharset(contentType)).getOrElse {
      if (contentType.startsWith("text/"))
        AsyncHttpProviderUtils.DEFAULT_CHARSET
      else
        "utf-8"
    }
    response.getResponseBody(charset)
  }

  /**
   * The response body as Json.
   */
  lazy val json: JsValue = Json.parse(response.getResponseBodyAsBytes)

}

// case class WSHandler() extends AsyncHandler[String]() {

//   import AsyncHandler.STATE

//   private var bytes = new ByteArrayOutputStream()

//   override def onStatusReceived(status:HttpResponseStatus):STATE = {
//     // val statusCode = status.getStatusCode()
//     // // The Status have been read
//     // // If you don't want to read the headers,body or stop processing the response
//     // if (statusCode >= 500)
//     //   STATE.ABORT
//     // else
//     STATE.CONTINUE
//   }

  
//   override def onHeadersReceived(response:HttpResponseHeaders):STATE = {
//     val headers = response.getHeaders()
//      // The headers have been read
//      // If you don't want to read the body, or stop processing the response
//     STATE.CONTINUE
//   }

  
//   override def onBodyPartReceived(bodyPart:HttpResponseBodyPart):STATE = {
//     bytes.write(bodyPart.getBodyPartBytes())
//     STATE.CONTINUE
//   }

  
//   override def onCompleted():String = {
//     // Will be invoked once the response has been fully read or a ResponseComplete exception
//     // has been thrown.
//     // NOTE: should probably use Content-Encoding from headers
//     bytes.toString("UTF-8")
//   }

  
//   override def onThrowable(t:Throwable) {
//     throw t
//   }

// }