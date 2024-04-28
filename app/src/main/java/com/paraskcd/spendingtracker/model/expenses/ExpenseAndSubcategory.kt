package com.paraskcd.spendingtracker.model.expenses

import androidx.room.Embedded
import androidx.room.Relation
import com.paraskcd.spendingtracker.model.categories.SubcategoriesTable
import com.paraskcd.spendingtracker.model.categories.SubcategoryAndCategory

data class ExpenseAndSubcategory(
    @Embedded
    val expense: ExpensesTable,
    @Relation(
        parentColumn = "subcategoryId",
        entityColumn = "id"
    )
    val subcategory: SubcategoriesTable
)
