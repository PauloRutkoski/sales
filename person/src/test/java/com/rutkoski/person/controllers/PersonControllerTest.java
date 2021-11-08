package com.rutkoski.person.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rutkoski.person.entities.Person;
import com.rutkoski.person.service.PersonService;
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
class PersonControllerTest {
    @MockBean
    private PersonService personService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;


    @Test
    void findAllNoData() throws Exception {
        Mockito.when(personService.findAll()).thenReturn(new ArrayList<>());

        RequestBuilder request = MockMvcRequestBuilders.get("/person");
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    void findAllWithContent() throws Exception {
        Person p1 = new Person(1L, "Name 1", "00000000000");
        Person p2 = new Person(2L, "Name 2", "11111111111");
        Person p3 = new Person(3L, "Name 3", "22222222222");
        List<Person> list = Arrays.asList(p1, p2, p3);
        Mockito.when(personService.findAll()).thenReturn(list);

        RequestBuilder request = MockMvcRequestBuilders.get("/person");
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(list, this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Person>>() {
        }));
    }

    @Test
    void findByIdSuccess() throws Exception {
        Person p1 = new Person(1L, "Name 1", "00000000000");
        Mockito.when(personService.load(1L)).thenReturn(p1);

        RequestBuilder request = MockMvcRequestBuilders.get("/person/{id}", 1L);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(p1, this.objectMapper.readValue(result.getResponse().getContentAsString(), Person.class));
    }

    @Test
    void findByIdNotFound() throws Exception {
        Mockito.when(personService.load(1L)).thenReturn(null);

        RequestBuilder request = MockMvcRequestBuilders.get("/person/{id}", 1L);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertNull(result.getResponse().getContentType());
    }

    @Test
    void insertSuccess() throws Exception {
        Person p = new Person(null, "Name 1", "00000000000");
        Mockito.when(personService.persist(p)).thenReturn(p);
        Mockito.when(personService.valid(any(Person.class))).thenCallRealMethod();

        RequestBuilder request = MockMvcRequestBuilders
                .post("/person")
                .content(this.objectMapper.writeValueAsString(p))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(p, this.objectMapper.readValue(result.getResponse().getContentAsString(), Person.class));
    }

    @Test
    void insertWithoutName() throws Exception {
        Person p1 = new Person(null, null, "00000000000");
        insertError(p1);
    }

    @Test
    void insertWithEmptyName() throws Exception {
        Person p1 = new Person(null, "", "00000000000");
        insertError(p1);
    }

    @Test
    void insertWithoutDocument() throws Exception {
        Person p1 = new Person(null, "Name 1", null);
        insertError(p1);
    }

    @Test
    void insertWithShortDocument() throws Exception {
        Person p1 = new Person(null, "Name 1", "000");
        insertError(p1);
    }

    @Test
    void insertWithShortestDocumentSuccess() throws Exception {
        Person p = new Person(null, "Name 1", "0000");
        Mockito.when(personService.persist(p)).thenReturn(p);
        Mockito.when(personService.valid(any(Person.class))).thenCallRealMethod();

        RequestBuilder request = MockMvcRequestBuilders
                .post("/person")
                .content(this.objectMapper.writeValueAsString(p))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(p, this.objectMapper.readValue(result.getResponse().getContentAsString(), Person.class));
    }

    void insertError(Person p1) throws  Exception{
        Mockito.when(personService.persist(any(Person.class))).thenReturn(p1);
        Mockito.when(personService.valid(any(Person.class))).thenCallRealMethod();

        RequestBuilder request = MockMvcRequestBuilders
                .post("/person")
                .content(this.objectMapper.writeValueAsString(p1))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertEquals("", result.getResponse().getContentAsString());    }

    @Test
    void updateSuccess() throws Exception {
        Person p = new Person(1L, "Name 1", "00000000000");
        Mockito.when(personService.persist(p)).thenReturn(p);
        Mockito.when(personService.valid(any(Person.class))).thenCallRealMethod();

        RequestBuilder request = MockMvcRequestBuilders
                .put("/person/{id}", p.getId())
                .content(this.objectMapper.writeValueAsString(p))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(p, this.objectMapper.readValue(result.getResponse().getContentAsString(), Person.class));
    }

    @Test
    void updateWithoutName() throws Exception {
        Person p = new Person(1L, null, "00000000000");
        updateError(p);
    }

    @Test
    void updateWithEmptyName() throws Exception {
        Person p = new Person(1L, "", "00000000000");
        updateError(p);
    }

    @Test
    void updateWithoutDocument() throws Exception {
        Person p = new Person(1L, "Name 1", null);
        updateError(p);
    }

    @Test
    void updateWithShortDocument() throws Exception {
        Person p = new Person(1L, "Name 1", "000");
        updateError(p);
    }

    @Test
    void updateWithShortestDocumentSuccess() throws Exception {
        Person p = new Person(1L, "Name 1", "0000");
        Mockito.when(personService.persist(p)).thenReturn(p);
        Mockito.when(personService.valid(any(Person.class))).thenCallRealMethod();

        RequestBuilder request = MockMvcRequestBuilders
                .put("/person/{id}", p.getId())
                .content(this.objectMapper.writeValueAsString(p))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(p, this.objectMapper.readValue(result.getResponse().getContentAsString(), Person.class));
    }

    void updateError(Person p) throws Exception{
        Mockito.when(personService.persist(p)).thenReturn(p);
        Mockito.when(personService.valid(any(Person.class))).thenCallRealMethod();

        RequestBuilder request = MockMvcRequestBuilders
                .put("/person/{id}", p.getId())
                .content(this.objectMapper.writeValueAsString(p))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertEquals("", result.getResponse().getContentAsString());
    }
}