package org.miq.liftkopf.api

import net.liftweb.http.{NotAcceptableResponse, XmlResponse, LiftResponse, Req}

// TODO: either dump this or think of extending RestHelper to customize handling of REST requests
trait AcceptedContentProvider {

  protected def getResponse(r: Req, jsonProvider: () => LiftResponse, xmlProvider: () => XmlResponse) : LiftResponse = {
    r.headers.find(_._1 == "Accept") match {
      case Some((k, "text/xml")) => xmlProvider()
      case Some((k, "application/xml")) => xmlProvider()
      case Some((k, "application/json")) => jsonProvider()
      case Some((k, "*/*")) => jsonProvider()
      case None => jsonProvider()
      case _ => new NotAcceptableResponse("No match for accept header")
    }
  }
}
