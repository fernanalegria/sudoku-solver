package com.sudoku.dto;

import com.sudoku.dto.base.BaseDTO;

public class CellPositionDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int row;
	private int column;
	
	public CellPositionDTO() {
		this.row = 0;
		this.column = 0;
	}
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	
	
}
