package com.example.bidding_interface2.Model;

import java.io.Serializable;
import java.text.NumberFormat;

public class ShoppingItem implements Serializable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name , type, description, productID;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getPrice() {
        return NumberFormat.getCurrencyInstance().format(price);
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int price, quantity;
    public ShoppingItem()
    {}

    public ShoppingItem(String productId, String name, String type, String description, int price, int quantity)
    {
        this.productID = productId;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
}
