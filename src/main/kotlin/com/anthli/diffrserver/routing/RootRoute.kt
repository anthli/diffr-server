/**
 * Copyright (c) 2020 Anthony Li
 *
 * This source code is licensed under the MIT license (see LICENSE for details)
 */

package com.anthli.diffrserver.routing

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get

/**
 * Routing for the root endpoint.
 */
fun Route.root() {
  get("/") {
    call.respondText("Root", ContentType.Text.Html)
  }
}