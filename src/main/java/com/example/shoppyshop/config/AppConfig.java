package com.example.shoppyshop.config;

import com.example.shoppyshop.service.CategoryGraphQLService;
import com.example.shoppyshop.service.ProductGraphQLService;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public GraphQL graphQL(ProductGraphQLService productGraphQLService, CategoryGraphQLService categoryGraphQLService) {
        //Schema generated from query classes
        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withBasePackages("com.example.shoppyshop")
                .withOperationsFromSingletons(productGraphQLService, categoryGraphQLService)
                .generate();
        return GraphQL.newGraphQL(schema).build();
    }
}
