package com.sudoku.dto.base;

import java.io.Serializable;
import java.util.List;

import flexjson.JSONSerializer;

public class BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}
	
	public static String toJsonArray(List<?> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

}
