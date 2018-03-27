package com.example.script.dao.base;

import java.util.List;

public interface BaseDao<T> {

  T getById(Long id,Class<T> c);

  T insert(T t);

  T update(T t);

  void delete(T t);

  List<T> getAll(Class<T> c);
}
