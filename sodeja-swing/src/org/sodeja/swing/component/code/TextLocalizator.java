package org.sodeja.swing.component.code;

import java.awt.Container;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.swing.text.JTextComponent;

import org.sodeja.model.DefaultLocalizableResource;
import org.sodeja.model.LocalizableResource;
import org.sodeja.swing.component.action.ApplicationAction;
import org.sodeja.swing.component.form.FormPanelGridData;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.resource.LocalizationUtils;

abstract class TextLocalizator<T extends ApplicationContext> {

	protected T ctx;
	protected Container container;
	protected FormPanelGridData gridData;
	protected String lblResource;
	
	protected JTextComponent tcValue;
	protected ApplicationAction actionValue;
	
	protected PrivateLocalizableResource resource;
	
	public TextLocalizator(T ctx, Container container, FormPanelGridData gridData, String lblResource) {
		this.ctx = ctx;
		this.container = container;
		this.gridData = gridData;
		this.lblResource = lblResource;
	}
	
	protected abstract void initComponents();

	protected void dictionaryCallback() {
		LocalizationUtils.showLocalizationDialog(resource, ctx, tcValue);
	}
	
	public void useValue(LocalizableResource resource) {
		this.resource = new PrivateLocalizableResource(resource);
		LocalizationUtils.resourceToComponent(resource, ctx, tcValue);
	}
	
	public void saveValues() {
		resource.comitData();
	}
	
	public void setEnabled(boolean enable) {
		tcValue.setEditable(enable);
		actionValue.setEnabled(enable);
	}
	
	private static class PrivateLocalizableResource extends DefaultLocalizableResource {
		
		private LocalizableResource other;
		
		public PrivateLocalizableResource(LocalizableResource other) {
			this.other = other;
		}

		@Override
		public String getId() {
			return other.getId();
		}

		@Override
		public String getLocalizedValue(Locale locale) {
			String value = super.getLocalizedValue(locale);
			if(value != null) {
				return value;
			}
			return other.getLocalizedValue(locale);
		}
		
		@Override
		public Collection<Locale> getAvailableLocales() {
			Set<Locale> allLocales = new HashSet<Locale>(other.getAvailableLocales());
			allLocales.addAll(super.getAvailableLocales());
			return allLocales;
		}

		private void comitData() {
			for(Map.Entry<Locale, String> entry : i18nMap.entrySet()) {
				other.setLocalizedValue(entry.getKey(), entry.getValue());
			}
		}
	}
}
