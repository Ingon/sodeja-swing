package org.sodeja.swing.dataservice;

import java.awt.GridBagLayout;
import java.util.Comparator;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import org.sodeja.dataservice.DataService;
import org.sodeja.swing.ButtonBarFactory;
import org.sodeja.swing.ComponentUtils;
import org.sodeja.swing.GridBag;
import org.sodeja.swing.component.ApplicationPanel;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.resource.ResourceConstants;
import org.sodeja.swing.validation.ValidationFailedDialog;
import org.sodeja.swing.validation.ValidationResult;

public abstract class DataServiceGenericPanel<T extends ApplicationContext, R> extends ApplicationPanel<T> {

	private static final long serialVersionUID = -7703771094766278196L;

	protected JToolBar toolbar;
	protected JPanel dataPanel;
	protected DataService<R> dataService;
	
	protected DataServiceFormPanel<T, R> addFormPanel;
	protected DataServiceFormPanel<T, R> editFormPanel;
	protected DataServiceFormPanel<T, R> viewFormPanel;
	
	public DataServiceGenericPanel(T ctx, DataService<R> dataService) {
		super(ctx);
		
		this.dataService = dataService;
		initComponents();
	}

	protected final void initComponents() {
		this.setLayout(new GridBagLayout());
		
		toolbar = ButtonBarFactory.createToolbar(
						ButtonBarFactory.searchButton(ctx, this), ButtonBarFactory.addButton(ctx, this), 
						ButtonBarFactory.editButton(ctx, this), ButtonBarFactory.deleteButton(ctx, this));
		toolbar.setFloatable(false);
//		toolbar.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		this.add(toolbar, GridBag.leftButtonLine(0));

//		this.add(ButtonBarFactory.constructHorizontalButtonsPane(ButtonBarFactory.searchButton(ctx, this),
//				ButtonBarFactory.addButton(ctx, this), ButtonBarFactory.editButton(ctx, this), ButtonBarFactory
//						.deleteButton(ctx, this)), GridBag.leftButtonLine(0));
		
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
	protected abstract DataServiceFormPanel<T, R> createAddForm();	

	protected abstract DataServiceFormPanel<T, R> createEditForm();	

	protected abstract DataServiceFormPanel<T, R> createViewForm();	

	protected abstract R findBestMatch(String term);
	
	protected abstract Comparator<R> createComparator();
	
	protected void validateDelete(R value, ValidationResult validation) {
	}
	
	protected void searchCallback() {
		String term = JOptionPane.showInputDialog(ctx.getRootFrame(), 
				ctx.getResourceProvider().getStringValue(ResourceConstants.DLG_SEARCH_TERM));
		if(term == null) {
			return;
		}
		
		R result = findBestMatch(term);
		if(result != null) {
			setSelectedValue(result);
		}
	}
	
	protected void addCallback() {
		if(checkEditForm()) {
			return;
		}
		
		if(addFormPanel == null) {
			addFormPanel = createAddForm();
		}
		
		addFormPanel.showForm();
		ComponentUtils.swapInContainerBigLater(dataPanel, addFormPanel);
	}
	
	protected void editCallback() {
		if(checkAddForm()) {
			return;
		}
		
		R value = getSelectedValue();
		if (value == null) {
			ComponentUtils.clearContainer(dataPanel);
			return;
		}
		
		if(editFormPanel == null) {
			editFormPanel = createEditForm();
		}
		
		editFormPanel.showForm(value);
		ComponentUtils.swapInContainerBigLater(dataPanel, editFormPanel);
	}
	
	protected void viewCallback() {
		if(checkAddEditForms()) {
			return;
		}
		
		R value = getSelectedValue();
		if (value == null) {
			ComponentUtils.clearContainer(dataPanel);
			return;
		}

		if(viewFormPanel == null) {
			viewFormPanel = createViewForm();
		}
		
		viewFormPanel.showForm(value);
		ComponentUtils.swapInContainerBigLater(dataPanel, viewFormPanel);
	}
	
	protected void deleteCallback() {
		R value = getSelectedValue();
		if(value == null) {
			return;
		}
		
		if(checkAddEditForms()) {
			return;
		}
		
		if(cancelDelete()) {
			return;
		}
		
		ValidationResult validationResult = new ValidationResult();
		validateDelete(value, validationResult);
		if(! validationResult.isValid()) {
			new ValidationFailedDialog<T>(ctx, validationResult);
			return;
		}
		
		dataService.delete(value);
		clearSelection();
		
		ComponentUtils.clearContainer(dataPanel);
	}
	
	protected void hideAllForms() {
		hideAddForm();
		hideEditForm();
		hideViewForm();
	}

	protected void hideAddForm() {
		if(addFormPanel != null && addFormPanel.isVisible()) {
			addFormPanel.hideForm();
		}
	}

	protected void hideEditForm() {
		if(editFormPanel != null && editFormPanel.isVisible()) {
			editFormPanel.hideForm();
		}
	}

	protected void hideViewForm() {
		if(viewFormPanel != null && viewFormPanel.isVisible()) {
			viewFormPanel.hideForm();
		}
	}
	
	protected boolean checkAddEditForms() {
		return checkAddForm() || checkEditForm();
	}
	
	protected boolean checkAddForm() {
		if(addFormPanel != null && addFormPanel.isFormVisible()) {
			if(cancelRejected(ResourceConstants.OPT_ADD_FORM_CANCEL)) {
				return true;
			}
			addFormPanel.hideForm();
		}
		return false;
	}
	
	protected boolean checkEditForm() {
		if(editFormPanel != null && editFormPanel.isFormVisible()) {
			if(cancelRejected(ResourceConstants.OPT_EDIT_FORM_CANCEL)) {
				return true;
			}
			editFormPanel.hideForm();
		}
		
		return false;
	}
	
	private boolean cancelDelete() {
		int result = JOptionPane.showConfirmDialog(ctx.getRootFrame(), 
				ctx.getResourceProvider().getStringValue(ResourceConstants.DLG_DELETE_CONFIRM_CONTENT), 
				ctx.getResourceProvider().getStringValue(ResourceConstants.DLG_DELETE_CONFIRM), 
				JOptionPane.YES_NO_OPTION);
		
		return result != JOptionPane.YES_OPTION;
	}

	private boolean cancelRejected(String textResource) {
		int result = JOptionPane.showConfirmDialog(ctx.getRootFrame(), 
				ctx.getResourceProvider().getStringValue(textResource), 
				ctx.getResourceProvider().getStringValue(ResourceConstants.DLG_FORM_CANCEL), 
				JOptionPane.YES_NO_OPTION);
		
		return result != JOptionPane.YES_OPTION;
	}
}
