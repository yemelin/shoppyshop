package com.example.shoppyshop.service;

import com.example.shoppyshop.models.Product;
import com.example.shoppyshop.models.ProductRepository;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Component;

@Component
public class ProductGraphQLService {

    private final ProductRepository productRepository;

    public ProductGraphQLService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GraphQLQuery(name = "products")
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }
}
