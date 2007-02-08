package org.sodeja.swing;

import javax.swing.JComponent;

public final class ComponentUtils {
	public static void swapInContainer(JComponent container, JComponent component) {
		swapInContainer(container, component, null);
	}

	public static void swapInContainer(JComponent container,
			JComponent component, Object layout) {
		container.removeAll();
		container.add(component, layout);

		container.revalidate();
		container.repaint();
	}
	
	public static void removeFromContainer(JComponent component) {
		JComponent parent = (JComponent) component.getParent();
		parent.remove(component);
		parent.revalidate();
		parent.repaint();
	}
	
	public static void clearContainer(JComponent container) {
		container.removeAll();
		container.revalidate();
		container.repaint();
	}
}
