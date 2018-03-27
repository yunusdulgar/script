package com.example.script.service.base;

import com.example.script.dao.base.BaseDao;
import com.example.script.model.base.BaseEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

  @Autowired
  BaseDao<T> baseDao;

  @Override
  public T getById(Long id,Class<T> c) {
    return baseDao.getById(id,c);
  }

  @Override
  public T insert(T t) {
    return baseDao.insert(t);
  }

  @Override
  public T update(T t) {
    return baseDao.update(t);
  }

  @Override
  public void delete(T t) {
    baseDao.delete(t);
  }

  @Override
  public List<T> getAll(Class<T> c){
    return baseDao.getAll(c);
  }



}
