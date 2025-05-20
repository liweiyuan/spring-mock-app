package spring.app.mock.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.app.mock.entity.Product;
import spring.app.mock.service.ProductService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        Product product = new Product();
        when(productService.getProducts()).thenReturn(Collections.singletonList(product));

        List<Product> result = productController.getAllProducts();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productService, times(1)).getProducts();
    }
    
    @Test
    void testGetProductById() {
        Long productId = 1L;
        Product product = new Product();
        when(productService.getProductById(productId)).thenReturn(product);

        Product result = productController.getProductById(productId);
        assertNotNull(result);
        verify(productService, times(1)).getProductById(productId);
    }
}

