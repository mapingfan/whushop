package com.whu.web.servlet;

import com.google.gson.Gson;
import com.whu.service.UserService;
import com.whu.vo.RegisterInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Servlet2", urlPatterns = {"/checkUsername"})
public class CheckUsername extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String name = request.getParameter("name");
        UserService service = new UserService();
        boolean flag = true;
        try {
            flag = service.isExist(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        RegisterInfo info = new RegisterInfo();
        Gson gson = new Gson();
        if (flag == true) {
            info.setInformation("可使用");
            String toJson = gson.toJson(info);
            System.out.println(toJson);

            response.getWriter().write(toJson);
        } else {
            info.setInformation("用户名已经被使用");
            String toJson = gson.toJson(info);
            System.out.println(toJson);
            response.getWriter().write(toJson);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
