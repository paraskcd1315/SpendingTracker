package com.paraskcd.spendingtracker.model.incomes

import androidx.room.Embedded
import androidx.room.Relation
import com.paraskcd.spendingtracker.model.categories.SubcategoriesTable
import com.paraskcd.spendingtracker.model.categories.SubcategoryAndCategory

data class IncomeAndSubcategory(
    @Embedded
    val income: IncomeTable,
    @Relation(
        parentColumn = "subcategoryId",
        entityColumn = "id"
    )
    val subcategory: SubcategoriesTable
)
