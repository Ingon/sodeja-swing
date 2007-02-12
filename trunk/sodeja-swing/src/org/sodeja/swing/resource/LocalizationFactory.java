package org.sodeja.swing.resource;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.event.CallLocalMethodLocaleChangeListener;

public class LocalizationFactory {

	private static final String I18NKey = "i18n"; //$NON-NLS-1$
	
	private ApplicationContext ctx;
	private List<WeakReference<JLabel>> labels;
	
	public LocalizationFactory(ApplicationContext ctx) {
		this.ctx = ctx;
		ctx.getLocaleProvider().addLocaleChangeListener(
				new CallLocalMethodLocaleChangeListener(this, "localeChangedCallback")); //$NON-NLS-1$
		labels = new ArrayList<WeakReference<JLabel>>();
	}
	
	public JLabel createLabel(String i18n) {
		JLabel lbl = new JLabel();
		lbl.putClientProperty(I18NKey, i18n);
		labels.add(new WeakReference<JLabel>(lbl));
		setText(lbl);
		return lbl;
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
	}

	private void setText(JLabel lbl) {
		lbl.setText(ctx.getResourceProvider().getStringValue((String) lbl.getClientProperty(I18NKey)));
	}
}
