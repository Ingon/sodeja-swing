package org.sodeja.swing.event;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.sodeja.lang.reflect.ReflectUtils;

public class CallLocalMethodOnDoubleClickMouseListener extends MouseAdapter {
	private Object targetInstance;
	private String targetMethodName;
	
	public CallLocalMethodOnDoubleClickMouseListener(Object targetInstance, String targetMethodName) {
		this.targetInstance = targetInstance;
		this.targetMethodName = targetMethodName;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2) {
			ReflectUtils.executeMethod(targetInstance, targetMethodName, 
					ReflectUtils.EMPTY_TYPES, ReflectUtils.EMPTY_PARAMETERS);
		}
	}
}
