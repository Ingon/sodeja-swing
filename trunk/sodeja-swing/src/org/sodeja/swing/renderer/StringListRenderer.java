package org.sodeja.swing.renderer;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public abstract class StringListRenderer<T> extends DefaultListCellRenderer {
	@SuppressWarnings("unchecked")
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		return super.getListCellRendererComponent(list, getText((T) value), index, isSelected, cellHasFocus);
	}

	public abstract String getText(T t);
}
