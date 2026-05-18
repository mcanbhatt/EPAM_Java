# Spring Boot Testing Demo

A comprehensive Spring Boot application demonstrating modern testing strategies including unit testing, integration testing, repository testing, and REST API testing.

## 📋 Table of Contents
- [Overview](#overview)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Running the Application](#running-the-application)
- [Testing Layers](#testing-layers)
- [Test Explanations](#test-explanations)
- [Best Practices](#best-practices)

## Overview

This project demonstrates a complete Spring Boot REST API with comprehensive testing at multiple levels:
- **Repository Layer**: Testing JPA repositories with H2 database
- **Service Layer**: Unit testing with Mockito
- **Controller Layer**: Testing REST endpoints with MockMvc
- **Integration Tests**: End-to-end testing with full Spring context

### Application Features
- CRUD operations for Product entities
- REST API endpoints
- JPA with H2 in-memory database
- Custom repository queries
- Service layer business logic

## Project Structure

```
springboot-testing-demo/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/com/example/
    │   │   ├── Application.java                    # Main application
    │   │   ├── model/
    │   │   │   └── Product.java                    # Entity
    │   │   ├── repository/
    │   │   │   └── ProductRepository.java          # JPA Repository
    │   │   ├── service/
    │   │   │   └── ProductService.java             # Business logic
    │   │   └── controller/
    │   │       └── ProductController.java          # REST endpoints
    │   └── resources/
    │       └── application.properties              # Configuration
    └── test/
        └── java/com/example/
            ├── repository/
            │   └── ProductRepositoryTest.java      # @DataJpaTest
            ├── service/
            │   └── ProductServiceTest.java         # @ExtendWith(Mockito)
            ├── controller/
            │   └── ProductControllerTest.java      # @WebMvcTest
            └── integration/
                └── ProductIntegrationTest.java     # @SpringBootTest
```

## Technologies Used

| Technology | Version | Purpose |
|------------|---------|---------|
| Spring Boot | 3.2.5 | Application framework |
| Spring Data JPA | 3.2.5 | Database access |
| H2 Database | Runtime | In-memory database |
| JUnit 5 | 5.10.2 | Testing framework |
| Mockito | 5.11.0 | Mocking framework |
| Spring Boot Test | 3.2.5 | Spring testing utilities |
| MockMvc | 3.2.5 | REST API testing |

## Running the Application

### Prerequisites
- JDK 17 or higher
- Maven 3.6 or higher

### Start the Application

```bash
cd springboot-testing-demo
mvn spring-boot:run
```

Application starts at: http://localhost:8080

### Access H2 Console

URL: http://localhost:8080/h2-console

**Settings:**
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (empty)

### Run All Tests

```bash
mvn clean test
```

### Run Specific Test Types

```bash
# Repository tests only
mvn test -Dtest=*RepositoryTest

# Service tests only
mvn test -Dtest=*ServiceTest

# Controller tests only
mvn test -Dtest=*ControllerTest

# Integration tests only
mvn test -Dtest=*IntegrationTest
```

## Testing Layers

### Testing Pyramid

```
       /\
      /  \  Integration Tests (few)
     /____\
    /      \
   / Unit   \ Unit Tests (many)
  /__Tests__\
```

### Test Types in This Project

| Test Type | Annotation | Loads | Speed | Purpose |
|-----------|-----------|-------|-------|---------|
| Unit Test | `@ExtendWith(Mockito)` | Nothing | Fast | Test business logic |
| Repository Test | `@DataJpaTest` | JPA layer | Medium | Test database queries |
| Controller Test | `@WebMvcTest` | Web layer | Medium | Test REST endpoints |
| Integration Test | `@SpringBootTest` | Full context | Slow | Test complete flow |

## Test Explanations

### 1. Repository Testing (`@DataJpaTest`)

**File:** `ProductRepositoryTest.java`

```java
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testSaveAndFindProduct() {
        // Arrange
        Product product = new Product("Laptop", "Gaming Laptop", 1500.0);
        
        // Act
        Product saved = productRepository.save(product);
        
        // Assert
        assertNotNull(saved.getId());
        assertEquals("Laptop", saved.getName());
    }
}
```

**Key Features:**
- `@DataJpaTest` - Loads only JPA components
- Auto-configures H2 in-memory database
- Transactional (rolls back after each test)
- `TestEntityManager` - JPA testing utility

**What Gets Loaded:**
- ✅ JPA repositories
- ✅ Entity classes
- ✅ H2 database
- ❌ Controllers
- ❌ Services
- ❌ Full application context

**Testing Custom Queries:**

```java
@Test
void testFindByPriceRange() {
    // Insert test data
    entityManager.persist(new Product("Item1", "Desc1", 100.0));
    entityManager.persist(new Product("Item2", "Desc2", 250.0));
    entityManager.persist(new Product("Item3", "Desc3", 500.0));
    entityManager.flush();
    
    // Test custom query
    List<Product> products = productRepository.findByPriceRange(200.0, 400.0);
    
    assertEquals(1, products.size());
    assertEquals("Item2", products.get(0).getName());
}
```

### 2. Service Testing (Unit Test with Mockito)

**File:** `ProductServiceTest.java`

```java
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testCreateProduct() {
        // Arrange
        Product product = new Product("Test", "Description", 100.0);
        when(productRepository.save(any(Product.class)))
            .thenReturn(product);
        
        // Act
        Product created = productService.createProduct(product);
        
        // Assert
        assertNotNull(created);
        verify(productRepository, times(1)).save(product);
    }
}
```

**Key Features:**
- `@ExtendWith(MockitoExtension.class)` - Enable Mockito
- `@Mock` - Create mock repository
- `@InjectMocks` - Inject mocks into service
- No Spring context loaded (fastest tests)

**Testing Business Logic:**

```java
@Test
void testCreateProductWithNegativePrice() {
    // Arrange
    Product invalidProduct = new Product("Invalid", "Desc", -10.0);
    
    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> {
        productService.createProduct(invalidProduct);
    });
    
    // Verify repository was never called
    verify(productRepository, never()).save(any(Product.class));
}
```

### 3. Controller Testing (`@WebMvcTest`)

**File:** `ProductControllerTest.java`

```java
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateProduct() throws Exception {
        // Arrange
        Product product = new Product("Laptop", "Gaming", 1500.0);
        product.setId(1L);
        when(productService.createProduct(any(Product.class)))
            .thenReturn(product);
        
        // Act & Assert
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(1500.0));
        
        // Verify
        verify(productService, times(1)).createProduct(any(Product.class));
    }
}
```

**Key Features:**
- `@WebMvcTest` - Loads only web layer
- `MockMvc` - Simulates HTTP requests
- `@MockBean` - Mock Spring beans
- `ObjectMapper` - JSON serialization

**What Gets Loaded:**
- ✅ Controllers
- ✅ Web layer beans
- ✅ Request mappings
- ❌ Services (mocked)
- ❌ Repositories
- ❌ Database

**Testing GET Requests:**

```java
@Test
void testGetProduct() throws Exception {
    Product product = new Product("Mouse", "Wireless", 25.0);
    product.setId(1L);
    
    when(productService.getProductById(1L))
        .thenReturn(Optional.of(product));
    
    mockMvc.perform(get("/api/products/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Mouse"))
            .andExpect(jsonPath("$.price").value(25.0));
}
```

**Testing 404 Responses:**

```java
@Test
void testGetProductNotFound() throws Exception {
    when(productService.getProductById(99L))
        .thenReturn(Optional.empty());
    
    mockMvc.perform(get("/api/products/99"))
            .andExpect(status().isNotFound());
}
```

**MockMvc Matchers:**

```java
// Status checks
.andExpect(status().isOk())              // 200
.andExpect(status().isCreated())         // 201
.andExpect(status().isNoContent())       // 204
.andExpect(status().isBadRequest())      // 400
.andExpect(status().isNotFound())        // 404

// JSON path checks
.andExpect(jsonPath("$.id").value(1))
.andExpect(jsonPath("$.name").value("Test"))
.andExpect(jsonPath("$", hasSize(2)))
.andExpect(jsonPath("$[0].name").value("First"))

// Content checks
.andExpect(content().contentType(MediaType.APPLICATION_JSON))
.andExpect(content().string(containsString("success")))
```

### 4. Integration Testing (`@SpringBootTest`)

**File:** `ProductIntegrationTest.java`

```java
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
        // Clean database before each test
        productRepository.deleteAll();
    }

    @Test
    void testFullProductLifecycle() throws Exception {
        // Create product
        Product product = new Product("Test Product", "Description", 99.99);
        
        String response = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        Product created = objectMapper.readValue(response, Product.class);
        
        // Read product
        mockMvc.perform(get("/api/products/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));
        
        // Delete product
        mockMvc.perform(delete("/api/products/" + created.getId()))
                .andExpect(status().isNoContent());
        
        // Verify deleted
        mockMvc.perform(get("/api/products/" + created.getId()))
                .andExpect(status().isNotFound());
    }
}
```

**Key Features:**
- `@SpringBootTest` - Loads complete Spring context
- `@AutoConfigureMockMvc` - Configures MockMvc
- Real database (H2 in-memory)
- Tests complete request-response flow
- Slowest but most comprehensive

**What Gets Loaded:**
- ✅ Full Spring context
- ✅ All controllers
- ✅ All services
- ✅ All repositories
- ✅ Database connection

## REST API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/products` | Create a product |
| GET | `/api/products/{id}` | Get product by ID |
| GET | `/api/products` | Get all products |
| GET | `/api/products/search?name={name}` | Search by name |
| DELETE | `/api/products/{id}` | Delete product |

### Example cURL Commands

```bash
# Create product
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Laptop","description":"Gaming Laptop","price":1500.0}'

# Get all products
curl http://localhost:8080/api/products

# Get product by ID
curl http://localhost:8080/api/products/1

# Search products
curl "http://localhost:8080/api/products/search?name=Laptop"

# Delete product
curl -X DELETE http://localhost:8080/api/products/1
```

## Best Practices

### ✅ Do's

1. **Test at Multiple Levels**
   ```
   Many Unit Tests → Some Integration Tests → Few E2E Tests
   ```

2. **Use Appropriate Test Annotations**
   - `@DataJpaTest` for repositories
   - `@WebMvcTest` for controllers
   - `@SpringBootTest` for integration

3. **Clean Data Between Tests**
   ```java
   @BeforeEach
   void setUp() {
       productRepository.deleteAll();
   }
   ```

4. **Use Test Profiles**
   ```properties
   # src/test/resources/application-test.properties
   spring.jpa.show-sql=true
   spring.jpa.hibernate.ddl-auto=create-drop
   ```

5. **Name Tests Descriptively**
   ```java
   void testGetProductById_WhenProductExists_ReturnsProduct()
   void testCreateProduct_WithNegativePrice_ThrowsException()
   ```

### ❌ Don'ts

1. **Don't Use @SpringBootTest for Everything**
   - Slow startup time
   - Use lighter annotations when possible

2. **Don't Forget Transactional Rollback**
   ```java
   @DataJpaTest  // Already transactional
   @Transactional  // Add if needed for @SpringBootTest
   ```

3. **Don't Test Framework Code**
   ```java
   // ❌ Bad: Testing Spring's behavior
   @Test
   void testRepositorySave() {
       repository.save(entity);
       // Just testing if JPA works
   }
   
   // ✅ Good: Testing your logic
   @Test
   void testBusinessLogicWithSavedEntity() {
       repository.save(entity);
       assertTrue(service.processEntity(entity.getId()));
   }
   ```

## Test Coverage

### Running Coverage Report

```bash
# Generate coverage report
mvn clean test jacoco:report

# Report location
target/site/jacoco/index.html
```

### Add JaCoCo to pom.xml

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## Troubleshooting

### Issue: H2 Console Not Loading

**Solution:** Check `application.properties`:
```properties
spring.h2.console.enabled=true
```

### Issue: Tests Failing with "Table Not Found"

**Solution:** Verify JPA settings:
```properties
spring.jpa.hibernate.ddl-auto=create-drop
```

### Issue: Port Already in Use

**Solution:** Use random port for tests:
```java
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
```

### Issue: JSON Parsing Errors in Tests

**Solution:** Check ObjectMapper configuration and ensure proper Content-Type:
```java
.contentType(MediaType.APPLICATION_JSON)
```

## Further Reading

- [Spring Boot Testing Guide](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
- [Spring MVC Test Framework](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#spring-mvc-test-framework)
- [Testing with @DataJpaTest](https://www.baeldung.com/spring-boot-testing)
- [MockMvc Tutorial](https://www.baeldung.com/integration-testing-in-spring)

## Next Steps

1. Add validation with Bean Validation (@Valid)
2. Implement pagination and sorting
3. Add security testing with Spring Security
4. Explore TestContainers for real database testing
5. Add performance testing with JMeter or Gatling

---

**Happy Testing! 🚀**
