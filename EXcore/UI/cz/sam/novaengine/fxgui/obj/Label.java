package cz.sam.novaengine.fxgui.obj;

import cz.sam.novaengine.fxgui.NodeScene;
import cz.sam.novaengine.fxgui.graphics.FXGraphics;
import cz.sam.novaengine.fxgui.graphics.FXFontManager.FXFont;
import cz.sam.novaengine.fxgui.util.UIColor;

public class Label extends NodeScene {
	
	private String text;
	private FXGraphics graphics;
	private FXFont font;
	private int xMove;
	private int yMove;
	private boolean move;
	
	public Label(String text) {
		this.text = text;
	}
	
	public Label() {
		
	}
	
	@Override
	public void doSnapshot(FXGraphics g) {
		if(this.text == null) return;
		this.graphics = g;
		this.font = g.getFont(DEFAULT_FONT, DEFAULT_FONT_SIZE);
		g.drawString(this.text, this.getWordX(), this.getWordY() + this.getHeight() / 2, UIColor.fromHex("#292929"), font);
		if(this.move) {
			this.move = false;
			this.setLocation(this.xMove - this.getWidth(), this.yMove);
		}
	}
	
	public int getWidth() {
		if(this.font != null && this.graphics != null) {
			return this.font.getWidth(this.graphics, this.text);
		}
		return 0;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setLocationToMove(int x, int y) {
		this.xMove = x;
		this.yMove = y;
		this.move = true;
	}
	
}
