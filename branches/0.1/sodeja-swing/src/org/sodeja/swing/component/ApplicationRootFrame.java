package org.sodeja.swing.component;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.context.ApplicationContextListener;

public abstract class ApplicationRootFrame<T extends ApplicationContext> extends ApplicationFrame<T> implements ApplicationContextListener {

	public ApplicationRootFrame(final T ctx) {
		super(ctx);
		ctx.addApplicationContextListener(this);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ctx.shutdown();
			}
		});
	}

	public void applicationShutdown(ApplicationContext context) {
	}

	public void applicationStartup(ApplicationContext context) {
	}
}
