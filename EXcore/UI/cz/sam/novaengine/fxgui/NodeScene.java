package cz.sam.novaengine.fxgui;

import cz.sam.novaengine.fxgui.effect.BorderEffect;
import cz.sam.novaengine.fxgui.effect.ColorEffect;
import cz.sam.novaengine.fxgui.graphics.FXGraphics;
import cz.sam.novaengine.fxgui.util.UIColor;

public class NodeScene extends FXNode {
	
	protected int DEFAULT_BORDER_RADIUS = 7;
	protected int DEFAULT_FONT_SIZE = 12;
	
	protected String DEFAULT_FONT = "FILE:Assets/Fonts/OpenSans-Regular.ttf";
	
	protected void drawBorder(FXGraphics g) {
		BorderEffect border = this.getStyle().getProperty(this.getStyleNameWithAction(), "border");
		if(border == null) border = BorderEffect.Indentity;
		g.setBorder(border.getSize(), border.getRadius(), border.getColor());
	}
	
	protected void drawCircle(FXGraphics g, int x, int y, int r, UIColor color, int borderSize) {
		g.setBorder(borderSize, r/2, color.mul(0.7f));
		g.fillRect(x - r / 2, y - r / 2, r, r, color);
	}
	
	protected String getStyleNameWithAction() {
		String action = "";
		if(this.isFocused()) {
			action = ":hover";
		}
		if(this.isClicked()) {
			action = ":clicked";
		}
		return this.getStyleName() + action;
	}

	protected void drawBackground(FXGraphics g) {
		ColorEffect color = this.getStyle().getProperty(this.getStyleNameWithAction(), "background");
		if(color == null) color = ColorEffect.Indentity;
		g.fillRect(this.getWordX(), this.getWordY(), this.getWidth(), this.getHeight(), color.getColor());
	}
	
	public String getStyleName() {
		return "";
	}
}
