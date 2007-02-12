package org.sodeja.swing.resource;

import javax.swing.text.JTextComponent;

import org.sodeja.model.LocalizableResource;
import org.sodeja.swing.component.code.LocalizableResourceDialog;
import org.sodeja.swing.context.ApplicationContext;

public class LocalizationUtils {
	public static String getValue(LocalizableResource resource, ApplicationContext ctx) {
		return resource.getLocalizedValue(ctx.getLocaleProvider().getLocale());
	}

	public static void setValue(LocalizableResource resource, ApplicationContext ctx, String value) {
		resource.setLocalizedValue(ctx.getLocaleProvider().getLocale(), value);
	}
	
	public static void componentToResource(LocalizableResource resource, ApplicationContext ctx, JTextComponent component) {
		setValue(resource, ctx, component.getText());
	}
	
	public static void resourceToComponent(LocalizableResource resource, ApplicationContext ctx, JTextComponent component) {
		component.setText(getValue(resource, ctx));
	}
	
	public static void showLocalizationDialog(LocalizableResource resource, ApplicationContext ctx, JTextComponent component) {
		componentToResource(resource, ctx, component);
		
		LocalizableResourceDialog dialog = new LocalizableResourceDialog<ApplicationContext>(ctx);
		dialog.showLocalization(resource);
		
		resourceToComponent(resource, ctx, component);
	}
}
