package org.sodeja.swing.component.tabbed;

import org.sodeja.swing.component.ApplicationPanel;
import org.sodeja.swing.context.ApplicationContext;

public abstract class TabbedPanelElement<T extends ApplicationContext> extends ApplicationPanel<T> {
	
	public TabbedPanelElement(T ctx) {
		super(ctx);
	}

	public abstract String getName();
	
	public abstract void tabSelected();
}
