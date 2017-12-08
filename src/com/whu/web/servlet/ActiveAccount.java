package com.whu.web.servlet;

import com.whu.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Servlet", urlPatterns = {"/activeAccount"})
public class ActiveAccount extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        UserService service = new UserService();
        boolean flag = false;
        try {
            flag = service.active(uuid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (true == flag) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            response.getWriter().write("激活失败");
            response.sendRedirect(request.getContextPath() + "/register.jsp");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
