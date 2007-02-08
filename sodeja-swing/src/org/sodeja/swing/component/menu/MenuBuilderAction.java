package org.sodeja.swing.component.menu;

import java.awt.event.ActionEvent;

import org.sodeja.swing.component.action.LocalizedAction;
import org.sodeja.swing.context.ApplicationContext;

class MenuBuilderAction<T extends ApplicationContext> extends LocalizedAction<T> {
	private static final long serialVersionUID = 3410534067217183146L;

	public MenuBuilderAction(T ctx, String resourceName) {
		super(ctx, resourceName);
	}

	public void actionPerformed(ActionEvent e) {
		throw new UnsupportedOperationException();
	}
}