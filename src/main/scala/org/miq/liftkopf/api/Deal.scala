package org.miq.liftkopf.api

case class Deal(gameType: String, score: Int, actions: List[Actions]) {
  private val reWinLevels = List(121, 151, 181, 211, 240)
  private val contraWinLevels = List(120, 89, 59, 29, 0)

  def result : Standing = {
    // TODO calculate the new standings
    val reAnnouncement = getStrongestAnnouncementFor(Deal.Re)
    val contraAnnouncement = getStrongestAnnouncementFor(Deal.Contra)
    println("reAnnouncement:" + reAnnouncement)
    println("contraAnnouncement:" + contraAnnouncement)
    val winner =  if (score > 120) Deal.Re else Deal.Contra
    val increment = if (winner == Deal.Re) 1 else -1
    var points = if (winner == Deal.Re) {
      reWinLevels.filter(score >= _).size
    } else {
      (contraWinLevels.filter(score <= _).size + 1) * increment
    }
    if (reAnnouncement > 0 && winner == Deal.Re) {
      points *= 2
    }
    if (contraAnnouncement > 0 && winner == Deal.Contra) {
      points *= 2
    }
    println("points:" + points)
    new Standing(actions.map(a => if (a.party == Deal.Re) points else -points))
  }

  private def getStrongestAnnouncementFor(party: String) : Int = {
    val announcements = actions.filter(a => a.party == party && a.announcement > 0)
    if (announcements.isEmpty) {
      0
    } else {
      announcements.sort((a, b) => a.announcement < b.announcement).head.announcement
    }
  }
}

object Deal {
  val Re = "re"
  val Contra = "contra"
}
