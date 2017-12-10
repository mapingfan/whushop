package com.whu.web.servlet;

import com.whu.domain.*;
import com.whu.service.ProductService;
import com.whu.utils.CommonsUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Servlet10", urlPatterns = {"/submitOrder"})
public class SubmitOrder extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断是否登录
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");  //跳转后的代码仍会执行;
            return;  //所以直接return结束；
        }
        //封装order对象并进行传递;
        Order order = new Order();
        order.setOid(CommonsUtils.getUUID());
        order.setOrderTime(new Date());

        Cart cart = (Cart) session.getAttribute("cart");
        order.setTotal(cart.getTotal());
        order.setState(0);
        order.setAddress(null);
        order.setTelephone(null);
        order.setUser(user);
        Map<String, CartItem> cartItems = cart.getCartItems();
        for (Map.Entry<String, CartItem> cartItemEntry : cartItems.entrySet()) {
            OrderItem orderItem = new OrderItem();
            CartItem cartItem = cartItemEntry.getValue();
            orderItem.setItemId(CommonsUtils.getUUID());
            orderItem.setCount(cartItem.getBuyNum());
            System.out.println(cartItem.getBuyNum());
            orderItem.setSubTotal(cartItem.getSubTotal());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }
        ProductService service = new ProductService();
        service.submitOrder(order);
        session.setAttribute("order", order);
        response.sendRedirect(request.getContextPath() + "/order_info.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
