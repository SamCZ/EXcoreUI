package cz.sam.novaengine.fxgui.obj;

import java.awt.Shape;

import cz.sam.novaengine.fxgui.NodeScene;
import cz.sam.novaengine.fxgui.event.KeyEvent;
import cz.sam.novaengine.fxgui.event.MouseEvent;
import cz.sam.novaengine.fxgui.graphics.FXGraphics;
import cz.sam.novaengine.fxgui.graphics.FXFontManager.FXFont;
import cz.sam.novaengine.fxgui.graphics.TextAlign;
import cz.sam.novaengine.fxgui.prop.StringProperty;
import cz.sam.novaengine.fxgui.util.FXUtil;
import cz.sam.novaengine.fxgui.util.UIColor;

public class TextField extends NodeScene {
	
	private int fieldHeight = 20;
	private StringProperty textProperty = new StringProperty();
	private TextAlign textAlign;
	private int stringHeight;
	private int startShift = 5;
	private boolean canWrite;
	private float carpetTimer;
	private boolean carpetVisible;
	
	public TextField(String text, int width) {
		this.textProperty.setValue(text);
		this.setWidth(width);
		this.setheight(this.fieldHeight);
		this.setProperty("textfield", "border: 1px 5px #D1D1D1");
	}
	
	@Override
	public void doSnapshot(FXGraphics g) {
		this.drawBorder(g);
		this.drawBackground(g);
		
		FXFont font = g.getFont(DEFAULT_FONT, DEFAULT_FONT_SIZE);
		this.stringHeight = font.getHeight(g, this.getText());
		
		String textToRender = this.getText();
		int carpet = 0;
		while(font.getWidth(g, textToRender) + this.startShift > this.getWidth()) {
			carpet++;
			if(carpet < 0) break;
			if(textToRender.length() - carpet < 0) break;
			if(carpet > textToRender.length()) break;
			textToRender = textToRender.substring(carpet, textToRender.length()-1 - carpet);
		}
		Shape oldClip = g.getClip();
		g.clip(this.getWordX(), this.getWordY(), this.getWidth(), this.getHeight());
		g.drawString(textToRender, this.getWordX() + this.startShift, this.getWordY() + this.getHeight() / 2 - this.stringHeight / 2, UIColor.fromHex("#292929"), font);
		g.clip(oldClip);
		
		if(this.canWrite) {
			this.setProperty("textfield", "border: 1px 5px rgba(30,100,200,1)");
			
			float delta = 5f;
			if(delta > 0) {
				this.carpetTimer += 0.01f * delta;
			}
			if(this.carpetTimer > 5f) {
				this.carpetVisible = !this.carpetVisible;
				this.carpetTimer = 0f;
			}
			if(this.carpetVisible) {
				g.fillRect(this.getWordX() + font.getWidth(g, textToRender) + this.startShift, this.getWordY() + this.getHeight() /2 - this.stringHeight / 2, 1, this.stringHeight, UIColor.Black);
			}
		} else {
			this.setProperty("textfield", "border: 1px 5px #D1D1D1");
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if(!this.canWrite) return;
		this.carpetVisible = true;
		String text = this.getText();
		if((int)e.getKeyChar() == 11) {
			// CTRL+A
		}
		if((int)e.getKeyChar() == 22) {
			text += FXUtil.getClipboard();
		}
		
		if(e.getKeyCode() == 14) {
			if(text.length() > 0) {
				text = text.substring(0, text.length() - 1);
			}
			this.textProperty.setValue(text);
			return;
		}
		text += e.getKeyChar();
		if(text.length() == 0) return;
		this.textProperty.setValue(text);
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
	public void mouseEvent(MouseEvent e, boolean state) {
		if(state) {
			if(this.isFocused()) {
				this.canWrite = true;
			} else {
				this.canWrite = false;
			}
		}
	}
	
	@Override
	public String getStyleName() {
		return "textfield";
	}

	public TextAlign getTextAlign() {
		return textAlign;
	}

	public void setTextAlign(TextAlign textAlign) {
		this.textAlign = textAlign;
	}
	
	public String getText() {
		return this.textProperty.getValue();
	}
	
	public void setText(String text) {
		this.textProperty.setValue(text);
	}

	public StringProperty getTextProperty() {
		return textProperty;
	}
	
}
