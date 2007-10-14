package org.sodeja.swing.resource;

import java.awt.Image;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.locale.LocaleChangeListener;

public class DefaultResourceProvider implements ResourceProvider, LocaleChangeListener {

	private String bundleName;
	private String imagesBase;
    private ResourceBundle bundle;
	
    public DefaultResourceProvider(ApplicationContext context, String bundleName, String imagesBase) {
    	context.getLocaleProvider().addLocaleChangeListener(this);
    	
    	this.bundleName = bundleName;
    	this.imagesBase = imagesBase;

    	loadBundle(context.getLocaleProvider().getLocale());
    }
    
	@Override
    public String getString(String key) {
		try {
			return bundle.getString(key);
		} catch(MissingResourceException exc) {
			String message = exc.getMessage();
			if(! (message.endsWith(".mnemonic") || message.endsWith(".icon") || message.endsWith(".tooltip"))) {
				System.out.println(exc.getMessage());
			}
			return key;
		}
    }

	@Override
    public String getString(String key, Object[] params) {
    	String message = getString(key);
    	return MessageFormat.format(message, params);
	}

	@Override
	public String getString(Enum<?> key) {
		return getString(key.name());
	}
	
	@Override
	public String getEnumString(Enum<?> key) {
		return getString(key.getDeclaringClass().getName() + "." + key); //$NON-NLS-1$
	}

	@Override
	public Integer getInteger(String key) {
		String value = getString(key);
		if(value.equals(key)) {
			return 0;
		}
		return Integer.parseInt(value);
	}

	@Override
	public KeyStroke getKeyStroke(String key) {
		return KeyStroke.getKeyStroke(key);
	}

	@Override
	public KeyStroke getKeyStroke(Enum<?> key) {
		return getKeyStroke(key.name());
	}

	@Override
	public Image getImage(String key) {
    	return getImageIcon(key).getImage();
	}

	@Override
	public Icon getIcon(String key) {
    	return getImageIcon(key); 
    }

	private ImageIcon getImageIcon(String key) {
		return new ImageIcon(imagesBase + getString(key));
	}
	
	@Override
	public void localeChanged(Locale oldLocale, Locale newLocale) {
		loadBundle(newLocale);
	}

	private void loadBundle(Locale newLocale) {
		bundle = ResourceBundle.getBundle(bundleName, newLocale);
	}
}
