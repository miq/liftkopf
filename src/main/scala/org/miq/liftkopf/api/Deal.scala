package org.miq.liftkopf.api

case class Deal(gameType: String, score: Int, actions: List[Actions]) {
  private val reWinLevels = List(121, 151, 181, 211, 240)
  private val contraWinLevels = List(120, 89, 59, 29, 0)
  private val bidPointMap = Map(90 -> 1, 60 -> 2, 30 -> 3, 1 -> 4)

  def this(score: Int, actions: List[Actions]) = this("normal", score, actions)

  def result : Standing = {
    // TODO calculate the new standings
    val reBid = getStrongestBidFor(Deal.Re)
    val contraBid = getStrongestBidFor(Deal.Contra)
    println("re bid:" + reBid)
    println("contra bid:" + contraBid)
    val winner =  if (score > 120 && (reWonBid_?(reBid)
            || tieButContraBid_?(contraBid)) || contraLostBid_?(contraBid)) Deal.Re else Deal.Contra
    val increment = winner match {
      case Deal.Re => 1
      case Deal.Contra => -1
    }
    var points = if (winner == Deal.Re) {
      Math.max(reWinLevels.filter(score >= _).size, 1)
    } else {
      (contraWinLevels.filter(score <= _).size + 1) * increment
    }
    /* points for bids */
    points += bidPointMap.getOrElse(reBid, 0) * increment
    points += bidPointMap.getOrElse(contraBid, 0) * increment
    /* points for re, if contra party lost her bid */
    if (contraLostBid_?(contraBid)) {
      var p = contraBid;
      while (p < 120) {
        points += increment;
        p += 30;
      }
    }
    /* points for contra, if re party lost her bid */
    if (!reWonBid_?(reBid)) {
      // one point for "against the old ones"
      points += increment;
      var p = 120 - reBid;
      while (p > 0) {
        points += increment;
        p -= 30;
      }
    }
    points = applyBidBonus(points, reBid)
    points = applyBidBonus(points, contraBid)
    println("points:" + points)
    new Standing(actions.map(a => if (a.party == Deal.Re) points else -points))
  }

  def applyBidBonus(points: Int, bid: Int): Int = {
    if (bid > 0) {
      points * 2
    } else {
      points
    }
  }

  private def reWonBid_?(bid: Int) : Boolean = {
    (240 - score) < bid || bid <= 0
  }

  private def contraLostBid_?(bid: Int) : Boolean = {
    score >= bid && bid > 0
  }

  private def tieButContraBid_?(bid: Int) : Boolean = {
    score == 120 && bid > 0
  }

  private def getStrongestBidFor(party: String) : Int = {
    val bid = actions.filter(a => a.party == party && a.bid > 0)
    if (bid.isEmpty) {
      0
    } else {
      bid.sort((a, b) => a.bid < b.bid).head.bid
    }
  }
}

object Deal {
  val Re = "re"
  val Contra = "contra"
}
