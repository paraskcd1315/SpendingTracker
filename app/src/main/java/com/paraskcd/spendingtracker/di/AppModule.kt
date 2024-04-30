package com.paraskcd.spendingtracker.di

import android.content.Context
import androidx.room.Room
import com.paraskcd.spendingtracker.data.database.SpendingTrackerDatabase
import com.paraskcd.spendingtracker.data.categories.CategoryDatabaseDao
import com.paraskcd.spendingtracker.data.expense.ExpenseDatabaseDao
import com.paraskcd.spendingtracker.data.income.IncomeDatabaseDao
import com.paraskcd.spendingtracker.data.products.ProductsDatabaseDao
import com.paraskcd.spendingtracker.data.settings.SettingsDatabaseDao
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
    fun provideExpensesDatabaseDao(spendingTrackerDatabase: SpendingTrackerDatabase): ExpenseDatabaseDao = spendingTrackerDatabase.expenseDao()

    @Singleton
    @Provides
    fun provideIncomeDatabaseDao(spendingTrackerDatabase: SpendingTrackerDatabase): IncomeDatabaseDao = spendingTrackerDatabase.incomeDao()

    @Singleton
    @Provides
    fun provideSettingsDatabaseDao(spendingTrackerDatabase: SpendingTrackerDatabase): SettingsDatabaseDao = spendingTrackerDatabase.settingsDao()

    @Singleton
    @Provides
    fun provideProductsDatabaseDao(spendingTrackerDatabase: SpendingTrackerDatabase): ProductsDatabaseDao = spendingTrackerDatabase.productsDao()

    @Singleton
    @Provides
    fun provideCategoryDatabaseDao(spendingTrackerDatabase: SpendingTrackerDatabase): CategoryDatabaseDao = spendingTrackerDatabase.categoryDao()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): SpendingTrackerDatabase = Room.databaseBuilder(context, SpendingTrackerDatabase::class.java, "spendingTrackerDB").fallbackToDestructiveMigration().build()
}