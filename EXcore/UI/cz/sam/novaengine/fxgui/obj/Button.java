package cz.sam.novaengine.fxgui.obj;

import cz.sam.novaengine.fxgui.NodeScene;
import cz.sam.novaengine.fxgui.event.MouseEvent;
import cz.sam.novaengine.fxgui.graphics.FXGraphics;
import cz.sam.novaengine.fxgui.graphics.FXFontManager.FXFont;
import cz.sam.novaengine.fxgui.util.FXUtil;
import cz.sam.novaengine.fxgui.util.UIColor;

public class Button extends NodeScene {
	
	private String text;
	private int buttonPaddingLTRT = 10;
	private int buttonPaddingTB = 5;
	private boolean autoSize = true;
	
	public Button() {
		this.setProperty("button", "background: rgba(255, 0, 0, 1.0)");
		this.setProperty("button", "border: 1px 7px #8a8a8a");
	}
	
	public Button(String text) {
		this();
		this.setText(text);
	}
	
	public Button(String text, int width, int height) {
		this();
		this.setText(text);
		this.setSize(width, height);
		this.autoSize = false;
	}
	
	
	@Override
	public String getStyleName() {
		return "button";
	}
	
	@Override
	public void doSnapshot(FXGraphics g) {
		FXFont font = g.getFont(DEFAULT_FONT, DEFAULT_FONT_SIZE);
		int stringWidth = font.getWidth(g, this.text);
		int stringHeight = font.getHeight(g, this.text);
		if(this.autoSize) {
			int height = stringHeight + buttonPaddingTB;
			this.setSize(this.buttonPaddingLTRT * 2 + stringWidth, height + this.buttonPaddingTB / 2);
		}
		
		this.drawBorder(g);
		
		if(this.isClicked()) {
			g.setGradient(0, 0, UIColor.fromHex("#a3a3a3"), 0, 10, UIColor.fromHex("#d4d4d4"));
		} else if(this.isFocused()) {
			g.setGradient(0, 0, UIColor.fromHex("#d4d4d4"), 0, 20, UIColor.fromHex("#a3a3a3"));
		} else {
			g.setGradient(0, 0, UIColor.fromHex("#EFEDF0"), 0, 10, UIColor.fromHex("#D2D2D2"));
		}
		
		
		g.fillRect(getWordX(), getWordY(), this.getWidth(), this.getHeight(), UIColor.Gray);
		if(this.text != null) {
			g.drawString(this.text, getWordX() + this.getWidth() / 2 - stringWidth / 2, getWordY() + this.getHeight() / 2 - stringHeight / 2, UIColor.fromHex("#292929"), font);
		}
	}

	@Override
	public void mouseMove(MouseEvent e) {
		if(this.isMouseCollide(e)) {
			this.setFocused(true);
			return;
		}
		this.setFocused(false);
	}
	
	@Override
	public void mouseEvent(MouseEvent e, boolean state) {
		if(this.isMouseCollide(e) && this.isFocused()) {
			this.setClicked(state);
			super.mouseEvent(e, state);
			return;
		}
		this.setClicked(false);
		super.mouseEvent(e, state);
	}
	
	private boolean isMouseCollide(MouseEvent e) {
		if(FXUtil.MouseUtil.isMouseIntersects(this, this.getWidth(), this.getHeight(), e)) {
			return true;
		}
		return false;
	}
	
	public void createProperty(String nodeName, String nodeValue) {
		super.createProperty(nodeName, nodeValue);
		if(nodeName.equalsIgnoreCase("text")) {
			this.text = nodeValue;
		}
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
