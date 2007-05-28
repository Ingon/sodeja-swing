package org.sodeja.swing.dataservice;

import org.sodeja.dataservice.DataService;
import org.sodeja.lang.reflect.ReflectUtils;
import org.sodeja.swing.ButtonBarFactory;
import org.sodeja.swing.component.form.FormPanel;
import org.sodeja.swing.component.form.FormPanelGridData;
import org.sodeja.swing.component.form.FormUtils;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.validation.ValidationFailedDialog;
import org.sodeja.swing.validation.ValidationResult;

public abstract class DataServiceFormPanel<T extends ApplicationContext, R> extends FormPanel<T, R> {

	private DataServiceFormPanelType type;
	
	protected DataService<R> service;
	protected Class<R> dataClazz;
	protected R dataInstance;
	
	private int columns;
	private int rows;
	
	private boolean formVisible;
	
	public DataServiceFormPanel(T ctx, DataService<R> service, Class<R> dataClazz, 
			DataServiceFormPanelType type, String resourceId) {
		super(ctx);
		
		this.service = service;
		this.dataClazz = dataClazz;
		this.type = type;
		
		FormPanelGridData gridData = new FormPanelGridData();
		gridData.setColumnsCount(columns);
		
		FormUtils.standartNamedPreInit(this, ctx, gridData, resourceId);
		FormUtils.addEscapeListener(this, ctx, this);
		
		gridData.setRow(rows);
		if(type == DataServiceFormPanelType.VIEW) {
			disableComponents();
			FormUtils.standartPostInit(this, gridData, ButtonBarFactory.closeToCancelButtons(ctx, this));
		} else {
			FormUtils.standartPostInit(this, gridData, getActions());
			FormUtils.addCtrlEnterListener(this, ctx, this);
		}
	}

	@Override
	protected void preInitComponents(FormPanelGridData gridData) {
		columns = gridData.getColumnsCount();
		gridData.nextRow();
	}

	@Override
	protected void postInitComponents(FormPanelGridData gridData) {
		rows = gridData.getRow();
	}

	@Override
	protected void initKeyboardActions() {
	}

	@Override
	public void showForm() {
		this.showForm(createObject());
	}

	@Override
	public void showForm(R object) {
		this.dataInstance = object;
		fillForm(object);
		formVisible = true;
	}
	
	@Override
	protected void okCallback() {
		ValidationResult validationResult = new ValidationResult();
		validateData(validationResult);
		if(! validationResult.isValid()) {
			new ValidationFailedDialog<T>(ctx, validationResult);
			return;
		}
		fillObject(dataInstance);
		super.okCallback();
		type.doSave(service, dataInstance);
	}

	@Override
	protected void hideForm() {
		formVisible = false;
		super.hideForm();
	}

	protected boolean isFormVisible() {
		return formVisible;
	}
	
	protected abstract void disableComponents();
	
	protected abstract void fillForm(R object);

	protected abstract void fillObject(R object);
	
	protected abstract void validateData(ValidationResult validationResult);
	
	public static abstract class DataServiceFormPanelType {
		public static final DataServiceFormPanelType ADD = new DataServiceFormPanelType() {
			@SuppressWarnings("unchecked")
			@Override
			protected void doSave(DataService service, Object obj) {
				service.create(obj);
			}};
			
		public static final DataServiceFormPanelType VIEW = new DataServiceFormPanelType() {
			@Override
			protected void doSave(DataService service, Object obj) {
				throw new UnsupportedOperationException();
			}};
		public static final DataServiceFormPanelType EDIT = new DataServiceFormPanelType() {
			@SuppressWarnings("unchecked")
			@Override
			protected void doSave(DataService service, Object obj) {
				service.update(obj);
			}};
		
		private DataServiceFormPanelType() {
		}
		
		protected abstract void doSave(DataService service, Object obj);
	}
	
	private R createObject() {
		return ReflectUtils.newInstance(dataClazz);
	}
}
