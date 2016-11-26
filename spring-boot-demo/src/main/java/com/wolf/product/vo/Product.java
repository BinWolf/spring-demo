package com.wolf.product.vo;

/**
 * Created by wolf on 16/11/26.
 */
public class Product {
    private int productId;
    private String name;
    private double price;
    private String priceDayStr = "￥";

    public Product(int productId, String name, double price, String priceDayStr) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.priceDayStr = priceDayStr;
    }

    public Product(ProductBuilder productBuilder) {
        this.productId = productBuilder.productId;
        this.name = productBuilder.name;
        this.price = productBuilder.price;
        this.priceDayStr = productBuilder.priceDayStr;
    }

    public static class ProductBuilder{
        private int productId;
        private String name;
        private double price;
        private String priceDayStr = "￥";

        public ProductBuilder setProductId(int productId) {
            this.productId = productId;
            return this;
        }

        public ProductBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder setPrice(double price) {
            this.price = price;
            return this;
        }

        public ProductBuilder setPriceDayStr(String priceDayStr) {
            this.priceDayStr = priceDayStr;
            return this;
        }
        public Product build() {
            return new Product(this);
        }
    }

    public Product() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPriceDayStr() {
        return priceDayStr;
    }

    public void setPriceDayStr(String priceDayStr) {
        this.priceDayStr = priceDayStr;
    }
}
