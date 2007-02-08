package org.sodeja.swing.component.code;

import java.awt.HeadlessException;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.sodeja.model.Code;
import org.sodeja.swing.ButtonBarFactory;
import org.sodeja.swing.GridBag;
import org.sodeja.swing.component.ApplicationFrame;
import org.sodeja.swing.component.action.CallLocalMethodAction;
import org.sodeja.swing.component.form.FormDialog;
import org.sodeja.swing.component.form.FormPanelGridData;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.resource.ResourceConstants;

public class CodeDialog<T extends ApplicationContext> extends FormDialog<T> {

	private static final long serialVersionUID = -2318681092752453854L;

	private JLabel lblCodeId;
	private JTextField tfCodeId;
	
	private JTable tblLocalization;
	private CodeTableModel tblLocalizationModel;
	
	private Code code;
	
	public CodeDialog(T ctxCons) throws HeadlessException {
		super(ctxCons);
	}

	public CodeDialog(T ctxCons, ApplicationFrame parent) throws HeadlessException {
		super(ctxCons, parent);
	}

	@Override
	protected void initComponentsDelegate(FormPanelGridData gridData) {
		lblCodeId = new JLabel("Code: ");
		add(lblCodeId, GridBag.lineLabel(0));
		
		tfCodeId = new JTextField();
		add(tfCodeId, GridBag.lineField(0, 1));
		
		gridData.nextRow();
		
		tblLocalizationModel = new CodeTableModel();
		tblLocalization = new JTable(tblLocalizationModel);
		tblLocalization.getColumnModel().getColumn(0).setHeaderValue("Locale");
		tblLocalization.getColumnModel().getColumn(1).setHeaderValue("Value");
		
		add(new JScrollPane(tblLocalization), GridBag.bigPanel(1, 2));
		
		add(ButtonBarFactory.constructVerticalButtonsPane(
				new CallLocalMethodAction<T>(ctx, ResourceConstants.BTN_ADD, this, "addCallback"),
				new CallLocalMethodAction<T>(ctx, ResourceConstants.BTN_REMOVE, this, "removeCallback")),
			GridBag.buttonColumn(2, 1, 1));
		
		gridData.setColumnsCount(3);
	}
	
	@Override
	protected void postInitComponents() {
		setSize(400, 300);
		setModal(true);
		
		super.postInitComponents();
	}

	public void showLocalization(Code code) {
		this.code = code;
		
		tfCodeId.setText(code.getId());
		tblLocalizationModel.copyFrom(code);
		
		setVisible(true);
	}

	@Override
	protected void okCallback() {
		tblLocalizationModel.copyTo(code);
		code.setId(tfCodeId.getText());
		
		super.okCallback();
	}
	
	protected void addCallback() {
	}
	
	protected void removeCallback() {
	}
}
