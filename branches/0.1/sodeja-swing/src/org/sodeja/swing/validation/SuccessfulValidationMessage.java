package org.sodeja.swing.validation;

/**
 * Null object, validation actually has passed, but if we don't return null 
 * there will not be an check :)
 * 
 * @author i000281
 */
public class SuccessfulValidationMessage implements ValidationMessage {
	public boolean isFailure() {
		return false;
	}
	
	public void addLocalizableParameter(String resourceId) {
	}

	public void addParameter(Object obj) {
	}

	public String getMessage() {
		return null;
	}

	public Object[] getParams() {
		return null;
	}
}
