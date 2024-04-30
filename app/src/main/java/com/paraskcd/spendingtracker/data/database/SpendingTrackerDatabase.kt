package com.paraskcd.spendingtracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paraskcd.spendingtracker.data.categories.CategoryDatabaseDao
import com.paraskcd.spendingtracker.data.expense.ExpenseDatabaseDao
import com.paraskcd.spendingtracker.data.income.IncomeDatabaseDao
import com.paraskcd.spendingtracker.data.products.ProductsDatabaseDao
import com.paraskcd.spendingtracker.data.settings.SettingsDatabaseDao
import com.paraskcd.spendingtracker.model.categories.CategoriesTable
import com.paraskcd.spendingtracker.model.categories.SubcategoriesTable
import com.paraskcd.spendingtracker.model.expenses.ExpensesTable
import com.paraskcd.spendingtracker.model.incomes.IncomeTable
import com.paraskcd.spendingtracker.model.products.ProductsTable
import com.paraskcd.spendingtracker.model.settings.MainSettingsTable
import com.paraskcd.spendingtracker.utils.DateConverter
import com.paraskcd.spendingtracker.utils.UUIDConverter

@Database(
    entities = [CategoriesTable::class, SubcategoriesTable::class, ExpensesTable::class, IncomeTable::class, MainSettingsTable::class, ProductsTable::class],
    version = 7
)
@TypeConverters(DateConverter::class, UUIDConverter::class)
abstract class SpendingTrackerDatabase: RoomDatabase() {
    abstract fun categoryDao(): CategoryDatabaseDao
    abstract fun expenseDao(): ExpenseDatabaseDao
    abstract fun incomeDao(): IncomeDatabaseDao
    abstract fun settingsDao(): SettingsDatabaseDao
    abstract fun productsDao(): ProductsDatabaseDao
}