package org.sodeja.swing.component;

import javax.swing.JPanel;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.context.ApplicationContextProvider;

public abstract class ApplicationPanel<T extends ApplicationContext> extends
		JPanel implements ApplicationContextProvider<T> {

	protected T ctx;
	
	public ApplicationPanel(T ctx) {
		this.ctx = ctx;
	}

	public T getContext() {
		return ctx;
	}
}
