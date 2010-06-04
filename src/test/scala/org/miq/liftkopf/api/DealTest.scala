package org.miq.liftkopf.api

import org.scalatest.junit.AssertionsForJUnit
import org.junit.Test

class DealTest extends AssertionsForJUnit {

  @Test def calculateScoreWithoutExtraPoints() {
    val noSpecialActions = List.range(0, 4) map (_ => new Actions())
    assert(new Standing(List(0, 0, 0, 0)) === new Deal("normal", 125, noSpecialActions).result)
  }

}
