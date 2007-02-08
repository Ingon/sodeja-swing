package org.sodeja.swing.event;

import java.util.Locale;

import org.sodeja.lang.reflect.ReflectUtils;
import org.sodeja.swing.locale.LocaleChangeListener;

public class CallLocalMethodLocaleChangeListener implements LocaleChangeListener {

	private Object targetInstance;
	private String targetMethodName;
	
	public CallLocalMethodLocaleChangeListener(Object targetInstance, String targetMethodName) {
		this.targetInstance = targetInstance;
		this.targetMethodName = targetMethodName;
	}

	public void localeChanged(Locale oldLocale, Locale newLocale) {
		ReflectUtils.executeMethod(targetInstance, targetMethodName, 
				ReflectUtils.EMPTY_TYPES, ReflectUtils.EMPTY_PARAMETERS);
	}
}
