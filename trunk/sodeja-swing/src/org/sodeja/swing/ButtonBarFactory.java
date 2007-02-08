package org.sodeja.swing;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.sodeja.swing.component.action.ApplicationAction;
import org.sodeja.swing.component.action.CallLocalMethodAction;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.resource.ResourceConstants;

public final class ButtonBarFactory {
	
	public static final Dimension BUTTON_PREFERRED_SIZE;
	
	static {
		BUTTON_PREFERRED_SIZE = new JButton().getPreferredSize();
		BUTTON_PREFERRED_SIZE.width = 100;
		BUTTON_PREFERRED_SIZE.height = new JTextField().getPreferredSize().height;
	}
	
    public static JPanel constructHorizontalButtonsPane(ApplicationAction... actions) {
        return constructButtonsPane(1, 0, actions);
    }

    public static JPanel constructVerticalButtonsPane(ApplicationAction... actions) {
        return constructButtonsPane(0, 1, actions);
    }
    
    public static <T extends ApplicationContext> ApplicationAction[] okCancelButtons(T ctx, Object instance) {
    	return new ApplicationAction[] {
				new CallLocalMethodAction<T>(ctx, ResourceConstants.BTN_OK, instance, "okCallback"),
				new CallLocalMethodAction<T>(ctx, ResourceConstants.BTN_CANCEL, instance, "cancelCallback")};
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
}
