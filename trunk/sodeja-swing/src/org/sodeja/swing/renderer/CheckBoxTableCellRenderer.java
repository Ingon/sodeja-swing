package org.sodeja.swing.renderer;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.sodeja.functional.Predicate1;

public class CheckBoxTableCellRenderer extends JCheckBox implements TableCellRenderer {
	private static final long serialVersionUID = -7675705979343419313L;
	
	private Predicate1 functor;
	
	public CheckBoxTableCellRenderer() {
		functor = new Predicate1<Boolean>() {
			public Boolean execute(Boolean p) {
				return p;
			}
		};
	}
	
	public CheckBoxTableCellRenderer(Predicate1 functor) {
		this.functor = functor;
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		setSelected((Boolean) functor.execute(value));
		this.setBackground(table.getBackground());
		
		return this;
	}
}
