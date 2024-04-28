package com.paraskcd.spendingtracker.data.categories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.paraskcd.spendingtracker.model.categories.CategoriesTable
import com.paraskcd.spendingtracker.model.categories.CategoryAndSubcategories
import com.paraskcd.spendingtracker.model.categories.SubcategoriesTable
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDatabaseDao {
    @Transaction
    @Query("SELECT * FROM categories")
    fun getAll(): Flow<List<CategoryAndSubcategories>>

    @Transaction
    @Query("SELECT * FROM categories WHERE id = :id")
    fun getByCategoryId(id: String): Flow<CategoryAndSubcategories>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(category: CategoriesTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(vararg subcategory: SubcategoriesTable)

    @Update
    suspend fun update(category: CategoriesTable)

    @Update
    suspend fun update(vararg subcategory: SubcategoriesTable)

    @Delete
    suspend fun delete(category: CategoriesTable)

    @Delete
    suspend fun delete(vararg subccategory: SubcategoriesTable)
}