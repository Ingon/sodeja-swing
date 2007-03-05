package org.sodeja.swing.dataservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.AbstractListModel;

import org.sodeja.dataservice.DataService;
import org.sodeja.dataservice.DataServiceListener;

public class DataServiceListModel<T> extends AbstractListModel implements DataServiceListener<T> {

	private static final long serialVersionUID = -3832285095148408422L;
	
	private List<T> internalData;
	private DataService<T> dataService;
	private Comparator<T> comparator;
	
	public DataServiceListModel(DataService<T> dataService, Comparator<T> comparator) {
		this.dataService = dataService;
		this.comparator = comparator;
		
		internalData = new ArrayList<T>(dataService.findAll());
		Collections.sort(internalData, comparator);
		
		dataService.addDataServiceListener(this);
	}
	
	// AbstractListModel
	public Object getElementAt(int index) {
		return getElement(index);
	}
	
	public int getSize() {
		return internalData.size();
	}
	
	public T getElement(int index) {
		return internalData.get(index);
	}

	// DataServiceListener
	public void created(DataService<T> service, T data) {
		update();
	}

	public void deleted(DataService<T> service, T data) {
		update();
	}

	public void updated(DataService<T> service, T data) {
		update();
	}

	private void update() {
		int size = internalData.size();
		
		internalData.clear();
		internalData.addAll(dataService.findAll());
		
		Collections.sort(internalData, comparator);
		fireContentsChanged(this, 0, size);
	}
}
