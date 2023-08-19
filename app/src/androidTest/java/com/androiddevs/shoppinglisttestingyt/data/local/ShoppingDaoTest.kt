package com.androiddevs.shoppinglisttestingyt.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.Navigator.Name
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.androiddevs.shoppinglisttestingyt.launchFragmentInHiltContainer
import com.androiddevs.shoppinglisttestingyt.ui.ShoppingFragment
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

// Ensures this test will run in an Android environment
// Will run within emulator
//@RunWith(AndroidJUnit4::class)

// Tells that what we write here are unit tests
// @MediumTest -- for Integrated Tests
// @LargeTest -- for Automated Tests
@SmallTest
@ExperimentalCoroutinesApi // For runBlockingTest
@HiltAndroidTest
class ShoppingDaoTest {

    // Set rule to run tests one by one
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Specific rule for Hilt
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before // Executes before every test case
    fun setup() {
        hiltRule.inject()
        dao = database.shoppingDao()
    }

    @After // Executes after every test case
    fun tearDown() {
        database.close()
    }

    @Test // Won't run in Android Studio Giraffe
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem(
            "name",
            1,
            1f,
            "url",
            id = 1
        )
        dao.insertShoppingItem(shoppingItem)

        val allShoppingItems = dao.observeAllShoppingItems()
            .getOrAwaitValue()

        assertThat(allShoppingItems).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem(
            "name",
            1,
            1f,
            "url",
            id = 1
        )
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItems = dao.observeAllShoppingItems()
            .getOrAwaitValue()

        assertThat(allShoppingItems).doesNotContain(shoppingItem)
    }

    @Test
    fun observeTotalPriceSum() = runBlockingTest {
        val shoppingItem1 = ShoppingItem(
            "name",
            2,
            10f,
            "url",
            id = 1
        )
        val shoppingItem2 = ShoppingItem(
            "name",
            4,
            5.5f,
            "url",
            id = 2
        )
        val shoppingItem3 = ShoppingItem(
            "name",
            0,
            100f,
            "url",
            id = 3
        )
        dao.apply {
            insertShoppingItem(shoppingItem1)
            insertShoppingItem(shoppingItem2)
            insertShoppingItem(shoppingItem3)
        }

        val totalPriceSum = dao.observeTotalPrice()
            .getOrAwaitValue()

        assertThat(totalPriceSum).isEqualTo(2 * 10f + 4 * 5.5f)
    }

    @Test
    fun testLaunchFragmentInHiltContainer() {
        launchFragmentInHiltContainer<ShoppingFragment> {

        }
    }
}