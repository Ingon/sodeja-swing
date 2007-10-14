package org.sodeja.swing.panel;

import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.layout.GridBag;

public class FormUtils {
	
	public static <T extends ApplicationContext> JTextField addLabeledField(
			T ctx, Container parent, Enum<?> lblKey, int row) {
		return addLabeledComponent(ctx, parent, lblKey, row, new JTextField());
	}

	public static <T extends ApplicationContext> JPasswordField addLabeledPassword(
			T ctx, Container parent, Enum<?> lblKey, int row) {
		return addLabeledComponent(ctx, parent, lblKey, row, new JPasswordField());
	}
	
	public static <T extends ApplicationContext> JComboBox addLabeledCombo(
			T ctx, Container parent, Enum<?> lblKey, int row) {
		return addLabeledComponent(ctx, parent, lblKey, row, new JComboBox());
	}

	private static <T extends ApplicationContext, F extends JComponent> F addLabeledComponent(
			T ctx, Container parent, Enum<?> lblKey, int row, F component) {
		parent.add(ctx.getLocalizationFactory().createLabel(lblKey), 
				GridBag.lineLabel(row));
		
		parent.add(component, GridBag.lineField(row));
		
		return component;
	}

	public static void fillEmpty(Container parent, int row) {
		parent.add(new JPanel(), GridBag.bigPanel(row, 2));
	}
	
	public static <T extends ApplicationContext, F extends JComponent> F addLabaledScrollable(
			T ctx, Container parent, Enum<?> lblKey, int row, F component) {
		
		JScrollPane scroller = new JScrollPane(component);
		Border scrollerBorder = BorderFactory.createCompoundBorder(
				ctx.getLocalizationFactory().createTitledBorder(lblKey), scroller.getBorder());
		scroller.setBorder(scrollerBorder);
		
		parent.add(scroller, GridBag.bigPanel(row, 2));
		
		return component;
	}
	
	public static <T extends ApplicationContext> JTextArea addLabeledArea(
			T ctx, Container parent, Enum<?> lblKey, int row) {
		
		parent.add(ctx.getLocalizationFactory().createLabel(lblKey), 
				GridBag.lineLabel(row));
		
		JTextArea component = new JTextArea();
		component.setRows(3);
		parent.add(new JScrollPane(component), GridBag.lineField(row));
		
		return component;
	}
}
