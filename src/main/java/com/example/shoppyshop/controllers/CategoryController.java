package com.example.shoppyshop.controllers;

import com.example.shoppyshop.config.NullAwareBeanUtilsBean;
import com.example.shoppyshop.dto.CategoryCreateDto;
import com.example.shoppyshop.dto.CategoryUpdateDto;
import com.example.shoppyshop.exceptions.InternalErrorException;
import com.example.shoppyshop.exceptions.NotFoundException;
import com.example.shoppyshop.models.Category;
import com.example.shoppyshop.models.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("admin/api/categories")
public class CategoryController {
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;
    private NullAwareBeanUtilsBean utilsBean;

    public CategoryController(CategoryRepository categoryRepository, ModelMapper modelMapper, NullAwareBeanUtilsBean utilsBean) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.utilsBean = utilsBean;
    }

    @GetMapping
    public List<Category> findCategories() {return this.categoryRepository.findAll();}

    @GetMapping("/{id}")
    public Category findById(@PathVariable long id) {
        return categoryRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    @PostMapping
    public Category create(@RequestBody CategoryCreateDto c) {
        return categoryRepository.save(modelMapper.map(c, Category.class));
    }

    @PutMapping("/{id}")
    public Category update(@RequestBody CategoryUpdateDto cdto, @PathVariable Long id) {
        Category cat = categoryRepository.findById(id).orElseThrow(()->new NotFoundException(id));

        try {
            utilsBean.copyProperties(cat, cdto);
        }
        catch( InvocationTargetException | IllegalAccessException e){
            throw new InternalErrorException();
        }
        return categoryRepository.save(cat);
    }
}
