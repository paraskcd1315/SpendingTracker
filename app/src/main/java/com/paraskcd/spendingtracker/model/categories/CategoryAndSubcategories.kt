package com.paraskcd.spendingtracker.model.categories

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryAndSubcategories(
    @Embedded
    val category: CategoriesTable,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val subcategories: List<SubcategoriesTable>
)