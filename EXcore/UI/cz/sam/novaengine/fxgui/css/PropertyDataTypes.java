package cz.sam.novaengine.fxgui.css;

import cz.sam.novaengine.fxgui.util.PropertyParser;

public class PropertyDataTypes {
	
	private Class<?>[] dataTypes;
	
	@SafeVarargs
	public <T> PropertyDataTypes(T... types) {
		this.dataTypes = new Class<?>[types.length];
		for(int i = 0; i < types.length; i++) {
			this.dataTypes[i] = (Class<?>) types[i];
		}
	}
	
	public PropertyParser parse(String contents) {
		return PropertyParser.parse(contents, this.dataTypes);
	}
	
}
