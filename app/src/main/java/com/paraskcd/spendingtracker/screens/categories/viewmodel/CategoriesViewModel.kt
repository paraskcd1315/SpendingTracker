package com.paraskcd.spendingtracker.screens.categories.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paraskcd.spendingtracker.model.categories.CategoriesTable
import com.paraskcd.spendingtracker.model.categories.CategoryAndSubcategories
import com.paraskcd.spendingtracker.model.categories.SubcategoriesTable
import com.paraskcd.spendingtracker.model.categories.SubcategoryAndCategory
import com.paraskcd.spendingtracker.repository.categories.CategoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.lang.Thread.State
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val repository: CategoriesRepository): ViewModel() {
    private var _categoriesDatabase: MutableStateFlow<List<CategoryAndSubcategories>> = MutableStateFlow(emptyList())
    var categoriesDatabase: StateFlow<List<CategoryAndSubcategories>> = _categoriesDatabase.asStateFlow()

    private var _subcategoriesDatabase: MutableStateFlow<List<SubcategoryAndCategory>> = MutableStateFlow(emptyList())
    var subcategoriesDatabase: StateFlow<List<SubcategoryAndCategory>> = _subcategoriesDatabase.asStateFlow()

    private var _categoryById: MutableStateFlow<CategoryAndSubcategories?> = MutableStateFlow(null)
    val categoryById: StateFlow<CategoryAndSubcategories?> = _categoryById.asStateFlow()

    private var _subcategoryById: MutableStateFlow<SubcategoryAndCategory?> = MutableStateFlow(null)
    val subcategoryById: StateFlow<SubcategoryAndCategory?> = _subcategoryById.asStateFlow()

    private var _selectDeleteCategory: MutableStateFlow<CategoriesTable?> = MutableStateFlow(null)
    val selectDeleteCategory: StateFlow<CategoriesTable?> = _selectDeleteCategory.asStateFlow()

    private var _selectDeleteSubcategory: MutableStateFlow<SubcategoriesTable?> = MutableStateFlow(null)
    val selectDeleteSubcategory: StateFlow<SubcategoriesTable?> = _selectDeleteSubcategory.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllCategories()
        }
        viewModelScope.launch(Dispatchers.IO) {
            getAllSubcategories()
        }
    }

    suspend fun getAllCategories() {
        repository.getAllCategories().distinctUntilChanged().collect {
                listOfAllCategories ->
            if (listOfAllCategories.isEmpty()) {
                _categoriesDatabase.value = emptyList()
            } else {
                _categoriesDatabase.value = listOfAllCategories
            }
        }
    }

    suspend fun getAllSubcategories() {
        repository.getAllSubcategories().distinctUntilChanged().collect {
            listOfAllSubcategories ->
            if (listOfAllSubcategories.isEmpty()) {
                _subcategoriesDatabase.value = emptyList()
            } else {
                _subcategoriesDatabase.value = listOfAllSubcategories
            }
        }
    }

    fun getCategoryById(id: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getByCategoryId(id).distinctUntilChanged().collect {
                catById ->
            if (catById != null) {
                _categoryById.value = catById
            } else {
                _categoryById.value = null
            }
        }
    }

    fun getSubcategoryById(id: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getBySubcategoryId(id).distinctUntilChanged().collect {
            subcatById ->
            if (subcatById != null) {
                _subcategoryById.value = subcatById
            } else {
                _subcategoryById.value = null
            }
        }
    }

    fun resetSubcategoryById() {
        _subcategoryById.value = null
    }

    fun resetCategoryById() {
        _categoryById.value = null
    }

    fun addCategory(category: CategoriesTable) = viewModelScope.launch {
        repository.saveCategory(category)
        getAllCategories()
    }

    fun addSubcategory(subcategory: SubcategoriesTable) = viewModelScope.launch {
        repository.saveSubcategory(subcategory)
        getAllCategories()
        getCategoryById(subcategory.categoryId.toString())
    }

    fun editCategory(category: CategoriesTable) = viewModelScope.launch {
        repository.updateCategory(category)
        getCategoryById(category.id.toString())
    }

    fun editSubcategory(subcategory: SubcategoriesTable) = viewModelScope.launch {
        repository.updateSubcategory(subcategory)
        getCategoryById(subcategory.categoryId.toString())
    }

    fun selectDeleteCategory(category: CategoryAndSubcategories) = viewModelScope.launch {
        _selectDeleteCategory.value = category.category
    }

    fun selectDeleteSubcategory(category: CategoryAndSubcategories, subcategoryId: UUID) = viewModelScope.launch {
        val subcategory = category.subcategories.find { subcategory -> subcategoryId == subcategory.id }
        _selectDeleteSubcategory.value = subcategory
    }

    fun deleteCategory(category: CategoriesTable) = viewModelScope.launch {
        repository.deleteCategory(category)
        getAllCategories()
        resetCategoryById()
    }

    fun deleteSubcategory(subcategory: SubcategoriesTable) = viewModelScope.launch {
        repository.deleteSubcategory(subcategory)
        getCategoryById(subcategory.categoryId.toString())
    }
}