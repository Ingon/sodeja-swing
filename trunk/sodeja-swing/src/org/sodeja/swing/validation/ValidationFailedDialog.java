package org.sodeja.swing.validation;

import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.sodeja.collections.ArrayUtils;
import org.sodeja.functional.Function1;
import org.sodeja.lang.StringUtils;
import org.sodeja.swing.ButtonBarFactory;
import org.sodeja.swing.GridBag;
import org.sodeja.swing.SodejaSwingUtils;
import org.sodeja.swing.component.ApplicationDialog;
import org.sodeja.swing.component.form.FormPanelGridData;
import org.sodeja.swing.component.form.FormUtils;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.resource.ResourceConstants;

public class ValidationFailedDialog<T extends ApplicationContext> extends ApplicationDialog<T> {

	private static final long serialVersionUID = 5168927775760907280L;

	private JTextArea displayArea;
	private ValidationResult validationResult;
	
	public ValidationFailedDialog(T ctxCons, ValidationResult validationResult) throws HeadlessException {
		super(ctxCons);
		
		this.validationResult = validationResult;
		fillForm();
		showDialog();
	}

	@Override
	protected void initComponents() {
		FormPanelGridData gridData = new FormPanelGridData();
		FormUtils.standartNamedPreInit(getContentPane(), ctx, gridData, ResourceConstants.DLG_VALIDATION_FAILED);
		
		gridData.nextRow();
		
		displayArea = new JTextArea();
		displayArea.setEditable(false);
		
		add(displayArea, GridBag.bigPanel(gridData.getRow(), gridData.getColumnsCount()));
		
		gridData.nextRow();
		
		FormUtils.standartPostInit(getContentPane(), gridData, ButtonBarFactory.closeButtons(ctx, this));
	}
	
	@Override
	protected void postInitComponents() {
		setSize(400, 300);
		setModal(true);

		super.postInitComponents();
	}

	private void fillForm() {
		for(ValidationMessage msg : validationResult.getMessages()) {
			if(! msg.isFailure()) {
				continue;
			}
			
			String[] params = ArrayUtils.map(msg.getParams(), new Function1<String, Object>() {
				public String execute(Object p) {
					if(p instanceof LocalizableValidationParameter) {
						return ctx.getResourceProvider().getStringValue(((LocalizableValidationParameter) p).resourceId);
					}
					return StringUtils.nonNullValue(p);
				}});
			
			String value = ctx.getResourceProvider().getFormattedStringValue(msg.getMessage(), params);
			displayArea.append(value);
			displayArea.append("\r\n"); //$NON-NLS-1$
		}
	}
	
	private void showDialog() {
		JButton btn = (JButton) ((JPanel) this.getContentPane().getComponent(3)).getComponent(0);
		SodejaSwingUtils.grabFocus(btn);
		
		setVisible(true);
	}
}
