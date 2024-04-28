package com.paraskcd.spendingtracker.model.categories

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "categories")
data class CategoriesTable(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),

    val name: String,
    val iconName: String
)
