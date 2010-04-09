package org.miq.liftkopf.api

import net.liftweb.common.{Full}
import net.liftweb.http
import collection.mutable.ListBuffer
import http._
import org.miq.liftkopf.RestCreatedResponse
import org.miq.liftkopf.RichRequest._

class Sheet(val id: Int, val playerIds: List[Int]) {
  private val games : ListBuffer[Game] = new ListBuffer[Game]

}


object Sheet extends AcceptedContentProvider {

  private val baseUrl = List("api", "sheet")
  private val openSheets : ListBuffer[Sheet] = new ListBuffer[Sheet]

  def dispatch: LiftRules.DispatchPF = {
    case r @ Req(`baseUrl`, _, PostRequest) => () => Full(createNewSheet(r))
    case r @ Req("api" :: "sheet" :: sheetId :: "game" :: Nil, _, PostRequest) => () => Full(addGameToSheet(sheetId, r))
    // Invalid API request - route to our error handler
    case Req(`baseUrl`, "", _) => () => Full(new MethodNotAllowedResponse)
  }

  def createNewSheet(r: Req) : LiftResponse = {
     // TODO: create the sheet as an open sheet on the server and return the location
    println("Location: " + r.location)
    val playerIds  = r.getParameters("playerId")
    if (playerIds.size < 4) {
      return ResponseWithReason(BadResponse(), "Not enough user ids given: " + playerIds.size)
    }
    val newSheet = new Sheet(openSheets.size + 1, playerIds.map(_.toInt))
    openSheets + newSheet
    RestCreatedResponse(buildLocationUrl(r, newSheet.id), "New open sheet created")
  }

  def addGameToSheet(sheetId: String, r: Req) : LiftResponse = {
    println("adding game to sheet" + sheetId)
    new OkResponse()
  }

  // TODO: extract into response type or another suitable place
  private def buildLocationUrl(r: Req, sheetId: Int) : String = {
    "http://" + r.request.serverName + ":" + r.request.serverPort + "/" + baseUrl.mkString("/") + "/" + sheetId
  }
}

