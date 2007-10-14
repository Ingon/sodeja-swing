package org.sodeja.swing.resource;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.KeyStroke;

public class EmptyResourceProvider implements ResourceProvider {
	@Override
	public String getEnumString(Enum<?> key) {
		return key.name();
	}

	@Override
	public Icon getIcon(String key) {
		return null;
	}

	@Override
	public Image getImage(String key) {
		return null;
	}

	@Override
	public Integer getInteger(String key) {
		return 0;
	}

	@Override
	public KeyStroke getKeyStroke(String key) {
		return null;
	}

	@Override
	public KeyStroke getKeyStroke(Enum<?> key) {
		return null;
	}

	@Override
	public String getString(String key) {
		return key;
	}

	@Override
	public String getString(String key, Object[] params) {
		return key;
	}

	@Override
	public String getString(Enum<?> key) {
		return key.name();
	}

}
