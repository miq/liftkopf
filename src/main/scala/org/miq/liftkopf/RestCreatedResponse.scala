package org.miq.liftkopf

import net.liftweb.http._

class RestCreatedResponse(locationHeader: String, contentType: String, data: Array[Byte]) extends LiftResponse with HeaderDefaults {
  override val toResponse: BasicResponse = new InMemoryResponse(
    data,
    ("Content-Length", data.length.toString)
      :: ("Content-Type", contentType + "; charset=utf-8")
      :: ("Location", locationHeader)
      :: headers,
    cookies,
    201)
}

object RestCreatedResponse {
  def apply(locationHeader: String, contentType: String, content: String) : RestCreatedResponse = {
    new RestCreatedResponse(locationHeader, contentType, content.getBytes("UTF-8"))
  }

  def apply(locationHeader: String, content: String) : RestCreatedResponse = {
    apply(locationHeader, "text/plain", content)
  }
}
