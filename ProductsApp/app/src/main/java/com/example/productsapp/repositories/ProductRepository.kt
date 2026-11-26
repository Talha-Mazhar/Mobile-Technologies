package com.example.productsapp.repositories

import com.example.productsapp.dao.ProductDao
import com.example.productsapp.entities.Product
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {

    val allProducts: Flow<List<Product>> = productDao.getAllProducts()

    fun getProductById(productId: Long): Flow<Product?> {
        return productDao.getProductById(productId)
    }

    fun searchProducts(query: String): Flow<List<Product>> {
        return productDao.searchByName(query)
    }

    suspend fun insertProduct(product: Product): Long {
        return productDao.insert(product)
    }

    suspend fun updateProduct(product: Product) {
        productDao.update(product)
    }

    suspend fun deleteProduct(product: Product) {
        productDao.delete(product)
    }
}
