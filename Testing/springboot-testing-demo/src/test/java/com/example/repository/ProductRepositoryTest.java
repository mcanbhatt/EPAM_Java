package com.example.repository;

import com.example.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testSaveAndFindProduct() {
        Product product = new Product("Laptop", "Gaming Laptop", 1500.0);
        Product saved = productRepository.save(product);

        assertNotNull(saved.getId());
        assertEquals("Laptop", saved.getName());
    }

    @Test
    void testFindByName() {
        Product product1 = new Product("Mouse", "Wireless Mouse", 25.0);
        Product product2 = new Product("Mouse", "Gaming Mouse", 50.0);
        entityManager.persist(product1);
        entityManager.persist(product2);
        entityManager.flush();

        List<Product> products = productRepository.findByName("Mouse");

        assertEquals(2, products.size());
    }

    @Test
    void testFindByPriceGreaterThan() {
        entityManager.persist(new Product("Phone", "Smartphone", 800.0));
        entityManager.persist(new Product("Tablet", "iPad", 600.0));
        entityManager.persist(new Product("Watch", "Smartwatch", 300.0));
        entityManager.flush();

        List<Product> expensiveProducts = productRepository.findByPriceGreaterThan(500.0);

        assertEquals(2, expensiveProducts.size());
    }

    @Test
    void testFindByPriceRange() {
        entityManager.persist(new Product("Item1", "Description1", 100.0));
        entityManager.persist(new Product("Item2", "Description2", 250.0));
        entityManager.persist(new Product("Item3", "Description3", 500.0));
        entityManager.flush();

        List<Product> products = productRepository.findByPriceRange(200.0, 400.0);

        assertEquals(1, products.size());
        assertEquals("Item2", products.get(0).getName());
    }
}
