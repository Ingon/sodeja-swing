package org.sodeja.swing.component.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.sodeja.model.Code;

class CodeTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 4658917363517240515L;

	private List<Locale> locales;
	private Map<Locale, String> i18n;
	
	public CodeTableModel() {
		locales = new ArrayList<Locale>();
		i18n = new HashMap<Locale, String>();
	}
	
	public int getColumnCount() {
		return 2;
	}

	public int getRowCount() {
		return locales.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Locale locale = locales.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return locale;
		default:
			return i18n.get(locale);
		}
	}
	
	public void copyFrom(Code code) {
		locales.clear();
		i18n.clear();
		
		locales.addAll(code.getAvailableLocales());
		for(Locale locale : locales) {
			i18n.put(locale, code.getLocalizedValue(locale));
		}
	}
	
	public void copyTo(Code code) {
		for(Map.Entry<Locale, String> entry : i18n.entrySet()) {
			code.setLocalizedValue(entry.getKey(), entry.getValue());
		}
	}
}
