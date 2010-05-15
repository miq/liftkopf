package org.miq.liftkopf.api

import net.liftweb.http._
import js.JE._

import net.liftweb.common.Full
import org.miq.model.PlayerOverviewSummary
import org.miq.liftkopf.LiftkopfRest

// TODO: change to serveJx pattern with Convertable
object PlayerOverview extends LiftkopfRest {

  protected val baseUrl = List("api", "stats", "summary")

  serve {
    case XmlGet(`baseUrl`, _) => Full(getOverviewAsXml)
    case JsonGet(`baseUrl`, _) => Full(getOverviewAsJson)
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
