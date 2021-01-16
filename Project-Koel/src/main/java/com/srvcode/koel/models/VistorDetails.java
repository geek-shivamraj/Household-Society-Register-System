package com.srvcode.koel.models;

import lombok.Data;

@Data
public class VistorDetails {

	private String insertionOrderId;
	private String fullName;
	private String purpose;
	private String dateTimeIn;
	private String dateTimeOut;
	private String visitorAddress;
	private String visitingAddress;
	private String visitorContactNumber;
	private String governmentId;
	private String visitorStatus;
}
