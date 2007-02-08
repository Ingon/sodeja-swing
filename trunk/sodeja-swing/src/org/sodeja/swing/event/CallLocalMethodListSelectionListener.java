package org.sodeja.swing.event;

import java.lang.reflect.Method;

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
		Method method = ReflectUtils.findMethodInHierarchy(targetInstance, targetMethodName, new Class[0]);
		ReflectUtils.executeMethod(targetInstance, method, new Object[] {});
	}
}
