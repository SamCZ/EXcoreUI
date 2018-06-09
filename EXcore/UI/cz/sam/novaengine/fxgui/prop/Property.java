package cz.sam.novaengine.fxgui.prop;

import java.util.ArrayList;
import java.util.List;

public class Property<T> {
	
	private T value;
	private List<Property<T>> bindigns = new ArrayList<Property<T>>();
	
	public void bind(Property<T> prop) {
		this.bindigns.add(prop);
	}
	
	public void unbind(Property<T> prop) {
		this.bindigns.remove(prop);
	}
	
	public void setValue(T value) {
		if(this.value == value) return;
		this.value = value;
		for(Property<T> prop : this.bindigns) {
			prop.setValue(value);
		}
	}
	
	public void setValueRaw(T value) {
		this.value = value;
	}
	
	public T getValue() {
		return this.value;
	}
	
}
