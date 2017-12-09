package com.whu.service;

import com.whu.dao.ProductDao;
import com.whu.domain.Category;
import com.whu.domain.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    public List<Product> findHotProduct() throws SQLException {
        ProductDao dao = new ProductDao();
        List<Product> hotProducts = dao.findHotProduct();
        return hotProducts;
    }

    public List<Product> findNewProduct() throws SQLException {
        ProductDao dao = new ProductDao();
        List<Product> newProducts = dao.findNewProduct();
        return newProducts;
    }

    public List<Category> findAllCategory() throws SQLException {
        ProductDao dao = new ProductDao();
        List<Category> categoryList = dao.findAllCategory();
        return categoryList;
    }
}
