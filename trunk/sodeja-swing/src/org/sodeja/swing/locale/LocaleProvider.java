package org.sodeja.swing.locale;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.SwingUtilities;

public class LocaleProvider {
	
	private Locale locale = Locale.ENGLISH;
	private List<LocaleChangeListener> listenerList;

	public LocaleProvider() {
		listenerList = new ArrayList<LocaleChangeListener>();
	}
	
	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		notifyLocaleChanged(this.locale, locale);
		this.locale = locale;
	}

	public void addLocaleChangeListener(LocaleChangeListener listener) {
		listenerList.add(listener);
	}
	
	public void removeLocaleChangeListener(LocaleChangeListener listener) {
		listenerList.remove(listener);
	}
	
	private void notifyLocaleChanged(final Locale oldLocale, final Locale newLocale) {
		if(! SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					notifyLocaleChanged(oldLocale, newLocale);
				}
			});
			return;
		}
		for(LocaleChangeListener listener : listenerList) {
			listener.localeChanged(oldLocale, newLocale);
		}
	}
}
