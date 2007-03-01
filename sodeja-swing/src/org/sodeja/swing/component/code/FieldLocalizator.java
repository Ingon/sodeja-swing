package org.sodeja.swing.component.code;

import java.awt.Container;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.sodeja.swing.GridBag;
import org.sodeja.swing.component.action.CallLocalMethodAction;
import org.sodeja.swing.component.form.FormPanelGridData;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.resource.ResourceConstants;

public class FieldLocalizator<T extends ApplicationContext> extends TextLocalizator<T> {
	
	public FieldLocalizator(T ctx, Container container, FormPanelGridData gridData, String lblResource) {
		super(ctx, container, gridData, lblResource);
		initComponents();
	}

	protected void initComponents() {
		container.add(ctx.getLocalizationFactory().createLabel(lblResource), 
				GridBag.lineLabel(gridData.getRow()));
		
		tcValue = new JTextField();
		container.add(tcValue, GridBag.lineField(gridData.getRow(), gridData.getColumnsCount() - 2));
		
		actionValue = new CallLocalMethodAction<T>(ctx, ResourceConstants.BTN_DICTIONARY, this, "dictionaryCallback"); //$NON-NLS-1$
		container.add(new JButton(actionValue), GridBag.create(gridData.getColumnsCount() - 1, gridData.getRow(), 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL));
	}

	public void grabFocus() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				tcValue.grabFocus();
			}});
	}
}
