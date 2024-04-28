package com.paraskcd.spendingtracker.model.settings

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.Date
import java.util.UUID

@Entity(tableName = "settings")
data class MainSettingsTable(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val bankBalance: Float,
    val updated: Date = Date.from(Instant.now())
)
