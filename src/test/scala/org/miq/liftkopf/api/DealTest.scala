package org.miq.liftkopf.api

import org.scalatest.junit.AssertionsForJUnit
import org.junit.Test

class DealTest extends AssertionsForJUnit {
  private val reAction = new Actions(Deal.Re)
  private val contraAction = new Actions(Deal.Contra)
  private val noSpecialActions = List(reAction, contraAction, contraAction, reAction)

  @Test def calculateSimpleWins() {
    assert(new Deal("normal", 125, noSpecialActions).result === new Standing(List(1, -1, -1, 1)))
  }

  @Test def calculateScoreTie() {
    assert(new Deal("normal", 120, noSpecialActions).result === new Standing(List(-2, 2, 2, -2)))
  }

  @Test def calculateConsiderableWins() {
    assert(new Deal("normal", 151, noSpecialActions).result === new Standing(List(2, -2, -2, 2)))
    assert(new Deal("normal", 89, noSpecialActions).result === new Standing(List(-3, 3, 3, -3)))
    assert(new Deal("normal", 181, noSpecialActions).result === new Standing(List(3, -3, -3, 3)))
    assert(new Deal("normal", 59, noSpecialActions).result === new Standing(List(-4, 4, 4, -4)))
    assert(new Deal("normal", 211, noSpecialActions).result === new Standing(List(4, -4, -4, 4)))
    assert(new Deal("normal", 29, noSpecialActions).result === new Standing(List(-5, 5, 5, -5)))
    assert(new Deal("normal", 240, noSpecialActions).result === new Standing(List(5, -5, -5, 5)))
    assert(new Deal("normal", 0, noSpecialActions).result === new Standing(List(-6, 6, 6, -6)))
  }

  @Test def calculateAnnouncedWins() {
    val reWinActions = List(reAction, new Actions(Deal.Re, 120), contraAction, contraAction)
    assert(new Deal("normal", 151, reWinActions).result === new Standing(List(4, 4, -4, -4)))
    assert(new Deal("normal", 181, reWinActions).result === new Standing(List(6, 6, -6, -6)))
    val contraWinActions = List(contraAction, reAction, new Actions(Deal.Contra, 120), reAction)
    assert(new Deal("normal", 119, contraWinActions).result === new Standing(List(4, -4, 4, -4)))
    assert(new Deal("normal", 29, contraWinActions).result === new Standing(List(10, -10, 10, -10)))
  }

  @Test def calculateLossBecauseAnnouncement() {
    val reLossActions = List(new Actions(Deal.Re, 90), contraAction, reAction, contraAction)
    assert(new Deal("normal", 150, reLossActions).result === new Standing(List(-8, 8, -8, 8)))
  }

}
