package org.miq.liftkopf

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.Req


trait LiftkopfRest extends RestHelper {
  protected val baseUrl: List[String]

  override protected def suplimentalJsonResponse_?(in: Req): Boolean = {
    val accept = in.headers("accept")
    accept.isEmpty || accept.find(_.toLowerCase.indexOf("*/*") >= 0).isDefined
  }

  protected def buildLocationUrl(r: Req, sheetId: Int) : String = {
    "http://" + r.request.serverName + ":" + r.request.serverPort + "/" + baseUrl.mkString("/") + "/" + sheetId
  }
}
