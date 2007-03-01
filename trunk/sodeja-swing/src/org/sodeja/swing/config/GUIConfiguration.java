package org.sodeja.swing.config;

import java.util.HashMap;
import java.util.Map;

import javax.swing.filechooser.FileSystemView;

public class GUIConfiguration {
	
	private String fileDialogDirectory;
	private Map<String, String> properties;
	
	public GUIConfiguration() {
		fileDialogDirectory = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
		properties = new HashMap<String, String>();
	}
	
	public String getFileDialogDirectory() {
		return fileDialogDirectory;
	}

	public void setFileDialogDirectory(String fileDialogDirectory) {
		this.fileDialogDirectory = fileDialogDirectory;
	}
	
	public String get(String key) {
		return properties.get(key);
	}
	
	public String set(String key, String value) {
		return properties.put(key, value);
	}
}
