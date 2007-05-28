package org.sodeja.swing.component.picture;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import org.sodeja.collections.CollectionUtils;
import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.model.Resource;

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
	
	public List<Resource> getResources() {
		return ListUtils.map(images, new Function1<Resource, PictureResource>() {
			public Resource execute(PictureResource p) {
				return p.getResource();
			}});
	}

	public void setResources(List<Resource> newImages) {
		clear();
		
		CollectionUtils.map(newImages, images, new Function1<PictureResource, Resource>() {
			public PictureResource execute(Resource p) {
				return new PictureResource(p);
			}});
		
		fireContentsChanged(this, 0, 0);
	}
	
	public void scaleAll(int scale) {
		for(PictureResource res : images) {
			res.scaleImage(scale, scale);
		}
	}
}
