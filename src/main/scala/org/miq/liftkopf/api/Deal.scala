package org.miq.liftkopf.api

case class Deal(gameType: String, score: Int, actions: List[Actions]) {
  private val reWinLevels = List(121, 151, 181, 211, 240)
  private val contraWinLevels = List(120, 89, 59, 29, 0)

  def result : Standing = {
    println("score: " + score)
    println("actions count: " + actions.size)
    // TODO calculate the new standings
    val winner =  if (score > 120) Deal.Re else Deal.Contra
    var increment = if (winner == Deal.Re) 1 else -1
    val points = if (winner == Deal.Re) {
      reWinLevels.filter(score >= _).size
    } else {
      (contraWinLevels.filter(score <= _).size + 1) * increment
    }
    println("points:" + points)
    new Standing(actions.map(a => if (a.party == Deal.Re) points else -points))
  }
}

object Deal {
  val Re = "re"
  val Contra = "contra"
}
