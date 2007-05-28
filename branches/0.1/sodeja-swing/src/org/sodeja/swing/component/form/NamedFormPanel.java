package org.sodeja.swing.component.form;

import org.sodeja.swing.context.ApplicationContext;

public abstract class NamedFormPanel<T extends ApplicationContext, R> extends FormPanel<T, R> {
	public NamedFormPanel(T ctx) {
		super(ctx);
	}

	@Override
	protected void preInitComponents(FormPanelGridData gridData) {
		super.preInitComponents(gridData);
		FormUtils.standartNamedPreInit(this, ctx, gridData, getResourceName());
	}
	
	protected abstract String getResourceName();
}
