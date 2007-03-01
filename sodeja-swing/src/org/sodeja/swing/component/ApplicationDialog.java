package org.sodeja.swing.component;

import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

import org.sodeja.swing.ButtonBarFactory;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.context.ApplicationContextProvider;

public abstract class ApplicationDialog<T extends ApplicationContext> extends JDialog implements ApplicationContextProvider<T> {

	private static final String ESCAPE_ACTION_KEY = "ESCAPE_ACTION_KEY"; //$NON-NLS-1$

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
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        
		KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		this.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(stroke, ESCAPE_ACTION_KEY);
		this.getRootPane().getActionMap().put(ESCAPE_ACTION_KEY, ButtonBarFactory.closeButton(ctx, this));
	}
	
    protected abstract void initComponents();
    
    protected void postInitComponents() {
    	this.setLocationRelativeTo(getParent());
    }

    protected void closeCallback() {
    	setVisible(false);
    }
}
