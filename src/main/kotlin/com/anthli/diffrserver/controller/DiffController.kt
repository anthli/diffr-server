/**
 * diffr - just another diff tool
 * Copyright (C) 2020 Anthony Li
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.anthli.diffrserver.controller

import com.anthli.kdiff.Diff
import com.anthli.kdiff.DiffCalculator
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Routing for the diff endpoint.
 */
@RestController
class DiffController {
  @GetMapping("/diff")
  fun getDiff(
    oldString: String?,
    newString: String?
  ): ResponseEntity<List<Diff>> {
    if (oldString == null || newString == null) {
      return ResponseEntity.unprocessableEntity().build()
    }

    val diffCalculator = DiffCalculator(oldString, newString)
    val diffs = diffCalculator.compute()
    return ResponseEntity.ok(diffs.toList())
  }
}