package org.sodeja.swing.context;

import java.util.EventListener;

public interface ApplicationContextListener extends EventListener {
	public void applicationStartup(ApplicationContext context);
	public void applicationShutdown(ApplicationContext context);
}
