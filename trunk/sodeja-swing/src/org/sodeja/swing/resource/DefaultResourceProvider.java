package org.sodeja.swing.resource;

import java.awt.Image;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.context.ApplicationContextListener;
import org.sodeja.swing.locale.LocaleChangeListener;

public class DefaultResourceProvider implements ResourceProvider, ApplicationContextListener, LocaleChangeListener {

	private String bundleName;
	private String imagesBase;
    private ResourceBundle bundle;
	
    public DefaultResourceProvider(ApplicationContext context, String bundleName, String imagesBase) {
    	context.addApplicationContextListener(this);
    	
    	this.bundleName = bundleName;
    	this.imagesBase = imagesBase;

    	loadBundle(context.getLocaleProvider().getLocale());
    }
    
    public String getStringValue(String key) {
        return bundle.getString(key);
    }

    public String getEnumValue(Enum key) {
		return getStringValue(key.getDeclaringClass().getName() + "." + key);
	}

	public Image getImageValue(String key) {
    	return getImageIcon(key).getImage();
	}

	public Icon getIconValue(String key) {
    	return getImageIcon(key); 
    }

	private ImageIcon getImageIcon(String key) {
		return new ImageIcon(this.getClass().getResource(imagesBase + getStringValue(key)));//$NON-NLS-1$
	}

	public void applicationStartup(ApplicationContext context) {
		context.getLocaleProvider().addLocaleChangeListener(this);
		loadBundle(context.getLocaleProvider().getLocale());
	}
	
	public void applicationShutdown(ApplicationContext context) {
		context.getLocaleProvider().removeLocaleChangeListener(this);
	}
	
	public void localeChanged(Locale oldLocale, Locale newLocale) {
		loadBundle(newLocale);
	}

	private void loadBundle(Locale newLocale) {
		bundle = ResourceBundle.getBundle(bundleName, newLocale);
	}
}
