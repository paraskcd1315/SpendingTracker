package com.paraskcd.spendingtracker.model.incomes

import androidx.room.Embedded
import androidx.room.Relation
import com.paraskcd.spendingtracker.model.categories.SubcategoriesTable
import com.paraskcd.spendingtracker.model.categories.SubcategoryAndCategory

data class SubcategoryAndIncomes(
    @Embedded
    val subcategory: SubcategoriesTable,
    @Relation(
        parentColumn = "id",
        entityColumn = "subcategoryId"
    )
    val incomes: List<IncomeTable>
)
