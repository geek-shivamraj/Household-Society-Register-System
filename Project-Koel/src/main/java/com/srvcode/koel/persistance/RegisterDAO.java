package com.srvcode.koel.persistance;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.srvcode.koel.models.AddVisitorRequest;
import com.srvcode.koel.models.VisitorResponse;

@Repository
public class RegisterDAO {

	public VisitorResponse getVisitorDetail(String governmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addVisitorDetail(AddVisitorRequest request, String dateTimeIn) {
		// TODO Auto-generated method stub
		
	}

	public void addToVisitorMaster(AddVisitorRequest request, String dateTimeIn) {
		// TODO Auto-generated method stub
		
	}

	public List<VisitorResponse> searchVistorById(String governmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<VisitorResponse> searchVistorByDate(String date) {
		// TODO Auto-generated method stub
		return null;
	}


}
