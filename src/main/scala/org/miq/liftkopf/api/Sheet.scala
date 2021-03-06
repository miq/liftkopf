package org.miq.liftkopf.api

import net.liftweb.http._
import collection.mutable.ListBuffer
import net.liftweb.json.JsonAST.JValue
import net.liftweb.common.Full
import org.miq.liftkopf.{LiftkopfRest, RestCreatedResponse}
import net.liftweb.json.JsonDSL._
import net.liftweb.json.{Printer, JsonAST}

class Sheet(val id: Int, val location: String, val group: String, val playerIds: List[Int]) {
  private val deals : ListBuffer[Deal] = new ListBuffer[Deal]
  private var currentStanding = Standing(playerIds.map(_ => 0): _*)

  def addDeal(newDeal: Deal) : Standing = {
    deals + newDeal
    currentStanding = getStanding() + newDeal.result
    return getStanding()
  }

  def getStanding() : Standing = currentStanding
}


object Sheet extends LiftkopfRest {

  protected val baseUrl = List("api", "sheet")
  private val openSheets : ListBuffer[Sheet] = new ListBuffer[Sheet]

  serve {
    case JsonPost(`baseUrl`, json) => () => Full(createNewSheet(json._1, json._2))
    case JsonPost("api" :: "sheet" :: id :: "deal" :: _, json) => Full(addDealTo(id.toInt, json._1, json._2))
    // TODO: implement XML interface sometime later
//    case r @ XmlPost(`baseUrl`, xml) => () => Full(createNewSheet(r))
  }

  private def createNewSheet(json: JValue, r: Req) : LiftResponse = {
    println("json:" + json)
    val newSheetRequest  = json.extract[NewSheet]
    val playerIds = newSheetRequest.playerIds
    if (playerIds.size < 4) {
      return ResponseWithReason(BadResponse(), "Not enough user ids given: " + playerIds.size)
    }
    val newSheet = new Sheet(openSheets.size + 1, newSheetRequest.location, newSheetRequest.group, playerIds.map(_.toInt))
    openSheets + newSheet
    val jsonResponse = ("standing" -> newSheet.getStanding.scores)
    RestCreatedResponse(buildLocationUrl(r, newSheet.id), "application/json", Printer.compact(JsonAST.render(jsonResponse)))
  }

  private def addDealTo(sheetId: Int, json: JValue, r: Req) : LiftResponse = {
    println("adding deal to sheet" + sheetId)
    val deal = json.extract[Deal]
    val jsonResponse = ("standing" -> openSheets(sheetId - 1).addDeal(deal).scores)
    RestCreatedResponse(buildLocationUrl(r, openSheets(sheetId - 1).id), "application/json", Printer.compact(JsonAST.render(jsonResponse)))
  }
}

case class NewSheet(group: String, location: String, playerIds: List[Int])
