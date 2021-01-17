package com.srvcode.koel.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.srvcode.koel.models.AddVisitorRequest;
import com.srvcode.koel.models.ClientResponseDTO;
import com.srvcode.koel.models.SearchRequest;
import com.srvcode.koel.models.VisitorDetails;
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
			visitor = registerDAO.getVisitorDetail(request.getGovernmentId());
		} else {
			log.info("Existing Visitor. Adding Visitor to visitor details");
		}
		registerDAO.addVisitorDetail(request, dateTimeIn, visitor.getInsertionOrderId());
		visitorResponse.setFullName(request.getFullName());
		visitorResponse.setDateTimeIn(dateTimeIn);
		visitorResponse.setVisitorStatus(ENTER);
		return new ClientResponseDTO<>(visitorResponse, true, HttpStatus.CREATED.value());
	}
	
	@Override
	public ClientResponseDTO<VisitorResponse> updateVisitorStatus(String governmentId, String status, String userId) {
		
		ClientResponseDTO<VisitorResponse> response = null;
		VisitorResponse visitor = registerDAO.getVisitorDetail(governmentId);
		VisitorDetails visitorDetails = null;
		String dateTimeOut = null;
		if(Objects.isNull(visitor)) {
			response = new ClientResponseDTO<>(null, false, HttpStatus.NOT_FOUND.value(), "No visitor found in Master.");
		} else {
			visitorDetails = registerDAO.getVisitorLatestRecord(visitor.getInsertionOrderId());
			if(ENTER.equalsIgnoreCase(visitorDetails.getVisitorStatus())) {
				dateTimeOut = LocalDateTime.now().toString();
				registerDAO.updateVisitorStatus(visitorDetails.getInsertionOrderId(), dateTimeOut);
			}
			VisitorResponse visitorResponse = new VisitorResponse();
			visitorResponse.setDateTimeIn(visitorResponse.getDateTimeIn());
			visitorResponse.setDateTimeOut(dateTimeOut);
			visitorResponse.setVisitorStatus(EXIT);
			visitorResponse.setFullName(visitor.getFullName());
			response = new ClientResponseDTO<>(visitorResponse, true, HttpStatus.OK.value(), "Visitor exit status updated successfully.");
		}
		return response;
	}

	@Override
	public ClientResponseDTO<List<VisitorResponse>> searchVisitor(SearchRequest request) {

		ClientResponseDTO<List<VisitorResponse>> response = null;
		List<VisitorResponse> visitorList = new ArrayList<>();
		if(!Objects.isNull(request.getGovernmentId())) {
			log.info("Searching visitor by Id.");
			VisitorResponse visitor = registerDAO.getVisitorDetail(request.getGovernmentId());
			if(!Objects.isNull(visitor)) {
				visitorList = registerDAO.searchVistorById(visitor.getInsertionOrderId());
			}
			response = new ClientResponseDTO<>(visitorList, true, HttpStatus.OK.value());
		} else if(!Objects.isNull(request.getDate())) {
			log.info("Searching visitor by Date.");
			visitorList = registerDAO.searchVistorByDate(request.getDate());
			response = new ClientResponseDTO<>(visitorList, true, HttpStatus.OK.value());
		} else {
			log.error("Empty Search Criteria.");
			response = new ClientResponseDTO<>(visitorList, false, HttpStatus.BAD_REQUEST.value(), "Search criteria is empty.");
		}
		return response;
	}

}
