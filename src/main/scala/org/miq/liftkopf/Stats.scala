package org.miq.liftkopf

import java.text.DecimalFormat

object Stats {

  def calculatePercentage(numerator: Int, denominator: Int) : Double = {
    numerator / denominator.toDouble * 100
  }

  def formatPercentage(p: Double) : String = new DecimalFormat("0.000").format(p)
}
