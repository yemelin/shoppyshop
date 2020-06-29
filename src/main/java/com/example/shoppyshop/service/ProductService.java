package com.example.shoppyshop.service;

import com.example.shoppyshop.helpers.NullAwareBeanUtilsBean;
import com.example.shoppyshop.models.Category;
import com.example.shoppyshop.models.Product;
import com.example.shoppyshop.models.ProductRepository;

import org.springframework.stereotype.Service;

@Service
public class ProductService extends AbstractCrudService<Product, ProductRepository>{
    private final CategoryService categoryService;

    public ProductService(ProductRepository repo, CategoryService categoryService, NullAwareBeanUtilsBean utilsBean) {
        super(repo, utilsBean);
        this.categoryService = categoryService;
    }

    @Override
    public Product save(Product product) {
        product.setCategory(categoryService.findById(product.getCategory().getId()));
        return super.save(product);
    }

    @Override
    public Product partialUpdate(Long id, Product product) {
        Category category = product.getCategory();
        product.setCategory(null); // so that NullAwareBeanUtilsBean should ignore this field
        Product productEntity = updatedEntity(id, product, true);
        if (category != null) {
            productEntity.setCategory(category);
        }
        return repo.save(productEntity);
    }
}
