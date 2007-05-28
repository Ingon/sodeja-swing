package org.sodeja.swing.event;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.sodeja.lang.reflect.ReflectUtils;

public class CallLocalMethodListSelectionListener implements ListSelectionListener {
	
	private Object targetInstance;
	private String targetMethodName;
	
	public CallLocalMethodListSelectionListener(Object targetInstance, String targetMethodName) {
		this.targetInstance = targetInstance;
		this.targetMethodName = targetMethodName;
	}
	
	public void valueChanged(ListSelectionEvent e) {
		ReflectUtils.executeMethod(targetInstance, targetMethodName, 
				ReflectUtils.EMPTY_TYPES, ReflectUtils.EMPTY_PARAMETERS);
	}
}
