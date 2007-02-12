package org.sodeja.swing.component.menu;

import java.util.Stack;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.sodeja.swing.component.action.ApplicationAction;
import org.sodeja.swing.context.ApplicationContext;

public class MenuBuilder<T extends ApplicationContext> {
	
	private T ctx;
	private Stack<JMenu> menuStack;
	private JMenu menu;
	
	public MenuBuilder(T ctx) {
		this.ctx = ctx;
		menuStack = new Stack<JMenu>();
	}
	
	public void startMenu(String name) {
		if(menu != null) {
			throw new IllegalStateException("Already finished menu constrcution"); //$NON-NLS-1$
		}
		JMenu resultingMenu = new JMenu(new MenuBuilderAction<T>(ctx, name));
		if(! menuStack.isEmpty()) {
			menuStack.peek().add(resultingMenu);
		}
		menuStack.push(resultingMenu);
	}
	
	public void addMenuItem(ApplicationAction action) {
		if(menuStack.isEmpty()) {
			throw new IllegalStateException("Should start with a menu"); //$NON-NLS-1$
		}
		menuStack.peek().add(new JMenuItem(action));
	}
	
	public void addSeparator() {
		menuStack.peek().addSeparator();
	}
	
	public void finishMenu() {
		if(menuStack.size() > 1) {
			menuStack.pop();
		}
		menu = menuStack.pop();
	}
	
	public JMenu finishAll() {
		while(menu == null) {
			finishMenu();
		}
		return menu;
	}
	
	public JMenu getMenu() {
		if(menu == null) {
			throw new IllegalStateException("Menu is still not finished"); //$NON-NLS-1$
		}
		return menu;
	}
}
