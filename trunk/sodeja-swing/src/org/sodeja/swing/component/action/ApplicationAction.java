package org.sodeja.swing.component.action;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.context.ApplicationContextProvider;

public abstract class ApplicationAction<T extends ApplicationContext> extends
		AbstractAction implements ApplicationContextProvider<T> {

	public static final String TYPE = "TYPE";

	private T ctx;
	
	public ApplicationAction(T ctx) {
		this.ctx = ctx;
		putValue(TYPE, JButton.class);
	}

	public T getContext() {
		return ctx;
	}
	
	protected void setIcon(Icon icon) {
		putValue(Action.SMALL_ICON, icon);
	}

	protected void setName(String name) {
		putValue(Action.NAME, name);
	}

	protected void setTooltip(String tip) {
		putValue(Action.SHORT_DESCRIPTION, tip);
	}

	protected void setMnemonic(String key) {
		putValue(Action.MNEMONIC_KEY, key);
	}

	protected void setAccelerator(KeyStroke stroke) {
		putValue(Action.ACCELERATOR_KEY, stroke);
	}

	protected void setTypeToggle() {
		setType(JToggleButton.class);
	}

	protected void setType(Class clazz) {
		putValue(TYPE, clazz);
	}
}
