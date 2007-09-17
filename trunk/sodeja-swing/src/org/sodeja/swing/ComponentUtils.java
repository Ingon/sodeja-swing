package org.sodeja.swing;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import org.sodeja.swing.context.ApplicationContext;

public final class ComponentUtils {
	public static void swapInContainer(JComponent container, JComponent component) {
		swapInContainer(container, component, null);
	}

	public static void swapInContainerBig(JComponent container, JComponent component) {
		swapInContainer(container, component, GridBag.bigPanel());
	}
	
	public static void swapInContainerBigLater(final JComponent container, final JComponent component) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				swapInContainerBig(container, component);
			}});
	}
	
	public static void swapInContainer(JComponent container,
			JComponent component, Object layout) {
		container.removeAll();
		container.add(component, layout);

		repaint(container);
	}
	
	public static void removeFromContainer(JComponent component) {
		JComponent parent = (JComponent) component.getParent();
		if(parent == null) {
			return;
		}
		parent.remove(component);
		repaint(parent);
	}
	
	public static void clearContainer(JComponent container) {
		container.removeAll();
		repaint(container);
	}

	private static void repaint(JComponent container) {
		container.revalidate();
		container.repaint();
	}
	
	public static void selectFirstIfNull(JComboBox combo, Object obj) {
		if(obj == null) {
			combo.setSelectedIndex(0);
		} else {
			combo.setSelectedItem(obj);
		}
	}
	
	public static <E extends Enum> void fillFromEnum(JComboBox combo, Class<E> enumClass) {
		for(Object obj : enumClass.getEnumConstants()) {
			combo.addItem(obj);
		}
	}
	
	public static void setHeaderValue(ApplicationContext ctx, JTable tbl, int column, Enum key) {
		setHeaderValue(ctx, tbl, column, key.name());
	}
	
	public static void setHeaderValue(ApplicationContext ctx, JTable tbl, int column, String key) {
		tbl.getColumnModel().getColumn(column).setHeaderValue(ctx.getResourceProvider().getStringValue(key));
	}
}
