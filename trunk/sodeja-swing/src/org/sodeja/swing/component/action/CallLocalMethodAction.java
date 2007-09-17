package org.sodeja.swing.component.action;

import java.awt.event.ActionEvent;

import org.sodeja.lang.reflect.ReflectUtils;
import org.sodeja.swing.context.ApplicationContext;

public class CallLocalMethodAction<T extends ApplicationContext> extends LocalizedAction<T> {

	private static final long serialVersionUID = -3734093629767792086L;
	
	private Object targetInstance;
	private String targetMethodName;
	
	public CallLocalMethodAction(T ctx, Enum i18nKey, Object targetInstance, String targetMethodName) {
		this(ctx, i18nKey.name(), targetInstance, targetMethodName);
	}
	
	public CallLocalMethodAction(T ctx, String i18nKey, Object targetInstance, String targetMethodName) {
		super(ctx, i18nKey);
		this.targetInstance = targetInstance;
		this.targetMethodName = targetMethodName;
	}

	public void actionPerformed(ActionEvent e) {
		ReflectUtils.executeMethod(targetInstance, targetMethodName, 
				ReflectUtils.EMPTY_TYPES, ReflectUtils.EMPTY_PARAMETERS);
	}
}
