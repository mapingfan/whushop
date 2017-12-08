package com.whu.service;

import com.whu.dao.UserDao;
import com.whu.domain.User;

import java.sql.SQLException;

public class UserService {
    public boolean register(User user) throws SQLException {
        UserDao dao = new UserDao();
        boolean flag = dao.register(user);
        return flag;
    }

    public boolean active(String uuid) throws SQLException {
        UserDao dao = new UserDao();
        boolean flag = dao.active(uuid);
        return flag;
    }

    public boolean isExist(String name) throws SQLException {
        UserDao dao = new UserDao();
        boolean flag = dao.isExist(name);
        return flag;
    }
}
