package org.sodeja.swing.formbuilder;

import java.util.Collections;
import java.util.List;

class FormObject {
	public final String name;
	public final List<FormObjectField> fields;
	
	public FormObject(String name, List<FormObjectField> fields) {
		this.name = name;
		this.fields = Collections.unmodifiableList(fields);
	}
}
