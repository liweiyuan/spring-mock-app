package spring.app.mock.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import spring.app.mock.entity.Product

class ProductServiceTest {

    @Mock
    private lateinit var productService: ProductService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }
    
    @Test
    fun testGetProductById() {
        val productId = 1L
        val product = Product()
        `when`(productService.getProductById(productId)).thenReturn(product)

        val result = productService.getProductById(productId)
        assertEquals(product, result)
    }
}

