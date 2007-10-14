package org.sodeja.swing.panel;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.VFunction1;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.layout.GridBag;

public class ButtonsPanel extends ApplicationPanel<ApplicationContext> {

	public enum Orientation {
		HORIZONTAL(1, 0, 1.0, 0.0),
		VERTICAL(0, 1, 0.0, 1.0);
		
		private final int rows;
		private final int cols;
		
		private final double weightx;
		private final double weighty;
		
		Orientation(int rows, int cols, double weightx, double weighty) {
			this.rows = rows;
			this.cols = cols;
			this.weightx = weightx;
			this.weighty = weighty;
		}
	}
	
	public enum Position {
		TOP_RIGHT(GridBagConstraints.NORTHEAST),
		BOTTOM_LEFT(GridBagConstraints.SOUTHWEST);
		
		private final int anchor;
		
		Position(int anchor) {
			this.anchor = anchor;
		}
	}
		
	private static final long serialVersionUID = 5515378401902685065L;

	private final Orientation orientation;
	private final Position position;
	
	private List<Action> model;
	
	public ButtonsPanel() {
		this(Orientation.HORIZONTAL);
	}
	
	public ButtonsPanel(Orientation orientation) {
		this(orientation, Position.TOP_RIGHT);
	}
	
	public ButtonsPanel(Orientation orientation, Position position) {
		this.orientation = orientation;
		this.position = position;
		
		model = new ArrayList<Action>();
	}

	public void addAction(Action action) {
		model.add(action);
	}
	
	public void addActionInBegining(Action action) {
		model.add(0, action);
	}
	
	public void addSeparator() {
		model.add(null);
	}
	
	@Override
	protected void initComponents() {
		final JPanel realButtonsPanel = createButtonsPanel();
		// TODO revalidate this one
		add(realButtonsPanel, GridBag.create(0, 0, 1, 1, orientation.weightx, orientation.weighty, 
				position.anchor, GridBagConstraints.HORIZONTAL));
	}
	
	private JPanel createButtonsPanel() {
		GridLayout layout = new GridLayout(orientation.rows, orientation.cols, 4, 4);
		final JPanel realButtonsPanel = new JPanel(layout);
		ListUtils.execute(model, new VFunction1<Action>() {
			@Override
			public void executeV(Action p) {
				addAction(realButtonsPanel, p);
			}
		});

		return realButtonsPanel;
	}

	private void addAction(JPanel realButtonsPanel, Action action) {
		if(action == null) {
			realButtonsPanel.add(new JPanel());
			return;
		}
		
		realButtonsPanel.add(new JButton(action));
	}
}
