package org.sodeja.swing.component.action;

import java.awt.event.ActionEvent;
import java.lang.reflect.Method;

import org.sodeja.lang.reflect.ReflectUtils;
import org.sodeja.swing.context.ApplicationContext;

public class CallLocalMethodAction<T extends ApplicationContext> extends LocalizedAction<T> {

	private static final long serialVersionUID = -3734093629767792086L;
	
	private Object targetInstance;
	private String targetMethodName;
	
	public CallLocalMethodAction(T ctx, String i18nKey, Object targetInstance, String targetMethodName) {
		super(ctx, i18nKey);
		this.targetInstance = targetInstance;
		this.targetMethodName = targetMethodName;
	}

	public void actionPerformed(ActionEvent e) {
		Method method = ReflectUtils.findMethodInHierarchy(targetInstance, targetMethodName, new Class[0]);
		ReflectUtils.executeMethod(targetInstance, method, new Object[] {});
	}
}
