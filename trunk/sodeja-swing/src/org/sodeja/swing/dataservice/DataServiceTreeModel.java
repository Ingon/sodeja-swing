package org.sodeja.swing.dataservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.sodeja.dataservice.DataService;
import org.sodeja.dataservice.DataServiceListener;

public class DataServiceTreeModel<T> implements TreeModel, DataServiceListener<T> {

	private List<TreeModelListener> listeners;
	private DataTreeNode<T> rootNode;
	private DataService<T> dataService;
	private DataPathExtractor<T> extractor;
	private Comparator<T> comparator;
	
	public DataServiceTreeModel(DataService<T> dataService, String rootNodeStr, 
			DataPathExtractor<T> extractor, Comparator<T> comparator) {
		this.dataService = dataService;
		this.extractor = extractor;
		this.comparator = comparator;
		
		listeners = new ArrayList<TreeModelListener>();		
		
		rootNode = new DataTreeNode<T>(rootNodeStr);
		initNodes();
		
		dataService.addDataServiceListener(this);
	}
	
	private void initNodes() {
		List<T> elements = new ArrayList<T>(dataService.findAll());
		Collections.sort(elements, comparator);
		for(T obj : elements) {
			addLeaf(obj);
		}
	}

	private void addLeaf(T obj) {
		String[] path = extractor.getPathFor(obj);
		
		DataTreeNode<T> current = rootNode;
		for(String pathElement : path) {
			current = current.findAndAddIfNotPresent(pathElement);
		}
		
		current.addLeaf(obj);
	}
	
	// TreeModelTreeModel
	public void addTreeModelListener(TreeModelListener l) {
		listeners.add(l);
	}
	
	public void removeTreeModelListener(TreeModelListener l) {
		listeners.remove(l);
	}

	public Object getChild(Object parent, int index) {
		if(parent instanceof DataTreeNode) {
			return ((DataTreeNode) parent).subdata(index);
		}
		return null;
	}

	public int getChildCount(Object parent) {
		if(parent instanceof DataTreeNode) {
			return ((DataTreeNode) parent).subdataCount();
		}
		return 0;
	}

	public int getIndexOfChild(Object parent, Object child) {
		if(parent instanceof DataTreeNode) {
			return ((DataTreeNode) parent).indexOf(child);
		}
		throw new UnsupportedOperationException();
	}

	public Object getRoot() {
		return rootNode;
	}

	public boolean isLeaf(Object node) {
		if(node instanceof DataTreeNode) {
			return false;
		}
		return true;
	}

	public void valueForPathChanged(TreePath path, Object newValue) {
		throw new UnsupportedOperationException();
	}

	// DataServiceListener
	// TODO Ugly and very inneficient implementation. 
	public void created(DataService<T> service, T data) {
		rootNode.clearChilds();
		initNodes();
		
		TreeModelEvent treeModelEvent = new TreeModelEvent(this, new Object[] {rootNode});
		for(TreeModelListener listener : listeners) {
			listener.treeStructureChanged(treeModelEvent);
		}
	}

	public void updated(DataService<T> service, T data) {
		rootNode.clearChilds();
		initNodes();
		
		TreeModelEvent treeModelEvent = new TreeModelEvent(this, new Object[] {rootNode});
		for(TreeModelListener listener : listeners) {
			listener.treeStructureChanged(treeModelEvent);
		}
	}
	
	public void deleted(DataService<T> service, T data) {
		rootNode.clearChilds();
		initNodes();
		
		for(TreeModelListener listener : listeners) {
			listener.treeStructureChanged(new TreeModelEvent(this, new Object[] {rootNode}));
		}
	}
}
