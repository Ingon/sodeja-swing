package org.sodeja.swing.context;

import org.sodeja.swing.frame.ApplicationFrame;
import org.sodeja.swing.locale.LocaleProvider;
import org.sodeja.swing.localization.EmptyLocalizationFactory;
import org.sodeja.swing.localization.LocalizationFactory;
import org.sodeja.swing.resource.EmptyResourceProvider;
import org.sodeja.swing.resource.ResourceProvider;

public class DefaultApplicationContext implements ApplicationContext {

	protected LocaleProvider localeProvider;
	protected ResourceProvider resourceProvider;
	protected LocalizationFactory localizationFactory;
	
	protected ApplicationFrame<?> rootFrame;

	public DefaultApplicationContext() {
		this.localeProvider = new LocaleProvider();
		this.resourceProvider = new EmptyResourceProvider();
		this.localizationFactory = new EmptyLocalizationFactory();
	}

	@Override
	public LocaleProvider getLocaleProvider() {
		return localeProvider;
	}

	@Override
	public ResourceProvider getResourceProvider() {
		return resourceProvider;
	}

	@Override
	public ApplicationFrame<?> getRootFrame() {
		return rootFrame;
	}
	
	@Override
	public LocalizationFactory getLocalizationFactory() {
		return localizationFactory;
	}

	public void setRootFrame(ApplicationFrame<?> rootFrame) {
		this.rootFrame = rootFrame;
	}
}
