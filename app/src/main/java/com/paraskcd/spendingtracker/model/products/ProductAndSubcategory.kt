package com.paraskcd.spendingtracker.model.products

import androidx.room.Embedded
import androidx.room.Relation
import com.paraskcd.spendingtracker.model.categories.SubcategoriesTable
import com.paraskcd.spendingtracker.model.incomes.IncomeTable

data class ProductAndSubcategory(
    @Embedded
    val product: ProductsTable,
    @Relation(
        parentColumn = "subcategoryId",
        entityColumn = "id"
    )
    val subcategory: SubcategoriesTable
)
