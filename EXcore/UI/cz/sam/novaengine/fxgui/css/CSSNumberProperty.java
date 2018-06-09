package cz.sam.novaengine.fxgui.css;

import java.lang.reflect.ParameterizedType;

import cz.sam.novaengine.fxgui.util.PropertyParser;

public class CSSNumberProperty<T> extends CSSProperty {
	
	private T value;
	
	@SuppressWarnings("unchecked")
	public CSSNumberProperty(String name) {
		super(name);
		this.setDataTypes(new PropertyDataTypes(this.getClassType()));
		try {
			this.value = (T) this.value.getClass().newInstance();
		} catch (Exception e) { }
	}
	
	@SuppressWarnings("unchecked")
	public Class<T> getClassType() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public CSSNumberProperty(String name, T value) {
		this(name);
		this.value = value;
	}

	public T getValue() {
		return this.value;
	}
	
	@Override
	public void set(CSSProperty property) {
		if(property.getClass() == this.getClass()) {
			@SuppressWarnings("unchecked")
			CSSNumberProperty<T> prop = (CSSNumberProperty<T>)property;
			prop.value = this.value;
		}
	}

	@Override
	public void load(String contents) {
		PropertyParser parser = this.getDataTypes().parse(contents);
		this.value = parser.get(0);
	}
	
	@Override
	public String toString() {
		return this.getName() + ": " + this.value;
	}

}
