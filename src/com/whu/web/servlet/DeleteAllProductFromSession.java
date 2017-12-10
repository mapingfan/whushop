package com.whu.web.servlet;

import com.whu.domain.Cart;
import com.whu.domain.CartItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "Servlet9", urlPatterns = {"/deleteAllProductFromSession"})
public class DeleteAllProductFromSession extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session != null) {
            Cart cart = (Cart) session.getAttribute("cart");
            Map<String, CartItem> cartItems = cart.getCartItems();
            cartItems.clear();
            cart.setTotal(0);
            session.setAttribute("cart", cart);
        }
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
