package org.sodeja.swing.component.code;

import java.awt.HeadlessException;
import java.util.Locale;

import javax.swing.DefaultCellEditor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.sodeja.functional.Pair;
import org.sodeja.model.LocalizableResource;
import org.sodeja.swing.ButtonBarFactory;
import org.sodeja.swing.GridBag;
import org.sodeja.swing.component.ApplicationDialog;
import org.sodeja.swing.component.ApplicationFrame;
import org.sodeja.swing.component.action.ApplicationAction;
import org.sodeja.swing.component.form.FormPanelGridData;
import org.sodeja.swing.component.form.NamedFormDialog;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.resource.ResourceConstants;
import org.sodeja.swing.validation.Validatable;

public class LocalizableResourceDialog<T extends ApplicationContext> extends NamedFormDialog<T> {

	private static final long serialVersionUID = -2318681092752453854L;

	private JTable tblLocalization;
	private LocalizableResourceTableModel tblLocalizationModel;
	
	private ApplicationAction<T> actionLocalizationAdd;
	private ApplicationAction<T> actionLocalizationRemove;
	
	private LocalizableResource code;
	
	public LocalizableResourceDialog(T ctxCons) throws HeadlessException {
		super(ctxCons);
	}

	public LocalizableResourceDialog(T ctxCons, ApplicationFrame parent) throws HeadlessException {
		super(ctxCons, parent);
	}

	public LocalizableResourceDialog(T ctxCons, ApplicationDialog parent) throws HeadlessException {
		super(ctxCons, parent);
	}

	@Override
	protected void initComponentsDelegate(FormPanelGridData gridData) {
		tblLocalizationModel = new LocalizableResourceTableModel();
		tblLocalization = new JTable(tblLocalizationModel);
		tblLocalization.getColumnModel().getColumn(0).setHeaderValue(
				ctx.getResourceProvider().getStringValue(ResourceConstants.TH_LOCALE));
		tblLocalization.getColumnModel().getColumn(0).setCellRenderer(new LocaleTableRenderer<T>(ctx));
		
		tblLocalization.getColumnModel().getColumn(1).setHeaderValue(
				ctx.getResourceProvider().getStringValue(ResourceConstants.TH_VALUE));
		
		tblLocalization.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JTextField()));
		
		add(new JScrollPane(tblLocalization), GridBag.bigPanel(gridData.getRow(), 2));
		
		actionLocalizationAdd = ButtonBarFactory.addButton(ctx, this);
		actionLocalizationRemove = ButtonBarFactory.removeButton(ctx, this);
		ctx.getValidationController().addTimedValidatable(new RemoveValidateable());
		
		add(ButtonBarFactory.constructVerticalButtonsPane(
				actionLocalizationAdd, actionLocalizationRemove),
			GridBag.buttonColumn(2, gridData.getRow(), 1));
		
		gridData.setColumnsCount(3);
	}
	
	@Override
	protected void postInitComponents() {
		setSize(400, 300);
		setModal(true);
		
		super.postInitComponents();
	}

	public void showLocalization(LocalizableResource code) {
		this.code = code;
		
		tblLocalizationModel.copyFrom(code);
		
		setVisible(true);
	}

	@Override
	protected void okCallback() {
		tblLocalizationModel.copyTo(code);
		
		super.okCallback();
	}
	
	protected void addCallback() {
		AddLocalizationDialog<T> dialog = new AddLocalizationDialog<T>(ctx, this);
		Pair<Locale, String> result = dialog.showAddLocalization();
		if(result != null) {
			tblLocalizationModel.addLocale(result.first, result.second);
		}
	}
	
	protected void removeCallback() {
		int row = tblLocalization.getSelectedRow();
		if(row < 0) {
			return;
		}
		
		tblLocalizationModel.removeLocaleAt(row);
	}

	@Override
	protected String getResourceName() {
		return ResourceConstants.DLG_LOCALIZATION;
	}

	private final class RemoveValidateable implements Validatable {
		public void validate() {
			int row = tblLocalization.getSelectedRow();
			if(row < 0) {
				actionLocalizationRemove.setEnabled(false);
				return;
			}
			
			Locale locale = tblLocalizationModel.getLocale(row);
			actionLocalizationRemove.setEnabled(! locale.equals(ctx.getLocaleProvider().getLocale()));
		}
	}
}
