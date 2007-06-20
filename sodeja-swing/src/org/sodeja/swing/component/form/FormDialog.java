package org.sodeja.swing.component.form;

import java.awt.HeadlessException;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.sodeja.swing.ButtonBarFactory;
import org.sodeja.swing.component.ApplicationDialog;
import org.sodeja.swing.component.ApplicationFrame;
import org.sodeja.swing.component.action.ApplicationAction;
import org.sodeja.swing.context.ApplicationContext;

public abstract class FormDialog<T extends ApplicationContext> extends ApplicationDialog<T> {

	private static final String ENTER_ACTION_KEY = "ENTER_ACTION_KEY"; //$NON-NLS-1$
	
	public FormDialog(T ctxCons) throws HeadlessException {
		super(ctxCons);
	}

	public FormDialog(ApplicationFrame<T> parent) throws HeadlessException {
		super(parent);
	}

	public FormDialog(ApplicationDialog<T> parent) throws HeadlessException {
		super(parent);
	}

	@Override
	protected final void initComponents() {
		FormPanelGridData gridData = new FormPanelGridData();
		preInitComponents(gridData);
		initComponentsDelegate(gridData);
		postInitComponents(gridData);
	}

	protected void preInitComponents(FormPanelGridData gridData) {
		this.getRootPane().getActionMap().put(ESCAPE_ACTION_KEY, ButtonBarFactory.closeToCancelButton(ctx, this));
		
		KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_DOWN_MASK);
		this.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(stroke, ENTER_ACTION_KEY);
		this.getRootPane().getActionMap().put(ENTER_ACTION_KEY, ButtonBarFactory.okButton(ctx, this));
	}

	protected abstract void initComponentsDelegate(FormPanelGridData gridData);

	protected void postInitComponents(FormPanelGridData gridData) {
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
