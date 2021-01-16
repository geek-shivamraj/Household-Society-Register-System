package com.srvcode.koel.models;

import lombok.Data;

@Data
public class ClientResponseDTO<T> {

	private T data;

    private boolean success;
    
    private Integer statusCode;
    
	private String errorMessage;

    public ClientResponseDTO(){}

    public ClientResponseDTO(T data) {
        this.data = data;
        this.success = true;
    }

    public ClientResponseDTO(T data, boolean success) {
        this.data = data;
        this.success = success;
    }
    
    public ClientResponseDTO(T data, boolean success, Integer statusCode) {
		super();
		this.data = data;
		this.success = success;
		this.statusCode = statusCode;
	}

    public ClientResponseDTO(T data, boolean success, Integer statusCode, String errorMessage) {
		super();
		this.data = data;
		this.success = success;
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "ClientResponseDTO [data=" + data + ", success=" + success + ", statusCode=" + statusCode
				+ ", errorMessage=" + errorMessage + "]";
	}

}
