package com.whu.web.servlet;

import com.whu.domain.User;
import com.whu.service.UserService;
import com.whu.utils.CommonsUtils;
import com.whu.utils.MailUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@WebServlet(name = "Register", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        ConvertUtils.register(new Converter() {
            @Override
            public Object convert(Class aClass, Object o) {
//                将String转换为Date；
                SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
                Date date = null;
                try {
                    date = format.parse(o.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date;
            }
        }, Date.class);
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
        String uuid = CommonsUtils.getUUID();
        user.setCode(uuid);
        /*讲user对象传递到service层*/
        UserService service = new UserService();
        boolean flag = false;
        try {
            flag = service.register(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (flag == true) {
            //您已注册成功；发送激活邮件
            try {
                String message = "恭喜您注册成功，请点击下面链接激活账户.<br>";
                message += "<a href=http://localhost:8080/activeAccount?uuid=" + uuid + ">" +
                        "http://localhost:8080/activeAccount?uuid=" + uuid + "</a>";
                MailUtils.sendMail(user.getEmail(), message);

            } catch (MessagingException e) {
                System.out.println("验证邮件发送失败");
                e.printStackTrace();
            }
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
