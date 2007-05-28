package org.sodeja.swing.component.code;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.sodeja.swing.ButtonBarFactory;
import org.sodeja.swing.GridBag;
import org.sodeja.swing.component.form.FormPanelGridData;
import org.sodeja.swing.context.ApplicationContext;

public class AreaLocalizator<T extends ApplicationContext> extends TextLocalizator<T> {
	private double rowWeight;
	
	public AreaLocalizator(T ctx, Container container, FormPanelGridData gridData, String lblResource, double rowWeight) {
		super(ctx, container, gridData, lblResource);
		this.rowWeight = rowWeight;
		
		initComponents();
	}

	protected void initComponents() {
		container.add(ctx.getLocalizationFactory().createLabel(lblResource), 
				GridBag.lineLabelNorth(gridData.getRow()));
		
		tcValue = new JTextArea();
		tcValue.setPreferredSize(new Dimension(200, 200));
		container.add(new JScrollPane(tcValue), GridBag.create(1, gridData.getRow(), gridData.getColumnsCount() - 2, 1, 1.0, rowWeight));
		
		actionValue = ButtonBarFactory.dictionaryButton(ctx, this);
		container.add(new JButton(actionValue), GridBag.create(gridData.getColumnsCount() - 1, gridData.getRow(), 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL));
	}
}
