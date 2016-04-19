package com.websystique.spring;

public class Field {

	private String name;

	public Field(String name, Class<?> type) {
		super();
		this.name = name;
		this.type = type;
	}

	private Class<?> type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

}
