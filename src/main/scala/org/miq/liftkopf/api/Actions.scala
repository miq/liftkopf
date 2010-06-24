package org.miq.liftkopf.api

case class Actions(
    // TODO: improve types
    party: String,
    bid: Int,
    foxesCaught: Int,
    foxesLost: Int,
    charly: String,
    doubleHeads: Int,
    hasSwines: Boolean,
    hasMarriage: Boolean,
    isPoor: Boolean) {

  def this(party: String, bid: Int) = this(party, bid, 0, 0, "", 0, false, false, false)

  def this(party: String) = this(party, 0)
}
