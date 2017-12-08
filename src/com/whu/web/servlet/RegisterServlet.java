package com.whu.web.servlet;

import com.whu.domain.User;
import com.whu.service.UserService;
import com.whu.utils.CommonsUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(name = "Register", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        try {
            //映射封装，指定一个类型转换器；如本例将String转换为Date；
            BeanUtils.populate(user, parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //        手动封装UID；电话，state，code

        user.setUid(CommonsUtils.getUUID());
        user.setTelephone(null);
        user.setState(0);
        user.setCode(CommonsUtils.getUUID());
        /*讲user对象传递到service层*/
        UserService service = new UserService();
        boolean flag = false;
        try {
            flag = service.register(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (flag == true) {
            //您已注册成功；
            response.sendRedirect(request.getContextPath() + "/registerSuccess.jsp");
        } else {
            //注册失败
            response.sendRedirect(request.getContextPath() + "/registerFailure.jsp");

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
