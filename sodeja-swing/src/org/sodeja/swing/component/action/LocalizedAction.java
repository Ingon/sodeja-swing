package org.sodeja.swing.component.action;

import java.util.Locale;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.locale.LocaleChangeListener;

public abstract class LocalizedAction<T extends ApplicationContext> extends ApplicationAction<T>
		implements LocaleChangeListener {

	private String i18nKey;
	
	public LocalizedAction(T ctx, String i18nKey) {
		super(ctx);
		this.i18nKey = i18nKey;
		updateLocalizableResources(ctx);
	}

	public void updateLocalizableResources(T ctx) {
		setName(ctx.getResourceProvider().getStringValue(i18nKey));
		// setIcon(LocalizationUtils.getIcon(i18nKey));
		// setMnemonic(LocalizationUtils.getMnemonic(i18nKey));
		// setTooltip(LocalizationUtils.getTooltip(i18nKey));
		// setAccelerator(LocalizationUtils.getKeyStroke(i18nKey));
		// if (getAccelerator() != null) {
		// SwingServiceFinder.getInstance().getRootPane(b)
		// .registerKeyboardAction(this, getAccelerator(),
		// JComponent.WHEN_IN_FOCUSED_WINDOW);
		// }
	}

	public void localeChanged(Locale oldLocale, Locale newLocale) {
		updateLocalizableResources(getContext());
	}
}
