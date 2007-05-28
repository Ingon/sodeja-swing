package org.sodeja.swing.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import org.sodeja.functional.Pair;
import org.sodeja.functional.Predicate1;
import org.sodeja.swing.context.ApplicationContext;

public class CheckBoxTableCellRenderer<T extends ApplicationContext> extends JPanel implements TableCellRenderer {
	private static final long serialVersionUID = -7675705979343419313L;
	
	@SuppressWarnings("unused")
	private T ctx;
	private Pair<Color, Color> scheme;
	private JCheckBox chkDelegate;
	
	private Predicate1 functor;
	
	public CheckBoxTableCellRenderer(T ctx) {
		super(new GridBagLayout());
		this.ctx = ctx;
		
		functor = new Predicate1<Boolean>() {
			public Boolean execute(Boolean p) {
				return p;
			}
		};

		scheme = RendererUtils.makeColors(ctx, this);
		
		setBorder(BorderFactory.createEmptyBorder());
		chkDelegate = new JCheckBox();
		chkDelegate.setVerticalAlignment(SwingConstants.CENTER);
		add(chkDelegate, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, 
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
	}
	
	public CheckBoxTableCellRenderer(Predicate1 functor) {
		this.functor = functor;
	}
	
	@SuppressWarnings("unchecked")
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		RendererUtils.updateView(this, scheme, row);
		// Special hack, because the checkbox background is actually gray
		if(this.getBackground() != table.getBackground() && row % 2 == 0) {
			this.setBackground(table.getBackground());
		}

		chkDelegate.setSelected((Boolean) functor.execute(value));
		
		RendererUtils.setProperBorder(this, isSelected, hasFocus);
		
		return this;
	}
}
