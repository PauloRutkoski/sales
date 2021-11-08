package com.rutkoski.orders.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rutkoski.orders.entities.Order;
import com.rutkoski.orders.entities.OrderProduct;
import com.rutkoski.orders.entities.Person;
import com.rutkoski.orders.entities.Product;
import com.rutkoski.orders.services.OrderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    @MockBean
    private OrderService orderService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    final private String PATH = "/orders";

    private Order example;

    @BeforeEach
    void setup() {
        Person person = new Person(1L, "Test Name", "0321984743");
        Product product1 = new Product(1L, "0001", "Product 1", "Brand 1", 2550.00);
        Product product2 = new Product(2L, "0002", "Product 2", "Brand 1", 250.00);
        Product product3 = new Product(3L, "0003", "Product 3", "Brand 2", 549.90);

        OrderProduct op1 = new OrderProduct(1L, product1, 2.0);
        OrderProduct op2 = new OrderProduct(2L, product2, 4.0);
        OrderProduct op3 = new OrderProduct(3L, product3, 1.0);

        List<OrderProduct> products = Arrays.asList(op1, op2, op3);

        this.example = new Order(1L, person, products);
    }

    @Test
    void findAllNoData() throws Exception {
        Mockito.when(orderService.findAll()).thenReturn(new ArrayList<>());

        RequestBuilder request = MockMvcRequestBuilders.get(PATH);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    void findAllWithContent() throws Exception {
        //Products and Person are no relevant for this test
        Order order1 = new Order(1L, null, null);
        Order order2 = new Order(1L, null, null);
        Order order3 = new Order(1L, null, null);

        List<Order> list = Arrays.asList(order1, order2, order3);
        Mockito.when(orderService.findAll()).thenReturn(list);

        RequestBuilder request = MockMvcRequestBuilders.get(PATH);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(list, this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Order>>() {
        }));
    }

    @Test
    void findByIdSuccess() throws Exception {
        Mockito.when(orderService.load(1L)).thenReturn(this.example);

        RequestBuilder request = MockMvcRequestBuilders.get(PATH+ "/{id}", 1L);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(this.example, this.objectMapper.readValue(result.getResponse().getContentAsString(), Order.class));
    }

    @Test
    void findByIdNotFound() throws Exception {
        Mockito.when(orderService.load(1L)).thenReturn(null);

        RequestBuilder request = MockMvcRequestBuilders.get(PATH+ "/{id}", 1L);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertNull(result.getResponse().getContentType());
    }

    @Test
    void insertSuccess() throws Exception {
        this.example.setId(null);
        Mockito.when(orderService.persist(this.example)).thenReturn(this.example);
        Mockito.when(orderService.validate(any(Order.class))).thenCallRealMethod();

        RequestBuilder request = MockMvcRequestBuilders
                .post(PATH)
                .content(this.objectMapper.writeValueAsString(this.example))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(this.example, this.objectMapper.readValue(result.getResponse().getContentAsString(), Order.class));
    }

    @Test
    void insertWithoutPerson() throws Exception {
        this.example.setPerson(null);
        insertError(this.example);
    }

    @Test
    void insertWithoutProducts() throws Exception {
        this.example.setProducts(null);
        insertError(this.example);
    }

    @Test
    void insertEmptyProducts() throws Exception {
        this.example.setProducts(new ArrayList<>());
        insertError(this.example);
    }

    @Test
    void insertWithProductInvalidQty() throws Exception {
        this.example.getProducts().get(0).setQuantity(0.0);
        insertError(this.example);
    }

    @Test
    void insertWithOPWithoutProduct() throws Exception {
        this.example.getProducts().get(0).setProduct(null);
        insertError(this.example);
    }

    void insertError(Order order) throws Exception {
        Mockito.when(orderService.persist(order)).thenReturn(order);
        Mockito.when(orderService.validate(any(Order.class))).thenCallRealMethod();

        RequestBuilder request = MockMvcRequestBuilders
                .post(PATH)
                .content(this.objectMapper.writeValueAsString(order))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertEquals("", result.getResponse().getContentAsString());
    }

    @Test
    void updateSuccess() throws Exception {
        Mockito.when(orderService.persist(this.example)).thenReturn(this.example);
        Mockito.when(orderService.validate(any(Order.class))).thenCallRealMethod();

        RequestBuilder request = MockMvcRequestBuilders
                .put("/products/{id}", this.example.getId())
                .content(this.objectMapper.writeValueAsString(this.example))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(this.example, this.objectMapper.readValue(result.getResponse().getContentAsString(), Order.class));
    }

    @Test
    void updateWithoutPerson() throws Exception {
        this.example.setPerson(null);
        updateError(this.example);
    }

    @Test
    void updateWithoutProducts() throws Exception {
        this.example.setProducts(null);
        updateError(this.example);
    }

    @Test
    void updateEmptyProducts() throws Exception {
        this.example.setProducts(new ArrayList<>());
        updateError(this.example);
    }

    @Test
    void updateWithProductInvalidQty() throws Exception {
        this.example.getProducts().get(0).setQuantity(0.0);
        updateError(this.example);
    }

    @Test
    void updateWithOPWithoutProduct() throws Exception {
        this.example.getProducts().get(0).setProduct(null);
        updateError(this.example);
    }

    void updateError(Order order) throws Exception {
        Mockito.when(orderService.persist(any(Order.class))).thenReturn(order);
        Mockito.when(orderService.validate(any(Order.class))).thenCallRealMethod();

        RequestBuilder request = MockMvcRequestBuilders
                .put(PATH+ "/{id}", order.getId())
                .content(this.objectMapper.writeValueAsString(order))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertEquals("", result.getResponse().getContentAsString());
    }
}