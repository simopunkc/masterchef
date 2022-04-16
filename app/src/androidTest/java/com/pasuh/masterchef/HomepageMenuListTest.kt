package com.pasuh.masterchef

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pasuh.masterchef.view.HomepageMenuListFragment
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class HomepageMenuListTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun ingredient_inventory_button_is_clickable() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        val startFragment =
            launchFragmentInContainer<HomepageMenuListFragment>(themeResId = R.style.Theme_Masterchef)
        startFragment.onFragment{ fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        onView(withId(R.id.menuRecipeInventory))
            .perform(ViewActions.scrollTo())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(ViewActions.click())

        Assert.assertEquals(navController.currentDestination?.id, R.id.ingredientListFragment)
    }

    @Test
    fun restock_history_button_is_clickable() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        val startFragment =
            launchFragmentInContainer<HomepageMenuListFragment>(themeResId = R.style.Theme_Masterchef)
        startFragment.onFragment{ fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        onView(withId(R.id.menuRestockHistory))
            .perform(ViewActions.scrollTo())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(ViewActions.click())

        Assert.assertEquals(navController.currentDestination?.id, R.id.restockListFragment)
    }

    @Test
    fun menu_inventory_button_is_clickable() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        val startFragment =
            launchFragmentInContainer<HomepageMenuListFragment>(themeResId = R.style.Theme_Masterchef)
        startFragment.onFragment{ fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        onView(withId(R.id.menuInventory))
            .perform(ViewActions.scrollTo())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(ViewActions.click())

        Assert.assertEquals(navController.currentDestination?.id, R.id.menuListFragment)
    }

    @Test
    fun sales_transaction_button_is_clickable() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        val startFragment =
            launchFragmentInContainer<HomepageMenuListFragment>(themeResId = R.style.Theme_Masterchef)
        startFragment.onFragment{ fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        onView(withId(R.id.menuTransactionHistory))
            .perform(ViewActions.scrollTo())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(ViewActions.click())

        Assert.assertEquals(navController.currentDestination?.id, R.id.salesTransactionListFragment)
    }

    @Test
    fun max_profit_button_is_clickable() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        val startFragment =
            launchFragmentInContainer<HomepageMenuListFragment>(themeResId = R.style.Theme_Masterchef)
        startFragment.onFragment{ fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        onView(withId(R.id.menuMaxProfit))
            .perform(ViewActions.scrollTo())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(ViewActions.click())

        Assert.assertEquals(navController.currentDestination?.id, R.id.profitListFragment)
    }
}