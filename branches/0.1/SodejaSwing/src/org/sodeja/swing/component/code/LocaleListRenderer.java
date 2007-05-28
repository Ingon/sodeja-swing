/**
 * 
 */
package org.sodeja.swing.component.code;

import java.util.Locale;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.renderer.ExtractStringListRenderer;

class LocaleListRenderer extends ExtractStringListRenderer<Locale> {
	private static final long serialVersionUID = -6284862581490326146L;
	
	private ApplicationContext ctx;
	
	LocaleListRenderer(ApplicationContext ctx) {
		this.ctx = ctx;
	}

	@Override
	public String getText(Locale t) {
		return getDisplayName(t, ctx);
	}

	public static String getDisplayName(Locale locale, ApplicationContext ctx) {
		return locale.getDisplayName(ctx.getLocaleProvider().getLocale());
	}
}