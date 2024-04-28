package com.paraskcd.spendingtracker.model.expenses

import androidx.room.Embedded
import androidx.room.Relation
import com.paraskcd.spendingtracker.model.categories.SubcategoriesTable
import com.paraskcd.spendingtracker.model.categories.SubcategoryAndCategory

data class SubcategoryAndExpenses(
    @Embedded
    val subcategory: SubcategoriesTable,
    @Relation(
        parentColumn = "id",
        entityColumn = "subcategoryId"
    )
    val expenses: List<ExpensesTable>
)