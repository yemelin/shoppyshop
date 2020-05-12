package com.example.shoppyshop.models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {
    @Query("select p from Product p left join fetch p.category where p.id=:id")
    Optional<Product> findByIdFetchCategory(@Param("id") Long id);
}
