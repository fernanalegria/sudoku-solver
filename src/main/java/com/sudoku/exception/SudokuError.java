package com.sudoku.exception;

import org.apache.commons.lang.StringUtils;

public enum SudokuError {
	
	UNDEFINED_ERROR("SUDOKUWEB_0001","Ups! Something went wrong"),
	WRONG_SIZE("SUDOKUWEB_0002","Ups! Something went wrong"),
	WRONG_SOLUTION("SUDOKUWEB_0003","An error occurred when computing the solution"),
	EMPTY_DOES_NOT_MATCH_VALUE("SUDOKUWEB_0004","Ups! Something went wrong");
	
	private String errorCode;
	private String errorMessage;
	
	private SudokuError(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public final String getErrorCode() {
		return errorCode;
	}
	
	public final String getErrorMessage() {
		return errorMessage;
	}
	
	public static SudokuError getError(String errorCode) {
		
		if(!StringUtils.isEmpty(errorCode)) {
			SudokuError[] errorValues = SudokuError.values();
			for(SudokuError error : errorValues) {
				if(error.getErrorCode().equals(errorCode)) {
					return error;
				}
			}
		}
		
		return null;
	}

}
