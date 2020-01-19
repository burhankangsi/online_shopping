package com.example.bidding_interface2.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties
public class Single_Bid_Item implements Serializable {
    private String item_name;
    private String description;
    private String id;

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

    private String category;
    private Integer quantity;


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

    private String seller;

//    public int getItem_add_to_cart() {
//        return item_add_to_cart;
//    }
//
//    public void setItem_add_to_cart(int item_add_to_cart) {
//        this.item_add_to_cart = item_add_to_cart;
//    }

   // private int item_add_to_cart;

    @JsonProperty("name")
    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    @JsonProperty("price")
    public int getItem_price() {
        return item_price;
    }

    public void setItem_price(int item_price) {
        this.item_price = item_price;
    }


    private int item_price;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;

    public Single_Bid_Item()
    {
    }
    public Single_Bid_Item(String item_name, String thumbnail, int price, String item_desc, String seller )
    {
        this.item_name = item_name;
        this.image = thumbnail;
        this.item_price = price;
        this.description = item_desc;
        this.seller = seller;

        //this.item_add_to_cart = add_to_cart;
    }

    public Single_Bid_Item(String name, String image, int price){
        this.item_name = name;
        this.image = image;
        this.item_price = price;
    }

    public Single_Bid_Item(String name, String image, int price, String description, String seller, String id, String category)
    {
        this.item_name = name;
        this.image = image;
        this.item_price = price;
        this.description = description;
        this.seller = seller;
        this.id = id;
        this.category = category;

    }
    public Single_Bid_Item(String name, String image, int price, String description, String seller, String id, Integer quantity){
        this.item_name = name;
        this.image = image;
        this.item_price = price;
        this.description = description;
        this.seller = seller;
        this.id = id;
        this.quantity = quantity;
    }

}
