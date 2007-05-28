package org.sodeja.swing.renderer;

import org.sodeja.swing.context.ApplicationContext;

public class EnumResourceListRenderer<E extends Enum> extends ExtractStringListRenderer<E> {
	
	private static final long serialVersionUID = -5030405234852587553L;
	
	private ApplicationContext ctx;
	
	public EnumResourceListRenderer(ApplicationContext ctx) {
		this.ctx = ctx;
	}
	
	@Override
	public String getText(E t) {
		return ctx.getResourceProvider().getEnumValue(t);
	}
}
