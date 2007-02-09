package org.sodeja.swing.renderer;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.sodeja.model.LocalizableResource;
import org.sodeja.swing.context.ApplicationContext;

public class LocalizedResourceTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -288881810747361668L;

	private ApplicationContext ctx;
	
	public LocalizedResourceTableCellRenderer(ApplicationContext ctx) {
		this.ctx = ctx;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if(! (value instanceof LocalizableResource)) {
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
		LocalizableResource loc = (LocalizableResource) value;
		return super.getTableCellRendererComponent(table, loc.getLocalizedValue(ctx.getLocaleProvider().getLocale()), 
				isSelected, hasFocus, row, column);
	}

}
