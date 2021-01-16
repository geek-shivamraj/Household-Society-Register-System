package com.srvcode.koel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/register")
@Slf4j
public class RegisterSystemController {
	
	@Autowired
	private RegisterService registerService;

	@CrossOrigin
	@PostMapping("/addVisitor")
	public ResponseEntity<ClientResponseDTO<VisitorResponse>> addVisitor(AddVisitorRequest request) {
		
		log.info("Calling add visitor Service.");
		ClientResponseDTO<VisitorResponse> response = registerService.addVisitorDetails(request);
		return ResponseEntity.ok(response);
	}
	
	@CrossOrigin
	@PatchMapping("/updateVisitorStatus")
	public ResponseEntity<ClientResponseDTO<VisitorResponse>> updateVisitorStatus(String governmentId, String status, String userId) {
		
		log.info("Calling update visitor status service.");
		ClientResponseDTO<VisitorResponse> response = registerService.updateVisitorStatus(governmentId, status, userId);
		return ResponseEntity.ok(response);
	}
	
	@CrossOrigin
	@PostMapping("/searchVisitor")
	public ResponseEntity<ClientResponseDTO<List<VisitorResponse>>> searchVisitor(SearchRequest request) {
		
		log.info("Calling search visitor Service.");
		ClientResponseDTO<List<VisitorResponse>> response = registerService.searchVisitor(request);
		return ResponseEntity.ok(response);
	}
}
