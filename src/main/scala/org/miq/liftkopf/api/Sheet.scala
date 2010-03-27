package org.miq.liftkopf.api

import net.liftweb.common.{Full}
import net.liftweb.http
import collection.mutable.ListBuffer
import http._
import org.miq.liftkopf.RestCreatedResponse

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
    new RestCreatedResponse(buildLocationUrl(r, newSheet.id), bytes)
  }

  // TODO: extract into response type or another suitable place
  private def buildLocationUrl(r: Req, sheetId: Int) : String = {
    "http://" + r.request.serverName + ":" + r.request.serverPort + "/" + baseUrl.mkString("/") + "/" + sheetId
  }
}
