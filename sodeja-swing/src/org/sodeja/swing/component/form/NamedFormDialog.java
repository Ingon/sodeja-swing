package org.sodeja.swing.component.form;

import java.awt.HeadlessException;

import org.sodeja.swing.component.ApplicationDialog;
import org.sodeja.swing.component.ApplicationFrame;
import org.sodeja.swing.context.ApplicationContext;

public abstract class NamedFormDialog<T extends ApplicationContext> extends FormDialog<T> {
	public NamedFormDialog(T ctxCons, ApplicationDialog parent) throws HeadlessException {
		super(ctxCons, parent);
	}

	public NamedFormDialog(T ctxCons, ApplicationFrame parent) throws HeadlessException {
		super(ctxCons, parent);
	}

	public NamedFormDialog(T ctxCons) throws HeadlessException {
		super(ctxCons);
	}

	@Override
	protected void preInitComponents(FormPanelGridData gridData) {
		super.preInitComponents(gridData);
		FormUtils.standartNamedPreInit(this, ctx, gridData, getResourceName());
	}

	protected abstract String getResourceName();
}
