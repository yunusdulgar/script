package com.example.script.service;

import com.example.script.dao.UserDao;
import com.example.script.model.User;
import com.example.script.service.base.BaseService;
import com.example.script.service.base.BaseServiceImpl;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService,UserDetailsService {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private UserDao userDao;

  @Override
  public User getUserByUserName(String userName) {
    LOGGER.debug("userService.getUserByUserName : {}",userName);
    return userDao.getUserByUserName(userName);
  }

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    LOGGER.debug(" jrebel try userService.loadUserByUsername : {}",userName);

    User user = getUserByUserName(userName);
    if (user == null) {
      LOGGER.error("user not found : {}",userName);

      throw new UsernameNotFoundException(userName);
    }
    return user;  }
}
