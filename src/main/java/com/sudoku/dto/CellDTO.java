package com.sudoku.dto;

import com.sudoku.dto.base.BaseDTO;

public class CellDTO extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int cellNumber;
	private boolean empty;
	
	public CellDTO() {
		super();
		this.cellNumber = 0;
		this.empty = true;
	}
	
	public CellDTO(int cellNumber, boolean empty) {
		super();
		this.cellNumber = cellNumber;
		this.empty = empty;
	}
	
	public int getCellNumber() {
		return cellNumber;
	}
	public void setCellNumber(int cellNumber) {
		this.cellNumber = cellNumber;
	}
	public boolean isEmpty() {
		return empty;
	}
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}
	
}
