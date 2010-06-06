package org.miq.liftkopf.api

import org.scalatest.junit.AssertionsForJUnit
import org.junit.Test

class DealTest extends AssertionsForJUnit {

  @Test def calculateSimpleWins() {
    val noSpecialActions = List(new Actions("re"), new Actions("contra"), new Actions("contra"), new Actions("re"))
    assert(new Standing(List(1, -1, -1, 1)) === new Deal("normal", 125, noSpecialActions).result)
    assert(new Standing(List(-2, 2, 2, -2)) === new Deal("normal", 101, noSpecialActions).result)
  }

}
