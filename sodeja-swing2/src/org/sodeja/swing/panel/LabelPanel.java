package org.sodeja.swing.panel;

import javax.swing.JLabel;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.layout.GridBag;

class LabelPanel<T extends ApplicationContext> extends ApplicationPanel<T> {
	private static final long serialVersionUID = -9115213246055401734L;

	private Enum<?> i18nKey;
	
	public LabelPanel(Enum<?> i18nKey) {
		this.i18nKey = i18nKey;
	}
	
	@Override
	protected void initComponents() {
		JLabel lblName = getContext().getLocalizationFactory().createLabel(i18nKey);
		lblName.setFont(lblName.getFont().deriveFont(18f));
		lblName.setHorizontalAlignment(JLabel.CENTER);
		
		add(lblName, GridBag.bigPanel());
	}
}
