package com.whu.web.servlet;

import com.whu.domain.User;
import com.whu.utils.CommonsUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Servlet11", urlPatterns = {"/login"})
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * private String  uid;
         private String  username;
         private String password;
         private String name;
         private String email;
         private String telephone;
         private Date birthday;
         private String sex;
         private int state; //是否激活
         private String code; //激活码
         */
        String username = request.getParameter("username");
        String pasword = request.getParameter("password");
        User user = new User();
        user.setUsername(username);
        user.setPassword(pasword);
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        response.sendRedirect(request.getContextPath()+"/index");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
