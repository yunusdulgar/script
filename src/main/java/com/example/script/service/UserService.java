package com.example.script.service;

import com.example.script.dao.UserDao;
import com.example.script.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService<User> {

  User getUserByUserName(String userName);

}
