package cz.sam.novaengine.fxgui.effect;

import cz.sam.novaengine.fxgui.css.CSSProperty;
import cz.sam.novaengine.fxgui.css.PropertyDataTypes;
import cz.sam.novaengine.fxgui.util.PropertyParser;
import cz.sam.novaengine.fxgui.util.UIColor;

public class ColorEffect extends Effect {

	private UIColor color = UIColor.White;
	
	public static ColorEffect Indentity = new ColorEffect();
	
	public ColorEffect(String name) {
		super(new PropertyDataTypes(UIColor.class), name);
	}
	
	public ColorEffect() {
		this("Color");
	}

	@Override
	public void load(String contents) {
		PropertyParser parser = this.getDataTypes().parse(contents);
		this.color = parser.get(0);
	}

	@Override
	public void set(CSSProperty property) {
		if(property instanceof ColorEffect) {
			ColorEffect effect = (ColorEffect) property;
			effect.setColor(color);
		}
	}

	public UIColor getColor() {
		return color;
	}

	public void setColor(UIColor color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return this.getName() + ": " + this.color.toStringCss();
	}
	
}
