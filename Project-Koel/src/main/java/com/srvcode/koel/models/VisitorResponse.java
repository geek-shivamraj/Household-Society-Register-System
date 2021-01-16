package com.srvcode.koel.models;

import lombok.Data;

@Data
public class VisitorResponse {
	
	private String fullName;
	private String dateTimeIn;
	private String dateTimeOut;
	private String visitorStatus;
	
}
