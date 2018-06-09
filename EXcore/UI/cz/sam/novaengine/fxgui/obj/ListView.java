package cz.sam.novaengine.fxgui.obj;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import cz.sam.novaengine.fxgui.NodeScene;
import cz.sam.novaengine.fxgui.event.KeyEvent;
import cz.sam.novaengine.fxgui.event.ListViewSelectListener;
import cz.sam.novaengine.fxgui.event.MouseEvent;
import cz.sam.novaengine.fxgui.graphics.FXFontManager.FXFont;
import cz.sam.novaengine.fxgui.graphics.FXGraphics;
import cz.sam.novaengine.fxgui.util.FXUtil;
import cz.sam.novaengine.fxgui.util.UIColor;

public class ListView<T> extends NodeScene {
	
	private List<T> data = new ArrayList<T>();
	private List<ListViewSelectListener<T>> listeners = new ArrayList<ListViewSelectListener<T>>();
	private List<T> selectedItems = new ArrayList<T>();
	private Queue<T> selectedItemsQueue = new LinkedList<T>();
	private Queue<T> selectedItemsQueueRemove = new LinkedList<T>();
	
	private boolean haveScrollbar;
	private int scrollContainerBarWidth = 14;
	private int scrollBarWidth = 7;
	private int itemHeight = 25;
	
	private int scrollBarHeight;
	
	private int scrollBarPublicX;
	private int scrollBarPublicY;
	
	private int scrollBarY;
	private int scrollBarYshift;
	
	private boolean scrollbarDragged;
	private int scrollBarDragY;
	
	private int itemsShift;
	private boolean isCTRLdown;
	
	public ListView(int width, int height) {
		this.setSize(width, height);
		this.setProperty("listView", "border: 1px 0px rgba(150, 150, 150, 1)");
	}
	
	private void updateQueue() {
		while(!this.selectedItemsQueue.isEmpty()) {
			this.selectedItems.add(this.selectedItemsQueue.poll());
		}
		while(!this.selectedItemsQueueRemove.isEmpty()) {
			this.selectedItems.remove(this.selectedItemsQueueRemove.poll());
		}
	}
	
	@Override
	public void doSnapshot(FXGraphics g) {
		this.updateQueue();
		this.drawBorder(g);
		this.drawBackground(g);
		
		int itemsHeight = this.data.size() * this.itemHeight;
		this.haveScrollbar = itemsHeight > this.getHeight();
		int itemWidth = this.getWidth();
		if(this.haveScrollbar) {
			itemWidth -= this.scrollContainerBarWidth;
			
			this.scrollBarHeight = (int) ((this.getHeight() / (float)itemsHeight) * this.getHeight());
			this.scrollBarHeight = Math.max(this.scrollBarHeight, this.scrollBarWidth * 2);
			this.scrollBarHeight = Math.min(this.scrollBarHeight, this.getHeight() -  this.scrollBarWidth);
			
			g.fillRect(this.getWordX() + itemWidth + 1, this.getWordY() + 1, this.scrollContainerBarWidth, this.getHeight(), UIColor.fromHex("#EAEAEA"));
			g.fillRect(this.getWordX() + itemWidth + 1, this.getWordY() + 1, 1, this.getHeight(), UIColor.fromHex("#999999"));
			
			this.scrollBarPublicX = this.getWordX() + itemWidth + 1 + this.scrollContainerBarWidth / 2 - this.scrollBarWidth / 2;
			this.scrollBarPublicY = this.getWordY() + 1 + 2;
			
			if(this.scrollBarYshift < 0) {
				this.scrollBarYshift = 0;
			}
			
			if(this.scrollBarPublicY + this.scrollBarYshift + this.scrollBarHeight > this.getWordY() + this.getHeight()) {
				this.scrollBarYshift = this.getHeight() - this.scrollBarHeight - 4;
			}
			
			this.scrollBarY = this.scrollBarPublicY + this.scrollBarYshift;
			
			g.setBorder(0, 7, UIColor.White);
			g.fillRect(
					this.scrollBarPublicX, 
					this.scrollBarPublicY + this.scrollBarYshift, 
					this.scrollBarWidth, this.scrollBarHeight, UIColor.fromHex("#A1A1A1"));
		} else {
			this.scrollBarYshift = 0;
		}
		
		FXFont font = g.getFont("Tahoma", 11);
		
		Shape oldClip = g.getClip();
		g.clip(this.getWordX(), this.getWordY(), itemWidth + 1, this.getHeight() +1);
		this.itemsShift = -(int)((this.scrollBarYshift / (float)(this.getHeight() -5)) * itemsHeight);
		int nextY = this.itemsShift;
		int ix = 0;
		int iy = 0;
		for(int i = 0; i < this.data.size(); i++) {
			T item = this.data.get(i);
			String str = item.toString();
			ix = 5;
			iy = nextY + this.itemHeight / 2 - font.getHeight(g, str);
			if(this.selectedItems.contains(item)) {
				g.fillRect(this.getWordX() + 1, this.getWordY() + nextY + 1, itemWidth + 1, this.itemHeight,  UIColor.fromHex("#F95F57").mulLocal(1.8f));
			}
			g.drawString(str, this.getWordX() + ix, this.getWordY() + iy + this.itemHeight / 2 - font.getHeight(g, str) / 2, UIColor.Black, font);
			nextY += this.itemHeight;
		}
		
		g.clip(oldClip);
	}
	
	@Override
	public void keyDown(KeyEvent event) {
		/*if(event.getKeyCode() == KeyInput.KEY_LCONTROL) {
			this.isCTRLdown = true;
		}*/
	}
	
	@Override
	public void keyUp(KeyEvent event) {
		/*if(event.getKeyCode() == KeyInput.KEY_LCONTROL) {
			this.isCTRLdown = false;
		}*/
	}
	
	@Override
	public void onWheelAction(WheelAction action) {
		if(this.isFocused() && this.haveScrollbar) {
			if(action == WheelAction.Up) {
				this.scrollBarYshift -= 10;
			} else if(action == WheelAction.Down) {
				this.scrollBarYshift += 10;
			}
		}
	}
	
	@Override
	public void mouseEvent(MouseEvent e, boolean state) {
		if(!state) {
			this.scrollbarDragged = false;
		}
		if(this.isFocused()) {
			if(state && FXUtil.MouseUtil.isMouseIntersects(this.scrollBarPublicX, this.scrollBarY, this.scrollBarWidth, this.scrollBarHeight, e)) {
				this.scrollbarDragged = true;
				this.scrollBarDragY = e.getY() - this.scrollBarY;
			} else {
				this.scrollbarDragged = false;
			}
		}
		if(!state || !FXUtil.MouseUtil.isMouseIntersects(this, this.haveScrollbar ? this.getWidth() - this.scrollContainerBarWidth : this.getWidth(), this.getHeight(), e)) return;
		int nextY = this.itemsShift;
		for(int i = 0; i < this.data.size(); i++) {
			T item = this.data.get(i);
			if(FXUtil.MouseUtil.isMouseIntersects(this.getWordX() + 1, this.getWordY() + nextY + 1, this.getWidth() + 1, this.itemHeight, e)) {
				if(!this.isCTRLdown) {
					for(T rm : this.selectedItems) {
						this.onItemChange(rm, true);
					}
					this.selectedItems.clear();
				}
				if(this.selectedItems.contains(item)) {
					if(this.selectedItems.size() > 1) {
						this.selectedItems.remove(item);
						this.onItemChange(item, true);
					}
				} else {
					this.selectedItems.add(item);
					this.onItemChange(item, false);
				}
			}
			nextY += this.itemHeight;
		}
	}
	
	private void onItemChange(T item, boolean remove) {
		for(ListViewSelectListener<T> listener : this.listeners) {
			listener.onSelect(this, item, remove);
		}
	}

	@Override
	public void mouseMove(MouseEvent e) {
		if(this.scrollbarDragged) {
			int pos = e.getY() - this.scrollBarPublicY;
			this.scrollBarYshift = pos - this.scrollBarDragY;
		}
		if(FXUtil.MouseUtil.isMouseIntersects(this, this.getWidth(), this.getHeight(), e)) {
			this.setFocused(true);
			return;
		}
		this.setFocused(false);
		super.mouseMove(e);
	}
	
	public void addSelectListener(ListViewSelectListener<T> listener) {
		this.listeners.add(listener);
	}
	
	public void removeSelectListener(ListViewSelectListener<T> listener) {
		this.listeners.remove(listener);
	}
	
	public void addSelected(T data) {
		this.selectedItemsQueue.add(data);
	}
	
	public void removeSelected(T data) {
		this.selectedItemsQueueRemove.add(data);
	}
	
	public T getSelected() {
		return this.selectedItems.get(0);
	}
	
	public void setSelected(T data) {
		this.selectedItems.clear();
		if(data != null) {
			this.selectedItems.add(data);
		}
	}
	
	public void add(T data) {
		if(this.selectedItems.isEmpty() && this.data.size() == 0) {
			this.selectedItems.add(data);
		}
		this.data.add(data);
	}
	
	public boolean constainsData(T data) {
		return this.data.contains(data);
	}
	
	public void remove(T data) {
		this.data.remove(data);
		if(this.selectedItems.contains(data)) {
			this.selectedItems.remove(data);
		}
	}
	
	public void setData(T[] data) {
		this.data.clear();
		this.selectedItems.clear();
		if(data != null) {
			if(data.length > 0) {
				this.selectedItems.add(data[0]);
			}
			for(T d : data) {
				this.data.add(d);
			}
		}
	}

	@Override
	public String getStyleName() {
		return "listView";
	}
	
}
