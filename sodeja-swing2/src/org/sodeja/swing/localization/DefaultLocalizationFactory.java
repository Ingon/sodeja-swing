package org.sodeja.swing.localization;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

import org.sodeja.swing.resource.ResourceProvider;

public class DefaultLocalizationFactory implements LocalizationFactory {

	private ResourceProvider resources;
	public DefaultLocalizationFactory(ResourceProvider resources) {
		this.resources = resources;
	}
	
	@Override
	public JLabel createLabel(Enum<?> i18n) {
		return new JLabel(resources.getString(i18n));
	}

	@Override
	public Border createTitledBorder(Enum<?> i18n) {
		return BorderFactory.createTitledBorder(resources.getString(i18n));
	}
}
