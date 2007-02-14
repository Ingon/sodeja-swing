package org.sodeja.swing.event;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.sodeja.lang.reflect.ReflectUtils;

public class CallLocalMethodTreeSelectionListener implements TreeSelectionListener {

	private Object targetInstance;
	private String targetMethodName;
	
	public CallLocalMethodTreeSelectionListener(Object targetInstance, String targetMethodName) {
		this.targetInstance = targetInstance;
		this.targetMethodName = targetMethodName;
	}
	
	public void valueChanged(TreeSelectionEvent e) {
		ReflectUtils.executeMethod(targetInstance, targetMethodName, 
				ReflectUtils.EMPTY_TYPES, ReflectUtils.EMPTY_PARAMETERS);
	}
}
