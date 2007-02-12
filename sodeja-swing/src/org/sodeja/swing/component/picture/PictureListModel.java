package org.sodeja.swing.component.picture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;

class PictureListModel extends AbstractListModel {
	private static final long serialVersionUID = 4221309134308387966L;
	
	private List<PictureResource> images;
	
	public PictureListModel() {
		images = new ArrayList<PictureResource>();
	}
	
	public Object getElementAt(int index) {
		return images.get(index);
	}

	public int getSize() {
		return images.size();
	}

	public void addPicure(PictureResource image) {
		int initialSize = images.size();
		images.add(image);
		fireIntervalAdded(this, initialSize, images.size());
	}
	
	public void removePicture(PictureResource image) {
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
	
	public List<PictureResource> getImages() {
		return Collections.unmodifiableList(images);
	}

	public void setImages(List<PictureResource> newImages) {
		images.clear();
		images.addAll(newImages);
	}
}
