package com.paraskcd.spendingtracker.model.categories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "subcategories",
    foreignKeys = [
        ForeignKey(
            entity = CategoriesTable::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SubcategoriesTable(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val name: String,
    @ColumnInfo(index = true)
    val categoryId: UUID
)
