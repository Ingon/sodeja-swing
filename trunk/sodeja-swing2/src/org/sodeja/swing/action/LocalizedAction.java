package org.sodeja.swing.action;

import java.util.Locale;

import javax.swing.JComponent;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.locale.LocaleChangeListener;
import org.sodeja.swing.util.SwingUtils;

public abstract class LocalizedAction<T extends ApplicationContext> extends ApplicationAction<T> implements LocaleChangeListener {

	private static final String SUFFIX_MNEMONIC = ".mnemonic";
	private static final String SUFFIX_KEY_STROKE = ".accelerator";
	private static final String SUFFIX_TOOLTIP = ".tooltip";
	private static final String SUFFIX_ICON = ".icon";

	private String i18nKey;
	
	public LocalizedAction(Enum<?> i18nKey) {
		this.i18nKey = i18nKey.name();
	}

	public LocalizedAction(String i18nKey) {
		this.i18nKey = i18nKey;
	}
	
	@Override
	protected void init(T ctx) {
		performLocalization(ctx);
	}
	
	@Override
	public void localeChanged(Locale oldLocale, Locale newLocale) {
		T ctx = SwingUtils.getContext(null);
		performLocalization(ctx);
	}

	protected void performLocalization(T ctx) {
	    setName(ctx.getResourceProvider().getString(i18nKey));
		setIcon(ctx.getResourceProvider().getIcon(i18nKey + SUFFIX_ICON));
		setMnemonic(ctx.getResourceProvider().getInteger(i18nKey + SUFFIX_MNEMONIC));
		setTooltip(ctx.getResourceProvider().getString(i18nKey + SUFFIX_TOOLTIP));
		setAccelerator(ctx.getResourceProvider().getKeyStroke(i18nKey + SUFFIX_KEY_STROKE));
		
		if (getAccelerator() != null) {
			ctx.getRootFrame().getRootPane().registerKeyboardAction(this, getAccelerator(),
							JComponent.WHEN_IN_FOCUSED_WINDOW);
		}
	}
	
	@Override
	protected void destroy(T ctx) {
	}
}
