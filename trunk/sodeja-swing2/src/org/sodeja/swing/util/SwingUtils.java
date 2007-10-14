package org.sodeja.swing.util;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.frame.ApplicationFrame;
import org.sodeja.swing.layout.GridBag;

public class SwingUtils {
	public static void showFrame(final JFrame frame) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame.setVisible(true);
			}});
	}

	public static void showDialog(final JDialog dialog) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				dialog.setVisible(true);
			}});
	}

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

	@SuppressWarnings("unchecked")
	public static <T extends ApplicationContext> T getContext(Component component) {
		ApplicationFrame<T> rootFrame = (ApplicationFrame<T>) SwingUtils.getRootComponent(component);
		return rootFrame.getContext();
	}
	
	// Peshos
	public static Frame getRootComponent(Component ctx) {
		ctx = getComponent(ctx);
		Component temp = SwingUtilities.getRoot(ctx);
		if (temp == null && ctx instanceof JMenuItem) {
			JPopupMenu p = (JPopupMenu) SwingUtilities.getAncestorOfClass(
					JPopupMenu.class, ctx);
			ctx = p.getInvoker();
			temp = SwingUtilities.getRoot(ctx);
		}

		return (Frame) ((temp.getParent() != null) ? getRootComponent(temp
				.getParent()) : temp);
	}

	private static Component getComponent(Component ctx) {
		if (ctx == null) {
			Toolkit t = Toolkit.getDefaultToolkit();
			EventQueue q = t.getSystemEventQueue();
			AWTEvent e = EventQueue.getCurrentEvent();
			if (e == null) {
				e = q.peekEvent();
			}
			Object o = e.getSource();
			if (o instanceof Component) {
				ctx = (Component) o;
			}
		}
		return ctx;
	}
}
