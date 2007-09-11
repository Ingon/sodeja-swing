package org.sodeja.swing.event;

import javax.swing.SwingUtilities;

import org.sodeja.event.AbstractEventDispatcherEmitter;
import org.sodeja.event.Event;

public class DefaultEventDispatcher extends AbstractEventDispatcherEmitter {

	@Override
	public void emit(final Event event) {
		if(! SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					emit(event);
				}});
			return;
		}
		
		
		dispatch(event);
	}
}
