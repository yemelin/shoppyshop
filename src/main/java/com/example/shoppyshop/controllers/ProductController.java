package com.example.shoppyshop.controllers;

import com.example.shoppyshop.config.NullAwareBeanUtilsBean;
import com.example.shoppyshop.dto.ProductCreateRequestDto;
import com.example.shoppyshop.dto.ProductShortResponseDto;
import com.example.shoppyshop.dto.ProductResponseDto;
import com.example.shoppyshop.dto.ProductUpdateRequestDto;
import com.example.shoppyshop.exceptions.InternalErrorException;
import com.example.shoppyshop.exceptions.NotFoundException;
import com.example.shoppyshop.models.CategoryRepository;
import com.example.shoppyshop.models.Product;
import com.example.shoppyshop.models.ProductRepository;
import com.example.shoppyshop.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("admin/api/products")
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    // TODO: check if not-nil category ID is validated
    @PostMapping
    public ProductResponseDto create(@RequestBody @Valid ProductCreateRequestDto pdto) {
        Product product = modelMapper.map(pdto, Product.class);
        return modelMapper.map(productService.save(product), ProductResponseDto.class);
//        if(pdto.getCategoryId() != null) {
//            p.setCategory(categoryRepository.findById(pdto.getCategoryId()).orElseThrow(()->new NotFoundException(pdto.getCategoryId())));
//        }
//        return modelMapper.map(productRepository.save(p), ProductResponseDto.class);
    }

    @RolesAllowed("ADMIN")
    @GetMapping
    public List<ProductShortResponseDto> findAll() {
        List<ProductShortResponseDto> result = new ArrayList<>();
        productService.findAll().forEach(p -> result.add(modelMapper.map(p, ProductShortResponseDto.class)));
        return result;
//        return StreamSupport.stream(productRepository.findAll().spliterator(), false).map((Product prod) -> modelMapper.map(prod, ProductShortDto.class)).collect(Collectors.toList());
        }

    @GetMapping("/{id}")
    public ProductResponseDto findById(@PathVariable Long id) {
        return modelMapper.map(productService.findById(id), ProductResponseDto.class);
    }

    @PatchMapping("/{id}")
    public ProductResponseDto partialUpdate(@PathVariable Long id, @RequestBody ProductUpdateRequestDto pdto) {
        Product product = modelMapper.map(pdto, Product.class);
        return modelMapper.map(productService.partialUpdate(id, product), ProductResponseDto.class);

//        Product p = productRepository.findByIdFetchCategory(id).orElseThrow(()-> new NotFoundException(id));
//        try {
//            utilsBean.copyProperties(p, pdto);
//        }
//        catch( InvocationTargetException | IllegalAccessException e){
//            throw new InternalErrorException();
//        }
//        System.out.println(pdto);
//        if(pdto.getCategoryId() != null) {
//            System.out.println(pdto);
//            p.setCategory(categoryRepository.findById(pdto.getCategoryId()).orElseThrow(()->new NotFoundException(pdto.getCategoryId())));
//        }
//        return modelMapper.map(productRepository.save(p), ProductResponseDto.class);
    }


}
