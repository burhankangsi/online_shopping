package com.example.bidding_interface2.Model;

import java.io.Serializable;

public class ProductClass implements Serializable {
private String name;
private String image;
private int price;
private String description;
private String seller;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    private String id;
private String category;
private Integer quantity;

public ProductClass()
        {}

public ProductClass(String name, String image, int price){
        this.name = name;
        this.image = image;
        this.price = price;
        }
public ProductClass(String name, String image, int price, String description, String seller){
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.seller = seller;
        }
public ProductClass(String name, String image, int price, String description, String seller, String id, String category)
        {
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.seller = seller;
        this.id = id;
        this.category = category;

        }
   public ProductClass(String name, String image, int price, String description, String seller, String id, Integer quantity){
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.seller = seller;
        this.id = id;
        this.quantity = quantity;
    }


        }
