package org.miq.liftkopf.api

case class Deal(gameType: String, score: Int, actions: List[Actions]) {
  private val reWinLevels = List(121, 151, 181, 211, 240)
  private val contraWinLevels = List(120, 89, 59, 29, 0)
  private val bidPointMap = Map(90 -> 1, 60 -> 2, 30 -> 3, 1 -> 4)
  private val charlyPointMap = Map(Actions.CharlyCaught -> 1, Actions.CharlyPoint -> 1, Actions.CharlyPointCaught -> 2)
  private val soloTypes = List(Deal.JackSoloType, Deal.QueenSoloType, Deal.ColorSoloType, Deal.QuietSoloType)

  def this(score: Int, actions: List[Actions]) = this("normal", score, actions)

  def result : Standing = {
    // TODO calculate the new standings
    val reBid = getStrongestBidFor(Deal.Re)
    val contraBid = getStrongestBidFor(Deal.Contra)
    val winner =  if (score > 120 && (reWonBid_?(reBid)
            || tieButContraBid_?(contraBid)) || contraLostBid_?(contraBid)) Deal.Re else Deal.Contra
    val increment = winner match {
      case Deal.Re => 1
      case Deal.Contra => -1
    }
    var points = if (winner == Deal.Re) {
      Math.max(reWinLevels.filter(score >= _).size, 1)
    } else {
      (contraWinLevels.filter(score <= _).size + (if (soloGame_? || poverty_?) 0 else 1)) * increment
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
    points += computeExtraPoints(actions.filter(_.party == Deal.Re), actions.filter(_.party == Deal.Contra))
    new Standing(actions.map(a => calculatePointsResultFor(a, points)) : _*)
  }

  private def calculatePointsResultFor(a: Actions, points: Int) : Int = {
    if (a.party == Deal.Re) {
      if (soloGame_?) points * 3 else points
    } else {
      -points
    }
  }

  private def soloGame_? : Boolean = soloTypes.contains(gameType)

  private def poverty_? : Boolean = actions.exists(_.isPoor)

  private def applyBidBonus(points: Int, bid: Int): Int = {
    if (bid > 0) {
      points * 2
    } else {
      points
    }
  }

  private def computeExtraPoints(reActions: List[Actions], contraActions: List[Actions]) : Int = {
    var extraPoints = 0
    reActions.foreach(a => extraPoints += a.foxesCaught)
    reActions.foreach(a => extraPoints += a.doubleHeads)
    reActions.foreach(a => extraPoints += charlyPointMap.getOrElse(a.charly, 0))
    contraActions.foreach(a => extraPoints -= a.foxesCaught)
    contraActions.foreach(a => extraPoints -= a.doubleHeads)
    contraActions.foreach(a => extraPoints -= charlyPointMap.getOrElse(a.charly, 0))
    return extraPoints;
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
  val JackSoloType = "jackSolo"
  val QueenSoloType = "queenSolo"
  val QuietSoloType = "quietSolo"
  val ColorSoloType = "colorSolo"
}
