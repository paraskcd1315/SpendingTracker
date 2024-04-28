package com.paraskcd.spendingtracker.data.income

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
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomeDatabaseDao {
    @Transaction
    @Query("SELECT * FROM income")
    fun getAll(): Flow<List<IncomeAndSubcategory>>

    @Transaction
    @Query("SELECT * FROM subcategories WHERE id = :id")
    fun getIncomesBySubcategoryId(id: String): Flow<SubcategoryAndIncomes>

    @Transaction
    @Query("SELECT * FROM income WHERE id = :id")
    fun getByIncomeId(id: String): Flow<IncomeAndSubcategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(income: IncomeTable)

    @Update
    suspend fun update(income: IncomeTable)

    @Delete
    suspend fun delete(income: IncomeTable)
}