package com.androiddevs.shoppinglisttestingyt.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

// Ensures this test will run in an Android environment
// Will run within emulator
@RunWith(AndroidJUnit4::class)

// Tells that what we write here are unit tests
// @MediumTest -- for Integrated Tests
// @LargeTest -- for Automated Tests
@SmallTest
@ExperimentalCoroutinesApi // For runBlockingTest
class ShoppingDaoTest {

    private lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before // Executes before every test case
    fun setup() {
        // Not a real database, only in RAM
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.shoppingDao()
    }

    @After // Executes after every test case
    fun tearDown() {
        database.close()
    }

    @Test
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

}