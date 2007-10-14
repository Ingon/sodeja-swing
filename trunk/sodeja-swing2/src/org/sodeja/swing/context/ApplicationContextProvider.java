package org.sodeja.swing.context;

public interface ApplicationContextProvider<T extends ApplicationContext> {
	public T getContext();
}
