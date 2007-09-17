package org.sodeja.swing.formbuilder;

import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JTextField;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Predicate1;
import org.sodeja.lang.reflect.ReflectUtils;
import org.sodeja.swing.ButtonBarFactory;
import org.sodeja.swing.GridBag;
import org.sodeja.swing.component.action.ApplicationAction;
import org.sodeja.swing.component.form.FormDialog;
import org.sodeja.swing.component.form.FormPanel;
import org.sodeja.swing.component.form.FormPanelGridData;
import org.sodeja.swing.component.form.NamedFormDialog;
import org.sodeja.swing.context.ApplicationContext;

public class FormBuilder<T extends ApplicationContext> {
	
	private T ctx;
	private List<FormObject> objects;
	
	public FormBuilder(T ctx, Reader reader) {
		this.ctx = ctx;
		
		SFBLexer lexer = new SFBLexer(reader);
		List<String> lexicalTokens = lexer.tokenize();
		
		SFBParser parser = new SFBParser();
		objects = parser.parse(lexicalTokens);
	}
	
	public <M> FormPanel<T, M> viewForm(Class<M> clazz) {
		return viewForm(clazz.getName());
	}
	
	public <M> FormPanel<T, M> viewForm(final String name) {
		return viewForm(ListUtils.find(objects, new Predicate1<FormObject>() {
			@Override
			public Boolean execute(FormObject p) {
				return p.equals(name);
			}}));
	}
	
	protected <M> FormPanel<T, M> viewForm(FormObject obj) {
		throw new UnsupportedOperationException();
	}

	public <M> FormDialog<T> viewDialog(Class<M> clazz) {
		return viewDialog(clazz.getName());
	}
	
	public FormDialog<T> viewDialog(final String name) {
		return viewDialog(ListUtils.find(objects, new Predicate1<FormObject>() {
			@Override
			public Boolean execute(FormObject p) {
				return p.equals(name);
			}}));
	}
	
	protected FormDialog<T> viewDialog(final FormObject fobj) {
		return new NamedFormDialog<T>(ctx) {
			private static final long serialVersionUID = 307196235019404300L;

			private Map<String, JComponent> components = new HashMap<String, JComponent>();
			
			@Override
			protected String getResourceName() {
				return fobj.name + "_VIEW";
			}

			@Override
			protected void initComponentsDelegate(FormPanelGridData gridData) {
				gridData.setColumnsCount(2);
				gridData.setFillEmpty(true);
				
				for(Iterator<FormObjectField> fieldsIte = fobj.fields.iterator();fieldsIte.hasNext();) {
					FormObjectField field = fieldsIte.next();
					add(ctx.getLocalizationFactory().createLabel(fobj.name + "_" + field.name), 
							GridBag.lineLabel(gridData.getRow()));
					
					JTextField tf = new JTextField();
					tf.setEditable(false);
					components.put(field.name, tf);
					add(tf, GridBag.lineField(gridData.getRow()));
					
					if(fieldsIte.hasNext()) {
						gridData.nextRow();
					}
				}
			}

			@Override
			protected ApplicationAction<T>[] getActions() {
				return ButtonBarFactory.closeToCancelButtons(ctx, this);
			}
			
			public void showDialog(Object obj) {
				if(! obj.getClass().getName().equals(fobj.name)) {
					throw new IllegalArgumentException();
				}
				
				for(FormObjectField field : fobj.fields) {
					((JTextField) components.get(field.name)).setText(ReflectUtils.getFieldValue(obj, field.name).toString());
				}
			}
		};
	}
}
