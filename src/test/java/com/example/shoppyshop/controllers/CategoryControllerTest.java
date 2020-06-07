package com.example.shoppyshop.controllers;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Test
    @WithMockUser(roles = "ADMIN")
    void findCategoriesTest(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/admin/api/categories")).
                andExpect(status().isOk()).
                andExpect(content().string("[{\"name\":\"test category 1\"},{\"name\":\"test category 2\"}]"));
    }

    @Test
    void categoriesTest_Unauthorized(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/admin/api/categories")).
                andExpect(status().isForbidden());
        mvc.perform(get("/admin/api/categories/1")).
                andExpect(status().isForbidden());
        mvc.perform(post("/admin/api/categories").
                content("{\"name\":\"test category 3\"}").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void findByIdTest(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/admin/api/categories/1")).
                andExpect(status().isOk()).
                andExpect(content().string("{\"name\":\"test category 1\"}"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void findByIdTest_NotFound(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/admin/api/categories/0")).
                andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Transactional
    void createTest(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(post("/admin/api/categories").
                content("{\"name\":\"test category 3\"}").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(content().string("{\"name\":\"test category 3\"}"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Transactional
    void updateTest(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(put("/admin/api/categories/1").
                content("{\"name\":\"test category 48\"}").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(content().string("{\"name\":\"test category 48\"}"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Transactional
    void partialUpdateTest(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(put("/admin/api/categories/1").
                content("{\"name\":\"test category 48\"}").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(content().string("{\"name\":\"test category 48\"}"));    }
}