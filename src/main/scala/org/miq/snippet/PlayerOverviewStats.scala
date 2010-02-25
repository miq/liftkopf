package org.miq.snippet

import scala.xml.NodeSeq
import net.liftweb.util.Helpers._
import org.miq.model.PlayerOverviewSummary
import java.text.DecimalFormat


class PlayerOverviewStats {
  def list(html: NodeSeq) : NodeSeq = {
    getAllStats.flatMap(item =>
            bind("player", html,
              "name" -> item.name,
              "nick" -> item.nick,
              "total_points" -> item.totalPoints,
              "game_count" -> item.gameCount,
              "total_wins" -> item.totalWins,
              "win_percentage" -> new DecimalFormat("0.000").format(item.winPercentage),
              "points_per_game" -> new DecimalFormat("0.000").format(item.pointsPerGame),
              "re_percentage" -> new DecimalFormat("0.000").format(item.rePercentage),
              "announcement_percentage" -> new DecimalFormat("0.000").format(item.announcementPercentage),
              "announcement_win_percentage" -> new DecimalFormat("0.000").format(item.announcementWinPercentage)
            )
    )
  }

  private def getAllStats = {
    List(new PlayerOverviewSummary("John Doe", "johnny", 350, 1023, 560, 543, 273, 199),
      new PlayerOverviewSummary("Jane Doe", "jane", 100, 847, 450, 441, 199, 143),
      new PlayerOverviewSummary("Agent Smith", "elrond", -84, 666, 301, 311, 123, 57))
  }
}
