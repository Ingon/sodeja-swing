package org.sodeja.swing.resource;

import java.awt.Image;

import javax.swing.Icon;

public interface ResourceProvider {
    public String getStringValue(String key);
	public String getFormattedStringValue(String key, String[] params);
	
    public String getEnumValue(Enum key);
    public Image getImageValue(String key);
    public Icon getIconValue(String key);
}
