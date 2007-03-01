package org.sodeja.swing.dataservice;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.ListUtils;

public class DataTreeNode<T> {
	private String data;
	private List<DataTreeNode<T>> children;
	private List<T> leaves;
	
	public DataTreeNode(String data) {
		this.data = data;
		
		children = new ArrayList<DataTreeNode<T>>();
		leaves = new ArrayList<T>();
	}
	
	public String getData() {
		return data;
	}
	
	DataTreeNode<T> findChild(String data) {
		for(DataTreeNode<T> node : children) {
			if(node.data.equals(data)) {
				return node;
			}
		}
		return null;
	}
	
	DataTreeNode<T> addChild(String data) {
		DataTreeNode<T> dataTreeNode = new DataTreeNode<T>(data);
		children.add(dataTreeNode);
		return dataTreeNode;
	}
	
	DataTreeNode<T> findAndAddIfNotPresent(String data) {
		DataTreeNode<T> node = findChild(data);
		if(node == null) {
			node = addChild(data);
		}
		return node;
	}

	void clearChilds() {
		for(DataTreeNode<T> node : children) {
			node.clearChilds();
		}
		
		children.clear();
	}

	void addLeaf(T data) {
		leaves.add(data);
	}
	
	@SuppressWarnings("unchecked")
	List<DataTreeNode<T>> getPath(T searchLeaf) {
		for(T leaf : leaves) {
			if(leaf.equals(searchLeaf)) {
				return ListUtils.asList(this);
			}
		}
		
		for(DataTreeNode<T> child : children) {
			List<DataTreeNode<T>> found = child.getPath(searchLeaf);
			if(found != null) {
				found.add(0, this);
				return found;
			}
		}
		
		return null;
	}

	int subdataCount() {
		return children.size() + leaves.size();
	}
	
	Object subdata(int treePosition) {
		int position = treePosition;
		if(position < children.size()) {
			return children.get(position);
		}
		position -= children.size();
		return leaves.get(position);
	}

	public int indexOf(Object child) {
		if(child instanceof DataTreeNode) {
			return children.indexOf((DataTreeNode) child);
		}
		return children.size() + leaves.indexOf(child);
	}
}
