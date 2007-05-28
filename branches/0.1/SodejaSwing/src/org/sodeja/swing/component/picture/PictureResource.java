package org.sodeja.swing.component.picture;

import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;

import org.sodeja.model.Resource;

public class PictureResource {
	private Resource resource;
	private ImageIcon imageIcon;
	
	public PictureResource(Resource resource) {
		this.resource = resource;
		try {
			this.imageIcon = new ImageIcon(Resource.Util.loadData(resource));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Resource getResource() {
		return resource;
	}
	
	protected void scaleImage(int width, int height) {
		Image img = imageIcon.getImage();
		img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		imageIcon.setImage(img);
	}
	
	protected ImageIcon getImage() {
		return imageIcon;
	}
}
