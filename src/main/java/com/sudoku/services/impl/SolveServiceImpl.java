package com.sudoku.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sudoku.dto.CellDTO;
import com.sudoku.dto.CellPositionDTO;
import com.sudoku.enums.Constants;
import com.sudoku.exception.SudokuError;
import com.sudoku.exception.SudokuException;
import com.sudoku.services.SolveService;

@Service
public class SolveServiceImpl implements SolveService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SolveServiceImpl.class);
	
	@Autowired
	SolveServiceHelper solveServiceHelper;

	public List<CellDTO> getSolution(List<CellDTO> cellList) {
		
		if(cellList.size()!=Constants.SUDOKU_SIZE.getParam())
			throw new SudokuException(SudokuError.WRONG_SIZE);
		for(CellDTO cell : cellList) {
			if((cell.isEmpty()&&cell.getCellNumber()!=0)||(!cell.isEmpty()&&cell.getCellNumber()==0))
				throw new SudokuException(SudokuError.EMPTY_DOES_NOT_MATCH_VALUE);
		}
		
		List<ArrayList<CellDTO>> cellMatrix = solveServiceHelper.createCellMatrix(cellList);
		LOGGER.debug("Cell matrix has been created");
		
		boolean ok = computeSolution(cellMatrix);
		LOGGER.debug("The solution has been computed");
		if(!ok)
			throw new SudokuException(SudokuError.WRONG_SOLUTION);
		
		List<CellDTO> result = solveServiceHelper.fromMatrixToList(cellMatrix);
		
		return result;
	}

	private boolean computeSolution(List<ArrayList<CellDTO>> cellMatrix) {
		
		int posIndex = 0;
		List<CellPositionDTO> cellPositionList = new ArrayList<CellPositionDTO>();
		for(int i=0; i<Constants.SUDOKU_SIZE.getParam(); i++) {
			cellPositionList.add(new CellPositionDTO());
		}
		
		for(int i=0; i<Constants.SUDOKU_NINE.getParam(); i++) {
			for(int j=0; j<Constants.SUDOKU_NINE.getParam(); j++) {
				
				if(cellMatrix.get(i).get(j).isEmpty()) {
					if(cellMatrix.get(i).get(j).getCellNumber()<Constants.SUDOKU_NINE.getParam()) {
						// comparisons: posIndexer that checks whether row, column and 3x3 group have been tracked
						int comparisons = 0;
						int iSearch = 0;
						int jSearch = 0;
						while(comparisons!=3 && cellMatrix.get(i).get(j).getCellNumber()<Constants.SUDOKU_NINE.getParam()+1) {
							comparisons = 0;
							cellMatrix.get(i).get(j).setCellNumber(cellMatrix.get(i).get(j).getCellNumber()+1);
							
							//Check cells in the same column
							for(iSearch=0; iSearch<Constants.SUDOKU_NINE.getParam(); iSearch++) {
								if(iSearch!=i && (cellMatrix.get(i).get(j).getCellNumber() == cellMatrix.get(iSearch).get(j).getCellNumber()))
									break;
							}
							if(iSearch!=Constants.SUDOKU_NINE.getParam())
								continue;
							else
								comparisons++;
							
							//Check cells in the same row
							for(jSearch=0; jSearch<Constants.SUDOKU_NINE.getParam(); jSearch++) {
								if(jSearch!=j && (cellMatrix.get(i).get(j).getCellNumber() == cellMatrix.get(i).get(jSearch).getCellNumber()))
									break;
							}
							if(jSearch!=Constants.SUDOKU_NINE.getParam())
								continue;
							else
								comparisons++;
							
							//Check cells within the same 3x3 grid
							int resultGroupCheck = checkGrid(cellMatrix,i,j);
							if(resultGroupCheck != (i/3)*3+3)
								continue;
							else
								comparisons++;
							
						}
						
						if(cellMatrix.get(i).get(j).getCellNumber()==Constants.SUDOKU_NINE.getParam()+1) {
							
							cellMatrix.get(i).get(j).setCellNumber(0);
							
							if(cellPositionList.get(posIndex).getColumn() != 0) {
								i = cellPositionList.get(posIndex).getRow();
								j = cellPositionList.get(posIndex).getColumn()-1;
							} else {
								i = cellPositionList.get(posIndex).getRow()-1;
								j = Constants.SUDOKU_NINE.getParam()-1;
							}
							if(posIndex==0)
								posIndex = 80;
							else
								posIndex--;
							
						} else {
							
							posIndex++;
							cellPositionList.get(posIndex).setRow(i);
							cellPositionList.get(posIndex).setColumn(j);
							
						}
					} else {
						
						cellMatrix.get(i).get(j).setCellNumber(0);
						
						if(cellPositionList.get(posIndex).getColumn() != 0) {
							i = cellPositionList.get(posIndex).getRow();
							j = cellPositionList.get(posIndex).getColumn()-1;
						} else {
							i = cellPositionList.get(posIndex).getRow()-1;
							j = Constants.SUDOKU_NINE.getParam()-1;
						}
						posIndex--;
						
					}
				}
			}
		}
		
		for(List<CellDTO> row : cellMatrix) {
			for(CellDTO cell : row) {
				if(cell.getCellNumber() == 0)
					return false;
			}
		}
		return true;
	}

	private int checkGrid(List<ArrayList<CellDTO>> cellMatrix, int i, int j) {
		
		int iInit = (i/3)*3;
		int jInit = (j/3)*3;
		int iSearch;
		int jSearch;
		
		groupCheckLoop:
		for(iSearch = iInit; iSearch<(iInit+Constants.SUDOKU_THREE.getParam()); iSearch++) {
			for(jSearch = jInit; jSearch<(jInit+Constants.SUDOKU_THREE.getParam()); jSearch++) {
				if((iSearch!=i||jSearch!=j) && (cellMatrix.get(i).get(j).getCellNumber() == cellMatrix.get(iSearch).get(jSearch).getCellNumber()))
					break groupCheckLoop;
			}
		}
		
		return iSearch;
	}

}
