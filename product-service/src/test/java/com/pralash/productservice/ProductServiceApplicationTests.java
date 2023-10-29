package com.pralash.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pralash.productservice.dto.ProductRequest;
import com.pralash.productservice.dto.ProductResponse;
import com.pralash.productservice.model.Product;
import com.pralash.productservice.repo.ProductRepository;
import static org.junit.jupiter.api.Assertions.*;

import com.pralash.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductServiceApplicationTests {

	@Container
	static MySQLContainer mySQLContainer=new MySQLContainer<>("mysql:5.7");
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductService productService;
	static void setProperty(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.datasource.url",mySQLContainer::getJdbcUrl);
	}
	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestString))
				.andExpect(status().isCreated());
	}
	@Test
	void shouldGetAllProduct() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/product")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		// Create and save test data to the repository
		Product product1 = Product.builder().name("Iphone 14").description("Apple Mobile").price(BigDecimal.valueOf(1000)).build();
		productRepository.save(product1);

		Product product2 = Product.builder().name("Samsung S23").description("Samsung Mobile").price(BigDecimal.valueOf(900)).build();
		productRepository.save(product2);

		// Get the result from the service
		List<ProductResponse> productResponses = productService.getAllProduct();

		// Verify that the result matches the expected data
		assertEquals(2, productResponses.size());
		assertEquals("Iphone 14", productResponses.get(0).getName());
		assertEquals("Samsung S23", productResponses.get(1).getName());
	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("Iphone 13")
				.description("Apple Phone")
				.price(BigDecimal.valueOf(1200))
				.build();
	}

}
