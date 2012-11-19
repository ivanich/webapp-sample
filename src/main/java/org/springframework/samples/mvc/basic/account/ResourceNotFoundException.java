package org.springframework.samples.mvc.basic.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	
	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -6022095339190047095L;
	private Long resourceId;
	
	public ResourceNotFoundException(Long resourceId) {
		this.resourceId = resourceId;
	}
	
	public Long getResourceId() {
		return resourceId;
	}
	
}
