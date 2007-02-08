package org.sodeja.swing.component.form;

import java.awt.HeadlessException;

import org.sodeja.swing.ButtonBarFactory;
import org.sodeja.swing.component.ApplicationDialog;
import org.sodeja.swing.component.ApplicationFrame;
import org.sodeja.swing.component.action.ApplicationAction;
import org.sodeja.swing.context.ApplicationContext;

public abstract class FormDialog<T extends ApplicationContext> extends ApplicationDialog<T> {

	public FormDialog(T ctxCons) throws HeadlessException {
		super(ctxCons);
	}

	public FormDialog(T ctxCons, ApplicationFrame parent) throws HeadlessException {
		super(ctxCons, parent);
	}

	@Override
	protected final void initComponents() {
		FormPanelGridData gridData = new FormPanelGridData();
		preInitComponents(gridData);
		initComponentsDelegate(gridData);
		postInitComponents(gridData);
	}

	private void preInitComponents(FormPanelGridData gridData) {
	}

	protected abstract void initComponentsDelegate(FormPanelGridData gridData);

	private void postInitComponents(FormPanelGridData gridData) {
		FormUtils.standartPostInit(this, gridData, getActions());
	}

	protected ApplicationAction[] getActions() {
		return ButtonBarFactory.okCancelButtons(ctx, this);
	}

	protected void okCallback() {
		hideForm();
	}
	
	protected void cancelCallback() {
		hideForm();
	}

	protected void hideForm() {
		setVisible(false);
	}
}
