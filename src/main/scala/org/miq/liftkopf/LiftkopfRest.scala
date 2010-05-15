package org.miq.liftkopf

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.Req


trait LiftkopfRest extends RestHelper {
  override protected def suplimentalJsonResponse_?(in: Req): Boolean = {
    val accept = in.headers("accept")
    accept.isEmpty || accept.find(_.toLowerCase.indexOf("*/*") >= 0).isDefined
  }
}
