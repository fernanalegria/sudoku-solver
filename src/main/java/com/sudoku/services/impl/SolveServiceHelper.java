package com.sudoku.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sudoku.dto.CellDTO;
import com.sudoku.enums.Constants;

@Service
public class SolveServiceHelper {

	public List<ArrayList<CellDTO>> createCellMatrix(List<CellDTO> cellList) {
		
		List<ArrayList<CellDTO>> cellMatrix = new ArrayList<ArrayList<CellDTO>>();
		for(int i=0; i<Constants.SUDOKU_NINE.getParam(); i++) {
			List<CellDTO> row = new ArrayList<CellDTO>();
			for(int j=0; j<Constants.SUDOKU_NINE.getParam(); j++) {
				CellDTO cellDTO = new CellDTO();
				row.add(cellDTO);
			}
			cellMatrix.add((ArrayList<CellDTO>) row);
		}
		
		int i = 0, j = 0;
		for(CellDTO cellDTO : cellList) {
			cellMatrix.get(i).get(j).setCellNumber(cellDTO.getCellNumber());
			cellMatrix.get(i).get(j).setEmpty(cellDTO.isEmpty());
			
			j++;
			if(j >= Constants.SUDOKU_NINE.getParam()) {
				j = 0;
				i++;
			}
		}
		
		return cellMatrix;
	}
	
	public List<CellDTO> fromMatrixToList(List<ArrayList<CellDTO>> cellMatrix) {
		
		List<CellDTO> list = new ArrayList<CellDTO>();
		
		for(List<CellDTO> row : cellMatrix) {
			for(CellDTO cell : row) {
				list.add(cell);
			}
		}
			
		return list;
	}
	
}
