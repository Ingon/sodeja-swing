package org.sodeja.swing.dataservice;

import org.sodeja.dataservice.DataService;
import org.sodeja.lang.reflect.ReflectUtils;
import org.sodeja.swing.component.form.FormPanel;
import org.sodeja.swing.component.form.FormPanelGridData;
import org.sodeja.swing.component.form.FormUtils;
import org.sodeja.swing.context.ApplicationContext;

public abstract class DataServiceFormPanel<T extends ApplicationContext, R> extends FormPanel<T, R> {

	private DataServiceFormPanelType type;
	
	private DataService<R> service;
	private Class<R> dataClazz;
	protected R dataInstance;
	private int columns;
	
	public DataServiceFormPanel(T ctx, DataService<R> service, Class<R> dataClazz, 
			DataServiceFormPanelType type, String resourceId) {
		super(ctx);
		
		this.service = service;
		this.dataClazz = dataClazz;
		this.type = type;
		
		FormPanelGridData gridData = new FormPanelGridData();
		gridData.setColumnsCount(columns);
		FormUtils.standartNamedPreInit(this, ctx, gridData, resourceId);
		if(type == DataServiceFormPanelType.VIEW) {
			disableComponents();
		}
	}

	@Override
	protected void preInitComponents(FormPanelGridData gridData) {
		columns = gridData.getColumnsCount();
		gridData.nextRow();
	}

	@Override
	public void showForm() {
		dataInstance = ReflectUtils.newInstance(dataClazz);
		this.showForm(dataInstance);
	}

	@Override
	public void showForm(R object) {
		this.dataInstance = object;
		fillForm(object);
	}

	@Override
	protected void okCallback() {
		fillObject(dataInstance);
		type.doSave(service, dataInstance);
		super.okCallback();
	}

	protected abstract void disableComponents();
	
	protected abstract void fillForm(R object);

	protected abstract void fillObject(R object);
	
	public static abstract class DataServiceFormPanelType {
		public static final DataServiceFormPanelType ADD = new DataServiceFormPanelType() {
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
			@Override
			protected void doSave(DataService service, Object obj) {
				service.update(obj);
			}};
		
		private DataServiceFormPanelType() {
		}
		
		protected abstract void doSave(DataService service, Object obj);
	}
}
