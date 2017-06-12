package com.sudoku.web.base;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sudoku.exception.SudokuError;
import com.sudoku.exception.SudokuException;

public class BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);
	
	@SuppressWarnings("unchecked")
	protected ResponseEntity<String> handleException (Exception e) {
		JSONObject json = new JSONObject();
		json.put("success", false);

		if (e instanceof SudokuException) {
			SudokuException exception = (SudokuException) e;
			json.put("info", exception.getSudokuError().getErrorMessage());
			LOGGER.warn(exception.getSudokuError().getErrorMessage());
		} else {
			json.put("info",SudokuError.UNDEFINED_ERROR.getErrorMessage());
			LOGGER.error(e.getMessage(), e);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json.toJSONString(), headers,
				HttpStatus.OK);
	}

}
