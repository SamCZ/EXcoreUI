package cz.sam.novaengine.fxgui.effect;

import cz.sam.novaengine.fxgui.css.CSSProperty;
import cz.sam.novaengine.fxgui.css.PropertyDataTypes;
import cz.sam.novaengine.fxgui.util.FXUtil;
import cz.sam.novaengine.fxgui.util.UIColor;

public abstract class Effect extends CSSProperty {
	
	public Effect(PropertyDataTypes types, String name) {
		super(name);
		this.setDataTypes(types);
	}
	
	public static UIColor parseColor(String part) {
		if(part.contains("#")) {
			return UIColor.fromHex(part);
		}
		int red = 0;
		int green = 0;
		int blue = 0;
		float alpha = 0;
		String colors = FXUtil.getBetween(part, "rgba(", ")", true);
		String[] split = colors.split(",");
		if(split.length == 4) {
			red = FXUtil.parseInt(split[0]);
			green = FXUtil.parseInt(split[1]);
			blue = FXUtil.parseInt(split[2]);
			alpha = FXUtil.parseFloat(split[3]);
		}
		return new UIColor(red, green, blue, (int)(alpha * 255));
	}

}
