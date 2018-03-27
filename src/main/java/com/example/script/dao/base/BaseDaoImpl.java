package com.example.script.dao.base;

import com.example.script.model.base.BaseEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseDaoImpl<T extends BaseEntity> implements BaseDao<T> {

  protected Class<T> entityClass;

  @PersistenceContext
  protected EntityManager entityManager;

  @Override
  public T getById(Long id,Class<T> c) {
    return (T)this.entityManager.find(c, id);
  }

  @Override
  public T insert(T t) {
    this.entityManager.persist(t);
    return t;
  }

  @Override
  public T update(T t) {
    return this.entityManager.merge(t);
  }

  @Override
  public void delete(T t) {
    t = this.entityManager.merge(t);
    this.entityManager.remove(t);
  }

  @Override
  public List<T> getAll(Class<T> c){
    return this.entityManager.createQuery("SELECT o FROM "+ c.getName() +" o").getResultList();
  }
}
