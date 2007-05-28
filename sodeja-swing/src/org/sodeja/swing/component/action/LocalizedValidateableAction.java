package org.sodeja.swing.component.action;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.validation.Validatable;

public abstract class LocalizedValidateableAction<T extends ApplicationContext> extends
		LocalizedAction<T> implements Validatable {
	public LocalizedValidateableAction(T ctx, String i18nKey) {
		super(ctx, i18nKey);
		ctx.getValidationController().addTimedValidatable(this);
	}
}
