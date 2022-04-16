package com.pasuh.masterchef.ingredient_inventory.floor1

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pasuh.masterchef.MainActivity
import com.pasuh.masterchef.R
import com.pasuh.masterchef.view.v_ingredient_inventory.floor1.IngredientListFragment
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IngredientInventoryTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun add_ingredient_button_is_clickable() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        val startOrderScenario =
            launchFragmentInContainer<IngredientListFragment>(themeResId = R.style.Theme_Masterchef)
        startOrderScenario.onFragment{ fragment ->
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(destId = R.id.ingredientListFragment)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.floatingActionButton))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(click())

        assertEquals(navController.currentDestination?.id, R.id.addIngredientFragment)
    }
}