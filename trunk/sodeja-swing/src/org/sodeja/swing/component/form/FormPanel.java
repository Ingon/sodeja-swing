package org.sodeja.swing.component.form;

import java.awt.GridBagLayout;

import org.sodeja.swing.ButtonBarFactory;
import org.sodeja.swing.ComponentUtils;
import org.sodeja.swing.component.ApplicationPanel;
import org.sodeja.swing.component.action.ApplicationAction;
import org.sodeja.swing.context.ApplicationContext;

public abstract class FormPanel<T extends ApplicationContext, R> extends ApplicationPanel<T> {
	
	public FormPanel(T ctx) {
		super(ctx);
		initComponents();
	}

	protected final void initComponents() {
		setLayout(new GridBagLayout());
		
		FormPanelGridData gridData = new FormPanelGridData();
		preInitComponents(gridData);
		initComponentsDelegate(gridData);
		postInitComponents(gridData);
		initKeyboardActions();
	}

	protected void preInitComponents(FormPanelGridData gridData) {
	}

	protected void initComponentsDelegate(FormPanelGridData gridData) {
	}

	protected void postInitComponents(FormPanelGridData gridData) {
		FormUtils.standartPostInit(this, gridData, getActions());
	}
	
	protected ApplicationAction[] getActions() {
		return ButtonBarFactory.okCancelButtons(ctx, this);
	}
	
	protected void initKeyboardActions() {
		FormUtils.addEscapeListener(this, ctx, this);
		FormUtils.addCtrlEnterListener(this, ctx, this);
	}
	
	public void showForm() {
		showForm(null);
	}
	
	public void showForm(R object) {
		showForm();
	}
	
	protected void okCallback() {
		hideForm();
	}
	
	protected void cancelCallback() {
		hideForm();
	}

	protected void hideForm() {
		ComponentUtils.removeFromContainer(this);
	}
}
