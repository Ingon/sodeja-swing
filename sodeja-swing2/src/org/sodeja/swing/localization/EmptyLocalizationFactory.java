package org.sodeja.swing.localization;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class EmptyLocalizationFactory implements LocalizationFactory {

	public EmptyLocalizationFactory() {
	}
	
	@Override
	public JLabel createLabel(Enum<?> i18n) {
		return new JLabel(i18n.name());
	}

	@Override
	public Border createTitledBorder(Enum<?> i18n) {
		return BorderFactory.createTitledBorder(i18n.name());
	}

}
