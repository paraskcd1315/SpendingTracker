package com.paraskcd.spendingtracker.model.products

import androidx.room.Embedded
import androidx.room.Relation
import com.paraskcd.spendingtracker.model.categories.SubcategoriesTable

data class SubcategoryAndProducts(
    @Embedded
    val subcategory: SubcategoriesTable,
    @Relation(
        parentColumn = "id",
        entityColumn = "subcategoryId"
    )
    val products: List<ProductsTable>
)