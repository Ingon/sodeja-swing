package org.sodeja.swing.localization;

import javax.swing.JLabel;
import javax.swing.border.Border;

public interface LocalizationFactory {
	public JLabel createLabel(Enum<?> i18n);
	public Border createTitledBorder(Enum<?> i18n);
}
