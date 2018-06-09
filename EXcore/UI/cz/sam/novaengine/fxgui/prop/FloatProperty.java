package cz.sam.novaengine.fxgui.prop;

import cz.sam.novaengine.fxgui.util.FXUtil;

public class FloatProperty extends NumberProperty<Float> {
	
	public static FloatProperty parse(String str) {
		FloatProperty prop = new FloatProperty();
		float val = FXUtil.extractFloat(str);
		prop.setValue(val);
		prop.setType(NumberProperty.getType(str.replaceAll("" + val, "")));
		return prop;
	}
	
}
