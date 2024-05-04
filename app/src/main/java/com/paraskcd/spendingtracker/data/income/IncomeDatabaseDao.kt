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
import java.util.Date

@Dao
interface IncomeDatabaseDao {
    @Transaction
    @Query("SELECT * FROM income ORDER BY entryDate DESC")
    fun getAll(): Flow<List<IncomeAndSubcategory>>

    @Transaction
    @Query("SELECT * FROM subcategories WHERE id = :id")
    fun getIncomesBySubcategoryId(id: String): Flow<SubcategoryAndIncomes>

    @Transaction
    @Query("SELECT * FROM income WHERE id = :id")
    fun getByIncomeId(id: String): Flow<IncomeAndSubcategory>

    @Transaction
    @Query("SELECT * FROM income WHERE entryDate BETWEEN :from AND :to")
    fun getByIncomeEntryDate(from: Date, to: Date): Flow<List<IncomeAndSubcategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(income: IncomeTable)

    @Update
    suspend fun update(income: IncomeTable)

    @Delete
    suspend fun delete(income: IncomeTable)
}