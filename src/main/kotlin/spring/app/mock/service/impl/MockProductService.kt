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
        return this.products.toList()
    }

    override fun getProductById(id: Long): Product? {
        return products.find { it.id == id }
    }

    override fun createProduct(product: Product): Product {
        // 生成新的ID - 当前最大ID加1，如果列表为空则使用1
        val newId = if (products.isEmpty()) 1L else products.maxByOrNull { it.id }?.id?.plus(1) ?: 1L

        // 创建带有新ID的产品副本
        val newProduct = Product()
        newProduct.id = newId
        newProduct.name = product.name
        newProduct.price = product.price

        // 添加到列表
        products.add(newProduct)

        return newProduct
    }

    override fun updateProduct(product: Product): Product? {
        // 查找现有产品
        val existingProductIndex = getProductById(product.id)?.let {
            products.indexOf(it)
        } ?: return null
        // 创建更新后的产品对象，保留原始ID
        val updatedProduct = Product()
        updatedProduct.id = product.id
        updatedProduct.name = product.name
        updatedProduct.price = product.price

        // 替换列表中的现有产品
        products[existingProductIndex] = updatedProduct

        return updatedProduct
    }

    override fun deleteProduct(id: Long) {
        // 查找并删除产品
        val productToRemove = getProductById(id) // 这将在产品不存在时抛出异常
        products.remove(productToRemove)
    }
}