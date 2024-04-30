package com.paraskcd.spendingtracker.data.products

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.paraskcd.spendingtracker.model.incomes.IncomeAndSubcategory
import com.paraskcd.spendingtracker.model.incomes.IncomeTable
import com.paraskcd.spendingtracker.model.incomes.SubcategoryAndIncomes
import com.paraskcd.spendingtracker.model.products.ProductAndSubcategory
import com.paraskcd.spendingtracker.model.products.ProductsTable
import com.paraskcd.spendingtracker.model.products.SubcategoryAndProducts
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDatabaseDao {
    @Transaction
    @Query("SELECT * FROM products")
    fun getAll(): Flow<List<ProductAndSubcategory>>

    @Transaction
    @Query("SELECT * FROM subcategories WHERE id = :id")
    fun getProductsBySubcategoryId(id: String): Flow<SubcategoryAndProducts>

    @Transaction
    @Query("SELECT * FROM products WHERE id = :id")
    fun getByProductId(id: String): Flow<ProductAndSubcategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(product: ProductsTable)

    @Update
    suspend fun update(product: ProductsTable)

    @Delete
    suspend fun delete(product: ProductsTable)
}