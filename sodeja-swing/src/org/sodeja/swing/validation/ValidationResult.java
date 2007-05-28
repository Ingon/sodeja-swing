package org.sodeja.swing.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Predicate1;

public class ValidationResult {
	
	private List<ValidationMessage> messages;
	
	public ValidationResult() {
		messages = new ArrayList<ValidationMessage>();
	}
	
	public void addValidationMessage(String resourceId, Object... details) {
		messages.add(new FailedValidationMessage(resourceId, details));
	}
	
	public void addValidationMessage(ValidationMessage message) {
		if(message == null) {
			return;
		}
		
		messages.add(message);
	}

	public boolean isValid() {
		if(messages.isEmpty()) {
			return true;
		}
		
		return ! ListUtils.elem(messages, new Predicate1<ValidationMessage>() {
			public Boolean execute(ValidationMessage p) {
				return p.isFailure();
			}});
	}
	
	public List<ValidationMessage> getMessages() {
		return Collections.unmodifiableList(messages);
	}
}
