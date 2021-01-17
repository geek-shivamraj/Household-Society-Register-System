package com.srvcode.koel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srvcode.koel.models.AddVisitorRequest;
import com.srvcode.koel.models.ClientResponseDTO;
import com.srvcode.koel.models.SearchRequest;
import com.srvcode.koel.models.VisitorResponse;
import com.srvcode.koel.service.RegisterService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@ApiOperation(value="Register System Controller", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class RegisterSystemController {
	
	@Autowired
	private RegisterService registerService;

	@CrossOrigin
	@ApiOperation(value="End point to add Visitor new record.")
	@PostMapping("/addVisitor")
	public ResponseEntity<ClientResponseDTO<VisitorResponse>> addVisitor(AddVisitorRequest request) {
		
		log.info("Calling add visitor Service.");
		ClientResponseDTO<VisitorResponse> response = registerService.addVisitorDetails(request);
		return ResponseEntity.ok(response);
	}
	
	@CrossOrigin
	@ApiOperation(value="End point to update Visitor status based on his/her governmentId.")
	@PatchMapping("/updateVisitorStatus")
	public ResponseEntity<ClientResponseDTO<VisitorResponse>> updateVisitorStatus(String governmentId, String status, String userId) {
		
		log.info("Calling update visitor status service.");
		ClientResponseDTO<VisitorResponse> response = registerService.updateVisitorStatus(governmentId, status, userId);
		return ResponseEntity.ok(response);
	}
	
	@CrossOrigin
	@ApiOperation(value="End point to search Visitor based on a particular date or government Id.")
	@PostMapping("/searchVisitor")
	public ResponseEntity<ClientResponseDTO<List<VisitorResponse>>> searchVisitor(SearchRequest request) {
		
		log.info("Calling search visitor Service.");
		ClientResponseDTO<List<VisitorResponse>> response = registerService.searchVisitor(request);
		return ResponseEntity.ok(response);
	}
}
