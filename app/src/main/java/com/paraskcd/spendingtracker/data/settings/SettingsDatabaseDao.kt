package com.paraskcd.spendingtracker.data.settings

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.paraskcd.spendingtracker.model.settings.MainSettingsTable
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDatabaseDao {
    @Transaction
    @Query("SELECT * FROM settings")
    fun getAll(): Flow<List<MainSettingsTable>>

    @Transaction
    @Query("SELECT * FROM settings WHERE id = :id")
    fun getBySettingId(id: String): Flow<MainSettingsTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(setting: MainSettingsTable)

    @Update
    suspend fun update(setting: MainSettingsTable)

    @Delete
    suspend fun delete(setting: MainSettingsTable)
}