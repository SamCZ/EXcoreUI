package cz.sam.novaengine.fxgui.obj;

import cz.sam.novaengine.fxgui.NodeScene;
import cz.sam.novaengine.fxgui.effect.BorderEffect;
import cz.sam.novaengine.fxgui.event.MouseEvent;
import cz.sam.novaengine.fxgui.graphics.FXGraphics;
import cz.sam.novaengine.fxgui.prop.FloatProperty;
import cz.sam.novaengine.fxgui.util.FXUtil;
import cz.sam.novaengine.fxgui.util.UIColor;

public class Slider extends NodeScene {
	
	public enum Align {
		Horizontal, Vertical
	}
	
	private int length;
	private int thickness = 3;
	private int buttonSize = thickness * 4;
	private Align align;
	private boolean dragged;
	private int dragPos;
	private FloatProperty valueProperty = new FloatProperty();
	
	public Slider(float value, int length, Align align) {
		this.setValue(value);
		this.length = length;
		this.align = align;
		
		this.setProperty("slider", "background: rgba(255, 0, 0, 1.0)");
		this.setProperty("slider", "border: 1px 3px #8a8a8a");
		this.setProperty("button", "border: 1px 7px #8a8a8a");
	}
	
	@Override
	public void doSnapshot(FXGraphics g) {
		if(!this.isVisible()) return;
		this.drawBorder(g);
		if(this.align == Align.Horizontal) {
			g.setGradient(0, 0, UIColor.fromHex("#EFEDF0"), 0, 20, UIColor.fromHex("#D2D2D2"));
			this.setSize(this.length, this.thickness);
		} else if(this.align == Align.Vertical) {
			g.setGradient(0, 0, UIColor.fromHex("#EFEDF0"), 20, 0, UIColor.fromHex("#D2D2D2"));
			this.setSize(this.thickness, this.length);
		}
		this.drawBackground(g);
		
		BorderEffect border = this.getStyle().getProperty("button", "border");
		if(border == null) border = BorderEffect.Indentity;
		g.setBorder(border.getSize(), border.getRadius(), border.getColor());
		if(this.align == Align.Horizontal) {
			g.fillRect(this.getWordX() + this.getSliderPos() - this.buttonSize / 2, this.getWordY()+2 - this.buttonSize / 2, this.buttonSize, this.buttonSize);
		} else if(this.align == Align.Vertical) {
			g.fillRect(this.getWordX()+1 - this.buttonSize / 2, this.getWordY() + this.getSliderPos() - this.buttonSize / 2, this.buttonSize, this.buttonSize);
		}
	}
	
	private int getSliderPos() {
		int pos;
		if(this.align == Align.Horizontal) {
			float s = (this.getWidth() / 1f);
			pos = (int) (s * this.getValue());
		} else {
			float s = (this.getHeight() / 1f);
			pos = (int) (s * this.getValue());
		}
		return pos;
	}
	
	@Override
	public void mouseMove(MouseEvent e) {
		if(this.dragged) {
			if(this.align == Align.Horizontal) {
				int pos = (e.getX() - this.getWordX()) - this.dragPos;
				this.setValue(pos / (float)this.getWidth());
			} else if(this.align == Align.Vertical) {
				int pos = (e.getY() - this.getWordY()) - this.dragPos;
				this.setValue(pos / (float)this.getHeight());
			}
		}
		if(this.align == Align.Horizontal) {
			if(FXUtil.MouseUtil.isMouseIntersects(this.getWordX() + this.getSliderPos() - this.buttonSize / 2, this.getWordY()+2 - this.buttonSize / 2, this.buttonSize, this.buttonSize, e)) {
				this.setFocused(true);
				return;
			}
		} else if(this.align == Align.Vertical) {
			if(FXUtil.MouseUtil.isMouseIntersects(this.getWordX() + 1 - this.buttonSize / 2, this.getWordY()+ this.getSliderPos() - this.buttonSize / 2, this.buttonSize, this.buttonSize, e)) {
				this.setFocused(true);
				return;
			}
		}
		this.setFocused(false);
	}
	
	@Override
	public void mouseEvent(MouseEvent e, boolean state) {
		if(!state) {
			this.dragged = false;
			return;
		}
		if(!this.isFocused()) return;
		this.dragged = true;
		if(this.align == Align.Horizontal) {
			this.dragPos = e.getX() - (this.getWordX() + this.getSliderPos());
		} else if(this.align == Align.Horizontal) {
			this.dragPos = e.getY() - (this.getWordY() + this.getSliderPos());
		}
	}
	
	@Override
	public String getStyleName() {
		return "slider";
	}

	public float getValue() {
		return this.valueProperty.getValue();
	}

	
	public FloatProperty getValueProperty() {
		return this.valueProperty;
	}
	
	public void setValue(float value) {
		if(value > 1F) {
			value = 1F;
		}
		if(value < 0F) {
			value = 0F;
		}
		this.valueProperty.setValue(value);
	}

	public int getThickness() {
		return thickness;
	}

	public void setThickness(int thickness) {
		this.thickness = thickness;
	}

	public Align getAlign() {
		return align;
	}

	public void setAlign(Align align) {
		this.align = align;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
}
