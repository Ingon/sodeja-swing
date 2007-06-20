package org.sodeja.swing.component.form;

import java.awt.HeadlessException;

import org.sodeja.swing.component.ApplicationDialog;
import org.sodeja.swing.component.ApplicationFrame;
import org.sodeja.swing.context.ApplicationContext;

public abstract class NamedFormDialog<T extends ApplicationContext> extends FormDialog<T> {
	public NamedFormDialog(ApplicationDialog<T> parent) throws HeadlessException {
		super(parent);
	}

	public NamedFormDialog(ApplicationFrame<T> parent) throws HeadlessException {
		super(parent);
	}

	public NamedFormDialog(T ctxCons) throws HeadlessException {
		super(ctxCons);
	}

	@Override
	protected void preInitComponents(FormPanelGridData gridData) {
		super.preInitComponents(gridData);
		FormUtils.standartNamedPreInit(this, ctx, gridData, getResourceName());
	}

	@Override
	protected void postInitComponents() {
		super.postInitComponents();
		
		setTitle(ctx.getResourceProvider().getStringValue(getResourceName()));
	}

	protected abstract String getResourceName();
}
