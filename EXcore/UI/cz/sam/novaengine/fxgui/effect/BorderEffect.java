package cz.sam.novaengine.fxgui.effect;

import cz.sam.novaengine.fxgui.css.CSSProperty;
import cz.sam.novaengine.fxgui.css.PropertyDataTypes;
import cz.sam.novaengine.fxgui.prop.IntProperty;
import cz.sam.novaengine.fxgui.util.PropertyParser;
import cz.sam.novaengine.fxgui.util.UIColor;

public class BorderEffect extends Effect {
	
	private int size;
	private int radius;
	private UIColor color = UIColor.Transparent;
	
	public static BorderEffect Indentity = new BorderEffect();
	
	public BorderEffect() {
		super(new PropertyDataTypes(IntProperty.class, IntProperty.class, UIColor.class), "border");
	}
	
	public BorderEffect(int size, int radius, UIColor color) {
		this();
		this.size = size;
		this.radius = radius;
		this.color = color;
	}

	@Override
	public void set(CSSProperty property) {
		if(property instanceof BorderEffect) {
			BorderEffect effect = (BorderEffect) property;
			effect.setSize(this.size);
			effect.setRadius(this.radius);
			effect.setColor(this.color);
		}
	}
	
	@Override
	public void load(String contents) {
		PropertyParser parser = this.getDataTypes().parse(contents);
		this.size = ((IntProperty)parser.get(0)).getValue();
		this.radius = ((IntProperty)parser.get(1)).getValue();
		this.color = parser.get(2);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public UIColor getColor() {
		return color;
	}

	public void setColor(UIColor color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return "border: " + this.size + "px " + this.radius + "px " + this.color.toStringCss();
	}
}
