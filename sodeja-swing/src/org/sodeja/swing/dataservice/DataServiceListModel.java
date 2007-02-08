package org.sodeja.swing.dataservice;

import java.util.List;

import javax.swing.AbstractListModel;

import org.sodeja.dataservice.DataService;
import org.sodeja.dataservice.DataServiceListener;

public class DataServiceListModel<T> extends AbstractListModel implements DataServiceListener<T> {

	private static final long serialVersionUID = -3832285095148408422L;
	
	private List<T> internalData;
	private DataService<T> dataService;
	
	public DataServiceListModel(DataService<T> dataService) {
		this.dataService = dataService;
		
		internalData = dataService.findAll();
		dataService.addDataServiceListener(this);
	}
	
	public void created(DataService<T> service, T data) {
		update();
	}

	public void deleted(DataService<T> service, T data) {
		update();
	}

	public void updated(DataService<T> service, T data) {
		update();
	}

	public Object getElementAt(int index) {
		return internalData.get(index);
	}

	public int getSize() {
		return internalData.size();
	}
	
	private void update() {
		int size = internalData.size();
		internalData = dataService.findAll();
		fireContentsChanged(this, 0, size);
	}
}
