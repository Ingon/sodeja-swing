package org.sodeja.swing.component.menu;

import javax.swing.JMenuBar;

import org.sodeja.swing.context.ApplicationContext;

public class MenuBarBuilder<T extends ApplicationContext> {
	
	private T ctx;
	private JMenuBar menuBar;
	private MenuBuilder menuBuilder;
	
	public MenuBarBuilder(T ctx) {
		this.ctx = ctx;
		menuBar = new JMenuBar();
	}

	public MenuBuilder startMenu(String name) {
		if(menuBuilder != null) {
			menuBar.add(menuBuilder.finishAll());
		}
		menuBuilder = new MenuBuilder<T>(ctx);
		menuBuilder.startMenu(name);
		return menuBuilder;
	}

	public JMenuBar finishMenu() {
		if(menuBuilder == null) {
			throw new IllegalStateException("Did not start menu to finish it");
		}
		menuBar.add(menuBuilder.finishAll());
		menuBuilder = null;
		return menuBar;
	}
	
	public JMenuBar getMenuBar() {
		return menuBar;
	}
}
