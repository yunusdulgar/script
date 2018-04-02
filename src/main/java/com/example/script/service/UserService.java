package com.example.script.service;


import com.example.script.model.User;
import com.example.script.service.base.BaseService;

public interface UserService extends BaseService<User> {

  User getUserByUserName(String userName);

}
