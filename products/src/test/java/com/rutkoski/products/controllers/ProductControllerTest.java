package com.rutkoski.products.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rutkoski.products.entities.Product;
import com.rutkoski.products.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @MockBean
    private ProductService productService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAllNoData() throws Exception {
        Mockito.when(productService.findAll()).thenReturn(new ArrayList<>());

        RequestBuilder request = MockMvcRequestBuilders.get("/products");
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    void findAllWithContent() throws Exception {
        Product p1 = new Product(1L, "0001", "Product 1", "Brand 1", 2500.00);
        Product p2 = new Product(2L, "0002", "Product 2", "Brand 2", 3000.00);
        Product p3 = new Product(3L, "0003", "Product 3", "Brand 3", 2000.00);
        List<Product> list = Arrays.asList(p1, p2, p3);
        Mockito.when(productService.findAll()).thenReturn(list);

        RequestBuilder request = MockMvcRequestBuilders.get("/products");
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(list, this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Product>>() {
        }));
    }

    @Test
    void findByIdSuccess() throws Exception {
        Product p1 = new Product(1L, "0001", "Product 1", "Brand 1", 2500.00);
        Mockito.when(productService.load(1L)).thenReturn(p1);

        RequestBuilder request = MockMvcRequestBuilders.get("/products/{id}", 1L);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(p1, this.objectMapper.readValue(result.getResponse().getContentAsString(), Product.class));
    }

    @Test
    void findByIdNotFound() throws Exception {
        Mockito.when(productService.load(1L)).thenReturn(null);

        RequestBuilder request = MockMvcRequestBuilders.get("/products/{id}", 1L);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertNull(result.getResponse().getContentType());
    }

    @Test
    void insertSuccess() throws Exception {
        Product p = new Product(null, "0001", "Product 1", "Brand 1", 2500.00);
        Mockito.when(productService.persist(p)).thenReturn(p);
        Mockito.when(productService.validate(any(Product.class))).thenCallRealMethod();

        RequestBuilder request = MockMvcRequestBuilders
                .post("/products")
                .content(this.objectMapper.writeValueAsString(p))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(p, this.objectMapper.readValue(result.getResponse().getContentAsString(), Product.class));
    }

    @Test
    void insertWithoutRef() throws Exception {
        Product p = new Product(null, null, "Product 1", "Brand 1", 2500.00);
        insertError(p);
    }

    @Test
    void insertEmptyRef() throws Exception {
        Product p = new Product(null, "", "Product 1", "Brand 1", 2500.00);
        insertError(p);
    }

    @Test
    void insertWithoutName() throws Exception {
        Product p = new Product(null, "0001", null, "Brand 1", 2500.00);
        insertError(p);
    }

    @Test
    void insertEmptyName() throws Exception {
        Product p = new Product(null, "0001", "", "Brand 1", 2500.00);
        insertError(p);
    }

    @Test
    void insertWithoutBrand() throws Exception {
        Product p = new Product(null, "0001", "Product 1", null, 2500.00);
        insertError(p);
    }

    @Test
    void insertEmptyBrand() throws Exception {
        Product p = new Product(null, "0001", "Product 1", "", 2500.00);
        insertError(p);
    }

    @Test
    void insertWithoutPrice() throws Exception {
        Product p = new Product(null, "0001", "Product 1", "Brand 1", null);
        insertError(p);
    }

    @Test
    void insertZeroPrice() throws Exception {
        Product p = new Product(null, "0001", "Product 1", "Brand 1", 0.0);
        insertError(p);
    }

    void insertError(Product p) throws Exception {
        Mockito.when(productService.persist(p)).thenReturn(p);
        Mockito.when(productService.validate(any(Product.class))).thenCallRealMethod();

        RequestBuilder request = MockMvcRequestBuilders
                .post("/products")
                .content(this.objectMapper.writeValueAsString(p))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertEquals("", result.getResponse().getContentAsString());
    }

    @Test
    void updateSuccess() throws Exception {
        Product p = new Product(1L, "0001", "Product 1", "Brand 1", 2500.00);
        Mockito.when(productService.persist(p)).thenReturn(p);
        Mockito.when(productService.validate(any(Product.class))).thenCallRealMethod();

        RequestBuilder request = MockMvcRequestBuilders
                .put("/products/{id}", p.getId())
                .content(this.objectMapper.writeValueAsString(p))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(p, this.objectMapper.readValue(result.getResponse().getContentAsString(), Product.class));
    }

    @Test
    void updateWithoutRef() throws Exception {
        Product p = new Product(1L, null, "Product 1", "Brand 1", 2500.00);
        this.updateError(p);
    }

    @Test
    void updateEmptyRef() throws Exception {
        Product p = new Product(1L, "", "Product 1", "Brand 1", 2500.00);
        this.updateError(p);
    }

    @Test
    void updateWithoutName() throws Exception {
        Product p = new Product(1L, "0001", null, "Brand 1", 2500.00);
        this.updateError(p);
    }

    @Test
    void updateEmptyName() throws Exception {
        Product p = new Product(1L, "0001", "", "Brand 1", 2500.00);
        this.updateError(p);
    }

    @Test
    void updateWithoutBrand() throws Exception {
        Product p = new Product(1L, "0001", "Product 1", null, 2500.00);
        this.updateError(p);
    }

    @Test
    void updateEmptyBrand() throws Exception {
        Product p = new Product(1L, "0001", "Product 1", "", 2500.00);
        this.updateError(p);
    }

    @Test
    void updateWithoutPrice() throws Exception {
        Product p = new Product(1L, "0001", "Product 1", "Brand 1", null);
        this.updateError(p);
    }

    @Test
    void updateZeroPrice() throws Exception {
        Product p = new Product(1L, "0001", "Product 1", "Brand 1", 0.0);
        this.updateError(p);
    }

    void updateError(Product p) throws Exception {
        Mockito.when(productService.persist(p)).thenReturn(p);
        Mockito.when(productService.validate(any(Product.class))).thenCallRealMethod();

        RequestBuilder request = MockMvcRequestBuilders
                .put("/products/{id}", p.getId())
                .content(this.objectMapper.writeValueAsString(p))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertEquals("", result.getResponse().getContentAsString());
    }
}