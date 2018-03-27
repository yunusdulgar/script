package com.example.script.dao;

import com.example.script.dao.base.BaseDaoImpl;
import com.example.script.model.Script;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ScriptDaoImpl extends BaseDaoImpl<Script> implements ScriptDao{


}
