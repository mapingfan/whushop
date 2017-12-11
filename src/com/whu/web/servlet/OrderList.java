package com.whu.web.servlet;

import com.whu.domain.Order;
import com.whu.domain.OrderItem;
import com.whu.domain.Product;
import com.whu.domain.User;
import com.whu.service.ProductService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Servlet12", urlPatterns = {"/orderList"})
public class OrderList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        ProductService service = new ProductService();
        List<Order> orderList = null;
        try {
            orderList = service.findAllOrders("99320c40-e171-410a-a73e-4c0c01591c08");
            for (Order order : orderList) {
                List<Map<String, Object>> mapList = service.findAllOrderItemByOid(order.getOid());
                for (Map<String, Object> map : mapList) {
                    OrderItem orderItem = new OrderItem();
                    BeanUtils.populate(order, map);
                    Product product = new Product();
                    BeanUtils.populate(product, map);
                    orderItem.setProduct(product);
                    order.getOrderItems().add(orderItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        request.setAttribute("orderList", orderList);
        System.out.println(orderList);
        request.getRequestDispatcher(request.getContextPath()+"/order_list.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
