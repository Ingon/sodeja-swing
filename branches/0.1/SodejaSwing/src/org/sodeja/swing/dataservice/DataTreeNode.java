package org.sodeja.swing.dataservice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Predicate1;

public class DataTreeNode<T> {
	private DataServiceTreeModel<T> model;
	private List<DataTreeNode<T>> children;
	private List<T> leaves;
	private String data;
	
	protected DataTreeNode(DataServiceTreeModel<T> model, String data) {
		this.model = model;
		this.data = data;
		
		children = new ArrayList<DataTreeNode<T>>();
		leaves = new ArrayList<T>();
	}

	public String getData() {
		return data;
	}
	
	protected Object getChild(int index) {
		int position = index;
		if(position < children.size()) {
			return children.get(position);
		}
		position -= children.size();
		return leaves.get(position);
	}
	
	protected int getChildCount() {
		return children.size() + leaves.size();
	}
	
	protected int getIndexOfChild(Object child) {
		if(child instanceof DataTreeNode) {
			return children.indexOf((DataTreeNode) child);
		}
		return children.size() + leaves.indexOf(child);
	}
	
	protected void addData(List<String> path, T data, List<DataTreeNode> parentPath) {
		parentPath.add(this);
		
		if(path.isEmpty()) {
			int insertIndex = addLeaf(data);
			model.fireInserted(parentPath, children.size() + insertIndex, data);
			parentPath.remove(this);
			return;
		}
		
		String headPath = ListUtils.head(path);
		DataTreeNode<T> child = findChild(headPath);
		if(child != null) {
			child.addData(ListUtils.tail(path), data, parentPath);
			parentPath.remove(this);
			return;
		}
		
		child = new DataTreeNode<T>(model, headPath);
		int insertIndex = addChild(child);
		model.fireInserted(parentPath, insertIndex, child);
		child.addData(ListUtils.tail(path), data, parentPath);
	}
	
	protected void deleteData(List<String> path, T data, List<DataTreeNode> parentPath) {
		parentPath.add(this);
		
		if(path.isEmpty()) {
			int index = deleteLeaf(data);
			model.fireRemoved(parentPath, children.size() + index, data);
			parentPath.remove(this);
			return;
		}
		
		DataTreeNode<T> child = findChild(ListUtils.head(path));
		child.deleteData(ListUtils.tail(path), data, parentPath);
		if(child.isEmpty()) {
			int index = deleteChild(child);
			model.fireRemoved(parentPath, index, child);
		}
		parentPath.remove(this);
	}
	
	protected void changeData(List<String> path, T data, List<DataTreeNode> parentPath) {
		parentPath.add(this);
		
		if(path.isEmpty()) {
			model.fireChanged(parentPath, leaves.indexOf(data), data);
			parentPath.remove(this);
			return;
		}
		
		DataTreeNode<T> child = findChild(ListUtils.head(path));
		child.changeData(ListUtils.tail(path), data, parentPath);
		parentPath.remove(this);
	}
	
	protected void findPath(List<String> path, T data, List<DataTreeNode> parentPath) {
		parentPath.add(this);
		if(path.isEmpty()) {
			return;
		}
		
		DataTreeNode<T> child = findChild(ListUtils.head(path));
		child.findPath(ListUtils.tail(path), data, parentPath);
	}
	
	private boolean isEmpty() {
		return children.isEmpty() && leaves.isEmpty();
	}

	private DataTreeNode<T> findChild(final String idata) {
		return ListUtils.find(children, new Predicate1<DataTreeNode<T>>() {
			public Boolean execute(DataTreeNode<T> p) {
				return p.data.equals(idata);
			}});
	}
	
	private int addChild(DataTreeNode<T> child) {
		return ListUtils.insertSorted(children, child, new Comparator<DataTreeNode<T>>() {
			public int compare(DataTreeNode<T> o1, DataTreeNode<T> o2) {
				return o1.data.compareTo(o2.data);
			}});
	}
	
	private int deleteChild(DataTreeNode child) {
		int childIndex = children.indexOf(child);
		children.remove(childIndex);
		return childIndex;
	}
	
	private int addLeaf(T leaf) {
		return ListUtils.insertSorted(leaves, leaf, model.getComparator());
	}
	
	private int deleteLeaf(T leaf) {
		int leafIndex = leaves.indexOf(leaf);
		leaves.remove(leafIndex);
		return leafIndex;
	}
}
