package com.example.shoppyshop.controllers;

import com.example.shoppyshop.config.NullAwareBeanUtilsBean;
import com.example.shoppyshop.dto.ProductShortDto;
import com.example.shoppyshop.dto.SingleProductDto;
import com.example.shoppyshop.dto.ProductUpdateDto;
import com.example.shoppyshop.exceptions.InternalErrorException;
import com.example.shoppyshop.exceptions.NotFoundException;
import com.example.shoppyshop.models.CategoryRepository;
import com.example.shoppyshop.models.Product;
import com.example.shoppyshop.models.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("admin/api/products")
public class ProductController {
    private ProductRepository productRepository;
    private ModelMapper modelMapper;
    private NullAwareBeanUtilsBean utilsBean;
    private CategoryRepository categoryRepository;

    public ProductController(ProductRepository productRepository, ModelMapper modelMapper, NullAwareBeanUtilsBean utilsBean, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.utilsBean = utilsBean;
        this.categoryRepository = categoryRepository;
    }

    @PostMapping
    public SingleProductDto create(@RequestBody ProductUpdateDto pdto) {
        Product p = modelMapper.map(pdto, Product.class);
        if(pdto.getCategoryId() != null) {
            p.setCategory(categoryRepository.findById(pdto.getCategoryId()).orElseThrow(()->new NotFoundException(pdto.getCategoryId())));
        }
        return modelMapper.map(productRepository.save(p), SingleProductDto.class);
    }

    @RolesAllowed("ADMIN")
    @GetMapping
    public List<ProductShortDto> findAll() {
        List<ProductShortDto> result = new ArrayList<>();
        productRepository.findAll().forEach(p -> result.add(modelMapper.map(p, ProductShortDto.class)));
        return result;
//        return StreamSupport.stream(productRepository.findAll().spliterator(), false).map((Product prod) -> modelMapper.map(prod, ProductShortDto.class)).collect(Collectors.toList());
        }

    @GetMapping("/{id}")
    public SingleProductDto findById(@PathVariable Long id) {
        return modelMapper.map(productRepository.findByIdFetchCategory(id).orElseThrow(()->new NotFoundException(id)), SingleProductDto.class);
    }

    @PatchMapping("/{id}")
    public SingleProductDto partialUpdate(@PathVariable Long id, @RequestBody ProductUpdateDto pdto) {

        Product p = productRepository.findByIdFetchCategory(id).orElseThrow(()-> new NotFoundException(id));
        try {
            utilsBean.copyProperties(p, pdto);
        }
        catch( InvocationTargetException | IllegalAccessException e){
            throw new InternalErrorException();
        }
        System.out.println(pdto);
        if(pdto.getCategoryId() != null) {
            System.out.println(pdto);
            p.setCategory(categoryRepository.findById(pdto.getCategoryId()).orElseThrow(()->new NotFoundException(pdto.getCategoryId())));
        }
        return modelMapper.map(productRepository.save(p), SingleProductDto.class);
    }


}
