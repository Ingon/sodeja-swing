package org.sodeja.swing.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

import org.sodeja.functional.Pair;
import org.sodeja.swing.context.ApplicationContext;

public abstract class MultyLineTableCellRenderer<T extends ApplicationContext, R> extends JTextArea implements TableCellRenderer {

	protected T ctx;
	private Pair<Color, Color> scheme;
	
	public MultyLineTableCellRenderer(T ctx) {
		this.ctx = ctx;
		scheme = RendererUtils.makeColors(ctx, this);
		setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
	}
	
	@SuppressWarnings("unchecked")
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		RendererUtils.updateView(this, scheme, row);
		
		String text = getTextDelegate((R) value);
		setText(text);
		updateRowHeight(table, row);
		
		RendererUtils.setProperBorder(this, isSelected, hasFocus);
		
		return this;
	}

	public abstract String getTextDelegate(R t);
	
	private void updateRowHeight(JTable table, int row) {
		int size = this.getText().split("\r\n").length; //$NON-NLS-1$
		int newRowHeight = getRowHeight() + 6;
		if(size > 1) {
			newRowHeight = getRowHeight() * size + 6;
		}
		
		if(table.getRowHeight(row) < newRowHeight || row == 0) {
			table.setRowHeight(row, newRowHeight);
		}
	}
}
