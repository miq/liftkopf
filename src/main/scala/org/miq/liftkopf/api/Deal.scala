package org.miq.liftkopf.api

case class Deal(gameType: String, score: Int, actions: List[Actions]) {
  def result : Standing = {
    println("score: " + score)
    println("actions count: " + actions.size)
    // TODO calculate the new standings
    val winner =  if (score > 120) "re" else "contra"
    var (points, increment) = if (winner == "re") (1, 1) else (-2, -1)
    // additional points for re winning steps
    var tempScore = 150
    while (score > tempScore && winner == "re") {
      points += increment
      tempScore += 30
    }
    // for shutting out contra
    if (score == 240 && winner == "re") {
      points += increment
    }
    // additional points for contra winning steps
    var reLosingScore = 90
    while (score < reLosingScore && winner == "contra") {
      points += increment
      reLosingScore -= 30
    }
    // for shutting out contra
    if (score == 0 && winner == "contra") {
      points += increment
    }
    new Standing(actions.map(a => if (a.party == "re") points else -points))
  }
}
