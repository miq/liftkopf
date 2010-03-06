package org.miq.liftkopf.api

import net.liftweb.http._
import js.JE._

import net.liftweb.common.Full
import org.miq.model.PlayerOverviewSummary

object PlayerOverview extends AcceptedContentProvider {

  def dispatch: LiftRules.DispatchPF = {
    case r@ Req(List("api", "stats", "summary"), _, GetRequest) =>
      () => Full(getAllPlayerOverviewStats(r))
//    case r @ Req(List("api", "expense", "", PutRequest) => () => addExpense(r)
    // Invalid API request - route to our error handler
//    case Req(List("api", _), "", _) => failure _
  }

  def getAllPlayerOverviewStats(r: Req) : LiftResponse = {
    getResponse(r, getOverviewAsJson, getOverviewAsXml)
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
