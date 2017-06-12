package com.sudoku.web;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sudoku.services.SolveService;
import com.sudoku.web.base.BaseController;

import com.sudoku.dto.CellDTO;

@Controller
@RequestMapping("/solve")
public class SolveController extends BaseController {
	
	@Autowired
	SolveService solveService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SolveController.class);
	
	@RequestMapping(value="getSolution",headers = "Accept=application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getSolution(@RequestParam("jsonArray") String jsonArray){
		LOGGER.debug("Solution calculation has started");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=UTF-8");
		try{
			ObjectMapper mapper = new ObjectMapper();
			List<CellDTO> cellList = mapper.readValue(jsonArray, mapper.getTypeFactory().constructCollectionType(List.class, CellDTO.class));
			List<CellDTO> result = solveService.getSolution(cellList);
			return new ResponseEntity<String>(CellDTO.toJsonArray(result),headers,HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.debug("An error occurred while calculating the solution");
			return super.handleException(e);
		}
	}
	
}
