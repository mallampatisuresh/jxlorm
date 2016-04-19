package com.websystique.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.spring.dao.BaseDao;
import com.websystique.spring.model.BaseEntity;

@Service("baseService")
@Transactional
public class BaseServiceImpl implements BaseService {

	@Autowired
	private BaseDao dao;

	@Override
	public void saveBaseEntity(BaseEntity baseEntity) {
		dao.saveBaseEntity(baseEntity);

	}

	@Override
	public void updateBaseEntity(BaseEntity baseEntity) {
		dao.updateBaseEntity(baseEntity);

	}
}
