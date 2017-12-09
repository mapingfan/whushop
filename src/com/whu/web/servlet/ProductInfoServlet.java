package com.whu.web.servlet;

import com.whu.domain.Product;
import com.whu.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Servlet6", urlPatterns = {"/productInfo"})
public class ProductInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        ProductService service = new ProductService();
        Product product = null;
        try {
            product = service.findProductByPid(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("product", product);
        request.getRequestDispatcher(request.getContextPath()+"/product_info.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
