/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.navigation

import androidx.test.InstrumentationRegistry
import androidx.test.filters.SmallTest
import androidx.test.runner.AndroidJUnit4
import com.google.common.truth.Truth.assertWithMessage
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class NavGraphTest {
    private val navGraphNavigator = NavGraphNavigator(InstrumentationRegistry.getTargetContext())
    private val navigator = NoOpNavigator()

    @Test
    fun plusAssign() {
        val graph = NavGraph(navGraphNavigator)
        val destination = NavDestination(navigator).apply { id = DESTINATION_ID }
        graph += destination
        assertWithMessage("plusAssign destination should be retrieved with get")
            .that(graph[DESTINATION_ID])
            .isSameAs(destination)
    }

    @Test
    fun minusAssign() {
        val graph = NavGraph(navGraphNavigator)
        val destination = NavDestination(navigator).apply { id = DESTINATION_ID }
        graph += destination
        assertWithMessage("plusAssign destination should be retrieved with get")
            .that(graph[DESTINATION_ID])
            .isSameAs(destination)
        graph -= destination
        assertWithMessage("Destination should be removed after minusAssign")
            .that(graph)
            .doesNotContain(DESTINATION_ID)
    }

    @Test
    fun plusAssignGraph() {
        val graph = NavGraph(navGraphNavigator)
        val other = NavGraph(navGraphNavigator)
        other += NavDestination(navigator).apply { id = DESTINATION_ID }
        other += NavDestination(navigator).apply { id = SECOND_DESTINATION_ID }
        graph += other
        assertWithMessage("NavGraph should have destination1 from other")
            .that(graph)
            .contains(DESTINATION_ID)
        assertWithMessage("other nav graph should not have destination1")
            .that(other)
            .doesNotContain(DESTINATION_ID)

        assertWithMessage("NavGraph should have destination2 from other")
            .that(graph)
            .contains(SECOND_DESTINATION_ID)
        assertWithMessage("other nav graph should not have destination2")
            .that(other)
            .doesNotContain(SECOND_DESTINATION_ID)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getIllegalArgumentException() {
        val graph = NavGraph(navGraphNavigator)
        graph[DESTINATION_ID]
    }
}

private const val DESTINATION_ID = 1
private const val SECOND_DESTINATION_ID = 2
