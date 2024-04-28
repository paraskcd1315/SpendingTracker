package com.paraskcd.spendingtracker.repository.categories

import com.paraskcd.spendingtracker.data.categories.CategoryDatabaseDao
import com.paraskcd.spendingtracker.model.categories.CategoriesTable
import com.paraskcd.spendingtracker.model.categories.SubcategoriesTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CategoriesRepository @Inject constructor(private val categoriesDatabaseDao: CategoryDatabaseDao) {
    suspend fun saveCategory(category: CategoriesTable) = categoriesDatabaseDao.save(category)
    suspend fun saveSubcategory(subcategory: SubcategoriesTable) = categoriesDatabaseDao.save(subcategory)
    suspend fun updateCategory(category: CategoriesTable) = categoriesDatabaseDao.update(category)
    suspend fun updateSubcategory(subcategory: SubcategoriesTable) = categoriesDatabaseDao.update(subcategory)
    suspend fun deleteCategory(category: CategoriesTable) = categoriesDatabaseDao.delete(category)
    suspend fun deleteSubcategory(subcategory: SubcategoriesTable) = categoriesDatabaseDao.delete(subcategory)
    fun getByCategoryId(id: String) = categoriesDatabaseDao.getByCategoryId(id).flowOn(Dispatchers.IO).conflate()
    fun getAllCategories() = categoriesDatabaseDao.getAll().flowOn(Dispatchers.IO).conflate()
    fun getBySubcategoryId(id: String) = categoriesDatabaseDao.getSubcategoryById(id).flowOn(Dispatchers.IO).conflate()
    fun getAllSubcategories() = categoriesDatabaseDao.getAllSubcategories().flowOn(Dispatchers.IO).conflate()
}