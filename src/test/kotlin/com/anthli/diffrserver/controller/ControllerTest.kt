/**
 * Copyright (c) 2020 Anthony Li
 *
 * This source code is licensed under the MIT license (see LICENSE for details)
 */

package com.anthli.diffrserver.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.util.MultiValueMap

/**
 * A base class containing generalized testing functionality for controllers.
 */
@AutoConfigureMockMvc
abstract class ControllerTest(private val endpoint: String) {
  @Autowired
  private lateinit var mvc: MockMvc

  /**
   * Tests the endpoint with the given method, media type, and parameters.
   * A comparison is performed on the expected status and content coming from
   * the endpoint.
   *
   * @param method
   *   The HTTP method to request with.
   * @param mediaType
   *   The media type to request with.
   * @param params
   *   The parameters to send to the endpoint.
   * @param expectedStatus
   *   The expected status of the endpoint.
   * @param expectedContent
   *   The expected content received from the endpoint.
   */
  fun testEndpoint(
    method: HttpMethod,
    mediaType: MediaType = MediaType.ALL,
    params: MultiValueMap<String, String>? = null,
    expectedStatus: ResultMatcher,
    expectedContent: String? = null
  ) {
    val builder = MockMvcRequestBuilders
      .request(method, endpoint)
      .accept(mediaType)
    if (params != null) {
      builder.params(params)
    }

    val result = mvc.perform(builder).andExpect(expectedStatus)
    if (expectedContent != null) {
      result.andExpect(content().string(expectedContent))
    }
  }
}