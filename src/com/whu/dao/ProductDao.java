package com.whu.dao;

import com.whu.domain.Category;
import com.whu.domain.Product;
import com.whu.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class ProductDao {
    public List<Product> findHotProduct() throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where is_hot = ? limit ?,?";
        List<Product> productList = runner.query(sql, new BeanListHandler<>(Product.class), 1, 0, 9);
        return productList;
    }

    public List<Product> findNewProduct() throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product order by pdate desc limit ?,?";
        List<Product> newProducts = runner.query(sql, new BeanListHandler<>(Product.class),0, 9);
        return newProducts;
    }

    public List<Category> findAllCategory() throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from category";
        return runner.query(sql, new BeanListHandler<>(Category.class));
    }
}
