package cz.sam.novaengine.fxgui.obj;

import java.util.ArrayList;
import java.util.List;

import cz.sam.novaengine.fxgui.NodeScene;
import cz.sam.novaengine.fxgui.event.MouseEvent;
import cz.sam.novaengine.fxgui.graphics.FXGraphics;
import cz.sam.novaengine.fxgui.util.FXUtil;

public class MenuBar extends NodeScene {
	
	private List<MenuItem> items = new ArrayList<MenuItem>();
	
	private int menuHeight = 20;
	private MenuItem focusedItem;
	
	public MenuBar(int width) {
		this.setWidth(width);
		this.setheight(this.menuHeight);
		
		this.setProperty("menubar", "background: #ffffff");
		//this.setProperty("menubar", "background: rgba(120,120,120,1)");
		this.setProperty("menubar", "border: 0px 0px #8a8a8a");
	}
	
	@Override
	public void doSnapshot(FXGraphics g) {
		this.drawBorder(g);
		
		/*if(this.isFocused() && !this.isClicked()) {
			g.setGradient(0, 0, UIColor.fromHex("#a3a3a3"), 0, 10, UIColor.fromHex("#d4d4d4"));
		} else {
			g.setGradient(0, 0, UIColor.fromHex("#d4d4d4"), 0, 20, UIColor.fromHex("#a3a3a3"));
		}*/
		
		this.drawBackground(g);
		
		int shift = 10;
		int xShift = shift;
		
		for(MenuItem item : this.items) {
			item.setLocation(this.getWordX() + xShift, this.getWordY()+1);
			item.doSnapshot(g, this.getHeight(), shift, 0, false, -1);
			
			xShift += item.getWidth();
		}
	}
	
	@Override
	public void mouseMove(MouseEvent e) {
		if(!this.isFocused()) {
			for(MenuItem item : this.items) {
				item.setFocused(false);
			}
		}
		if(FXUtil.MouseUtil.isMouseIntersects(this, this.getWidth(), this.getHeight(), e) || this.isChildsFocused(e)) {
			this.setFocused(true);
			this.focusedItem = null;
			boolean foundFocus = false;
			for(MenuItem item : this.items) {
				if((FXUtil.MouseUtil.isMouseIntersects(item, item.getWidth(), item.getHeight(), e) || item.isSomeChildFocused(e)) && !foundFocus) {
					item.setFocused(true);
					this.focusedItem = item;
					foundFocus = true;
				} else {
					item.setFocused(false);
				}
			}
			
			return;
		}
		this.setFocused(false);
	}
	
	private boolean isChildsFocused(MouseEvent e) {
		for(MenuItem item : this.items) {
			if(item.isSomeChildFocused(e)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void mouseEvent(MouseEvent e, boolean state) {
		for(MenuItem item : this.items) {
			item.mouseEvent(e, state);
		}
		if(this.isFocused() && this.focusedItem != null) {
			//this.focusedItem.setClicked(state);
		}
	}
	
	public void addMenuItem(MenuItem item) {
		this.items.add(item);
	}
	
	public void removeMenuItem(MenuItem item) {
		this.items.remove(item);
	}
	
	@Override
	public String getStyleName() {
		return "menubar";
	}
	
}
