package com.srvcode.koel.service;

import java.util.List;

import com.srvcode.koel.models.AddVisitorRequest;
import com.srvcode.koel.models.ClientResponseDTO;
import com.srvcode.koel.models.SearchRequest;
import com.srvcode.koel.models.VisitorResponse;


public interface RegisterService {

	public ClientResponseDTO<VisitorResponse> addVisitorDetails(AddVisitorRequest request);
	
	public ClientResponseDTO<VisitorResponse> updateVisitorStatus(String governmentId, String status, String userId);
	
	public ClientResponseDTO<List<VisitorResponse>> searchVisitor(SearchRequest request);
	
}
