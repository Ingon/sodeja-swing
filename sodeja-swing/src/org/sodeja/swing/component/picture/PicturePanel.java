package org.sodeja.swing.component.picture;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.sodeja.swing.ButtonBarFactory;
import org.sodeja.swing.GridBag;
import org.sodeja.swing.component.action.ApplicationAction;
import org.sodeja.swing.component.action.CallLocalMethodAction;
import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.resource.ResourceConstants;

public class PicturePanel<T extends ApplicationContext> extends JComponent {

	private static final long serialVersionUID = 8883907109594290021L;

	private T ctx;
	
	private ApplicationAction<T> addAction;
	private ApplicationAction<T> removeAction;
	
	private JList contentList;
	private PictureListModel contentModel;
	
	public PicturePanel(T ctx) {
		this.ctx = ctx;
		
		initComponents();
	}
	
	private void initComponents() {
		setBorder(BorderFactory.createTitledBorder("Pictures: "));
		
		setLayout(new GridBagLayout());
		
		contentModel = new PictureListModel();
		contentList = new JList(contentModel);
		contentList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		contentList.setLayoutOrientation(JList.VERTICAL_WRAP);
		contentList.setVisibleRowCount(-1);
		add(new JScrollPane(contentList), GridBag.bigPanel());
		
		addAction = new CallLocalMethodAction<T>(ctx, ResourceConstants.BTN_ADD, this, "addCallback");
		removeAction = new CallLocalMethodAction<T>(ctx, ResourceConstants.BTN_REMOVE, this, "removeCallback");
		
		add(ButtonBarFactory.constructVerticalButtonsPane(addAction, removeAction),
			GridBag.buttonColumn(1, 1));
	}
	
	// USED through reflection - call local method action
	@SuppressWarnings("unused")
	private void addCallback() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Choose image");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setMultiSelectionEnabled(true);
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG file", "jpg", "jpeg"));
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG file", "png"));
		
		int result = chooser.showOpenDialog(ctx.getRootFrame());
		if(result != JFileChooser.APPROVE_OPTION) {
			return;
		}
		
		for(File file : chooser.getSelectedFiles()) {
			ImageIcon icon = new ImageIcon(file.getAbsolutePath());
			int scale = getHeight();
			Image scaled = icon.getImage().getScaledInstance(scale, scale, Image.SCALE_DEFAULT);
			icon.setImage(scaled);
			contentModel.addImage(icon);
		}
	}

	// USED through reflection - call local method action
	@SuppressWarnings("unused")
	private void removeCallback() {
		for(Object value : contentList.getSelectedValues()) {
			contentModel.removeImage((ImageIcon) value);
		}
	}
	
	public void clear() {
		contentModel.clear();
	}
	
	public List<ImageIcon> getData() {
		return contentModel.getImages();
	}
	
	public void setData(List<ImageIcon> images) {
		contentModel.setImages(images);
	}
	
	@Override
	public void setEnabled(boolean state) {
		addAction.setEnabled(false);
		removeAction.setEnabled(false);
		super.setEnabled(state);
	}
}
