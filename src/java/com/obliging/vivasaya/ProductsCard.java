package com.obliging.vivasaya;

import android.view.View;

public class ProductsCard{
    public String product;

    public ProductsCard() {
    }

    public ProductsCard(String product_item) {
        this.product = product_item;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product_item) {
        this.product = product_item;
    }

}