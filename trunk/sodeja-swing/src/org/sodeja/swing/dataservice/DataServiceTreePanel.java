package org.sodeja.swing.dataservice;

import javax.swing.JComponent;

import org.sodeja.dataservice.DataService;
import org.sodeja.swing.context.ApplicationContext;

public abstract class DataServiceTreePanel<T extends ApplicationContext, R> extends DataServiceGenericPanel<T, R> {

	private static final long serialVersionUID = 6727223126028907038L;

	public DataServiceTreePanel(T ctx, DataService<R> dataService) {
		super(ctx, dataService);
	}

	@Override
	protected JComponent initObjectsComponent() {
		return null;
	}

	@Override
	protected void clearSelection() {
	}

	@Override
	protected R getSelectedValue() {
		return null;
	}

	@Override
	protected void setSelectedValue(R value) {
	}
}
