package org.sodeja.swing;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class SodejaSwingUtils {
	public static void scrollTabsToLatest(JTabbedPane tabPane) {
		// WORKAROUND http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=5037709
		final JViewport viewport = (JViewport) tabPane.getComponents()[2];
		viewport.revalidate();

		final JComponent view = ((JComponent) viewport.getView());
		view.revalidate();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				view.scrollRectToVisible(new Rectangle(view.getWidth() - 10,
						view.getHeight() - 10, 10, 10));
			}
		});
	}

	public static void showFrame(final JFrame frame) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame.setVisible(true);
			}});
	}
	
	public static void postEvent(AWTEvent event) {
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(event);
	}
	
	public static void grabFocus(final JComponent comp) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				comp.grabFocus();
			}});
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

	public static boolean setLF(String clazz, Container main) {
		try {
			UIManager.setLookAndFeel(clazz);
			updateUITree(main);
			return true;
		} catch (Exception exc) {
			return false;
		}
	}

	public static void updateUITree(Container main) {
		SwingUtilities.updateComponentTreeUI(main);
		if (main instanceof Window) {
			Window[] w = ((Window) main).getOwnedWindows();
			for (int i = 0; i < w.length; i++) {
				SwingUtilities.updateComponentTreeUI(w[i]);
			}
		}
	}

	public static final void setVisible(Component c, boolean visible) {
		c.setVisible(visible);
		if (c instanceof Container) {
			Container cont = (Container) c;
			Component[] cc = cont.getComponents();
			for (int i = 0; i < cc.length; i++) {
				setVisible(cc[i], visible);
			}
		}
		if (c instanceof JComponent) {
			JComponent jc = (JComponent) c;
			JLabel l = (JLabel) jc.getClientProperty("labeledBy"); //$NON-NLS-1$
			if (l != null) {
				l.setVisible(visible);
			}
		}
	}
}
