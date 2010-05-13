package org.miq.liftkopf

import net.liftweb.http._

class RestCreatedResponse(locationHeader: String, data: Array[Byte]) extends LiftResponse with HeaderDefaults {
  override val toResponse: BasicResponse = new InMemoryResponse(
    data,
    ("Content-Length", data.length.toString)
      :: ("Content-Type", "text/plain; charset=utf-8")
      :: ("Location", locationHeader)
      :: headers,
    cookies,
    201)
}

object RestCreatedResponse {
  def apply(locationHeader: String, content: String) = {
    new RestCreatedResponse(locationHeader, content.getBytes("UTF-8"))
  }
}
