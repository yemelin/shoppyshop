package com.example.shoppyshop.service;

import com.example.shoppyshop.models.Category;
import com.example.shoppyshop.models.CategoryRepository;
import com.example.shoppyshop.models.Product;
import com.example.shoppyshop.models.ProductRepository;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Component;

@Component
public class CategoryGraphQLService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryGraphQLService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @GraphQLQuery(name = "categories")
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    @GraphQLQuery
    public Iterable<Product> products(@GraphQLContext Category category) {
        return productRepository.findByCategoryId(category.getId());
    }
}
