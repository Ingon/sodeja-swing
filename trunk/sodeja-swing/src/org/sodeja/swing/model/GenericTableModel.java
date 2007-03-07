package org.sodeja.swing.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.sodeja.collections.ArrayUtils;
import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;

public class GenericTableModel<T> extends AbstractTableModel {

	private static final long serialVersionUID = -7947554586824053358L;
	
	private List<T> data;
	private List<Function1<Object, T>> columnFunctors;

	public GenericTableModel(Function1<Object, T>... functors) {
		data = new ArrayList<T>();
		columnFunctors = ListUtils.asList(functors);
	}
	
	public int getColumnCount() {
		return columnFunctors.size();
	}

	public int getRowCount() {
		return data.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		T value = data.get(rowIndex);
		return columnFunctors.get(columnIndex).execute(value);
	}
	
	public void copyTo(Collection<T> target) {
		target.clear();
		target.addAll(data);
	}
	
	public void copyFrom(Collection<T> target) {
		data.clear();
		data.addAll(target);
	}
	
	public void addObject(T object) {
		data.add(object);
		fireTableDataChanged();
	}

	public void addObjects(Collection<T> objects) {
		data.addAll(objects);
		fireTableDataChanged();
	}
	
	public void updateObject(T object) {
		fireTableDataChanged();
	}
	
	public void removeObject(T object) {
		data.remove(object);
		fireTableDataChanged();
	}
	
	public T getObject(int index) {
		return data.get(index);
	}
	
	public void removeObject(int index) {
		data.remove(index);
		fireTableDataChanged();
	}

	public void removeObjects(int[] indexes) {
		if(ArrayUtils.isEmpty(indexes)) {
			return;
		}
		
		for(int i = indexes.length - 1;i >= 0;i--) {
			data.remove(indexes[i]);
		}
		
		fireTableDataChanged();
	}
	
	public List<T> getData() {
		return Collections.unmodifiableList(data);
	}
	
	public void moveUp(int[] indexes) {
		if(ArrayUtils.isEmpty(indexes)) {
			return;
		}
		
		moveBlocks(divideOnBlocks(indexes), -1);
	}

	public void moveDown(int[] indexes) {
		if(ArrayUtils.isEmpty(indexes)) {
			return;
		}

		moveBlocks(divideOnBlocks(indexes), 1);
	}
	
	private List<List<Integer>> divideOnBlocks(int[] indexes) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		
		for(int i = 0;i < indexes.length;i++) {
			if(result.isEmpty()) {
				result.add(new ArrayList<Integer>());
			}
			List<Integer> block = ListUtils.last(result);
			if(block.isEmpty()) {
				block.add(indexes[i]);
				continue;
			}
			
			int prev = ListUtils.last(block);
			if(prev + 1 == indexes[i]) {
				block.add(indexes[i]);
				continue;
			}
			
			List<Integer> newBlock = new ArrayList<Integer>();
			newBlock.add(indexes[i]);
			result.add(newBlock);
		}
		
		return result;
	}

	private void moveBlocks(List<List<Integer>> blocks, int factor) {
		for(List<Integer> block : blocks) {
			moveBlock(block, factor);
		}
	}
	
	private void moveBlock(List<Integer> block, int factor) {
		List<T> objects = new ArrayList<T>();
		for(int i = block.size() - 1;i >= 0;i--) {
			objects.add(data.remove((int) block.get(i)));
		}
		
		Collections.reverse(objects);
		
		int newPos = ListUtils.first(block) + factor;
		if(newPos < 0) {
			newPos = 0;
		}
		
		for(int i = 0, n = block.size();i < n;i++) {
			if(newPos > data.size()) {
				data.add(objects.get(i));
			} else {
				data.add(newPos++, objects.get(i));
			}
		}
	}
}
