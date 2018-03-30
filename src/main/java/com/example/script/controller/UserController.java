package com.example.script.controller;

import com.example.script.model.User;
import com.example.script.service.UserService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private UserService userService;

  @RequestMapping(path = "/api/users", method = RequestMethod.GET)
  public ResponseEntity<List<User>> getUsers() {
    LOGGER.info("getUsers");
    return new ResponseEntity<List<User>>(userService.getAll(User.class), HttpStatus.OK);
  }


}
