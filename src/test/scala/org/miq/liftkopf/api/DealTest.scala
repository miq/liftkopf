package org.miq.liftkopf.api

import org.scalatest.junit.AssertionsForJUnit
import org.junit.Test

class DealTest extends AssertionsForJUnit {
  private val reAction = new Actions(Deal.Re)
  private val contraAction = new Actions(Deal.Contra)
  private val noSpecialActions = List(reAction, contraAction, contraAction, reAction)

  @Test def calculateSimpleWins() {
    assert(new Standing(List(1, -1, -1, 1)) === new Deal("normal", 125, noSpecialActions).result)
  }

  @Test def calculateScoreTie() {
    assert(new Standing(List(-2, 2, 2, -2)) === new Deal("normal", 120, noSpecialActions).result)
  }

  @Test def calculateConsiderableWins() {
    assert(new Standing(List(2, -2, -2, 2)) === new Deal("normal", 151, noSpecialActions).result)
    assert(new Standing(List(-3, 3, 3, -3)) === new Deal("normal", 89, noSpecialActions).result)
    assert(new Standing(List(3, -3, -3, 3)) === new Deal("normal", 181, noSpecialActions).result)
    assert(new Standing(List(-4, 4, 4, -4)) === new Deal("normal", 59, noSpecialActions).result)
    assert(new Standing(List(4, -4, -4, 4)) === new Deal("normal", 211, noSpecialActions).result)
    assert(new Standing(List(-5, 5, 5, -5)) === new Deal("normal", 29, noSpecialActions).result)
    assert(new Standing(List(5, -5, -5, 5)) === new Deal("normal", 240, noSpecialActions).result)
    assert(new Standing(List(-6, 6, 6, -6)) === new Deal("normal", 0, noSpecialActions).result)
  }

  @Test def calculateAnnouncedWins() {
    val reWinActions = List(reAction, new Actions(Deal.Re, 120), contraAction, contraAction)
    assert(new Standing(List(4, 4, -4, -4)) === new Deal("normal", 151, reWinActions).result)
    assert(new Standing(List(6, 6, -6, -6)) === new Deal("normal", 181, reWinActions).result)
    val contraWinActions = List(contraAction, reAction, new Actions(Deal.Contra, 120), reAction)
    assert(new Standing(List(4, -4, 4, -4)) === new Deal("normal", 119, contraWinActions).result)
    assert(new Standing(List(10, -10, 10, -10)) === new Deal("normal", 29, contraWinActions).result)
  }

}
