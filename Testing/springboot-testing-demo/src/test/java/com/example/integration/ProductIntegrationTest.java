package com.example.integration;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void testFullProductLifecycle() throws Exception {
        Product product = new Product("Integration Test Product", "Test Description", 99.99);

        String response = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Integration Test Product"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Product created = objectMapper.readValue(response, Product.class);

        mockMvc.perform(get("/api/products/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Integration Test Product"));

        mockMvc.perform(delete("/api/products/" + created.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/products/" + created.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllProductsIntegration() throws Exception {
        productRepository.save(new Product("Product1", "Desc1", 100.0));
        productRepository.save(new Product("Product2", "Desc2", 200.0));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
