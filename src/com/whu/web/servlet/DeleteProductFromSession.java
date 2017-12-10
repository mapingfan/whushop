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

@WebServlet(name = "Servlet8", urlPatterns = {"/deleteProductFromSession"})
public class DeleteProductFromSession extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String pid = request.getParameter("pid");
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            Map<String, CartItem> cartItems = cart.getCartItems();
            CartItem remove = cartItems.remove(pid);
            cart.setTotal((cart.getTotal() - remove.getSubTotal()));
            session.setAttribute("cart", cart);
        }
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
