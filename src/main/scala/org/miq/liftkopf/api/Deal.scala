package org.miq.liftkopf.api

case class Deal(gameType: String, score: Int, actions: List[Actions]) {
  def result : Standing = {
    println("score: " + score)
    println("actions count: " + actions.size)
    // TODO calculate the new standings
    val winner =  if (score > 120) "re" else "contra"
    val points = if (winner == "re") 1 else -2
    new Standing(actions.map(a => if (a.party == "re") points else -points))
  }
}
