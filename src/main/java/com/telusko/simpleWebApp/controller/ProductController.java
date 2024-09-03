package com.telusko.simpleWebApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telusko.simpleWebApp.model.Product;
import com.telusko.simpleWebApp.service.ProductService;

@RestController
public class ProductController {
    @Autowired
    ProductService service;

    @RequestMapping("/products")
    public List<Product> getProducts() {
        return service.getProducts();
    }
}
