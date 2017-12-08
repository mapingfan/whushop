package com.whu.dao;

import com.whu.domain.User;
import com.whu.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

public class UserDao {
    public boolean register(User user) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
        /**
         * 此处异常全部不处理，抛出到web层处理；
         */
        int update = runner.update(sql,
                user.getUid(), user.getUsername(), user.getPassword(),
                user.getName(), user.getEmail(), user.getTelephone(),
                user.getBirthday(), user.getSex(), user.getState(),
                user.getCode());
        return update > 0 ? true : false;
    }
}
