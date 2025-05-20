package spring.app.mock.service

import com.github.javafaker.Faker
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import spring.app.mock.entity.Product
import spring.app.mock.service.impl.MockProductService
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime) // 可以是 Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime // 可以是 Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime
// 可以是 Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime
@OutputTimeUnit(TimeUnit.NANOSECONDS) // 输出时间单位 // 输出时间单位
// 输出时间单位
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS) // 预热迭代 // 预热迭代
// 预热迭代
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS) // 实际测量迭代 // 实际测量迭代
// 实际测量迭代
@Fork(1) // Fork 一个 JVM 进程进行测试 // Fork 一个 JVM 进程进行测试
open class ProductServiceBenchmark {
    private lateinit var productService: MockProductService
    private lateinit var existingProduct: Product
    private lateinit var newProductPayload: Product
    private var nextProductId = 0L // 用于创建和删除操作
    private val faker = Faker()

    @Setup(Level.Trial) // 每次完整的基准测试运行前执行一次
    fun trialSetup() {
        productService = MockProductService()
        productService.initialize() // 确保服务已初始化并填充数据

        // 获取一个已存在的产品用于测试 getById 和 update
        val products = productService.getProducts()
        existingProduct = if (products.isNotEmpty()) {
            products.random()
        } else {
            // 如果服务初始化后没有产品，则手动创建一个
            productService.createProduct(Product(name = "Initial Product", price = 10.0))
        }

        // 用于创建产品的 payload
        newProductPayload = Product(name = "Benchmark New Product", price = 99.99)

        // 初始化 nextProductId，确保 ID 的唯一性，避免与现有 ID 冲突
        nextProductId = (productService.getProducts().maxOfOrNull { it.id } ?: 0L) + 1000L
    }

    @Setup(Level.Invocation) // 每次测试方法调用前执行
    fun invocationSetup() {
        // 对于 create, update, delete，可能需要确保每次调用都有一个唯一的目标
        // 例如，为 createProduct 准备一个新的 ID
        // 为 deleteProduct 确保产品存在
        // 这里简化处理，依赖于 nextProductId 递增
    }

    @Benchmark
    fun getProducts(bh: Blackhole) {
        bh.consume(productService.getProducts())
    }

    @Benchmark
    fun getProductById_existing(bh: Blackhole) {
        bh.consume(productService.getProductById(existingProduct.id))
    }

    @Benchmark
    fun getProductById_nonExisting(bh: Blackhole) {
        bh.consume(productService.getProductById(-1L)) // 使用一个不存在的ID
    }

    @Benchmark
    fun createProduct(bh: Blackhole) {
        // 为了避免每次都创建相同ID的产品，如果 MockProductService 内部不处理ID，这里需要调整
        // 假设 MockProductService 内部会生成新 ID
        // 或者我们手动构造不同的产品数据
        val productToCreate = newProductPayload.copy(name = faker.commerce().productName()) // 确保每次产品名略有不同以模拟真实场景
        bh.consume(productService.createProduct(productToCreate))
    }

    @Benchmark
    fun updateProduct_existing(bh: Blackhole) {
        val productToUpdate = existingProduct.copy(price = faker.commerce().price().toDouble())
        bh.consume(productService.updateProduct(productToUpdate))
    }

    @Benchmark
    fun deleteProductAndRecreateForNextIteration(bh: Blackhole) {
        // 为了保证测试的幂等性，删除后立即创建一个相似的，以便其他测试或下一次迭代有数据
        // 这种方式更适合测量连续删除操作的性能
        val idToDelete = nextProductId++
        // 先创建一个产品，然后删除它
        val tempProduct = productService.createProduct(Product(id = idToDelete, name = "ToDelete", price = 1.0))
        bh.consume(productService.deleteProduct(tempProduct.id))
    }
}



