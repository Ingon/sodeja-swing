package org.sodeja.swing.component.code;

import java.awt.HeadlessException;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.sodeja.functional.Pair;
import org.sodeja.swing.component.ApplicationDialog;
import org.sodeja.swing.component.form.FormPanelGridData;
import org.sodeja.swing.component.form.FormUtils;
import org.sodeja.swing.component.form.NamedFormDialog;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.resource.ResourceConstants;

class AddLocalizationDialog<T extends ApplicationContext> extends NamedFormDialog<T> {
	private static final long serialVersionUID = 1194782941790952326L;

	private JComboBox cmbLocales;
	private JTextField tfValue;
	
	private Pair<Locale, String> result;
	
	public AddLocalizationDialog(T ctxCons, ApplicationDialog parent) throws HeadlessException {
		super(ctxCons, parent);
	}

	@Override
	protected String getResourceName() {
		return ResourceConstants.DLG_LOCALIZATION_ADD;
	}

	@Override
	protected void initComponentsDelegate(FormPanelGridData gridData) {
		cmbLocales = FormUtils.addLabeledCombo(ctx, this.getContentPane(), ResourceConstants.LBL_LOCALE, gridData);
		tfValue = FormUtils.addLabeledField(ctx, this.getContentPane(), ResourceConstants.LBL_VALUE, gridData);
		
		for(Locale locale : Locale.getAvailableLocales()) {
			cmbLocales.addItem(locale);
		}
	}
	
	@Override
	protected void postInitComponents() {
		setSize(300, 150);
		setModal(true);
		
		super.postInitComponents();
	}

	public Pair<Locale, String> showAddLocalization() {
		result = null;
		cmbLocales.setSelectedIndex(0);
		tfValue.setText(""); //$NON-NLS-1$
		
		setVisible(true);
		
		return result;
	}

	@Override
	protected void okCallback() {
		result = new Pair<Locale, String>((Locale) cmbLocales.getSelectedItem(), tfValue.getText());
		
		super.okCallback();
	}
}
