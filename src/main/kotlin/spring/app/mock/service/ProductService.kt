package spring.app.mock.service

import spring.app.mock.entity.Product

interface ProductService {

    fun getProducts(): List<Product>
    fun getProductById(id: Long): Product?
    fun createProduct(product: Product): Product
    fun updateProduct(product: Product): Product? {
        // 默认实现可以返回修改后的产品或 null
        return product
    }
    fun deleteProduct(id: Long) {
        // 默认实现无需返回值
    }
}