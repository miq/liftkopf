package org.miq.liftkopf.api

import net.liftweb.http._
import js.JE._

import org.miq.snippet.PlayerOverviewStats
import net.liftweb.common.Full
import org.miq.model.PlayerOverviewSummary

object PlayerOverview {

  def dispatch: LiftRules.DispatchPF = {
    case r@ Req(List("api", "stats", "playeroverview", "list"), _, GetRequest) =>
      () => Full(getAllPlayerOverviewStats(r))
//    case r @ Req(List("api", "expense", "", PutRequest) => () => addExpense(r)
    // Invalid API request - route to our error handler
//    case Req(List("api", _), "", _) => failure _
  }

  def getAllPlayerOverviewStats(r: Req) : LiftResponse = {
    r.headers.find(_._1 == "Accept") match {
      case Some((k, "text/xml")) => getOverviewAsXml
      case Some((k, "application/xml")) => getOverviewAsXml
      case Some((k, "application/json")) => getOverviewAsJson
      case None => getOverviewAsJson
      case _ => new NotAcceptableResponse("No match for accept header")
    }
  }

  private def getOverviewAsJson() : LiftResponse = {
    JsonResponse(JsArray(PlayerOverviewSummary.findAll.map(s => s.asJson): _*))
  }

  private def getOverviewAsXml() : XmlResponse = {
    XmlResponse(
      <statssummary>
        {PlayerOverviewSummary.findAll.map(s => s.asXml)}
      </statssummary>)
  }
}
