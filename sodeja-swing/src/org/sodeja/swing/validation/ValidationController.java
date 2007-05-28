package org.sodeja.swing.validation;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;

import org.sodeja.swing.context.ApplicationContext;
import org.sodeja.swing.context.ApplicationContextListener;

// TODO panels should be migrated to this?
public class ValidationController implements ApplicationContextListener {
	
    private Timer timer;
    private List<Validatable> timedValidatables;
    
    private Map<Component, List<Validatable>> componentValidatables;

    public ValidationController(ApplicationContext ctx) {
    	ctx.addApplicationContextListener(this);
    	
    	timedValidatables = new ArrayList<Validatable>();
    	componentValidatables = new HashMap<Component, List<Validatable>>();
    }

    public void addTimedValidatable(Validatable validator) {
    	timedValidatables.add(validator);
    }

    public void removeTimedValidatable(Validatable validator) {
    	timedValidatables.remove(validator);
    }
    
    public void addComponentValidatable(Component component, Validatable validator) {
    	List<Validatable> validatables = componentValidatables.get(component);
    	if(validatables == null) {
    		validatables = new ArrayList<Validatable>();
    		componentValidatables.put(component, validatables);
    	}
    	validatables.add(validator);
    }
    
    public void removeComponentValidatable(Component component, Validatable validator) {
    	List<Validatable> validatables = componentValidatables.get(component);
    	validatables.remove(validator);
    }
    
    public void performComponentValidation(Component component) {
    	List<Validatable> validatables = componentValidatables.get(component);
    	if(validatables == null) {
    		return;
    	}
    	validate(validatables);
    }
    
	public void applicationStartup(ApplicationContext context) {
        timer = new Timer(150, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validate(timedValidatables);
			}
		});
        timer.start();
	}
	
	public void applicationShutdown(ApplicationContext context) {
		timer.stop();
	}
    
    private void validate(List<Validatable> validators) {
        for(Validatable validator : validators) {
        	validator.validate();
        }
    }
}
