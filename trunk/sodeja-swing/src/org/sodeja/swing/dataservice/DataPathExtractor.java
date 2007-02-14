package org.sodeja.swing.dataservice;

public interface DataPathExtractor<T> {
	public String[] getPathFor(T leaf);
}
