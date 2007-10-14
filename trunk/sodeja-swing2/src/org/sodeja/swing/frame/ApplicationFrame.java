package org.sodeja.swing.frame;

import javax.swing.JFrame;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.context.ApplicationContextProvider;

public abstract class ApplicationFrame<T extends ApplicationContext> extends JFrame implements ApplicationContextProvider<T>{
	
	protected T ctx;
	
	public ApplicationFrame(T ctx) {
		this.ctx = ctx;
	}

	@Override
	public T getContext() {
		return ctx;
	}
}
