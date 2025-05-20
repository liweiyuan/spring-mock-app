package spring.app.mock.entity

data class Product(
    // 可变属性，允许在创建后修改
    var id: Long = 0,
    var name: String = "",
    var price: Double = 0.0
){
    // 如果需要额外的自定义验证逻辑，可以添加在这里
    init {
        require(price >= 0) { "Price cannot be negative" }
    }

    // 自定义方法示例
    fun applyDiscount(discountPercent: Double): Product {
        val discountFactor = 1 - (discountPercent / 100)
        return this.copy(price = price * discountFactor)
    }

    // 用于序列化/反序列化的伴生对象，如果需要的话
    companion object {
        const val CATEGORY_DEFAULT = "GENERAL"

        // 工厂方法示例
        fun createOnSale(name: String, originalPrice: Double): Product {
            return Product(0, name, originalPrice * 0.9)
        }
    }
}
