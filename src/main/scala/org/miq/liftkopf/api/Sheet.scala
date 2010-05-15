package org.miq.liftkopf.api

import net.liftweb.http
import collection.mutable.ListBuffer
import http._
import net.liftweb.json.JsonAST.JValue
import net.liftweb.common.Full
import org.miq.liftkopf.{LiftkopfRest, RestCreatedResponse}

class Sheet(val id: Int, val playerIds: List[Int]) {
  private val games : ListBuffer[Game] = new ListBuffer[Game]

}


object Sheet extends LiftkopfRest {

  private val baseUrl = List("api", "sheet")
  private val openSheets : ListBuffer[Sheet] = new ListBuffer[Sheet]

  serve {
    case JsonPost(`baseUrl`, json) => () => Full(createNewSheet(json._1, json._2))
    // TODO: implement XML interface sometime later
//    case r @ XmlPost(`baseUrl`, xml) => () => Full(createNewSheet(r))
  }

  def createNewSheet(json: JValue, r: Req) : LiftResponse = {
     // TODO: create the sheet as an open sheet on the server and return the location
    println("json:" + json)
    val newSheetRequest  = json.extract[NewSheet]
    val playerIds = newSheetRequest.playerIds
    println("playerIds:" + playerIds)
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

case class NewSheet(playerIds: List[Int])

