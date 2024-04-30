package com.paraskcd.spendingtracker.repository.products

import com.paraskcd.spendingtracker.data.products.ProductsDatabaseDao
import com.paraskcd.spendingtracker.model.products.ProductsTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductsRepository @Inject constructor(private val productsDatabaseDao: ProductsDatabaseDao) {
    suspend fun saveProduct(product: ProductsTable) = productsDatabaseDao.save(product)
    suspend fun updateIncome(product: ProductsTable) = productsDatabaseDao.update(product)
    suspend fun deleteIncome(product: ProductsTable) = productsDatabaseDao.delete(product)
    fun getByProductId(id: String) = productsDatabaseDao.getByProductId(id).flowOn(Dispatchers.IO).conflate()
    fun getAllProducts() = productsDatabaseDao.getAll().flowOn(Dispatchers.IO).conflate()
    fun getProductsBySubcategoryId(id: String) = productsDatabaseDao.getProductsBySubcategoryId(id).flowOn(
        Dispatchers.IO).conflate()
}