package com.whu.web.servlet;

import com.whu.domain.Cart;
import com.whu.domain.CartItem;
import com.whu.domain.Product;
import com.whu.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "Servlet7", urlPatterns = {"/addProductToCart"})
public class AddProductToCart extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        Integer buyNum = Integer.valueOf(request.getParameter("buyNum"));
        //获得product对象
        Product product = null;
        ProductService service = new ProductService();
        try {
            product = service.findProductByPid(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        double subTotal = product.getShop_price() * buyNum;
        CartItem cartItem = new CartItem(product, buyNum, subTotal);
        HttpSession session = request.getSession();
        Cart cart = (Cart)session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        if (cart.getCartItems().containsKey(product.getPid())) {
            CartItem item = cart.getCartItems().get(product.getPid());
            item.setBuyNum(item.getBuyNum() + cartItem.getBuyNum());
            item.setSubTotal(product.getShop_price() * item.getBuyNum());
            cart.getCartItems().put(product.getPid(), item);
        } else {
            cart.getCartItems().put(product.getPid(), cartItem);
        }
            cart.setTotal(cartItem.getSubTotal() + cart.getTotal());
            session.setAttribute("cart", cart);
            response.sendRedirect(request.getContextPath() + "/cart.jsp");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
