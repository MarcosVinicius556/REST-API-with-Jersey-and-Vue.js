package com.hepta.funcionarios.persistence.exception;

public class ObjectAlreadyAssociatedException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ObjectAlreadyAssociatedException(String msg) {
		super(msg);
	}
	
	
}
