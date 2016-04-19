package com.websystique.spring.dao;

import com.websystique.spring.model.BaseEntity;

public interface BaseDao {
	void saveBaseEntity(BaseEntity baseEntity);

	void updateBaseEntity(BaseEntity baseEntity);
}
