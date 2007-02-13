package org.sodeja.swing.dataservice;

import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.sodeja.dataservice.DataService;
import org.sodeja.functional.Predicate1;
import org.sodeja.swing.ButtonBarFactory;
import org.sodeja.swing.ComponentUtils;
import org.sodeja.swing.GridBag;
import org.sodeja.swing.component.ApplicationPanel;
import org.sodeja.swing.component.form.FormPanel;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.resource.ResourceConstants;

public abstract class DataServiceGenericPanel<T extends ApplicationContext, R> extends ApplicationPanel<T> {

	private static final long serialVersionUID = -7703771094766278196L;

	protected JPanel dataPanel;
	protected DataService<R> dataService;
	
	private FormPanel<T, R> addFormPanel;
	private FormPanel<T, R> editFormPanel;
	private FormPanel<T, R> viewFormPanel;
	
	public DataServiceGenericPanel(T ctx, DataService<R> dataService) {
		super(ctx);
		
		this.dataService = dataService;
		initComponents();
	}

	protected final void initComponents() {
		this.setLayout(new GridBagLayout());
		
		this.add(ButtonBarFactory.constructHorizontalButtonsPane(
				ButtonBarFactory.searchButton(ctx, this), ButtonBarFactory.addButton(ctx, this), 
				ButtonBarFactory.editButton(ctx, this), ButtonBarFactory.deleteButton(ctx, this)),
			GridBag.leftButtonLine(0));
		
		JSplitPane contentPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		contentPanel.setDividerSize(3);

		JScrollPane scrollExercisesList = new JScrollPane(initObjectsComponent());
		contentPanel.setLeftComponent(scrollExercisesList);

		dataPanel = new JPanel();
		dataPanel.setLayout(new GridBagLayout());
		contentPanel.setRightComponent(dataPanel);

		contentPanel.setDividerLocation(200);
		
		this.add(contentPanel, GridBag.bigPanel(1, 1));
	}
	
	// overriden by type panels
	protected abstract JComponent initObjectsComponent();

	protected abstract R getSelectedValue();
	
	protected abstract void setSelectedValue(R value);
	
	protected abstract void clearSelection();

	// overriden by real panels
	protected abstract FormPanel<T, R> createAddForm();	

	protected abstract FormPanel<T, R> createEditForm();	

	protected abstract FormPanel<T, R> createViewForm();	

	protected abstract Predicate1<R> createPredicate(String term);
	
	protected void searchCallback() {
		String term = JOptionPane.showInputDialog(ctx.getRootFrame(), 
				ctx.getResourceProvider().getStringValue(ResourceConstants.DLG_SEARCH_TERM));
		if(term == null) {
			return;
		}
		
		List<R> result = dataService.find(createPredicate(term));
		if(result.isEmpty()) {
			return;
		}
		
		setSelectedValue(result.get(0));
	}
	
	protected void addCallback() {
		if(addFormPanel == null) {
			addFormPanel = createAddForm();
		}
		
		addFormPanel.showForm();
		ComponentUtils.swapInContainer(dataPanel, addFormPanel, GridBag.bigPanel());
	}
	
	protected void editCallback() {
		R value = getSelectedValue();
		if (value == null) {
			ComponentUtils.clearContainer(dataPanel);
			return;
		}
		
		if(editFormPanel == null) {
			editFormPanel = createEditForm();
		}
		
		editFormPanel.showForm(value);
		ComponentUtils.swapInContainer(dataPanel, editFormPanel, GridBag.bigPanel());
	}
	
	protected void viewCallback() {
		R value = getSelectedValue();
		if (value == null) {
			ComponentUtils.clearContainer(dataPanel);
			return;
		}

		if(viewFormPanel == null) {
			viewFormPanel = createViewForm();
		}
		
		viewFormPanel.showForm(value);
		ComponentUtils.swapInContainer(dataPanel, viewFormPanel, GridBag.bigPanel());
	}
	
	protected void deleteCallback() {
		R value = getSelectedValue();
		if(value == null) {
			return;
		}
		
		dataService.delete(value);
		clearSelection();
		
		ComponentUtils.clearContainer(dataPanel);
	}
}
