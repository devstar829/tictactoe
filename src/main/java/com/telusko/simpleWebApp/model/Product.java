package com.telusko.simpleWebApp.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;

@Component
public class Product {

    private int prodId;
    private String prodName;
    private int price;

    public Product() {

    }

    public Product(int prodId, String prodName, int price) {
        this.prodId = prodId;
        this.prodName = prodName;
        this.price = price;
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
