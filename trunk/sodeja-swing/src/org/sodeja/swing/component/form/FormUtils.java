package org.sodeja.swing.component.form;

import java.awt.Container;
import java.awt.event.KeyEvent;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.sodeja.swing.ButtonBarFactory;
import org.sodeja.swing.GridBag;
import org.sodeja.swing.component.action.ApplicationAction;
import org.sodeja.swing.context.ApplicationContext;

public final class FormUtils {
	public static JTextField addLabeledField(ApplicationContext ctx, Container parent, 
			String lblKey, FormPanelGridData gridData) {
		parent.add(ctx.getLocalizationFactory().createLabel(lblKey), GridBag.lineLabel(gridData.getRow()));
		
		JTextField tf = new JTextField();
		parent.add(tf, GridBag.lineField(gridData.getRow()));
		
		gridData.nextRow();
		
		return tf;
	}

	public static JComboBox addLabeledCombo(ApplicationContext ctx, Container parent, 
			String lblKey, FormPanelGridData gridData) {
		parent.add(ctx.getLocalizationFactory().createLabel(lblKey), GridBag.lineLabel(gridData.getRow()));
		
		JComboBox cmb = new JComboBox();
		parent.add(cmb, GridBag.lineField(gridData.getRow()));
		
		gridData.nextRow();
		
		return cmb;
	}
	
	public static void standartPostInit(Container parent, FormPanelGridData gridData, ApplicationAction[] actions) {
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
	
	public static void standartNamedPreInit(Container cont, ApplicationContext ctx, FormPanelGridData gridData, String resourceName) {
		JLabel lblName = new JLabel(ctx.getResourceProvider().getStringValue(resourceName));
		lblName.setFont(lblName.getFont().deriveFont(18f));
		lblName.setHorizontalAlignment(JLabel.CENTER);
		
		cont.add(lblName, GridBag.create(gridData.getRow(), 0, gridData.getColumnsCount(), 1, 1.0, 0.0));
		
		gridData.nextRow();
	}
	
	public static void addEscapeListener(JComponent cont, ApplicationContext ctx, Object instance) {
		KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		cont.registerKeyboardAction(ButtonBarFactory.closeToCancelButtons(ctx, instance)[0], 
				stroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
	
	public static void addCtrlEnterListener(JComponent cont, ApplicationContext ctx, Object instance) {
		KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_DOWN_MASK);
		cont.registerKeyboardAction(ButtonBarFactory.okButton(ctx, instance), 
				stroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
}
