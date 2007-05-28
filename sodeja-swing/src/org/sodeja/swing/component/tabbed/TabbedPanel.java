package org.sodeja.swing.component.tabbed;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.sodeja.swing.GridBag;
import org.sodeja.swing.component.ApplicationPanel;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.locale.LocaleChangeListener;

public class TabbedPanel<T extends ApplicationContext> extends ApplicationPanel<T> implements LocaleChangeListener {

	private static final long serialVersionUID = 6781154245796329760L;
	
	private List<TabbedPanelElement> elements;
	private JTabbedPane tabs;
	private int selectedIndex = -1;
	
	public TabbedPanel(T ctx) {
		super(ctx);
		elements = new ArrayList<TabbedPanelElement>();
		initComponents();
	}
	
	protected void initComponents() {
		setLayout(new GridBagLayout());
		
		tabs = new JTabbedPane();
		tabs.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				tabDeselected(selectedIndex);
				tabSelected(tabs.getSelectedIndex());
			}
		});
		this.add(tabs, GridBag.bigPanel());
	}

	public void addTab(TabbedPanelElement element) {
		elements.add(element);
		tabs.addTab(element.getTabName(), element.getComponent());
	}

	private void tabSelected(int selectedIndex) {
		elements.get(selectedIndex).tabSelected();
		this.selectedIndex = selectedIndex;
	}

	private void tabDeselected(int selectedIndex) {
		if(selectedIndex == -1) {
			return;
		}
		elements.get(selectedIndex).tabDeselected();
	}
	
	public void localeChanged(Locale oldLocale, Locale newLocale) {
		for(int i = 0;i < elements.size();i++) {
			tabs.setTitleAt(i, elements.get(i).getTabName());
		}
	}
}
