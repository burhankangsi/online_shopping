package com.example.bidding_interface2.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties
public class BargainProduct extends ProductClass implements Serializable {
    private String name;

    public BargainProduct(String item_name, String image, int item_price, int parseInt, String description,
                          String seller, int position, String id, String s, int position1, int position2, long currentTimeMillis) {
        this.name = item_name;
        this.image = image;
        this.price = item_price;
        this.bidValue = parseInt;
        this.description = description;
        this.seller = seller;
        this.status = position;
        this.id = id;
        this.additionals = s;
        this.finalprice = position1;
        this.quantity = position2;
        this.timestamp = currentTimeMillis;
    }


    @Override
    public String getImage() {
        return (image);
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;
    private int price;
    private Integer bidValue;


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    public Integer getBidValue() {
        return bidValue;
    }

    public void setBidValue(Integer bidValue) {
        this.bidValue = bidValue;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getSeller() {
        return seller;
    }

    @Override
    public void setSeller(String seller) {
        this.seller = seller;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdditionals() {
        return additionals;
    }

    public void setAdditionals(String additionals) {
        this.additionals = additionals;
    }

    public Integer getFinalprice() {
        return finalprice;
    }

    public void setFinalprice(Integer finalprice) {
        this.finalprice = finalprice;
    }

    @Override
    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    private Integer status;
    private String id;
    private String seller;
    private String description;
    private String additionals;
    private Integer finalprice;
    private Integer quantity;
    private long timestamp;

    public BargainProduct(String name, String image, int price, Integer bidValue, String description,
                          String seller, Integer status, String id, String additionals, Integer finalprice, Integer quantity, long timestamp){
        this.name = name;
        this.image = image;
        this.price = price;
        this.bidValue = bidValue;
        this.description = description;
        this.seller = seller;
        this.status = status;
        this.id = id;
        this.additionals = additionals;
        this.finalprice = finalprice;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }

//    public BargainProduct(String name, String image, Integer price, Integer bidValue
//            ,String description,String seller, Integer status
//            , String id, String additionals, Integer finalprice, Integer quantity, long timestamp)
//    {
//        this.name = name;
//        this.image = image;
//        this.price = price;
//        this.bidValue = bidValue;
//        this.description = description;
//        this.seller = seller;
//        this.status = status;
//        this.id = id;
//        this.additionals = additionals;
//        this.finalprice = finalprice;
//        this.quantity = quantity;
//        this.timestamp = timestamp;
//    }


}
