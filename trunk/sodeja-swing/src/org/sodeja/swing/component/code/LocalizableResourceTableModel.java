package org.sodeja.swing.component.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.sodeja.model.LocalizableResource;

class LocalizableResourceTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 4658917363517240515L;

	private List<Locale> locales;
	private Map<Locale, String> i18n;
	
	public LocalizableResourceTableModel() {
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
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 1;
	}
	
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		Locale locale = getLocale(rowIndex);
		i18n.put(locale	, (String) value);
	}

	protected Locale getLocale(int index) {
		return locales.get(index);
	}
	
	public void removeLocaleAt(int index) {
		Locale locale = getLocale(index);
		locales.remove(locale);
		i18n.remove(locale);
		
		fireTableDataChanged();
	}
	
	public void addLocale(Locale locale, String value) {
		if(! locales.contains(locale)) {
			locales.add(locale);
		}
		
		i18n.put(locale, value);
		
		fireTableDataChanged();
	}
	
	public void copyFrom(LocalizableResource code) {
		locales.clear();
		i18n.clear();
		
		locales.addAll(code.getAvailableLocales());
		for(Locale locale : locales) {
			i18n.put(locale, code.getLocalizedValue(locale));
		}
	}
	
	public void copyTo(LocalizableResource code) {
		for(Locale locale : code.getAvailableLocales()) {
			code.setLocalizedValue(locale, null);
		}
		
		for(Map.Entry<Locale, String> entry : i18n.entrySet()) {
			code.setLocalizedValue(entry.getKey(), entry.getValue());
		}
	}
}
