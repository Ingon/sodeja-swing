package org.sodeja.swing.dataservice;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import org.sodeja.dataservice.DataService;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.event.CallLocalMethodTreeSelectionListener;

public abstract class DataServiceTreePanel<T extends ApplicationContext, R> extends DataServiceGenericPanel<T, R> {

	private static final long serialVersionUID = 6727223126028907038L;

	private JTree dataTree;
	private DataServiceTreeModel<R> dataModel;
	
	public DataServiceTreePanel(T ctx, DataService<R> dataService) {
		super(ctx, dataService);
	}

	@Override
	protected JComponent initObjectsComponent() {
		dataModel = new DataServiceTreeModel<R>(dataService, getRootNode(), getExtractor());
		dataTree = new JTree(dataModel);
		dataTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		dataTree.setCellRenderer(getCellRenderer());
		dataTree.addTreeSelectionListener(new CallLocalMethodTreeSelectionListener(this, "viewCallback")); //$NON-NLS-1$
		return dataTree;
	}

	protected abstract String getRootNode();
	
	protected abstract DataPathExtractor<R> getExtractor();
	
	protected abstract TreeCellRenderer getCellRenderer();

	@Override
	protected void clearSelection() {
		dataTree.clearSelection();
	}

	@Override
	protected R getSelectedValue() {
		if(dataTree.getSelectionPath() == null) {
			return null;
		}
		Object selected = dataTree.getSelectionPath().getLastPathComponent();
		if(selected instanceof DataTreeNode) {
			return null;
		}
		return (R) selected;
	}

	@Override
	protected void setSelectedValue(R value) {
	}
}
