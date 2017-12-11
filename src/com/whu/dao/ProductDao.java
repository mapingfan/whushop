package com.whu.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whu.domain.Category;
import com.whu.domain.Order;
import com.whu.domain.OrderItem;
import com.whu.domain.Product;
import com.whu.utils.DataSourceUtils;
import com.whu.vo.Page;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

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
        JedisPoolConfig poolConfig = new JedisPoolConfig();

        Gson gson = new Gson();
        poolConfig.setMinIdle(30);
        poolConfig.setMinIdle(10);
        poolConfig.setMaxTotal(50);
        JedisPool pool = new JedisPool(poolConfig, "45.78.63.172", 6379);
        Jedis poolResource = pool.getResource();
        String poolCategoryList = poolResource.get("categoryList");
        List<Category> categoryList = null;
        if (poolCategoryList == null) {
            QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
            String sql = "select * from category";
            categoryList = runner.query(sql, new BeanListHandler<>(Category.class));
            String toJson = gson.toJson(categoryList);
            poolResource.set("categoryList", toJson);
            return categoryList;
        } else {
            Type collectionType = new TypeToken<List<Category>>(){}.getType();
             List<Category> categorieFromRedis = gson.fromJson(poolCategoryList, collectionType);
            poolResource.close();
            pool.close();
            return categorieFromRedis;
        }
    }

    public List<Product> findAllProductByCid(String cid, int pageNoInt) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        int perPageSize = 12;
        String sql = "select * from product where cid = ? limit ?,?";
        List<Product> productList = runner.query(sql, new BeanListHandler<>(Product.class), cid, (pageNoInt - 1) * perPageSize, perPageSize);
        return productList;
    }


    public Page paging(String cid) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from product where cid = ?";
        Long query = (Long) runner.query(sql, new ScalarHandler(), cid);
        Page page = new Page();
        if (query != null) {
            page.setPerPageRecords(12);
            int totalRecords = query.intValue();
            page.setTotoalRecords(totalRecords);
            page.setTotalPages(totalRecords % 12 == 0 ? totalRecords / 12 : (totalRecords / 12 + 1));
            return page;
        } else {
            page.setTotoalRecords(0);
            page.setPerPageRecords(12);
            page.setTotalPages(0);
            return page;
        }
    }

    public Product findProductByPid(String pid) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where pid = ?";
        Product product = runner.query(sql, new BeanHandler<>(Product.class), pid);
        return product;
    }

    public void addOrders(Order order) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = format.format(order.getOrderTime());
        System.out.println(currentTime);
        runner.update(DataSourceUtils.getConnection(), sql, order.getOid(), currentTime, order.getTotal(), order.getState(),
                order.getAddress(), order.getName(), order.getTelephone(), order.getUser().getUid());

    }

    public void addOrderItem(Order order) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "insert into orderitem values(?,?,?,?,?)";
        Connection con = DataSourceUtils.getConnection();
        List<OrderItem> orderItemList = order.getOrderItems();
        for (OrderItem orderItem : orderItemList) {
            runner.update(con, sql, orderItem.getItemId(), orderItem.getCount(), orderItem.getSubTotal(), orderItem.getProduct().getPid(),
                    orderItem.getOrder().getOid());
        }
    }

    public List<Order> findAllOrders(String uid) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from orders where uid = ?";
        List<Order> orderList = runner.query(sql, new BeanListHandler<>(Order.class), uid);
        return orderList;
    }

    public List<Map<String, Object>> findAllOrderItemByOid(String oid) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from orderitem i, product p where i.pid = p.pid and i.oid = ?";
        List<Map<String, Object>> mapList = runner.query(sql, new MapListHandler(), oid);
        return mapList;
    }
}
