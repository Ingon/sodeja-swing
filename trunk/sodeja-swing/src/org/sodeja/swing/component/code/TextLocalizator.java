package org.sodeja.swing.component.code;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import org.sodeja.model.LocalizableResource;
import org.sodeja.swing.component.action.ApplicationAction;
import org.sodeja.swing.component.form.FormPanelGridData;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.resource.LocalizationUtils;

abstract class TextLocalizator<T extends ApplicationContext> {

	protected T ctx;
	protected JComponent container;
	protected FormPanelGridData gridData;
	protected String lblResource;
	
	protected JTextComponent tcValue;
	protected ApplicationAction actionValue;
	
	protected LocalizableResource resource;
	
	public TextLocalizator(T ctx, JComponent container, FormPanelGridData gridData, String lblResource) {
		this.ctx = ctx;
		this.container = container;
		this.gridData = gridData;
		this.lblResource = lblResource;
	}
	
	protected abstract void initComponents();

	protected void dictionaryCallback() {
		LocalizationUtils.showLocalizationDialog(resource, ctx, tcValue);
	}
	
	public void setValue(LocalizableResource resource) {
		this.resource = resource;
		LocalizationUtils.resourceToComponent(resource, ctx, tcValue);
	}
	
	public void getValue(LocalizableResource resource) {
	}
	
	public void setEnabled(boolean enable) {
		tcValue.setEditable(enable);
		actionValue.setEnabled(enable);
	}
}
