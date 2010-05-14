package org.miq.liftkopf.api

import net.liftweb.http._
import js.JE._

import net.liftweb.common.Full
import org.miq.model.PlayerOverviewSummary
import rest.RestHelper

object PlayerOverview extends RestHelper {

  serve {
    case XmlGet("api" :: "stats" :: "summary" :: _, _) => Full(getOverviewAsXml)
    case JsonGet("api" :: "stats" :: "summary" :: _, _) => Full(getOverviewAsJson)
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
