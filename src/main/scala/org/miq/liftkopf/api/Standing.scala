package org.miq.liftkopf.api

case class Standing(scores: Int*) {

  def +(s: Standing) : Standing = {
    Standing((for ((old, add) <- List(scores : _*) zip List(s.scores : _*)) yield old + add) : _*)
  }
}
