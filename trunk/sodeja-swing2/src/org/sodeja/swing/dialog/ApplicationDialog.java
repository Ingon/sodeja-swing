package org.sodeja.swing.dialog;

import javax.swing.JDialog;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.context.ApplicationContextProvider;

public class ApplicationDialog<T extends ApplicationContext> extends JDialog implements ApplicationContextProvider<T> {

	private static final long serialVersionUID = -8390157714027349435L;
	
	private T ctx;
	
	public ApplicationDialog(T ctx) {
		super(ctx.getRootFrame());
		this.ctx = ctx;
	}
	
	@Override
	public T getContext() {
		return ctx;
	}
}
