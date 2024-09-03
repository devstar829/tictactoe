package com.telusko.simpleWebApp.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.telusko.simpleWebApp.model.Product;

@Service
public class ProductService {

    List<Product> products = Arrays.asList(new Product(101, "iphone", 50000),
            new Product(102, "android", 70));

    public List<Product> getProducts() {
        return products;
    }
}
