package org.sodeja.swing.component.form;

import javax.swing.JLabel;

import org.sodeja.swing.GridBag;
import org.sodeja.swing.context.ApplicationContext;

public abstract class NamedFormPanel<T extends ApplicationContext, R> extends FormPanel<T, R> {
	public NamedFormPanel(T ctx) {
		super(ctx);
	}

	@Override
	protected void preInitComponents(FormPanelGridData gridData) {
		super.preInitComponents(gridData);
		
		JLabel lblName = new JLabel(ctx.getResourceProvider().getStringValue(getResourceName()));
		lblName.setFont(lblName.getFont().deriveFont(18f));
		lblName.setHorizontalAlignment(JLabel.CENTER);
		
		add(lblName, GridBag.create(gridData.getRow(), 0, gridData.getColumnsCount(), 1, 1.0, 0.0));
		
		gridData.nextRow();
	}
	
	protected abstract String getResourceName();
}
