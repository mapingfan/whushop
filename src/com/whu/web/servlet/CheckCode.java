package com.whu.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Servlet3", urlPatterns = {"/checkCode"})
public class CheckCode extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String checkcode = request.getParameter("checkcode");
        System.out.println(checkcode);
        HttpSession session = request.getSession();
        String sessioncode = (String) session.getAttribute("checkcode_session");
        System.out.println(sessioncode);
        if (sessioncode.equals(checkcode)) {
            response.getWriter().write("{\"information\":\"验证码正确\"}");
        } else {
            response.getWriter().write("{\"information\":\"验证码错误\"}");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
