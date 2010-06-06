package org.miq.liftkopf.api

case class Deal(gameType: String, score: Int, actions: List[Actions]) {
  def result : Standing = {
    println("score: " + score)
    println("actions count: " + actions.size)
    // TODO calculate the new standings
    val winner =  if (score > 120) Deal.Re else Deal.Contra
    var (points, increment) = if (winner == Deal.Re) (1, 1) else (-2, -1)
    // additional points for re winning steps
    var tempScore = 150
    while (score > tempScore && winner == Deal.Re) {
      points += increment
      tempScore += 30
    }
    // for shutting out contra
    if (score == 240 && winner == Deal.Re) {
      points += increment
    }
    // additional points for contra winning steps
    var reLosingScore = 90
    while (score < reLosingScore && winner == Deal.Contra) {
      points += increment
      reLosingScore -= 30
    }
    // for shutting out contra
    if (score == 0 && winner == Deal.Contra) {
      points += increment
    }
    new Standing(actions.map(a => if (a.party == Deal.Re) points else -points))
  }
}

object Deal {
  val Re = "re"
  val Contra = "contra"
}
