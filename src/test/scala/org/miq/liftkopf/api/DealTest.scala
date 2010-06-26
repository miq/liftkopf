package org.miq.liftkopf.api

import org.junit.Test
import org.scalatest.junit.ShouldMatchersForJUnit

class DealTest extends ShouldMatchersForJUnit {
  private val reAction = new Actions(Deal.Re)
  private val contraAction = new Actions(Deal.Contra)
  private val noSpecialActions = List(reAction, contraAction, contraAction, reAction)

  @Test def calculateSimpleWin() {
    new Deal(125, noSpecialActions).result should be (new Standing(List(1, -1, -1, 1)))
  }

  @Test def calculateSimpleLoss() {
    new Deal(119, noSpecialActions).result should be (new Standing(List(-2, 2, 2, -2)))
  }

  @Test def calculateScoreTie() {
    new Deal(120, noSpecialActions).result should be (new Standing(List(-2, 2, 2, -2)))
  }

  @Test def calculateConsiderableWins() {
    new Deal(151, noSpecialActions).result should be (new Standing(List(2, -2, -2, 2)))
    new Deal(89, noSpecialActions).result should be (new Standing(List(-3, 3, 3, -3)))
    new Deal(181, noSpecialActions).result should be (new Standing(List(3, -3, -3, 3)))
    new Deal(59, noSpecialActions).result should be (new Standing(List(-4, 4, 4, -4)))
    new Deal(211, noSpecialActions).result should be (new Standing(List(4, -4, -4, 4)))
    new Deal(29, noSpecialActions).result should be (new Standing(List(-5, 5, 5, -5)))
    new Deal(240, noSpecialActions).result should be (new Standing(List(5, -5, -5, 5)))
    new Deal(0, noSpecialActions).result should be (new Standing(List(-6, 6, 6, -6)))
  }

  @Test def calculateAnnouncedWins() {
    val reWinActions = List(reAction, new Actions(Deal.Re, 120), contraAction, contraAction)
    new Deal(151, reWinActions).result should be (new Standing(List(4, 4, -4, -4)))
    new Deal(181, reWinActions).result should be (new Standing(List(6, 6, -6, -6)))
    val contraWinActions = List(contraAction, reAction, new Actions(Deal.Contra, 120), reAction)
    new Deal(119, contraWinActions).result should be (new Standing(List(4, -4, 4, -4)))
    new Deal(29, contraWinActions).result should be (new Standing(List(10, -10, 10, -10)))
  }

  @Test def calculateShutdownAnnouncedWin() {
    val reWinActions = List(reAction, new Actions(Deal.Re, 1), contraAction, contraAction)
    new Deal(240, reWinActions).result should be (new Standing(List(18, 18, -18, -18)))
  }

  @Test def calculateLossBecauseOfBid() {
    val reLossActions = List(new Actions(Deal.Re, 90), contraAction, reAction, contraAction)
    new Deal(150, reLossActions).result should be (new Standing(List(-8, 8, -8, 8)))
    val contraLossActions = List(reAction, reAction, contraAction, new Actions(Deal.Contra, 90))
    new Deal(90, contraLossActions).result should be (new Standing(List(6, 6, -6, -6)))
  }

  @Test def calculateSoloGames() {
    val soloActions = List(contraAction, contraAction, reAction, contraAction)
    new Deal(Deal.JackSoloType, 130, soloActions).result should be (new Standing(List(-1, -1, 3, -1)))
    new Deal(Deal.QuietSoloType, 200, soloActions).result should be (new Standing(List(-3, -3, 9, -3)))
    new Deal(Deal.QueenSoloType, 119, soloActions).result should be (new Standing(List(1, 1, -3, 1)))
    new Deal(Deal.ColorSoloType, 88, soloActions).result should be (new Standing(List(2, 2, -6, 2)))
  }

  @Test def calculateSoloBids() {
    val soloBid = List(contraAction, new Actions(Deal.Re, 90), contraAction, contraAction)
    new Deal(Deal.QueenSoloType, 154, soloBid).result should be (new Standing(List(-6, 18, -6, -6)))
    new Deal(Deal.QueenSoloType, 147, soloBid).result should be (new Standing(List(6, -18, 6, 6)))
  }

  @Test def calculateFoxExtras() {
    val foxForRe = List(contraAction,
      new Actions(Deal.Re, 120, 1, 0, "", 0, false, false, false),
      reAction,
      new Actions(Deal.Contra, 0, 0, 1, "", 0, false, false, false))
    new Deal(121, foxForRe).result should be (new Standing(List(-3, 3, 3, -3)))
    val foxForEach = List(
      new Actions(Deal.Contra, 0, 1, 0, "", 0, false, false, false),
      new Actions(Deal.Re, 120, 1, 1, "", 0, false, false, false),
      reAction,
      new Actions(Deal.Contra, 0, 0, 1, "", 0, false, false, false))
    new Deal(121, foxForEach).result should be (new Standing(List(-2, 2, 2, -2)))
    val twoFoxesForContra = List(
      contraAction,
      new Actions(Deal.Re, 120, 0, 1, "", 0, false, false, false),
      new Actions(Deal.Re, 0, 0, 1, "", 0, false, false, false),
      new Actions(Deal.Contra, 0, 2, 0, "", 0, false, false, false))
    new Deal(121, twoFoxesForContra).result should be (new Standing(List(0, 0, 0, 0)))
  }

  @Test def calculateCharlyExtras() {
    val charlyPoint = List(contraAction,
      new Actions(Deal.Re, 0, 0, 0, Actions.CharlyPoint, 0, false, false, false),
      reAction,
      contraAction)
    new Deal(128, charlyPoint).result should be (new Standing(List(-2, 2, 2, -2)))
    val charlyCaught = List(contraAction,
      new Actions(Deal.Re, 0, 0, 0, Actions.CharlyCaught, 0, false, false, false),
      reAction,
      new Actions(Deal.Contra, 0, 0, 0, Actions.CharlyLost, 0, false, false, false))
    new Deal(128, charlyCaught).result should be (new Standing(List(-2, 2, 2, -2)))
    val charlyLost = List(
      new Actions(Deal.Contra, 0, 0, 0, Actions.CharlyCaught, 0, false, false, false),
      new Actions(Deal.Re, 0, 0, 0, Actions.CharlyLost, 0, false, false, false),
      reAction,
      contraAction)
    new Deal(128, charlyLost).result should be (new Standing(List(0, 0, 0, 0)))
    val charlyPointCaught = List(contraAction,
      new Actions(Deal.Re, 0, 0, 0, Actions.CharlyPointCaught, 0, false, false, false),
      reAction,
      new Actions(Deal.Contra, 0, 0, 0, Actions.CharlyLost, 0, false, false, false))
    new Deal(128, charlyPointCaught).result should be (new Standing(List(-3, 3, 3, -3)))
  }

  @Test def calculateDoubleHeadExtras() {
    val oneForRe = List(contraAction,
      new Actions(Deal.Re, 0, 0, 0, "", 1, false, false, false),
      reAction,
      contraAction)
    new Deal(128, oneForRe).result should be (new Standing(List(-2, 2, 2, -2)))
    val twoForRe = List(contraAction,
      new Actions(Deal.Re, 0, 0, 0, "", 2, false, false, false),
      reAction,
      contraAction)
    new Deal(128, twoForRe).result should be (new Standing(List(-3, 3, 3, -3)))
    val oneForEach = List(contraAction,
      new Actions(Deal.Re, 0, 0, 0, "", 1, false, false, false),
      reAction,
      new Actions(Deal.Contra, 0, 0, 0, "", 1, false, false, false))
    new Deal(128, oneForEach).result should be (new Standing(List(-1, 1, 1, -1)))
  }

  @Test def calculatePovertyLoss() {
    val poverty = List(contraAction, contraAction, new Actions(Deal.Re, 0, 0, 0, "", 0, false, false, true), reAction)
    new Deal(112, poverty).result should be (new Standing(List(1, 1, -1, -1)))
  }
}
