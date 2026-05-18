package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product("Test Product", "Test Description", 100.0);
        testProduct.setId(1L);
    }

    @Test
    void testCreateProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        Product created = productService.createProduct(testProduct);

        assertNotNull(created);
        assertEquals("Test Product", created.getName());
        verify(productRepository, times(1)).save(testProduct);
    }

    @Test
    void testCreateProductWithNegativePrice() {
        Product invalidProduct = new Product("Invalid", "Description", -10.0);

        assertThrows(IllegalArgumentException.class, () -> {
            productService.createProduct(invalidProduct);
        });

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testGetProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        Optional<Product> found = productService.getProductById(1L);

        assertTrue(found.isPresent());
        assertEquals("Test Product", found.get().getName());
    }

    @Test
    void testGetProductByIdNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Product> found = productService.getProductById(99L);

        assertFalse(found.isPresent());
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = Arrays.asList(testProduct, new Product("Product2", "Desc2", 200.0));
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testDeleteProduct() {
        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }
}
