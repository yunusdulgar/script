package com.example.script.dao;

import com.example.script.dao.base.BaseDaoImpl;
import com.example.script.model.User;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

  @Override
  public User getUserByUserName(String userName) {

    String hql = "FROM User as usr where usr.userName = :userName ";
    Query query = entityManager.createQuery(hql);
    query.setParameter("userName",userName);
    return (User) query.getSingleResult();
  }

}
