package org.sodeja.swing.component;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

public class ComponentCombinatorFactory {
    public static JScrollPane createTitledScrollPane(JComponent component, String title) {
        JScrollPane scroller = new JScrollPane(component);
        scroller.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(title), scroller.getBorder()));
        return scroller;
    }
}
