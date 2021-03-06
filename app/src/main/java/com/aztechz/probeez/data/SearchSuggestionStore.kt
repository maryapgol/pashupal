/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aztechz.probeez.data

import com.aztechz.probeez.R

/**
 * A static data store of [SearchSuggestion]s.
 */
object SearchSuggestionStore {

  val YESTERDAY_SUGGESTIONS = listOf(
    SearchSuggestion(
      R.drawable.ic_schedule,
      "481 Van Brunt Street",
      "Brooklyn, NY"
    ),
    SearchSuggestion(
      R.drawable.ic_home,
      "Home",
      "199 Pacific Street, Brooklyn, NY"
    )
  )

  val THIS_WEEK_SUGGESTIONS = listOf(
    SearchSuggestion(
      R.drawable.ic_schedule,
      "BEP GA",
      "Forsyth Street, New York, NY"
    ),
    SearchSuggestion(
      R.drawable.ic_schedule,
      "Sushi Nakazawa",
      "Commerce Street, New York, NY"
    ),
    SearchSuggestion(
      R.drawable.ic_schedule,
      "IFC Center",
      "6th Avenue, New York, NY"
    )
  )
}
