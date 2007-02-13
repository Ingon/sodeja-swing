package org.sodeja.swing.dataservice;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import org.sodeja.dataservice.DataService;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.event.CallLocalMethodListSelectionListener;

public abstract class DataServiceListPanel<T extends ApplicationContext, R> extends DataServiceGenericPanel<T, R> {

	private JList dataList;
	private DataServiceListModel<R> dataServiceListModel;
	
	public DataServiceListPanel(T ctx, DataService<R> dataService) {
		super(ctx, dataService);
	}

	@Override
	protected JComponent initObjectsComponent() {
		dataServiceListModel = new DataServiceListModel<R>(dataService);
		dataList = new JList(dataServiceListModel);
		dataList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dataList.setCellRenderer(getListCellRenderer());
		dataList.addListSelectionListener(new CallLocalMethodListSelectionListener(this, "viewCallback")); //$NON-NLS-1$
		return dataList;
	}
	
	protected abstract ListCellRenderer getListCellRenderer();
	
	@Override
	protected void clearSelection() {
		dataList.clearSelection();
	}

	@Override
	protected void setSelectedValue(R value) {
		dataList.setSelectedValue(value, true);
	}

	protected R getSelectedValue() {
		int selectedIndex = dataList.getSelectedIndex();
		if(selectedIndex < 0) {
			return null;
		}
		return dataServiceListModel.getElement(selectedIndex);
	}
}
