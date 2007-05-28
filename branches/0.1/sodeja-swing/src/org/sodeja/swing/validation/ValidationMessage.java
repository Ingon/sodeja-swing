package org.sodeja.swing.validation;

public interface ValidationMessage {
	public boolean isFailure();
	
	public void addParameter(Object obj);
	
	public void addLocalizableParameter(String resourceId);
	
	public String getMessage();
	
	public Object[] getParams();
}
