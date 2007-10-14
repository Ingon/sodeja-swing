package org.sodeja.swing.resource;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.KeyStroke;

public interface ResourceProvider {
    public String getString(String key);
	public String getString(String key, Object[] params);
    public String getString(Enum<?> key);
    
    public Integer getInteger(String key);
    public KeyStroke getKeyStroke(String key);
    public KeyStroke getKeyStroke(Enum<?> key);
	
    public String getEnumString(Enum<?> key);
    public Image getImage(String key);
    public Icon getIcon(String key);
}
