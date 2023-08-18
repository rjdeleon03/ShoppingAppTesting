package com.androiddevs.shoppinglisttestingyt.di

import android.content.Context
import androidx.room.Room
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingDao
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingItemDatabase
import com.androiddevs.shoppinglisttestingyt.data.remote.PixabayAPI
import com.androiddevs.shoppinglisttestingyt.other.Constants.BASE_URL
import com.androiddevs.shoppinglisttestingyt.other.Constants.DATABASE_NAME
import com.androiddevs.shoppinglisttestingyt.repositories.DefaultShoppingRepository
import com.androiddevs.shoppinglisttestingyt.repositories.ShoppingRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
// Changed to SingletonComponent for Hilt to be
// more inclusive of non-Android projects
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ): ShoppingItemDatabase {
        return Room.databaseBuilder(
            context,
            ShoppingItemDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixabayAPI
    ): ShoppingRepository {
        return DefaultShoppingRepository(dao, api)
    }

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ): ShoppingDao {
        return database.shoppingDao()
    }

    @Singleton
    @Provides
    fun providePixabayAPI(): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create()
    }
}