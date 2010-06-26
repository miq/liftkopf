package org.miq.liftkopf.api

case class Standing(scores: Int*) {

  def +(s: Standing) : Standing = {
    Standing((for (myScore <- scores; newScore <- s.scores) yield myScore + newScore) : _*)
  }
}
