package org.sodeja.swing.resource;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

import org.sodeja.model.LocalizableResource;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.event.CallLocalMethodLocaleChangeListener;

public class LocalizationFactory {

	private static final String I18NKey = "i18n"; //$NON-NLS-1$
	
	private ApplicationContext ctx;
	private List<WeakReference<JLabel>> labels;
	private List<WeakReference<LocalizableResource>> resources;
	
	public LocalizationFactory(ApplicationContext ctx) {
		this.ctx = ctx;
		ctx.getLocaleProvider().addLocaleChangeListener(
				new CallLocalMethodLocaleChangeListener(this, "localeChangedCallback")); //$NON-NLS-1$
		
		labels = new ArrayList<WeakReference<JLabel>>();
		resources = new ArrayList<WeakReference<LocalizableResource>>();
	}
	
	public JLabel createLabel(String i18n) {
		JLabel lbl = new JLabel();
		lbl.putClientProperty(I18NKey, i18n);
		labels.add(new WeakReference<JLabel>(lbl));
		setText(lbl);
		return lbl;
	}
	
	public JLabel createEnumLabel(Enum i18n) {
		JLabel lbl = new JLabel();
		lbl.putClientProperty(I18NKey, i18n);
		labels.add(new WeakReference<JLabel>(lbl));
		setText(lbl);
		return lbl;
	}
	
	public void connectLocalizableResourceWithProperty(LocalizableResource resource) {
		resources.add(new WeakReference<LocalizableResource>(resource));
		setLocalization(resource);
	}
	
	public Border createBorder(String i18n) {
		// TODO add some additional behavior here when locale has been changed
		return BorderFactory.createTitledBorder(ctx.getResourceProvider().getStringValue(i18n));
	}
	
	protected void localeChangedCallback() {
		for(WeakReference<JLabel> ref : labels) {
			JLabel lbl = ref.get();
			if(lbl != null) {
				setText(lbl);
			}
		}
		
		for(WeakReference<LocalizableResource> ref : resources) {
			LocalizableResource resource = ref.get();
			if(resource != null) {
				setLocalization(resource);
			}
		}
	}

	private void setText(JLabel lbl) {
		Object i18nProperty = lbl.getClientProperty(I18NKey);
		if(i18nProperty instanceof Enum) {
			lbl.setText(ctx.getResourceProvider().getEnumValue((Enum) i18nProperty));
		} else {
			lbl.setText(ctx.getResourceProvider().getStringValue((String) i18nProperty));
		}
	}
	
	private void setLocalization(LocalizableResource resource) {
		resource.setLocalizedValue(ctx.getLocaleProvider().getLocale(), ctx.getResourceProvider().getStringValue(resource.getId()));
	}
}
