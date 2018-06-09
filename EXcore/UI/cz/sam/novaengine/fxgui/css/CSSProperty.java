package cz.sam.novaengine.fxgui.css;

import java.util.HashMap;
import java.util.Map;

import cz.sam.novaengine.fxgui.effect.BorderEffect;
import cz.sam.novaengine.fxgui.effect.ColorEffect;

public abstract class CSSProperty {
	
	private static Map<String, Class<? extends CSSProperty>> propertyTypes = new HashMap<String, Class<? extends CSSProperty>>();
	
	private String name;
	private PropertyDataTypes dataTypes = new PropertyDataTypes();
	
	public CSSProperty(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setDataTypes(PropertyDataTypes types) {
		this.dataTypes = types;
	}
	
	public PropertyDataTypes getDataTypes() {
		return this.dataTypes;
	}
	
	public abstract void load(String contents);
	
	public abstract void set(CSSProperty property);
	
	public static Class<? extends CSSProperty> findLoader(String name) {
		if(propertyTypes.containsKey(name)) {
			return propertyTypes.get(name);
		}
		return null;
	}
	
	static {
		propertyTypes.put("width", CSSIntProperty.class);
		propertyTypes.put("height", CSSIntProperty.class);
		
		propertyTypes.put("prefWidth", CSSIntProperty.class);
		propertyTypes.put("prefHeight", CSSIntProperty.class);
		
		propertyTypes.put("maxWidth", CSSIntProperty.class);
		propertyTypes.put("maxHeight", CSSIntProperty.class);
		propertyTypes.put("minWidth", CSSIntProperty.class);
		propertyTypes.put("minHeight", CSSIntProperty.class);
		
		propertyTypes.put("opacity", CSSFloatProperty.class);
		propertyTypes.put("rotate", CSSFloatProperty.class);
		
		propertyTypes.put("border", BorderEffect.class);
		
		propertyTypes.put("background", ColorEffect.class);
	}
}
