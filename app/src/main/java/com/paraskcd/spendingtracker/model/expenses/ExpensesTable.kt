package com.paraskcd.spendingtracker.model.expenses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.paraskcd.spendingtracker.model.categories.SubcategoriesTable
import java.time.Instant
import java.util.Date
import java.util.UUID

@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(
            entity = SubcategoriesTable::class,
            parentColumns = ["id"],
            childColumns = ["subcategoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExpensesTable(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val amount: Float,
    val entryDate: Date = Date.from(Instant.now()),
    val description: String?,
    @ColumnInfo(index = true)
    val subcategoryId: UUID
)
