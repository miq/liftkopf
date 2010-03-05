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

  def asXml = {
    <summary>
      <name>{name}</name>
      <nick>{nick}</nick>
      <totalpoints>{totalPoints}</totalpoints>
      <gamecount>{gameCount}</gamecount>
      <totalwins>{totalWins}</totalwins>
      <recount>{reCount}</recount>
      <announcementcount>{announcementCount}</announcementcount>
      <announcementwins>{announcementWins}</announcementwins>
      <winPercentage>{winPercentage}</winPercentage>
      <pointspergame>{pointsPerGame}</pointspergame>
      <rePercentage>{rePercentage}</rePercentage>
      <announcementpercentage>{announcementPercentage}</announcementpercentage>
      <announcementwinpercentage>{announcementWinPercentage}</announcementwinpercentage>
    </summary>
  }
}

object PlayerOverviewSummary {
  
  def findAll : List[PlayerOverviewSummary] = {
    List(new PlayerOverviewSummary("John Doe", "johnny", 350, 1023, 560, 543, 273, 199),
      new PlayerOverviewSummary("Jane Doe", "jane", 100, 847, 450, 441, 199, 143),
      new PlayerOverviewSummary("Agent Smith", "elrond", -84, 666, 301, 311, 123, 57))
  }
}
