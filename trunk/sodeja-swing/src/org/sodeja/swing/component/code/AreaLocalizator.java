package org.sodeja.swing.component.code;

import java.awt.Container;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.sodeja.swing.GridBag;
import org.sodeja.swing.component.action.CallLocalMethodAction;
import org.sodeja.swing.component.form.FormPanelGridData;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.resource.ResourceConstants;

public class AreaLocalizator<T extends ApplicationContext> extends TextLocalizator<T> {
	private double rowWeight;
	
	public AreaLocalizator(T ctx, Container container, FormPanelGridData gridData, String lblResource, double rowWeight) {
		super(ctx, container, gridData, lblResource);
		this.rowWeight = rowWeight;
		
		initComponents();
	}

	protected void initComponents() {
		container.add(ctx.getLocalizationFactory().createLabel(lblResource), 
				GridBag.lineLabel(gridData.getRow()));
		
		tcValue = new JTextArea();
		container.add(new JScrollPane(tcValue), GridBag.create(1, gridData.getRow(), gridData.getColumnsCount() - 2, 1, 1.0, rowWeight));
		
		actionValue = new CallLocalMethodAction<T>(ctx, ResourceConstants.BTN_DICTIONARY, this, "dictionaryCallback"); //$NON-NLS-1$
		container.add(new JButton(actionValue), GridBag.create(gridData.getColumnsCount() - 1, gridData.getRow(), 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL));
	}
}
