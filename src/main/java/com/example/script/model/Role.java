package com.example.script.model;

import com.example.script.model.base.BaseEntity;
import javax.persistence.Entity;

@Entity
public class Role extends BaseEntity {

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
