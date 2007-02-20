package org.sodeja.swing.renderer;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public abstract class StringTableCellRenderer<T> extends DefaultTableCellRenderer {
	@SuppressWarnings("unchecked")
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		return super.getTableCellRendererComponent(table, getText((T) value), isSelected, hasFocus, row, column);
	}

	public abstract String getText(T t);
}
