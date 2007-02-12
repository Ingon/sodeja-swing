package org.sodeja.swing.renderer;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.sodeja.swing.context.ApplicationContext;

public class EnumResourceTableCellRenderer<E extends Enum> extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 4719989178354186555L;

	private ApplicationContext ctx;
	
	public EnumResourceTableCellRenderer(ApplicationContext ctx) {
		this.ctx = ctx;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if(! (value instanceof Enum)) {
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
		
		Enum object = (Enum) value;
		return super.getTableCellRendererComponent(table, ctx.getResourceProvider().getEnumValue(object), isSelected, hasFocus, row, column);
	}

}
