package cz.sam.novaengine.fxgui.obj;

import cz.sam.novaengine.fxgui.NodeScene;
import cz.sam.novaengine.fxgui.effect.BorderEffect;
import cz.sam.novaengine.fxgui.event.MouseEvent;
import cz.sam.novaengine.fxgui.graphics.FXGraphics;
import cz.sam.novaengine.fxgui.util.FXUtil;
import cz.sam.novaengine.fxgui.util.UIColor;

public class Switch extends NodeScene {
	
	private int buttonSize = 10;
	private int padding;
	private boolean state;
	
	public Switch() {
		this.setSize(30+1, 15+1);
		this.setProperty("switch", "background: rgba(0, 0, 0, 1.0)");
		this.setProperty("switch", "border: 1px 7px #8a8a8a");
		this.setProperty("button", "border: 1px 5px #8a8a8a");
		
		this.padding = this.getHeight() - this.buttonSize;
	}
	
	@Override
	public void doSnapshot(FXGraphics g) {
		this.drawBorder(g);
		g.setGradient(0, 0, UIColor.fromHex("#EFEDF0"), 0, 20, UIColor.fromHex("#D2D2D2"));
		this.drawBackground(g);
		
		BorderEffect border = this.getStyle().getProperty("button", "border");
		if(border == null) border = BorderEffect.Indentity;
		g.setBorder(border.getSize(), border.getRadius(), border.getColor());
		g.setGradient(0, 0, UIColor.fromHex("#d4d4d4").mul(1.1f), 0, 20, UIColor.fromHex("#a3a3a3").mul(1.1f));
		int shift = 0;
		if(this.state) {
			shift = this.getWidth() - this.buttonSize - this.padding;
		}
		g.fillRect(this.getWordX() + this.padding / 2 + shift, this.getWordY() + this.getHeight() / 2 - this.buttonSize / 2, this.buttonSize, this.buttonSize);
	}
	
	@Override
	public void mouseEvent(MouseEvent e, boolean state) {
		if(state && this.isFocused()) {
			this.state = !this.state;
		}
	}
	
	@Override
	public void mouseMove(MouseEvent e) {
		if(FXUtil.MouseUtil.isMouseIntersects(this, this.getWidth(), this.getHeight(), e)) {
			this.setFocused(true);
			return;
		}
		this.setFocused(false);
	}
	
	@Override
	public String getStyleName() {
		return "switch";
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
}
