package org.sodeja.swing.context;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.sodeja.swing.component.ApplicationFrame;
import org.sodeja.swing.config.GUIConfiguration;
import org.sodeja.swing.locale.LocaleProvider;
import org.sodeja.swing.resource.LocalizationFactory;
import org.sodeja.swing.resource.ResourceProvider;
import org.sodeja.swing.validation.ValidationController;

public class DefaultApplicationContext implements ApplicationContext {

	private List<ApplicationContextListener> listeners;
	
	private LocaleProvider localeProvider;
	private ResourceProvider resourceProvider;
	private LocalizationFactory localizationFactory;
	
	private ApplicationFrame rootFrame;
	private ValidationController validationController;
	private GUIConfiguration guiConfiguration;
	
	public DefaultApplicationContext() {
		listeners = new ArrayList<ApplicationContextListener>();
	}
	
	public void addApplicationContextListener(ApplicationContextListener listener) {
		listeners.add(listener);
	}
	
	public void removeApplicationContextListener(ApplicationContextListener listener) {
		listeners.remove(listener);
	}

	public void shutdown() {
		for(ApplicationContextListener listener : listeners) {
			listener.applicationShutdown(this);
		}
		
		System.exit(0);
	}

	public void startup() {
		if(! SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					startup();
				}
			});
			return;
		}
		
		for(ApplicationContextListener listener : listeners) {
			listener.applicationStartup(this);
		}
		
		getRootFrame().setVisible(true);
	}

	public ApplicationFrame getRootFrame() {
		return rootFrame;
	}

	public void setRootFrame(ApplicationFrame rootFrame) {
		this.rootFrame = rootFrame;
	}

	public LocaleProvider getLocaleProvider() {
		return localeProvider;
	}

	public void setLocaleProvider(LocaleProvider localeProvider) {
		this.localeProvider = localeProvider;
	}

	public ResourceProvider getResourceProvider() {
		return resourceProvider;
	}

	public void setResourceProvider(ResourceProvider resourceProvider) {
		this.resourceProvider = resourceProvider;
	}

	public LocalizationFactory getLocalizationFactory() {
		return localizationFactory;
	}

	public void setLocalizationFactory(LocalizationFactory localizationFactory) {
		this.localizationFactory = localizationFactory;
	}

	public ValidationController getValidationController() {
		return validationController;
	}

	public void setValidationController(ValidationController validationController) {
		this.validationController = validationController;
	}

	public GUIConfiguration getGuiConfiguration() {
		return guiConfiguration;
	}

	public void setGuiConfiguration(GUIConfiguration guiConfiguration) {
		this.guiConfiguration = guiConfiguration;
	}
}
