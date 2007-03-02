package org.sodeja.swing.renderer;

import org.sodeja.model.LocalizableResource;
import org.sodeja.swing.context.ApplicationContext;

public class LocalizedResourceTableCellRenderer<T extends ApplicationContext> extends ExtractStringTableCellRenderer<T, LocalizableResource> {

	private static final long serialVersionUID = -288881810747361668L;

	public LocalizedResourceTableCellRenderer(T ctx) {
		super(ctx);
	}

	@Override
	public String getText(LocalizableResource t) {
		return t.getLocalizedValue(ctx.getLocaleProvider().getLocale());
	}
}
