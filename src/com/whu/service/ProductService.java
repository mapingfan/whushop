package com.whu.service;

import com.whu.dao.ProductDao;
import com.whu.domain.Category;
import com.whu.domain.Order;
import com.whu.domain.Product;
import com.whu.utils.DataSourceUtils;
import com.whu.vo.Page;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

    public List<Product> findAllProductByCid(String cid, int pageNoInt) throws SQLException {
        ProductDao dao = new ProductDao();
        List<Product> productList = dao.findAllProductByCid(cid, pageNoInt);
        return productList;
    }

    public Page paging(String cid) throws SQLException {
        ProductDao dao = new ProductDao();
        Page page = dao.paging(cid);
        return page;
    }

    public Product findProductByPid(String pid) throws SQLException {
        ProductDao dao = new ProductDao();
        Product product = dao.findProductByPid(pid);
        return product;
    }

    public void submitOrder(Order order) {
        ProductDao dao = new ProductDao();
        try {
            DataSourceUtils.startTransaction();
            dao.addOrders(order);
            dao.addOrderItem(order);
        } catch (SQLException e) {
            try {
                DataSourceUtils.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                DataSourceUtils.commitAndRelease();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    public List<Order> findAllOrders(String uid) throws SQLException {
        List<Order> orderList = null;
        ProductDao dao = new ProductDao();
        orderList = dao.findAllOrders(uid);
        return orderList;
    }

    public List<Map<String, Object>> findAllOrderItemByOid(String oid) throws SQLException {
        ProductDao dao = new ProductDao();
        List<Map<String, Object>> mapList;
        mapList = dao.findAllOrderItemByOid(oid);
        return mapList;

    }
}
