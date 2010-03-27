package org.miq.liftkopf

import net.liftweb.http.{BasicResponse, LiftResponse, S, InMemoryResponse}

class RestCreatedResponse(locationHeader: String, data: Array[Byte]) extends LiftResponse {
  private val response = new InMemoryResponse(
    data,
    ("Content-Length", data.length.toString)
      :: ("Content-Type", "text/plain; charset=utf-8")
      :: ("Location", locationHeader)
      :: S.getHeaders(Nil),
    S.responseCookies,
    201)

  override def toResponse: BasicResponse = response
}
