package com.whu.web.servlet;

import com.whu.domain.Product;
import com.whu.service.ProductService;
import com.whu.vo.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Servlet5", urlPatterns = {"/productList"})
public class ProductList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        ProductService service = new ProductService();
        List<Product> productList = null;
        String pageNo = request.getParameter("pageNo");
        if (pageNo == null) {
            pageNo = 1 + "";
        }
        int pageNoInt = Integer.parseInt(pageNo);
        try {
            productList = service.findAllProductByCid(cid, pageNoInt);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //获取分页数据
        Page page = null;
        try {
            page = service.paging(cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("page", page);
        request.setAttribute("productList", productList);
        request.setAttribute("cid", cid);
        request.getRequestDispatcher(request.getContextPath()+"/product_list.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
