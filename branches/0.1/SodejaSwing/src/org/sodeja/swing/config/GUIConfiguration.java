package org.sodeja.swing.config;

import java.util.HashMap;
import java.util.Map;

import javax.swing.filechooser.FileSystemView;

public class GUIConfiguration {
	
	private String fileDialogDirectory;
	private Map<String, Object> properties;
	
	public GUIConfiguration() {
		fileDialogDirectory = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
		properties = new HashMap<String, Object>();
	}
	
	public String getFileDialogDirectory() {
		return fileDialogDirectory;
	}

	public void setFileDialogDirectory(String fileDialogDirectory) {
		this.fileDialogDirectory = fileDialogDirectory;
	}
	
	public Object get(String key) {
		return properties.get(key);
	}
	
	public Object set(String key, Object value) {
		return properties.put(key, value);
	}
}
