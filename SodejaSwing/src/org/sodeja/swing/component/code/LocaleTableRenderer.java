package org.sodeja.swing.component.code;

import java.util.Locale;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.renderer.ExtractStringTableCellRenderer;

class LocaleTableRenderer<T extends ApplicationContext> extends ExtractStringTableCellRenderer<T, Locale> {
	private static final long serialVersionUID = -911779633276934967L;
	
	LocaleTableRenderer(T ctx) {
		super(ctx);
	}
	
	@Override
	public String getText(Locale t) {
		return LocaleListRenderer.getDisplayName(t, ctx);
	}
}
