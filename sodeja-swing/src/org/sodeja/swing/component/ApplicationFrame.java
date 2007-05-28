package org.sodeja.swing.component;

import javax.swing.JFrame;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.context.ApplicationContextProvider;

public abstract class ApplicationFrame<T extends ApplicationContext> extends JFrame implements ApplicationContextProvider<T> {

	private static final long serialVersionUID = -315509710080557730L;

	protected T ctx;
	
	public ApplicationFrame(T ctx) {
		this.ctx = ctx;
	}
	
	public T getContext() {
		return ctx;
	}
}
