/*
 * Copyright (C) 2017 The Android Open Source Project
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

package android.support.navigation.app.nav;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.navigation.app.nav.activity.NavigationActivity;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;

import com.android.support.navigation.test.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

@SmallTest
public class NavControllerTest {
    @Rule
    public ActivityTestRule<NavigationActivity> mActivityRule =
            new ActivityTestRule<>(NavigationActivity.class, false, false);

    private Instrumentation mInstrumentation;

    @Before
    public void getInstrumentation() {
        mInstrumentation = InstrumentationRegistry.getInstrumentation();
    }

    @Test
    public void testStartDestination() {
        Context context = InstrumentationRegistry.getTargetContext();
        NavController navController = new NavController(context);
        TestNavigator navigator = new TestNavigator();
        navController.addNavigator(navigator);
        navController.setGraph(R.xml.nav_start_destination);
        assertThat(navController.getCurrentDestination().getId(), is(R.id.start_test));
    }

    @Test
    public void testNestedStartDestination() {
        Context context = InstrumentationRegistry.getTargetContext();
        NavController navController = new NavController(context);
        TestNavigator navigator = new TestNavigator();
        navController.addNavigator(navigator);
        navController.setGraph(R.xml.nav_nested_start_destination);
        assertThat(navController.getCurrentDestination().getId(), is(R.id.nested_test));
    }

    @Test
    public void testSetGraph() throws Throwable {
        NavigationActivity activity = launchActivity();
        NavController navController = activity.getNavController();
        assertThat(navController.getGraph(), is(nullValue(NavGraph.class)));

        navController.setGraph(R.xml.nav_start_destination);
        assertThat(navController.getGraph(), is(notNullValue(NavGraph.class)));
        assertThat(navController.getCurrentDestination().getId(), is(R.id.start_test));
    }

    private NavigationActivity launchActivity() throws Throwable {
        Intent intent = new Intent(mInstrumentation.getTargetContext(),
                NavigationActivity.class);
        NavigationActivity activity = mActivityRule.launchActivity(intent);
        mInstrumentation.waitForIdleSync();
        NavController navController = activity.getNavController();
        assertThat(navController, is(notNullValue(NavController.class)));
        TestNavigator navigator = new TestNavigator();
        navController.addNavigator(navigator);
        return activity;
    }
}
