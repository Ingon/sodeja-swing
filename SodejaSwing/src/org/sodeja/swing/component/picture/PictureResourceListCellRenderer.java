package org.sodeja.swing.component.picture;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class PictureResourceListCellRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = -2085958468370904885L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		if(! (value instanceof PictureResource)) {
			return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		}
		
		PictureResource resource = (PictureResource) value;
		return super.getListCellRendererComponent(list, resource.getImage(), index, isSelected, cellHasFocus);
	}
}
