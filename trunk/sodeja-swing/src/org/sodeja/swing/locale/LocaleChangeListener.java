package org.sodeja.swing.locale;

import java.util.EventListener;
import java.util.Locale;

public interface LocaleChangeListener extends EventListener {
	public void localeChanged(Locale oldLocale, Locale newLocale);
}
