package com.sudoku.enums;

public enum Constants {
	SUDOKU_SIZE(81),
	SUDOKU_NINE(9),
	SUDOKU_SIX(6),
	SUDOKU_THREE(3);
	
	private int parameter;
	
	private Constants(int parameter) {
		this.parameter = parameter;
	}
	
	public final int getParam() {
		return parameter;
	}
}
