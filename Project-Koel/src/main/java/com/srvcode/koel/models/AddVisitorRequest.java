package com.srvcode.koel.models;

import lombok.Data;

@Data
public class AddVisitorRequest {
	
	private String fullName;
	private String purpose;
	private String visitorAddress;
	private String visitingAddress;
	private String visitorContactNumber;
	private String governmentId;
	private String userId;

}
