package org.miq.model

import org.miq.liftkopf.Stats

class PlayerOverviewSummary(
        val name: String,
        val nick: String,
        val totalPoints: Int,
        val gameCount: Int,
        val totalWins: Int,
        val reCount: Int,
        val announcementCount: Int,
        val announcementWins: Int) {

  def winPercentage : Double = Stats.calculatePercentage(totalWins, gameCount)

  def pointsPerGame : Double = totalPoints / gameCount.toDouble

  def rePercentage : Double = Stats.calculatePercentage(reCount, gameCount)

  def announcementPercentage : Double = Stats.calculatePercentage(announcementCount, gameCount)

  def announcementWinPercentage : Double = Stats.calculatePercentage(announcementWins, announcementCount)
}
