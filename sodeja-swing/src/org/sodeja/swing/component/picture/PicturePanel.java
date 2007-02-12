package org.sodeja.swing.component.picture;

import java.awt.GridBagLayout;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.sodeja.model.FileResource;
import org.sodeja.swing.ButtonBarFactory;
import org.sodeja.swing.GridBag;
import org.sodeja.swing.component.action.ApplicationAction;
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
		setBorder(ctx.getLocalizationFactory().createBorder(ResourceConstants.TLT_PICTURES));
		
		setLayout(new GridBagLayout());
		
		contentModel = new PictureListModel();
		contentList = new JList(contentModel);
		contentList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		contentList.setLayoutOrientation(JList.VERTICAL_WRAP);
		contentList.setVisibleRowCount(-1);
		contentList.setCellRenderer(new PictureResourceListCellRenderer());
		add(new JScrollPane(contentList), GridBag.bigPanel());
		
		addAction = ButtonBarFactory.addButton(ctx, this);
		removeAction = ButtonBarFactory.removeButton(ctx, this);
		
		add(ButtonBarFactory.constructVerticalButtonsPane(addAction, removeAction),
			GridBag.buttonColumn(1, 1));
	}
	
	protected void addCallback() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle(ctx.getResourceProvider().getStringValue(ResourceConstants.DLG_CHOOSE_IMAGE));
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setMultiSelectionEnabled(true);
		chooser.addChoosableFileFilter(new FileNameExtensionFilter(
				ctx.getResourceProvider().getStringValue(ResourceConstants.CMB_FILE_JPEG), "jpg", "jpeg")); //$NON-NLS-1$ //$NON-NLS-2$
		chooser.addChoosableFileFilter(new FileNameExtensionFilter(
				ctx.getResourceProvider().getStringValue(ResourceConstants.CMB_FILE_PNG), "png")); //$NON-NLS-1$
		
		int result = chooser.showOpenDialog(ctx.getRootFrame());
		if(result != JFileChooser.APPROVE_OPTION) {
			return;
		}
		
		for(File file : chooser.getSelectedFiles()) {
			PictureResource resource = new PictureResource(new FileResource(file));
			int scale = getHeight();
			resource.scaleImage(scale, scale);
			contentModel.addPicure(resource);
		}
	}

	protected void removeCallback() {
		for(Object value : contentList.getSelectedValues()) {
			contentModel.removePicture((PictureResource) value);
		}
	}
	
	public void clear() {
		contentModel.clear();
	}
	
//	public List<ImageIcon> getData() {
//		return contentModel.getImages();
//	}
//	
//	public void setData(List<ImageIcon> images) {
//		contentModel.setImages(images);
//	}
	
	@Override
	public void setEnabled(boolean state) {
		addAction.setEnabled(false);
		removeAction.setEnabled(false);
		super.setEnabled(state);
	}
}
