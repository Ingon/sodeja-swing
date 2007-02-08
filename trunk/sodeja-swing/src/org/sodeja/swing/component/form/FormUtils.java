package org.sodeja.swing.component.form;

import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import org.sodeja.swing.ButtonBarFactory;
import org.sodeja.swing.GridBag;
import org.sodeja.swing.component.ApplicationPanel;
import org.sodeja.swing.component.action.ApplicationAction;
import org.sodeja.swing.context.ApplicationContext;

public final class FormUtils {
	public static JTextField addLabeledField(ApplicationContext ctx, ApplicationPanel parent, String lblKey, int index) {
		JLabel lbl = new JLabel(ctx.getResourceProvider().getStringValue(lblKey));
		parent.add(lbl, GridBag.lineLabel(index));
		
		JTextField tf = new JTextField();
		parent.add(tf, GridBag.lineField(index));
		
		return tf;
	}
	
	protected static void standartPostInit(Container parent, FormPanelGridData gridData, ApplicationAction[] actions) {
		if(gridData.isFillEmpty()) {
			gridData.nextRow();
			parent.add(new JPanel(), GridBag.bigPanel(gridData.getRow(), gridData.getColumnsCount()));
		}
		
		gridData.nextRow();
		parent.add(new JSeparator(JSeparator.HORIZONTAL), 
				GridBag.separatorLine(gridData.getRow(), gridData.getColumnsCount()));
		
		gridData.nextRow();
		parent.add(ButtonBarFactory.constructHorizontalButtonsPane(actions),
			GridBag.buttonLine(gridData.getRow(), gridData.getColumnsCount()));
	}
}
