package com.websystique.spring.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.websystique.spring.model.BaseEntity;

@Repository("baseDao")
public class BaseDaoImpl extends AbstractDao implements BaseDao {

	public void saveBaseEntity(BaseEntity baseEntity) {
		persist(baseEntity);

	}

	public void updateBaseEntity(BaseEntity baseEntity) {
		getSession().update(baseEntity);

	}

	protected Session getSession() {
		return super.getSession();
	}

}
