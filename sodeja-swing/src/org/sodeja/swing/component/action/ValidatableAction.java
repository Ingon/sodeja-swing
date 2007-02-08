package org.sodeja.swing.component.action;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.validation.Validatable;

public abstract class ValidatableAction<T extends ApplicationContext> extends
		ApplicationAction<T> implements Validatable {

	public ValidatableAction(T ctx) {
		super(ctx);
		ctx.getValidationController().addTimedValidatable(this);
	}
}
