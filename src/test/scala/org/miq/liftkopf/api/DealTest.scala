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

}
