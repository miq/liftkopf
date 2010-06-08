package org.miq.liftkopf.api

// TODO: rename announcement to bid (like in bridge)
case class Deal(gameType: String, score: Int, actions: List[Actions]) {
  private val reWinLevels = List(121, 151, 181, 211, 240)
  private val contraWinLevels = List(120, 89, 59, 29, 0)

  def result : Standing = {
    // TODO calculate the new standings
    val reAnnouncement = getStrongestAnnouncementFor(Deal.Re)
    val contraAnnouncement = getStrongestAnnouncementFor(Deal.Contra)
    println("reAnnouncement:" + reAnnouncement)
    println("contraAnnouncement:" + contraAnnouncement)
    val winner =  if (score > 120 && (reWonAnnouncement_?(reAnnouncement)
            || tieButContraAnnouncement_?(contraAnnouncement) || contraLostAnnouncement_?(contraAnnouncement))) Deal.Re else Deal.Contra
    val increment = winner match {
      case Deal.Re => 1
      case Deal.Contra => -1
    }
    var points = if (winner == Deal.Re) {
      reWinLevels.filter(score >= _).size
    } else {
      (contraWinLevels.filter(score <= _).size + 1) * increment
    }
    var k = 90;
    /* points for bids */
    while (k >= 0) {
      if ((reAnnouncement <= k) && (reAnnouncement > 0)) {
        points += increment;
      }
      if ((contraAnnouncement <= k) && (contraAnnouncement > 0)) {
        points += increment;
      }
      k -= 30;
    }
    /* points for contra, if re party lost her bid */
    if (!reWonAnnouncement_?(reAnnouncement)) {
      // one point for "against the old ones"
      points += increment;
      var p = 120 - reAnnouncement;
      while (p > 0) {
        points += increment;
        p -= 30;
      }
    }
    if (reAnnouncement > 0) {
      points *= 2
    }
    if (contraAnnouncement > 0) {
      points *= 2
    }
    println("points:" + points)
    new Standing(actions.map(a => if (a.party == Deal.Re) points else -points))
  }

  private def reWonAnnouncement_?(announcement: Int) : Boolean = {
    (240 - score) < announcement || announcement <= 0
  }

  private def contraLostAnnouncement_?(announcement: Int) : Boolean = {
    score >= announcement && announcement > 0
  }

  private def tieButContraAnnouncement_?(announcement: Int) : Boolean = {
    score == 120 && announcement > 0
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
