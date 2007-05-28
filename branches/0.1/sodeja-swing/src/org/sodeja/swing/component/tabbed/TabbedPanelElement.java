package org.sodeja.swing.component.tabbed;

import javax.swing.JComponent;

public interface TabbedPanelElement {
	public JComponent getComponent();
	public String getTabName();
	
	public void tabSelected();
	public void tabDeselected();
}
