package com.example.script.dao;

import com.example.script.dao.base.BaseDao;
import com.example.script.model.User;
import java.util.List;

public interface UserDao extends BaseDao<User> {

  User getUserByUserName(String userName);
}
