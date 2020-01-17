package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.page.ProductPageRequest;
import com.example.demo.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ProductService extends IService<Product> {

    Product get(Long id);

    Product getBuyer(Long id);

    Page<Product> list(ProductPageRequest pageRequest);

    Page<Product> listBuyer(ProductPageRequest pageRequest);

    Long add(ProductDto productDto);

    void update(Long id, ProductDto productDto);

    void delete(Long id);
}