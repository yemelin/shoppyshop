package com.example.shoppyshop.controllers;

import javax.transaction.Transactional;

import com.example.shoppyshop.models.Category;
import com.example.shoppyshop.models.CategoryRepository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CategoryControllerTest {

    @Autowired
    private CategoryRepository repo;

    @Test
    @WithMockUser(roles = "ADMIN")
    void findCategoriesTest(@Autowired MockMvc mvc) throws Exception {
        Category c1 = new Category();
        c1.setName("test category 1");
        Category c2 = new Category();
        c2.setName("test category 2");
        repo.save(c1);
        repo.save(c2);

        mvc.perform(get("/admin/api/categories")).
                andExpect(status().isOk()).
                andExpect(content().string("[{\"name\":\"test category 1\"},{\"name\":\"test category 2\"}]"));
    }

    @Test
    void categoriesTest_Unauthorized(@Autowired MockMvc mvc) throws Exception {
        Category c1 = new Category();
        c1.setName("test category 1");
        Category c2 = new Category();
        c2.setName("test category 2");
        c1 = repo.save(c1);
        repo.save(c2);

        mvc.perform(get("/admin/api/categories")).
                andExpect(status().isForbidden());
        mvc.perform(get("/admin/api/categories/" + c1.getId())).
                andExpect(status().isForbidden());
        mvc.perform(post("/admin/api/categories").
                content("{\"name\":\"test category 3\"}").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void findByIdTest(@Autowired MockMvc mvc) throws Exception {
        Category c1 = new Category();
        c1.setName("test category 1");
        c1 = repo.save(c1);

        mvc.perform(get("/admin/api/categories/" + c1.getId())).
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
    void createTest(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(post("/admin/api/categories").
                content("{\"name\":\"test category 3\"}").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(content().string("{\"name\":\"test category 3\"}"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateTest(@Autowired MockMvc mvc) throws Exception {
        Category c1 = new Category();
        c1.setName("test category 1");
        c1 = repo.save(c1);

        mvc.perform(put("/admin/api/categories/" + c1.getId()).
                content("{\"name\":\"test category 48\"}").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(content().string("{\"name\":\"test category 48\"}"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void partialUpdateTest(@Autowired MockMvc mvc) throws Exception {
        Category c1 = new Category();
        c1.setName("test category 1");
        c1 = repo.save(c1);

        mvc.perform(put("/admin/api/categories/" + c1.getId()).
                content("{\"name\":\"test category 48\"}").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(content().string("{\"name\":\"test category 48\"}"));    }
}