package org.sodeja.swing.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.sodeja.functional.Pair;
import org.sodeja.swing.context.ApplicationContext;

public abstract class ExtractStringTableCellRenderer<T extends ApplicationContext, R> extends DefaultTableCellRenderer {
	
	protected T ctx;
	private Pair<Color, Color> scheme;
	
	public ExtractStringTableCellRenderer(T ctx) {
		this.ctx = ctx;
		scheme = RendererUtils.makeColors(ctx, this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		RendererUtils.updateView(this, scheme, row);
		return super.getTableCellRendererComponent(table, getText((R) value), isSelected, hasFocus, row, column);
	}

	public abstract String getText(R t);
}
