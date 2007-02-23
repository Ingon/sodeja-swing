package org.sodeja.swing.component.code;

import java.util.Locale;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.renderer.StringTableCellRenderer;

class LocaleTableRenderer extends StringTableCellRenderer<Locale> {
	private ApplicationContext ctx;
	
	LocaleTableRenderer(ApplicationContext ctx) {
		this.ctx = ctx;
	}
	
	@Override
	public String getText(Locale t) {
		return LocaleListRenderer.getDisplayName(t, ctx);
	}
}
