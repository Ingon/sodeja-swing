package org.sodeja.swing.dataservice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.sodeja.collections.ListUtils;
import org.sodeja.dataservice.DataService;
import org.sodeja.dataservice.DataServiceListener;

public class DataServiceTreeModel<T> implements TreeModel, DataServiceListener<T> {
	
	private List<TreeModelListener> listeners;
	
	private DataService<T> dataService;
	private DataPathExtractor<T> extractor;
	private Comparator<T> comparator;
	
	private DataTreeNode<T> root;
	private Map<T, List<String>> pathMappings;

	public DataServiceTreeModel(DataService<T> dataService, String rootNodeStr, 
			DataPathExtractor<T> extractor, Comparator<T> comparator) {
		
		listeners = new ArrayList<TreeModelListener>();
		pathMappings = new HashMap<T, List<String>>();
		
		this.dataService = dataService;
		this.extractor = extractor;
		this.comparator = comparator;
		
		this.root = new DataTreeNode<T>(this, rootNodeStr);
		initNodes();
		
		dataService.addDataServiceListener(this);
	}
	
	private void initNodes() {
		for(T obj : dataService.findAll()) {
			created(dataService, obj);
		}
	}
	
	// TreeModel
	public void addTreeModelListener(TreeModelListener l) {
		listeners.add(l);
	}

	public void removeTreeModelListener(TreeModelListener l) {
		listeners.remove(l);
	}
	
	public Object getRoot() {
		return root;
	}
	
	public Object getChild(Object parent, int index) {
		if(! (parent instanceof DataTreeNode)) {
			throw new IllegalArgumentException();
		}
		
		return ((DataTreeNode) parent).getChild(index);
	}

	public int getChildCount(Object parent) {
		if(! (parent instanceof DataTreeNode)) {
			throw new IllegalArgumentException();
		}
		
		return ((DataTreeNode) parent).getChildCount();
	}

	public int getIndexOfChild(Object parent, Object child) {
		if(! (parent instanceof DataTreeNode)) {
			throw new IllegalArgumentException();
		}
		
		return ((DataTreeNode) parent).getIndexOfChild(child);
	}

	public boolean isLeaf(Object node) {
		return ! (node instanceof DataTreeNode);
	}

	public void valueForPathChanged(TreePath path, Object newValue) {
		throw new UnsupportedOperationException();
	}

	// DataServiceListener
	public void created(DataService<T> service, T data) {
		List<String> path = ListUtils.asList(extractor.getPathFor(data));
		root.addData(path, data, new ArrayList<DataTreeNode>());
		pathMappings.put(data, path);
	}

	public void updated(DataService<T> service, T data) {
		List<String> oldPath = pathMappings.get(data);
		List<String> path = ListUtils.asList(extractor.getPathFor(data));
		
		if(path.equals(oldPath)) {
			root.changeData(path, data, new ArrayList<DataTreeNode>());
			return;
		}
		
		root.deleteData(oldPath, data, new ArrayList<DataTreeNode>());
		root.addData(path, data, new ArrayList<DataTreeNode>());
		
		pathMappings.put(data, path);
	}
	
	public void deleted(DataService<T> service, T data) {
		root.deleteData(pathMappings.get(data), data, new ArrayList<DataTreeNode>());
	}

	protected void fireInserted(List<DataTreeNode> path, int index, Object data) {
		TreeModelEvent event = new TreeModelEvent(this, ListUtils.asArray(path), new int[] {index}, new Object[] {data});
		for(TreeModelListener listener : listeners) {
			listener.treeNodesInserted(event);
		}
	}
	
	protected void fireChanged(List<DataTreeNode> path, int index, Object data) {
		TreeModelEvent event = new TreeModelEvent(this, ListUtils.asArray(path), new int[] {index}, new Object[] {data});
		for(TreeModelListener listener : listeners) {
			listener.treeNodesChanged(event);
		}
	}
	
	protected void fireRemoved(List<DataTreeNode> path, int index, Object data) {
		TreeModelEvent event = new TreeModelEvent(this, ListUtils.asArray(path), new int[] {index}, new Object[] {data});
		for(TreeModelListener listener : listeners) {
			listener.treeNodesRemoved(event);
		}
	}
	
	protected Comparator<T> getComparator() {
		return comparator;
	}
	
//	public class DataTreeNode {
//		
//		private List<DataTreeNode> children;
//		private List<T> leaves;
//		private String data;
//		
//		private DataTreeNode(String data) {
//			this.data = data;
//			
//			children = new ArrayList<DataTreeNode>();
//			leaves = new ArrayList<T>();
//		}
//
//		public String getData() {
//			return data;
//		}
//		
//		private Object getChild(int index) {
//			int position = index;
//			if(position < children.size()) {
//				return children.get(position);
//			}
//			position -= children.size();
//			return leaves.get(position);
//		}
//		
//		private int getChildCount() {
//			return children.size() + leaves.size();
//		}
//		
//		private int getIndexOfChild(Object child) {
//			if(child instanceof DataServiceTreeModel.DataTreeNode) {
//				return children.indexOf((DataTreeNode) child);
//			}
//			return children.size() + leaves.indexOf(child);
//		}
//		
//		private void addData(List<String> path, T data, List<DataTreeNode> parentPath) {
//			parentPath.add(this);
//			
//			if(path.isEmpty()) {
//				int insertIndex = addLeaf(data);
//				fireInserted(parentPath, children.size() + insertIndex, data);
//				parentPath.remove(this);
//				return;
//			}
//			
//			String headPath = ListUtils.head(path);
//			DataTreeNode child = findChild(headPath);
//			if(child != null) {
//				child.addData(ListUtils.tail(path), data, parentPath);
//				parentPath.remove(this);
//				return;
//			}
//			
//			child = new DataTreeNode(headPath);
//			int insertIndex = addChild(child);
//			fireInserted(parentPath, insertIndex, child);
//			child.addData(ListUtils.tail(path), data, parentPath);
//		}
//		
//		private void deleteData(List<String> path, T data, List<DataTreeNode> parentPath) {
//			parentPath.add(this);
//			
//			if(path.isEmpty()) {
//				int index = deleteLeaf(data);
//				fireRemoved(parentPath, children.size() + index, data);
//				parentPath.remove(this);
//				return;
//			}
//			
//			DataTreeNode child = findChild(ListUtils.head(path));
//			child.deleteData(ListUtils.tail(path), data, parentPath);
//			if(child.isEmpty()) {
//				int index = deleteChild(child);
//				fireRemoved(parentPath, index, child);
//			}
//			parentPath.remove(this);
//		}
//		
//		private void changeData(List<String> path, T data, List<DataTreeNode> parentPath) {
//			parentPath.add(this);
//			
//			if(path.isEmpty()) {
//				fireChanged(parentPath, leaves.indexOf(data), data);
//				parentPath.remove(this);
//				return;
//			}
//			
//			DataTreeNode child = findChild(ListUtils.head(path));
//			child.changeData(ListUtils.tail(path), data, parentPath);
//			parentPath.remove(this);
//		}
//		
//		private boolean isEmpty() {
//			return children.isEmpty() && leaves.isEmpty();
//		}
//
//		private DataTreeNode findChild(final String idata) {
//			return ListUtils.find(children, new Predicate1<DataTreeNode>() {
//				public Boolean execute(DataTreeNode p) {
//					return p.data.equals(idata);
//				}});
//		}
//		
//		private int addChild(DataTreeNode child) {
//			return ListUtils.insertSorted(children, child, new Comparator<DataTreeNode>() {
//				public int compare(DataTreeNode o1, DataTreeNode o2) {
//					return o1.data.compareTo(o2.data);
//				}});
//		}
//		
//		private int deleteChild(DataTreeNode child) {
//			int childIndex = children.indexOf(child);
//			children.remove(childIndex);
//			return childIndex;
//		}
//		
//		private int addLeaf(T leaf) {
//			return ListUtils.insertSorted(leaves, leaf, comparator);
//		}
//		
//		private int deleteLeaf(T leaf) {
//			int leafIndex = leaves.indexOf(leaf);
//			leaves.remove(leafIndex);
//			return leafIndex;
//		}
//	}
}
