package com.srvcode.koel.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.srvcode.koel.models.AddVisitorRequest;
import com.srvcode.koel.models.ClientResponseDTO;
import com.srvcode.koel.models.SearchRequest;
import com.srvcode.koel.models.VisitorResponse;
import com.srvcode.koel.persistance.RegisterDAO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RegisterServiceImpl implements RegisterService {

	private static final String ENTER = "Enter";
	private static final String EXIT = "Exit";
	
	@Autowired
	private RegisterDAO registerDAO;
	
	@Override
	public ClientResponseDTO<VisitorResponse> addVisitorDetails(AddVisitorRequest request) {
		
		VisitorResponse visitorResponse = new VisitorResponse();
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setGovernmentId(request.getGovernmentId());
		VisitorResponse visitor = registerDAO.getVisitorDetail(request.getGovernmentId());
		
		String dateTimeIn = LocalDateTime.now().toString();
		log.info("Visitor with Government Id {} Entered. Date Time In : {}", request.getGovernmentId(), dateTimeIn);
		if(Objects.isNull(visitor)) {
			log.info("First time Visitor. Adding Visitor to master.");
			registerDAO.addToVisitorMaster(request, dateTimeIn);
		} else {
			log.info("Not first time Visitor. Adding Visitor to visitor details");
			registerDAO.addVisitorDetail(request, dateTimeIn);
		}
		visitorResponse.setFullName(request.getFullName());
		visitorResponse.setDateTimeIn(dateTimeIn);
		visitorResponse.setVisitorStatus(ENTER);
		return new ClientResponseDTO<>(visitorResponse, true, HttpStatus.CREATED.value());
	}
	
	@Override
	public ClientResponseDTO<VisitorResponse> updateVisitorStatus(String governmentId, String status, String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientResponseDTO<List<VisitorResponse>> searchVisitor(SearchRequest request) {

		ClientResponseDTO<List<VisitorResponse>> response = null;
		if(!Objects.isNull(request.getGovernmentId())) {
			log.info("Searching visitor by Id.");
			List<VisitorResponse> visitorList = registerDAO.searchVistorById(request.getGovernmentId());
			response = new ClientResponseDTO<>(visitorList, true, HttpStatus.OK.value());
		} else if(!Objects.isNull(request.getDate())) {
			log.info("Searching visitor by Date.");
			List<VisitorResponse> visitorList = registerDAO.searchVistorByDate(request.getDate());
			response = new ClientResponseDTO<>(visitorList, true, HttpStatus.OK.value());
		} else {
			log.error("Empty Search Criteria.");
			response = new ClientResponseDTO<>(null, false, HttpStatus.BAD_REQUEST.value(), "Search criteria is empty.");
		}
		return response;
	}

}
