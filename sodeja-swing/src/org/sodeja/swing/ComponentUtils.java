package org.sodeja.swing;

import javax.swing.JComboBox;
import javax.swing.JComponent;

public final class ComponentUtils {
	public static void swapInContainer(JComponent container, JComponent component) {
		swapInContainer(container, component, null);
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
}
