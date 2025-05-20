package spring.app.mock.service.impl

import com.github.javafaker.Faker
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import spring.app.mock.entity.Product
import spring.app.mock.service.ProductService
import java.util.concurrent.CopyOnWriteArrayList

@Service
class MockProductService: ProductService {

    // 使用线程安全的列表实现，适合模拟服务的并发场景
    private val products: MutableList<Product> = CopyOnWriteArrayList()
    private val faker: Faker = Faker()

    // Spring会在构造函数之后调用这个方法
    @PostConstruct
    fun initialize() {
        // 生成初始产品数据
        generateMockProducts()
    }

    private fun generateMockProducts() {
        // 清空现有列表，确保不会重复添加
        products.clear()

        // 生成10个模拟产品
        repeat(10) { index ->
            products.add(
                Product(
                    id = (index + 1).toLong(),
                    name = faker.commerce().productName(),
                    price = faker.commerce().price().toDouble()
                )
            )
        }
    }

    override fun getProducts(): List<Product> {
        return this.products.toList() // Return an immutable copy
    }

    override fun getProductById(id: Long): Product? {
        return products.find { it.id == id }
    }

    override fun createProduct(product: Product): Product {
        val newId = (products.maxOfOrNull { it.id } ?: 0L) + 1L
        // Assuming Product has a no-arg constructor and mutable properties
        // If Product were a data class, this would be: product.copy(id = newId)
        val newProduct = Product().apply {
            this.id = newId
            this.name = product.name
            this.price = product.price
        }
        products.add(newProduct)
        return newProduct
    }

    override fun updateProduct(product: Product): Product? {
        val index = products.indexOfFirst { it.id == product.id }
        return if (index != -1) {
            // Assuming Product has a no-arg constructor and mutable properties
            // If Product were a data class, this could be: products[index] = product.copy()
            // Or if properties are mutable: products[index].apply { name = product.name; price = product.price }
            // For now, creating a new instance and replacing, similar to original logic
            val updatedProduct = Product().apply {
                this.id = product.id // Keep original ID
                this.name = product.name
                this.price = product.price
            }
            products[index] = updatedProduct
            updatedProduct
        } else {
            null
        }
    }

    override fun deleteProduct(id: Long) {
        products.removeIf { it.id == id }
    }
}
