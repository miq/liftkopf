package org.miq.liftkopf.api

import org.scalatest.junit.AssertionsForJUnit
import org.junit.Test

class DealTest extends AssertionsForJUnit {
  private val noSpecialActions = List(new Actions(Deal.Re), new Actions(Deal.Contra), new Actions(Deal.Contra), new Actions(Deal.Re))

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

}
