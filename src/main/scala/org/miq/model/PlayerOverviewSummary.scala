package org.miq.model


class PlayerOverviewSummary(
        val name: String,
        val nick: String,
        val totalPoints: Int,
        val gameCount: Int,
        val totalWins: Int,
        val reCount: Int,
        val announcementCount: Int,
        val announcementWins: Int) {

  def winPercentage : Double = totalWins / gameCount.toDouble * 100

  def pointsPerGame : Double = totalPoints / gameCount.toDouble

  def rePercentage : Double = reCount / gameCount.toDouble * 100

  def announcementPercentage : Double = announcementCount / gameCount.toDouble * 100

  def announcementWinPercentage : Double = announcementWins / announcementCount.toDouble * 100
}
