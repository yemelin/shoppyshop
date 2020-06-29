package com.example.shoppyshop.controllers;

import com.example.shoppyshop.models.Category;
import com.example.shoppyshop.models.CategoryRepository;
import com.example.shoppyshop.models.Product;
import com.example.shoppyshop.models.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class GraphQLControllerTest {

    private static final String productQuery =
            "query products {\n" +
                    "   products {\n" +
                    "        price\n" +
                    "        name\n" +
                    "   }" +
                    "}";
    private static final String categoriesQuery =
            "query categories {\n" +
                    "        categories {\n" +
                    "        name\n" +
                    "   }" +
                    "}";
    private static final String categoriesWithProductsQuery =
            "query categories {\n" +
                    "       categories {\n" +
                    "        id" +
                    "        name" +
                    "        products {" +
                    "           name" +
                    "        }" +
                    "   }" +
                    "}";

    private static final Map<String, String> productsRequest, categoriesRequest, categoriesWithProductsRequest;

    static {
        productsRequest = new HashMap<>();
        productsRequest.put("operationName", "products");
        productsRequest.put("query", productQuery);

        categoriesRequest = new HashMap<>();
        categoriesRequest.put("operationName", "categories");
        categoriesRequest.put("query", categoriesQuery);

        categoriesWithProductsRequest = new HashMap<>();
        categoriesWithProductsRequest.put("operationName", "categories");
        categoriesWithProductsRequest.put("query", categoriesWithProductsQuery);
    }

    private String reqToString(Map<String, String> req) {
        try {
            return new ObjectMapper().writeValueAsString(req);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    // TODO: compare output jsons properly, perhaps with XPath?
    @Test
    void graphQLControllerTest(@Autowired MockMvc mvc) throws Exception {
        Category c1 = new Category();
        c1.setName("test category 1");
        Category c2 = new Category();
        c2.setName("test category 2");
        c1 = categoryRepository.save(c1);
        categoryRepository.save(c2);
        Product p1 = new Product();
        p1.setName("my fancy product 1");
        p1.setPrice(100);
        p1.setCategory(c1);
        Product p2 = new Product();
        p2.setName("my fancy product 2");
        p2.setPrice(200);
        p2.setCategory(c1);
        productRepository.save(p1);
        productRepository.save(p2);

        String expCategoriesWithProducts = String.format("{\"data\":{\"categories\":[{\"id\":%s,\"name\":\"test category 1\"," +
                        "\"products\":[{\"name\":\"my fancy product 1\"},{\"name\":\"my fancy product 2\"}]}," +
                        "{\"id\":%s,\"name\":\"test category 2\",\"products\":[]}]}}",
                c1.getId(), c2.getId());
        String expCategories = "{\"data\":{\"categories\":[{\"name\":\"test category 1\"},{\"name\":\"test category 2\"}]}}";
        String expProducts = "{\"data\":{\"products\":[{\"price\":100,\"name\":\"my fancy product 1\"},{\"price\":200,\"name\":\"my fancy product 2\"}]}}";

        mvc.perform(post("/api/shittyjson")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqToString(categoriesWithProductsRequest))).
                andExpect(status().isOk()).
                andExpect(content().string(expCategoriesWithProducts));

        mvc.perform(post("/api/shittyjson")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqToString(categoriesRequest))).
                andExpect(status().isOk()).
                andExpect(content().string(expCategories));

        mvc.perform(post("/api/shittyjson")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqToString(productsRequest))).
                andExpect(status().isOk()).
                andExpect(content().string(expProducts));

    }
}
