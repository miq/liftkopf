package org.miq.liftkopf.api

import net.liftweb.http
import collection.mutable.ListBuffer
import http._
import net.liftweb.json.JsonAST.JValue
import net.liftweb.common.Full
import org.miq.liftkopf.{LiftkopfRest, RestCreatedResponse}
import net.liftweb.json.JsonAST
import net.liftweb.json.JsonDSL._

class Sheet(val id: Int, val location: String, val group: String, val playerIds: List[Int]) {
  private val games : ListBuffer[Game] = new ListBuffer[Game]

  def getStanding() : Standing = new Standing(playerIds.map(_ => 0))

}


object Sheet extends LiftkopfRest {

  protected val baseUrl = List("api", "sheet")
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
    if (playerIds.size < 4) {
      return ResponseWithReason(BadResponse(), "Not enough user ids given: " + playerIds.size)
    }
    val newSheet = new Sheet(openSheets.size + 1, newSheetRequest.location, newSheetRequest.group, playerIds.map(_.toInt))
    openSheets + newSheet
    val jsonResponse = ("standing" -> newSheet.getStanding.scores)
    RestCreatedResponse(buildLocationUrl(r, newSheet.id), "application/json", compact(JsonAST.render(jsonResponse)))
  }

  def addGameToSheet(sheetId: String, r: Req) : LiftResponse = {
    println("adding game to sheet" + sheetId)
    new OkResponse()
  }
}

case class NewSheet(group: String, location: String, playerIds: List[Int])

case class Standing(scores: List[Int])

