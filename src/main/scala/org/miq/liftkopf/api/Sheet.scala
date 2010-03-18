package org.miq.liftkopf.api

import net.liftweb.common.{Full}
import net.liftweb.http._
import collection.mutable.ListBuffer

class Sheet(val id: Int) {

}


object Sheet extends AcceptedContentProvider {

  private val baseUrl = List("api", "sheet")
  private val openSheets : ListBuffer[Sheet] = new ListBuffer[Sheet]

  def dispatch: LiftRules.DispatchPF = {
    case r @ Req(`baseUrl`, _, PostRequest) => () => Full(createNewSheet(r))
    // Invalid API request - route to our error handler
    case Req(`baseUrl`, "", _) => () => Full(new MethodNotAllowedResponse)
  }

  def createNewSheet(r: Req) : LiftResponse = {
    // TODO: create the sheet as an open sheet on the server and return the location
    val newSheet = new Sheet(openSheets.size + 1)
    openSheets + newSheet
    val bytes = "New open sheet created".getBytes("UTF-8")
    // TODO: extract response into own type
    InMemoryResponse(
      bytes,
      (("Content-Length", bytes.length.toString) ::
        ("Content-Type", "text/plain; charset=utf-8") ::
        ("Location", buildLocationUrl(r, newSheet.id)) :: S.getHeaders(Nil)),
      S.responseCookies,
      201)
  }

  private def buildLocationUrl(r: Req, sheetId: Int) : String = {
    "http://" + r.request.serverName + ":" + r.request.serverPort + "/" + baseUrl.mkString("/") + "/" + sheetId
  }
}
