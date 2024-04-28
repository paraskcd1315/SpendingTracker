package com.paraskcd.spendingtracker.data.categories

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paraskcd.spendingtracker.model.categories.CategoriesTable
import com.paraskcd.spendingtracker.model.categories.SubcategoriesTable
import com.paraskcd.spendingtracker.utils.DateConverter
import com.paraskcd.spendingtracker.utils.UUIDConverter

@Database(
    entities = [CategoriesTable::class, SubcategoriesTable::class],
    version = 2
)
@TypeConverters(DateConverter::class, UUIDConverter::class)
abstract class CategoryDatabase: RoomDatabase() {
    abstract fun categoryDao(): CategoryDatabaseDao
}