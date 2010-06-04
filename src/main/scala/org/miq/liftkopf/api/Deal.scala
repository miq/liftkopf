package org.miq.liftkopf.api

case class Deal(gameType: String, score: Int, actions: List[Actions]) {
  def result : Standing = {
    println("score: " + score)
    println("actions count: " + actions.size)
    // TODO calculate the new standings
    new Standing(actions.map(_ => 0))
  }
}
