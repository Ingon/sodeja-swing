package org.sodeja.swing.dialog;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.panel.FormPanel;
import org.sodeja.swing.panel.StateListener;

public class FormDialog<T extends ApplicationContext> extends ApplicationDialog<T> {
	private static final long serialVersionUID = 8988331701334298822L;

	public FormDialog(T ctx, FormPanel<T> form, Enum<?> titleKey) {
		super(ctx);
		
		setTitle(ctx.getResourceProvider().getString(titleKey));
		setModal(true);
		
		initComponents(form);
	}

	private void initComponents(final FormPanel<T> form) {
		add(form, BorderLayout.CENTER);
		
		form.addStateListner(new StateListener() {
			@Override
			public void closed() {
				dispose();
			}
		});
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				form.cancelCallback();
				dispose();
			}
		});
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				form.openForm();
			}
		});
	}
}
