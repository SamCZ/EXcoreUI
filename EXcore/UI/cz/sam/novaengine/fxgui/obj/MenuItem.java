package cz.sam.novaengine.fxgui.obj;

import java.util.ArrayList;
import java.util.List;

import cz.sam.novaengine.fxgui.NodeScene;
import cz.sam.novaengine.fxgui.event.MouseEvent;
import cz.sam.novaengine.fxgui.graphics.FXGraphics;
import cz.sam.novaengine.fxgui.graphics.FXFontManager.FXFont;
import cz.sam.novaengine.fxgui.util.FXUtil;
import cz.sam.novaengine.fxgui.util.UIColor;

public class MenuItem extends NodeScene {
	
	private List<MenuItem> items = new ArrayList<MenuItem>();
	private String name;
	private int stringWidth;
	private int stringHeight;
	private int lastMaxWidth = 0;
	
	public MenuItem(String name) {
		this.name = name;
		this.setProperty("menuitem:hover", "background: rgba(200, 200, 200, 1)");
	}
	
	public void doSnapshot(FXGraphics g, int height, int shiftX, int shiftY, boolean vertical, int verticalForcedWidth) {
		FXFont font = g.getFont("Courier", 12);
		this.stringWidth = font.getWidth(g, this.name);
		this.stringHeight = font.getHeight(g, this.name);
		this.setWidth(this.stringWidth + (shiftX * 2));
		this.setheight(height + (shiftY * 2));
		if(verticalForcedWidth > 0) {
			this.setWidth(verticalForcedWidth);
		}
		
		if(this.isFocused() || vertical) {
			this.drawBackground(g);
		}
		
		if(this.name != null) {
			g.drawString(
					this.name, 
					getWordX() + this.getWidth() / 2 - this.stringWidth / 2, 
					getWordY() + height / 2 - this.stringHeight, 
					UIColor.fromHex("#000000"), font);
		}
		if(!this.isFocused()) return;
		int yShift = shiftY;
		for(MenuItem item : this.items) {
			item.setLocation(this.getWordX(), this.getWordY() + 1 + height + yShift);
			item.doSnapshot(g, this.getHeight(), shiftX, shiftY, true, this.lastMaxWidth);
			if(item.getWidth() > this.lastMaxWidth) {
				this.lastMaxWidth = item.getWidth();
			}
			yShift += item.getHeight();
		}
	}
	
	@Override
	public void mouseMove(MouseEvent e) {
		/*if(this.isFocused()) {
			boolean foundFocus = false;
			for(MenuItem item : this.items) {
				if(FXUtil.MouseUtil.isMouseIntersects(item, item.getWidth(), item.getHeight(), e) && !foundFocus) {
					item.setFocused(true);
					foundFocus = true;
				} else {
					item.setFocused(false);
				}
			}
		} else {
			for(MenuItem item : this.items) {
				item.setFocused(false);
			}
		}*/
	}
	
	@Override
	public void mouseEvent(MouseEvent e, boolean state) {
		super.mouseEvent(e, state);
	}
	
	public boolean isSomeChildFocused(MouseEvent e) {
		for(MenuItem obj : this.items) {
			if(obj.isSomeChildFocused(e)) {
				return true;
			}
		}
		int[] size = this.getChildsSize();
		return FXUtil.MouseUtil.isMouseIntersects(this.getWordX(), this.getWordY() + this.getHeight(), size[0], size[1] - this.getHeight(), e);
	}
	
	private int[] getChildsSize() {
		int width = 0, height = 0;
		for(MenuItem obj : this.items) {
			if(!obj.isVisible()) continue;
			if(width < obj.getWordX() + obj.getWidth()) {
				width = obj.getWordX() + obj.getWidth();
			}
			if(height < obj.getWordY() + obj.getHeight()) {
				height = obj.getWordY() + obj.getHeight();
			}
		}
		return new int[] { width - this.getWordX(), height - this.getWordY() };
	}
	
	public void addMenuItem(MenuItem item) {
		this.items.add(item);
	}
	
	public void removeMenuItem(MenuItem item) {
		this.items.remove(item);
	}
	
	@Override
	public String getStyleName() {
		return "menuitem";
	}
	
}
