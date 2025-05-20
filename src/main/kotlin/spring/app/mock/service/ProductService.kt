package spring.app.mock.service

import spring.app.mock.entity.Product

interface ProductService {

    fun getProducts(): List<Product>
    fun getProductById(id: Long): Product?
    fun createProduct(product: Product): Product
    fun updateProduct(product: Product): Product?
    fun deleteProduct(id: Long)
}