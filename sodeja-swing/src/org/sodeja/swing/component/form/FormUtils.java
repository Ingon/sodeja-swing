package org.sodeja.swing.component.form;

import java.awt.Container;
import java.awt.event.KeyEvent;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.sodeja.swing.ButtonBarFactory;
import org.sodeja.swing.GridBag;
import org.sodeja.swing.component.action.ApplicationAction;
import org.sodeja.swing.context.ApplicationContext;

public final class FormUtils {
	public static JTextField addLabeledField(ApplicationContext ctx, Container parent, 
			Enum lblKey, FormPanelGridData gridData) {
		return addLabeledField(ctx, parent, lblKey.name(), gridData);
	}
	
	public static JTextField addLabeledField(ApplicationContext ctx, Container parent, 
			String lblKey, FormPanelGridData gridData) {
		return addLabeledComponent(ctx, parent, lblKey, gridData, new JTextField());
	}
	
	public static JPasswordField addPasswordField(ApplicationContext ctx, Container parent,
			Enum lblKey, FormPanelGridData gridData) {
		return addPasswordField(ctx, parent, lblKey.name(), gridData);
	}
	
	public static JPasswordField addPasswordField(ApplicationContext ctx, Container parent,
			String lblKey, FormPanelGridData gridData) {
		return addLabeledComponent(ctx, parent, lblKey, gridData, new JPasswordField());
	}

	public static JComboBox addLabeledCombo(ApplicationContext ctx, Container parent, 
			Enum lblKey, FormPanelGridData gridData) {
		return addLabeledCombo(ctx, parent, lblKey.name(), gridData);
	}
	
	public static JComboBox addLabeledCombo(ApplicationContext ctx, Container parent, 
			String lblKey, FormPanelGridData gridData) {
		return addLabeledComponent(ctx, parent, lblKey, gridData, new JComboBox());
	}
	
	public static JTextArea addLabeledArea(ApplicationContext ctx, Container parent,
			Enum lblKey, FormPanelGridData gridData) {
		return addLabeledArea(ctx, parent, lblKey.name(), gridData);
	}
	
	public static JTextArea addLabeledArea(ApplicationContext ctx, Container parent,
			String lblKey, FormPanelGridData gridData) {
		
		parent.add(ctx.getLocalizationFactory().createLabel(lblKey), GridBag.lineLabelNorth(gridData.getRow()));
		
		JTextArea result = new JTextArea();
		result.setRows(3);
		parent.add(new JScrollPane(result), GridBag.lineField(gridData.getRow()));
		
		gridData.nextRow();
		
		return result;
	}
	
	private static <T extends ApplicationContext, F extends JComponent> F addLabeledComponent(T ctx, Container parent,
			String lblKey, FormPanelGridData gridData, F component) {
		parent.add(ctx.getLocalizationFactory().createLabel(lblKey), GridBag.lineLabel(gridData.getRow()));
		
		parent.add(component, GridBag.lineField(gridData.getRow()));
		
		gridData.nextRow();
		
		return component;
	}
	
	public static <T extends ApplicationContext> void standartPostInit(Container parent, FormPanelGridData gridData, ApplicationAction<T>[] actions) {
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
		cont.registerKeyboardAction(ButtonBarFactory.closeToCancelButton(ctx, instance), 
				stroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
	
	public static void addCtrlEnterListener(JComponent cont, ApplicationContext ctx, Object instance) {
		KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_DOWN_MASK);
		cont.registerKeyboardAction(ButtonBarFactory.okButton(ctx, instance), 
				stroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	public static JTable addLabeledTable(ApplicationContext ctx, Container parent, 
			Enum lblKey, FormPanelGridData gridData) {
		return addLabeledTable(ctx, parent, lblKey.name(), gridData);
	}

	public static JTable addLabeledTable(ApplicationContext ctx, Container parent, 
			String lblKey, FormPanelGridData gridData) {
		parent.add(ctx.getLocalizationFactory().createLabel(lblKey), GridBag.lineLabelNorth(gridData.getRow()));
		
		JTable result = new JTable();
		parent.add(new JScrollPane(result), GridBag.create(1, gridData.getRow(), 1, 1, 1.0, 1.0));
		
		gridData.nextRow();
		
		return result;
	}
}
