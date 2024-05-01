package com.paraskcd.spendingtracker.model.products

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.paraskcd.spendingtracker.model.categories.SubcategoriesTable
import java.util.UUID

@Entity(
    tableName = "products",
    foreignKeys = [
        ForeignKey(
            entity = SubcategoriesTable::class,
            parentColumns = ["id"],
            childColumns = ["subcategoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProductsTable(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    var name: String,
    var price: Float,
    @ColumnInfo(index = true)
    val subcategoryId: UUID
)
