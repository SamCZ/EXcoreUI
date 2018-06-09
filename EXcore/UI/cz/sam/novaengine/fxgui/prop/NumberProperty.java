package cz.sam.novaengine.fxgui.prop;

import java.util.ArrayList;
import java.util.List;

public class NumberProperty<T> {
	
	public static enum Type {
		Empty(""), Pixels("px"), Percent("%");
		
		private final String name;
		
		Type(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
	}
	
	private List<NumberProperty<T>> bindings = new ArrayList<NumberProperty<T>>();
	private Type type = Type.Empty;
	private T value;
	
	@SuppressWarnings("unchecked")
	public NumberProperty() {
		try {
			this.value = (T) new Integer(0);
		} catch (Exception e) {
			try {
				this.value = (T) new Float(0);
			} catch(Exception e2) { }
		}
	}
	
	public void bind(NumberProperty<T> property) {
		this.bindings.add(property);
	}
	
	public void unbind(NumberProperty<T> property) {
		this.bindings.remove(property);
	}
	
	public static Type getType(String name) {
		if(name.equalsIgnoreCase("px")) {
			return Type.Pixels;
		} else if(name.equalsIgnoreCase("%")) {
			return Type.Percent;
		}
		return Type.Empty;
	}
	
	public NumberProperty(T value) {
		this.setValue(value);
	}
	
	public NumberProperty(Type type, T value) {
		this.type = type;
		this.setValue(value);
	}
	
	public NumberProperty(Type type) {
		this();
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
		for(NumberProperty<T> prop : this.bindings) {
			prop.setValue(value);
		}
	}
	
	@Override
	public String toString() {
		return this.value + this.type.getName();
	}
}
