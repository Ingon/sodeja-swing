package org.sodeja.swing.component.picture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;

class PictureListModel extends AbstractListModel {
	private static final long serialVersionUID = 4221309134308387966L;
	
	private List<ImageIcon> images;
	
	public PictureListModel() {
		images = new ArrayList<ImageIcon>();
	}
	
	public Object getElementAt(int index) {
		return images.get(index);
	}

	public int getSize() {
		return images.size();
	}

	public void addImage(ImageIcon image) {
		int initialSize = images.size();
		images.add(image);
		fireIntervalAdded(this, initialSize, images.size());
	}
	
	public void removeImage(ImageIcon image) {
		int index = images.indexOf(image);
		images.remove(index);
		fireIntervalRemoved(this, index, index);
	}
	
	public void clear() {
		int size = images.size();
		if(size != 0) {
			images.clear();
			fireIntervalRemoved(this, 0, size - 1);
		}
	}
	
	public List<ImageIcon> getImages() {
		return Collections.unmodifiableList(images);
	}

	public void setImages(List<ImageIcon> newImages) {
		images.clear();
		images.addAll(newImages);
	}
}
