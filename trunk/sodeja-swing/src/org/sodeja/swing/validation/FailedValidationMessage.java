package org.sodeja.swing.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FailedValidationMessage implements ValidationMessage {
	private String resourceId;
	private List<Object> params;
	
	public FailedValidationMessage(String resourceId) {
		this.resourceId = resourceId;
		
		params = new ArrayList<Object>();
	}
	
	public FailedValidationMessage(String resourceId, Object... details) {
		this(resourceId);
		Collections.addAll(params, details);
	}
	
	public String getMessage() {
		return resourceId;
	}
	
	public void addParameter(Object obj) {
		this.params.add(obj);
	}
	
	public void addLocalizableParameter(String resourceId) {
		this.params.add(new LocalizableValidationParameter(resourceId));
	}

	public boolean isFailure() {
		return true;
	}

	public Object[] getParams() {
		return params.toArray();
	}
}
