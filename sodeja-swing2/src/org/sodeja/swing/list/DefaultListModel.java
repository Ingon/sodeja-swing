package org.sodeja.swing.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import org.sodeja.collections.CollectionUtils;

public class DefaultListModel<T> extends AbstractListModel {

	private static final long serialVersionUID = -4598343274536087504L;

	private List<T> elements = new ArrayList<T>();
	
	@Override
	public T getElementAt(int index) {
		return elements.get(index);
	}

	@Override
	public int getSize() {
		return elements.size();
	}

	public void addAll(List<T> newElements) {
		if(CollectionUtils.isEmpty(newElements)) {
			return;
		}
		
		elements.addAll(newElements);
		fireIntervalAdded(this, elements.size() - newElements.size(), elements.size() - 1);
	}

	public void add(T element) {
		elements.add(element);
		if(elements.size() == 1) {
			fireIntervalAdded(this, 0, 1);
		} else {
			fireIntervalAdded(this, elements.size() - 1, elements.size());
		}
	}

	public void removeAll() {
		int sz = elements.size();
		if(sz == 0) {
			return;
		}
		elements.clear();
		fireIntervalRemoved(this, 0, sz - 1);
	}

	public void remove(int modelIndex) {
		elements.remove(modelIndex);
		fireIntervalRemoved(this, modelIndex, modelIndex);
	}
	
	public int indexOf(T obj) {
		return elements.indexOf(obj);
	}
}
