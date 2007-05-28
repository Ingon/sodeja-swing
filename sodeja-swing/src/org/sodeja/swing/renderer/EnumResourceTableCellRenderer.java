package org.sodeja.swing.renderer;

import org.sodeja.swing.context.ApplicationContext;

public class EnumResourceTableCellRenderer<T extends ApplicationContext, E extends Enum> extends ExtractStringTableCellRenderer<T, E> {

	private static final long serialVersionUID = 4719989178354186555L;

	public EnumResourceTableCellRenderer(T ctx) {
		super(ctx);
	}

	@Override
	public String getText(E t) {
		return ctx.getResourceProvider().getEnumValue(t);
	}
}
