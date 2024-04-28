package com.paraskcd.spendingtracker.di

import android.content.Context
import androidx.room.Room
import com.paraskcd.spendingtracker.data.categories.CategoryDatabase
import com.paraskcd.spendingtracker.data.categories.CategoryDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideCategoryDatabaseDao(categoryDatabase: CategoryDatabase): CategoryDatabaseDao = categoryDatabase.categoryDao()

    @Singleton
    @Provides
    fun provideCategoryDatabase(@ApplicationContext context: Context): CategoryDatabase = Room.databaseBuilder(context, CategoryDatabase::class.java, "categoriesDB").fallbackToDestructiveMigration().build()
}