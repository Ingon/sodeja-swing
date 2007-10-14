
package org.sodeja.swing.panel;

import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.localization.LocalizationFactory;
import org.sodeja.swing.resource.ResourceProvider;
import org.sodeja.swing.util.SwingUtils;

public abstract class ApplicationPanel<T extends ApplicationContext> extends JPanel {
	private static final long serialVersionUID = -5050280198066409777L;

	public ApplicationPanel() {
		super(new GridBagLayout());
	}

	public ApplicationPanel(LayoutManager layout) {
		super(layout);
	}

	@Override
	public void addNotify() {
		initComponents();
		super.addNotify();
	}

	@Override
	public void removeNotify() {
		super.removeNotify();
		removeComponents();
	}

	protected void initComponents() {
	}

	protected void removeComponents() {
	}
	
	protected T getContext() {
		return SwingUtils.getContext(this);
	}
	
	protected ResourceProvider getResourceProvider() {
		return getContext().getResourceProvider();
	}
	
	protected LocalizationFactory getLocalizationFactory() {
		return getContext().getLocalizationFactory();
	}
}
