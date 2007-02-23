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
import org.sodeja.swing.component.form.FormPanelGridData;
import org.sodeja.swing.component.form.NamedFormDialog;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.resource.ResourceConstants;

public class LocalizableResourceDialog<T extends ApplicationContext> extends NamedFormDialog<T> {

	private static final long serialVersionUID = -2318681092752453854L;

	private JTextField tfCodeId;
	
	private JTable tblLocalization;
	private LocalizableResourceTableModel tblLocalizationModel;
	
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
		add(ctx.getLocalizationFactory().createLabel(ResourceConstants.LBL_ID), 
				GridBag.lineLabel(gridData.getRow()));
		
		tfCodeId = new JTextField();
		tfCodeId.setEditable(false);
		add(tfCodeId, GridBag.lineField(gridData.getRow(), 1));
		
		gridData.nextRow();
		
		tblLocalizationModel = new LocalizableResourceTableModel();
		tblLocalization = new JTable(tblLocalizationModel);
		tblLocalization.getColumnModel().getColumn(0).setHeaderValue(
				ctx.getResourceProvider().getStringValue(ResourceConstants.TH_LOCALE));
		tblLocalization.getColumnModel().getColumn(0).setCellRenderer(new LocaleTableRenderer(ctx));
		
		tblLocalization.getColumnModel().getColumn(1).setHeaderValue(
				ctx.getResourceProvider().getStringValue(ResourceConstants.TH_VALUE));
		
		tblLocalization.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JTextField()));
		
		add(new JScrollPane(tblLocalization), GridBag.bigPanel(gridData.getRow(), 2));
		
		add(ButtonBarFactory.constructVerticalButtonsPane(
				ButtonBarFactory.addButton(ctx, this), 
				ButtonBarFactory.removeButton(ctx, this)),
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
		
		tfCodeId.setText(code.getId());
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
}
