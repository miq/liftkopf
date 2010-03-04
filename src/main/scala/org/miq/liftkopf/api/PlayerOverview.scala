package org.miq.liftkopf.api

import net.liftweb.http._
import js.JE._

import org.miq.snippet.PlayerOverviewStats
import net.liftweb.common.Full

object PlayerOverview {

  def dispatch: LiftRules.DispatchPF = {
    case Req(List("api", "stats", "playeroverview", "list"), _, GetRequest) =>
      () => Full(getAllPlayerOverviewStats)
//    case r @ Req(List("api", "expense", "", PutRequest) => () => addExpense(r)
    // Invalid API request - route to our error handler
//    case Req(List("api", _), "", _) => failure _
  }

  def getAllPlayerOverviewStats() : LiftResponse = {
    JsonResponse(JsArray(new PlayerOverviewStats().getAllStats.map(s => s.asJson): _*))
  }
}
