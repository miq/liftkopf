package org.miq.liftkopf

import net.liftweb.http.Req


class RichRequest(r: Req) {

  def getParameters(name: String) : List[String] = {
    try {
      r.params(name)
    } catch {
      case e: NoSuchElementException => Nil
    }
  }
}

object RichRequest {

  implicit def reqToRichRequest(r: Req) : RichRequest = new RichRequest(r)
}
