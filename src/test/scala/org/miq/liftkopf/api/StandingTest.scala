package org.miq.liftkopf.api

import org.scalatest.junit.ShouldMatchersForJUnit
import org.junit.Test

class StandingTest extends ShouldMatchersForJUnit {

  @Test def addToZero() {
    Standing(0, 0, 0, 0) + Standing(1, 1, -1, -1) should be (Standing(1, 1, -1, -1))
  }

  @Test def add() {
    Standing(23, 17, -30, -10) + Standing(-4, -4, 4, 4) should be (Standing(19, 13, -26, -6))
  }
}
