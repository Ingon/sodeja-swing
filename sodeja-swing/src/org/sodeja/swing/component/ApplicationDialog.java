package org.sodeja.swing.component;

import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

import org.sodeja.swing.component.action.CallLocalMethodAction;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.context.ApplicationContextProvider;
import org.sodeja.swing.resource.ResourceConstants;

public abstract class ApplicationDialog<T extends ApplicationContext> extends JDialog implements ApplicationContextProvider<T> {

	private static final String ACTION_KEY = "ACTION_KEY"; //$NON-NLS-1$

	protected T ctx;
	
	public ApplicationDialog(T ctxCons) throws HeadlessException {
		super(ctxCons.getRootFrame());
		initInternal(ctxCons);
	}

	public ApplicationDialog(T ctxCons, ApplicationDialog parent) throws HeadlessException {
		super(parent);
		initInternal(ctxCons);
	}

	public ApplicationDialog(T ctxCons, ApplicationFrame parent) throws HeadlessException {
		super(parent);
		initInternal(ctxCons);
	}
	
	public T getContext() {
		return ctx;
	}

	private void initInternal(T ctxCons) {
		this.ctx = ctxCons;
		
		preInitComponents();
		initComponents();
		postInitComponents();
	}

	protected void preInitComponents() {
        setLayout(new GridBagLayout());
        
		KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		this.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(stroke, ACTION_KEY);
		this.getRootPane().getActionMap().put(ACTION_KEY, 
				new CallLocalMethodAction<T>(ctx, ResourceConstants.BTN_CLOSE, this, "closeCallback"));
	}
	
    protected abstract void initComponents();
    
    protected void postInitComponents() {
    	this.setLocationRelativeTo(getParent());
    }

    protected void closeCallback() {
    	setVisible(false);
    }
}
