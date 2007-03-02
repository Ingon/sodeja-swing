package org.sodeja.swing.renderer;

import java.awt.Color;

import javax.swing.JComponent;

import org.sodeja.functional.Pair;
import org.sodeja.swing.context.ApplicationContext;

public class RendererUtils {
	private RendererUtils() {
	}
	
	public static final String ODD_COLOR = "ODD_COLOR";
	
	protected static Pair<Color, Color> makeColors(ApplicationContext ctx, JComponent component) {
		Color defaultBackground = component.getBackground();
		Color rowBackground = (Color) ctx.getGuiConfiguration().get(RendererUtils.ODD_COLOR);
		if(rowBackground == null) {
			rowBackground = defaultBackground;
		}
		return new Pair<Color, Color>(defaultBackground, rowBackground);
	}
	
	protected static void updateView(JComponent component, Pair<Color, Color> scheme, int row) {
		if(row % 2 == 1) {
			component.setBackground(scheme.second);
		} else {
			component.setBackground(scheme.first);
		}
	}
}
