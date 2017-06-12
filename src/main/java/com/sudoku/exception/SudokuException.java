package com.sudoku.exception;

public class SudokuException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SudokuError sudokuError;
	
	public SudokuException(SudokuError sudokuError) {
		this.setSudokuError(sudokuError);
	}
	
	public SudokuException(SudokuError sudokuError, Throwable e) {
		super(e);
		this.setSudokuError(sudokuError);
	}

	public SudokuError getSudokuError() {
		return sudokuError;
	}

	public void setSudokuError(SudokuError sudokuError) {
		this.sudokuError = sudokuError;
	}

}
