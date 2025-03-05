package com.example.scanner.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Objects;

public class Product {

    private String data;

    private String symbology;

    public Product(String data, String symbology) {
        this.data = data;
        this.symbology = symbology;
    }

    public Product() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSymbology() {
        return symbology;
    }

    public void setSymbology(String symbology) {
        this.symbology = symbology;
    }

    @NonNull
    @Override
    public String toString() {
        return data + "\n" + symbology;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(data, product.data) && Objects.equals(symbology, product.symbology);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, symbology);
    }

    public boolean checkInList(ArrayList<Product> products) {
        for (Product product : products) {
            if (product.getData().equals(data)) {
                return true;
            }
        }
        return false;
    }
}
