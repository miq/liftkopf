package org.miq.liftkopf.api

import net.liftweb.common.{Failure, Full}
import net.liftweb.http._

object Sheet extends AcceptedContentProvider {

  def dispatch: LiftRules.DispatchPF = {
    case r @ Req(List("api", "sheet"), _, PostRequest) => () => Full(createNewSheet(r))
    // Invalid API request - route to our error handler
    case Req(List("api", "sheet"), "", _) => () => Full(new MethodNotAllowedResponse)
  }

  def createNewSheet(r: Req) : LiftResponse = {
    // TODO: create the sheet as an open sheet on the server and return the location
    val bytes = "New open sheet created".getBytes("UTF-8")
    InMemoryResponse(
      bytes,
      (("Content-Length", bytes.length.toString) ::
        ("Content-Type", "text/plain; charset=utf-8") ::
        ("Location", "somewhere...todo") :: S.getHeaders(Nil)),
      S.responseCookies,
      201)
  }
}
