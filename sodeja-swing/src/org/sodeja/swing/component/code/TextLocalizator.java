package org.sodeja.swing.component.code;

import java.awt.Container;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.text.JTextComponent;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Predicate1;
import org.sodeja.lang.StringUtils;
import org.sodeja.model.DefaultLocalizableResource;
import org.sodeja.model.LocalizableResource;
import org.sodeja.swing.component.action.ApplicationAction;
import org.sodeja.swing.component.form.FormPanelGridData;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.resource.LocalizationUtils;
import org.sodeja.swing.resource.ResourceConstants;
import org.sodeja.swing.validation.FailedValidationMessage;
import org.sodeja.swing.validation.SuccessfulValidationMessage;
import org.sodeja.swing.validation.ValidationMessage;

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
		LocalizationUtils.componentToResource(resource, ctx, tcValue);
		resource.comitData();
	}
	
	public void setEnabled(boolean enable) {
		tcValue.setEditable(enable);
		actionValue.setEnabled(enable);
	}

	public <R> ValidationMessage performValidation(List<LocalizableResource> otherResources) {
		LocalizationUtils.componentToResource(resource, ctx, tcValue);
		if(StringUtils.isEmpty(resource.getLocalizedValue(ctx.getLocaleProvider().getLocale()))) {
			return new FailedValidationMessage(ResourceConstants.VM_LOCALIZATION_MISSING);
		}

		
		for(final Locale locale : resource.getAvailableLocales()) {
			final String text = resource.getLocalizedValue(locale);
			List<LocalizableResource> result = ListUtils.filter(otherResources, 
				new Predicate1<LocalizableResource>() {
					public Boolean execute(LocalizableResource p) {
						if(p == resource.other) {
							return false;
						}
						
						String value = p.getLocalizedValue(locale);
						return text.equals(value);
					}});
			if(result.size() > 0) {
				return new FailedValidationMessage(ResourceConstants.VM_LOCALIZATION_COLLISION);
			}
		}
		
		return new SuccessfulValidationMessage();
	}
	
	private static final class PrivateLocalizableResource extends DefaultLocalizableResource {
		
		private LocalizableResource other;
		
		public PrivateLocalizableResource(LocalizableResource other) {
			this.other = other;
			this.id = other.getId();
			
			for(Locale locale : other.getAvailableLocales()) {
				this.i18nMap.put(locale, other.getLocalizedValue(locale));
			}
		}

		@Override
		public String getId() {
			return other.getId();
		}

		private void comitData() {
			for(Locale locale : other.getAvailableLocales()) {
				other.setLocalizedValue(locale, null);
			}
			
			for(Map.Entry<Locale, String> entry : i18nMap.entrySet()) {
				other.setLocalizedValue(entry.getKey(), entry.getValue());
			}
		}
	}
}
