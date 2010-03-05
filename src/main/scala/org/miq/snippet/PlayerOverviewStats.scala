package org.miq.snippet

import scala.xml.NodeSeq
import net.liftweb.util.Helpers._
import org.miq.model.PlayerOverviewSummary
import org.miq.liftkopf.Stats


class PlayerOverviewStats {
  def list(html: NodeSeq) : NodeSeq = {
    PlayerOverviewSummary.findAll.flatMap(item =>
      bind("player", html,
        "name" -> item.name,
        "nick" -> item.nick,
        "total_points" -> item.totalPoints,
        "game_count" -> item.gameCount,
        "total_wins" -> item.totalWins,
        "win_percentage" -> Stats.formatPercentage(item.winPercentage),
        "points_per_game" -> Stats.formatPercentage(item.pointsPerGame),
        "re_percentage" -> Stats.formatPercentage(item.rePercentage),
        "announcement_percentage" -> Stats.formatPercentage(item.announcementPercentage),
        "announcement_win_percentage" -> Stats.formatPercentage(item.announcementWinPercentage)
      )
    )
  }

}
