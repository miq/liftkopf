package org.miq.model

import org.miq.liftkopf.Stats
import net.liftweb.http.js.JE._
import net.liftweb.http.js.JsObj

class PlayerOverviewSummary(
        val name: String,
        val nick: String,
        val totalPoints: Int,
        val gameCount: Int,
        val totalWins: Int,
        val reCount: Int,
        val announcementCount: Int,
        val announcementWins: Int) {

  def winPercentage : Double = Stats.calculatePercentage(totalWins, gameCount)

  def pointsPerGame : Double = totalPoints / gameCount.toDouble

  def rePercentage : Double = Stats.calculatePercentage(reCount, gameCount)

  def announcementPercentage : Double = Stats.calculatePercentage(announcementCount, gameCount)

  def announcementWinPercentage : Double = Stats.calculatePercentage(announcementWins, announcementCount)

  def asJson : JsObj = {
    JsObj("name" -> name,
      "nick" -> nick,
      "totalpoints" -> totalPoints,
      "gamecount" -> gameCount,
      "totalwins" -> totalWins,
      "recount" -> reCount,
      "announcementcount" -> announcementCount,
      "announcementwins" -> announcementWins,
      "winPercentage" -> winPercentage,
      "pointspergame" -> pointsPerGame,
      "rePercentage" -> rePercentage,
      "announcementpercentage" -> announcementPercentage,
      "announcementwinpercentage" -> announcementWinPercentage)
  }
}
