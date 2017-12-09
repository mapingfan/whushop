package com.whu.web.servlet;

import com.whu.domain.Category;
import com.whu.domain.Product;
import com.whu.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "Servlet4", urlPatterns = {"/index"})
public class IndexServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService service = new ProductService();
        List<Product> hotProducts = null;
        try {
            hotProducts = service.findHotProduct();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Product> newProducts = null;
        try {
            newProducts = service.findNewProduct();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Category> categoryList = null;
        try {
            categoryList = service.findAllCategory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("newProducts", newProducts);
        request.setAttribute("hotProducts", hotProducts);
        request.getSession().setAttribute("categoryList", categoryList);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
