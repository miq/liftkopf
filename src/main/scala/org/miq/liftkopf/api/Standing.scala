package org.miq.liftkopf.api

case class Standing(scores: Int*) {

  def +(s: Standing) : Standing = {
    Standing((scores, s.scores).zipped.map(_ + _) : _*)
  }
}
