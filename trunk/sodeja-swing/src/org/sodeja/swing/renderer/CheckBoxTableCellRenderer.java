package org.sodeja.swing.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.sodeja.functional.Pair;
import org.sodeja.functional.Predicate1;
import org.sodeja.swing.context.ApplicationContext;

public class CheckBoxTableCellRenderer<T extends ApplicationContext> extends JCheckBox implements TableCellRenderer {
	private static final long serialVersionUID = -7675705979343419313L;
	
	private T ctx;
	private Pair<Color, Color> scheme;
	
	private Predicate1 functor;
	
	public CheckBoxTableCellRenderer(T ctx) {
		this.ctx = ctx;
		
		functor = new Predicate1<Boolean>() {
			public Boolean execute(Boolean p) {
				return p;
			}
		};

		scheme = RendererUtils.makeColors(ctx, this);
	}
	
	public CheckBoxTableCellRenderer(Predicate1 functor) {
		this.functor = functor;
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		RendererUtils.updateView(this, scheme, row);
		// Special hack, because the checkbox background is actually gray
		if(this.getBackground() != table.getBackground() && row % 2 == 0) {
			this.setBackground(table.getBackground());
		}

		setSelected((Boolean) functor.execute(value));
		
		return this;
	}
}
