package com.paraskcd.spendingtracker.model.categories

import androidx.room.Embedded
import androidx.room.Relation

data class SubcategoryAndCategory(
    @Embedded
    val subcategory: SubcategoriesTable,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: CategoriesTable
)