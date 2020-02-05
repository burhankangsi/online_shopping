package com.example.bidding_interface2.Model;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
    public class ImageUploadInfo {
        public String name;

        public String image;
        public String category;
        public String description;
        public String id;
        public String seller;

    public ImageUploadInfo(String name, String image, String category, String description, String id, String seller, int price) {
        this.name = name;
        this.image = image;
        this.category = category;
        this.description = description;
        this.id = id;
        this.seller = seller;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getSeller() {
        return seller;
    }

    public int getPrice() {
        return price;
    }
    public ImageUploadInfo()
    {}

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int price;




    }

