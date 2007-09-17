package org.sodeja.swing;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import org.sodeja.collections.ArrayUtils;
import org.sodeja.swing.component.action.ApplicationAction;
import org.sodeja.swing.component.action.CallLocalMethodAction;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.resource.ResourceConstants;

public final class ButtonBarFactory {
	
	public static final Dimension BUTTON_PREFERRED_SIZE;
	
	static {
		BUTTON_PREFERRED_SIZE = new JButton().getPreferredSize();
		BUTTON_PREFERRED_SIZE.width = 150;
		BUTTON_PREFERRED_SIZE.height = new JTextField().getPreferredSize().height;
	}
	
    public static JPanel constructHorizontalButtonsPane(ApplicationAction... actions) {
        return constructButtonsPane(1, 0, actions);
    }

    public static JPanel constructVerticalButtonsPane(ApplicationAction... actions) {
        return constructButtonsPane(0, 1, actions);
    }
    
    public static <T extends ApplicationContext> ApplicationAction<T>[] okCancelButtons(T ctx, Object instance) {
    	return ArrayUtils.asArray(okButton(ctx, instance), cancelButton(ctx, instance));
//    	return new ApplicationActcion<T>[] {okButton(ctx, instance), cancelButton(ctx, instance)};
    }
    
    public static <T extends ApplicationContext> ApplicationAction<T>[] closeButtons(T ctx, Object instance) {
    	return ArrayUtils.asArray(closeButton(ctx, instance));
//    	return new ApplicationAction<T>[] {closeButton(ctx, instance)};
    }

    public static <T extends ApplicationContext> ApplicationAction<T> closeButton(T ctx, Object instance) {
    	CallLocalMethodAction<T> result = new CallLocalMethodAction<T>(ctx, ResourceConstants.BTN_CLOSE, instance, "closeCallback"); //$NON-NLS-1$
    	result.setIcon(ctx.getResourceProvider().getIconValue(ResourceConstants.ICON_CLOSE));
		return result;
    }
    
    public static <T extends ApplicationContext> ApplicationAction<T>[] closeToCancelButtons(T ctx, Object instance) {
    	return new ApplicationAction[] {closeToCancelButton(ctx, instance)};
    }
    
    public static <T extends ApplicationContext> ApplicationAction<T> closeToCancelButton(T ctx, Object instance) {
    	CallLocalMethodAction<T> result = new CallLocalMethodAction<T>(ctx, ResourceConstants.BTN_CLOSE, instance, "cancelCallback");//$NON-NLS-1$
    	result.setIcon(ctx.getResourceProvider().getIconValue(ResourceConstants.ICON_CLOSE));
		return result; 
    }
    
    public static <T extends ApplicationContext> ApplicationAction<T> okButton(T ctx, Object instance) {
    	CallLocalMethodAction<T> result = new CallLocalMethodAction<T>(ctx, ResourceConstants.BTN_OK, instance, "okCallback");//$NON-NLS-1$
    	result.setIcon(ctx.getResourceProvider().getIconValue(ResourceConstants.ICON_OK));
		return result; 
    }

    public static <T extends ApplicationContext> ApplicationAction<T> cancelButton(T ctx, Object instance) {
    	CallLocalMethodAction<T> result = new CallLocalMethodAction<T>(ctx, ResourceConstants.BTN_CANCEL, instance, "cancelCallback"); //$NON-NLS-1$
    	result.setIcon(ctx.getResourceProvider().getIconValue(ResourceConstants.ICON_CANCEL));
		return result;
    }
    
    public static <T extends ApplicationContext> ApplicationAction<T> searchButton(T ctx, Object instance) {
    	return new CallLocalMethodAction<T>(ctx, ResourceConstants.BTN_SEARCH, instance, "searchCallback"); //$NON-NLS-1$
    }

    public static <T extends ApplicationContext> ApplicationAction<T> addButton(T ctx, Object instance) {
    	return addButton(ctx, instance, "addCallback"); //$NON-NLS-1$
    }
    
    public static <T extends ApplicationContext> ApplicationAction<T> addButton(T ctx, Object instance, String callback) {
    	CallLocalMethodAction<T> result = new CallLocalMethodAction<T>(ctx, ResourceConstants.BTN_ADD, instance, callback);
    	result.setIcon(ctx.getResourceProvider().getIconValue(ResourceConstants.ICON_ADD));
		return result;
    }
    
    public static <T extends ApplicationContext> ApplicationAction<T> editButton(T ctx, Object instance) {
    	return editButton(ctx, instance, "editCallback"); //$NON-NLS-1$
    }
    
    public static <T extends ApplicationContext> ApplicationAction<T> editButton(T ctx, Object instance, String callback) {
    	CallLocalMethodAction<T> result = new CallLocalMethodAction<T>(ctx, ResourceConstants.BTN_EDIT, instance, callback);
    	result.setIcon(ctx.getResourceProvider().getIconValue(ResourceConstants.ICON_EDIT));
		return result;
    }
    
    public static <T extends ApplicationContext> ApplicationAction<T> removeButton(T ctx, Object instance) {
    	return removeButton(ctx, instance, "removeCallback");//$NON-NLS-1$
    }
    
    public static <T extends ApplicationContext> ApplicationAction<T> removeButton(T ctx, Object instance, String callback) {
    	CallLocalMethodAction<T> result = new CallLocalMethodAction<T>(ctx, ResourceConstants.BTN_REMOVE, instance, callback);
    	result.setIcon(ctx.getResourceProvider().getIconValue(ResourceConstants.ICON_REMOVE));
		return result; 
    }

    public static <T extends ApplicationContext> ApplicationAction<T> deleteButton(T ctx, Object instance) {
    	CallLocalMethodAction<T> result = new CallLocalMethodAction<T>(ctx, ResourceConstants.BTN_DELETE, instance, "deleteCallback"); //$NON-NLS-1$
    	result.setIcon(ctx.getResourceProvider().getIconValue(ResourceConstants.ICON_DELETE));
		return result;
    }

    public static <T extends ApplicationContext> ApplicationAction<T> upButton(T ctx, Object instance) {
    	return upButton(ctx, instance, "upCallback"); //$NON-NLS-1$
    }
    
    public static <T extends ApplicationContext> ApplicationAction<T> upButton(T ctx, Object instance, String callback) {
    	CallLocalMethodAction<T> result = new CallLocalMethodAction<T>(ctx, ResourceConstants.BTN_UP, instance, callback);
    	result.setIcon(ctx.getResourceProvider().getIconValue(ResourceConstants.ICON_UP));
		return result;
    }

    public static <T extends ApplicationContext> ApplicationAction<T> downButton(T ctx, Object instance) {
    	return downButton(ctx, instance, "downCallback"); //$NON-NLS-1$
    }
    
    public static <T extends ApplicationContext> ApplicationAction<T> downButton(T ctx, Object instance, String callback) {
    	CallLocalMethodAction<T> result = new CallLocalMethodAction<T>(ctx, ResourceConstants.BTN_DOWN, instance, callback);
    	result.setIcon(ctx.getResourceProvider().getIconValue(ResourceConstants.ICON_DOWN));
		return result;
    }
    
    public static <T extends ApplicationContext> ApplicationAction<T> dictionaryButton(T ctx, Object instance) {
    	CallLocalMethodAction<T> result = new CallLocalMethodAction<T>(ctx, ResourceConstants.BTN_DICTIONARY, instance, "dictionaryCallback"); //$NON-NLS-1$
    	result.setIcon(ctx.getResourceProvider().getIconValue(ResourceConstants.ICON_DICTIONARY));
		return result;
    }

    private static JPanel constructButtonsPane(int rows, int cols, ApplicationAction... actions) {
        JPanel pnlButtons = new JPanel(new GridLayout(rows, cols, 4, 4));
        for(ApplicationAction action : actions) {
        	pnlButtons.add(createButton(action));
        }
        return pnlButtons;
    }
    
    private static AbstractButton createButton(ApplicationAction action) {
    	Class type = (Class) action.getValue(ApplicationAction.TYPE);
    	if(type.equals(JToggleButton.class)) {
    		return new JToggleButton(action);
    	}
    	return new JButton(action);
    }
    
    public static JToolBar createToolbar(ApplicationAction... actions) {
    	JToolBar toolBar = new JToolBar();
    	fillToolbar(toolBar, actions);
    	return toolBar;
    }

    public static void fillToolbar(JToolBar toolBar, ApplicationAction... actions) {
    	for(ApplicationAction action : actions) {
    		toolBar.add(action);
    	}
    }
}
