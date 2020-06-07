package com.example.shoppyshop.service;

import com.example.shoppyshop.config.NullAwareBeanUtilsBean;
import com.example.shoppyshop.models.Category;
import com.example.shoppyshop.models.CategoryRepository;

import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractCrudService <Category, CategoryRepository>{

    public CategoryService(CategoryRepository repo, NullAwareBeanUtilsBean utilsBean) {
        super(repo, utilsBean);
    }
}
