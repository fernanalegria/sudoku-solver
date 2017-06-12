package com.sudoku.services;

import java.util.List;

import com.sudoku.dto.CellDTO;

public interface SolveService {

	public List<CellDTO> getSolution(List<CellDTO> cellList);
	
}
