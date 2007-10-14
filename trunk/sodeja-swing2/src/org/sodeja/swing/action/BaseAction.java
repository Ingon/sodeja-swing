package org.sodeja.swing.action;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;

public abstract class BaseAction extends AbstractAction {
	public String getName() {
		return (String) getValue(Action.NAME);
	}

	public Icon getIcon() {
		return (Icon) getValue(Action.SMALL_ICON);
	}

	public String getTooltip() {
		return (String) getValue(Action.SHORT_DESCRIPTION);
	}

	public KeyStroke getAccelerator() {
		return (KeyStroke) getValue(Action.ACCELERATOR_KEY);
	}

	public char getMnemonic() {
		return (char) ((Integer) getValue(Action.MNEMONIC_KEY)).intValue();
	}

	protected void setName(String s) {
		putValue(Action.NAME, s);
	}

	protected void setIcon(Icon icon) {
		putValue(Action.SMALL_ICON, icon);
	}

	protected void setTooltip(String s) {
		putValue(Action.SHORT_DESCRIPTION, s);
	}

	protected void setMnemonic(Integer i) {
		if (i != null) {
			putValue(Action.MNEMONIC_KEY, i);
		}
	}

	protected void setAccelerator(KeyStroke k) {
		putValue(Action.ACCELERATOR_KEY, k);
	}
}
