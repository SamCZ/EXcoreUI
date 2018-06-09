package cz.sam.novaengine.fxgui.prop;

import cz.sam.novaengine.fxgui.util.FXUtil;

public class IntProperty extends NumberProperty<Integer> {
	
	public static IntProperty parse(String str) {
		IntProperty prop = new IntProperty();
		int val = FXUtil.extractInt(str);
		prop.setValue(val);
		prop.setType(NumberProperty.getType(str.replaceAll("" + val, "")));
		return prop;
	}
	
}
