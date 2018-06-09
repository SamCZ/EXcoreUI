package cz.sam.novaengine.fxgui.obj;

import cz.sam.novaengine.fxgui.FXNode;
import cz.sam.novaengine.fxgui.NodeScene;
import cz.sam.novaengine.fxgui.event.MouseEvent;
import cz.sam.novaengine.fxgui.graphics.FXFontManager.FXFont;
import cz.sam.novaengine.fxgui.graphics.FXGraphics;
import cz.sam.novaengine.fxgui.util.FXUtil;
import cz.sam.novaengine.fxgui.util.UIColor;

public class TitledPane extends NodeScene {
	
	private class TitlePaneControl extends NodeScene {
		
		private int r;
		private UIColor color;
		private int borderSize;
		
		public TitlePaneControl(UIColor color, int r, int borderSize) {
			this.color = color;
			this.r = r;
			this.borderSize = borderSize;
			this.setSize(r, r);
			this.setVisible(false);
		}
		
		@Override
		public void doSnapshot(FXGraphics g) {
			if(!this.isVisible()) return;
			if(this.isFocused()) {
				this.drawCircle(g, this.getWordX(), this.getWordY(), this.r, this.color, this.borderSize);
			} else {
				this.drawCircle(g, this.getWordX(), this.getWordY(), this.r, this.color.mul(1.5f), this.borderSize);
			}
		}
		
		@Override
		public void mouseMove(MouseEvent e) {
			if(!this.isVisible()) return;
			if(FXUtil.MouseUtil.isMouseIntersects(this.getWordX() - r/2, this.getWordY() - r/2, this.getWidth(), this.getHeight(), e)) {
				this.setFocused(true);
				return;
			}
			this.setFocused(false);
		}
		
		@Override
		public void mouseEvent(MouseEvent e, boolean state) {
			if(!this.isVisible()) return;
			if(this.isFocused()) {
				this.setClicked(true);
			} else {
				this.setClicked(false);
			}
			super.mouseEvent(e, state);
		}
		
	}
	
	public String title = "";
	private int titleHeight = 20;
	private int titlePaddingLeft = 10;
	private boolean contentVisible = true;
	
	private TitlePaneControl closeButton = new TitlePaneControl(UIColor.fromHex("#F95F57").mulLocal(0.9f), 10, 1);
	private TitlePaneControl hideButton = new TitlePaneControl(UIColor.fromHex("#748595").mulLocal(0.9f), 10, 1);
	
	public TitledPane() {
		this.hideButton.addClickListener(() -> {
			this.setContentVisible(!this.isContentVisible());
		});
		this.closeButton.addClickListener(() -> {
			this.setVisible(false);
		});
	}
	
	public TitledPane(String title) {
		this();
		this.title = title;
	}
	
	public void doSnapshot(FXGraphics g) {
		if(!this.isVisible()) return;
		FXFont font = g.getFont(DEFAULT_FONT, 12);
		int stringHeight = font.getHeight(g, this.title);
		
		this.titleHeight = 25;
		
		g.setBorder(1, DEFAULT_BORDER_RADIUS, UIColor.fromHex("#8a8a8a").mulLocal(1.2f));
		g.setGradient(0, 0, UIColor.fromHex("#EFEDF0"), 0, this.titleHeight, UIColor.fromHex("#D2D2D2"));
		g.fillRect(getWordX(), getWordY() - this.titleHeight, getWidth(), this.titleHeight + DEFAULT_BORDER_RADIUS);
		
		g.drawString(this.title, getWordX() + this.titlePaddingLeft, getWordY() - this.titleHeight + this.titleHeight / 2 - stringHeight / 2, UIColor.fromHex("#292929"), font);
		
		int prevX = 0;
		if(this.closeButton != null && this.closeButton.isVisible()) {
			this.closeButton.setLocation(this.getWordX() + this.getWidth() - 13, this.getWordY() - this.titleHeight + this.titleHeight / 2);
			this.closeButton.doSnapshot(g);
			prevX -= this.closeButton.getWidth() + 5;
		}
		if(this.hideButton != null && this.hideButton.isVisible()) {
			this.hideButton.setLocation(this.getWordX() + this.getWidth() - 13 + prevX, this.getWordY() - this.titleHeight + this.titleHeight / 2);
			this.hideButton.doSnapshot(g);
		}
		/*
		this.drawCircle(g, this.getWordX() + this.getWidth() - 13, this.getWordY() - this.titleHeight + this.titleHeight / 2, 10, UIColor.fromHex("#F95F57").mulLocal(0.9f), 1);
		this.drawCircle(g, this.getWordX() + this.getWidth() - 15 * 2, this.getWordY() - this.titleHeight + this.titleHeight / 2, 10, UIColor.fromHex("#748595"), 1);
		*/
		if(!this.contentVisible) return;
		g.setBorder(1, 0, UIColor.fromHex("#8a8a8a"));
		//g.setGradient(0, 0, UIColor.fromHex("#c3c3c3"), 0, this.titleHeight, UIColor.fromHex("#dbdbdb"));
		g.fillRect(getWordX(), getWordY(), getWidth(), getHeight() - this.titleHeight, UIColor.fromHex("#F2F2F2"));
		
		super.doSnapshot(g);
	}
	
	@Override
	public void mouseMove(MouseEvent e) {
		super.mouseMove(e);
		if(this.isVisible()) {
			if(this.closeButton != null) {
				this.closeButton.mouseMove(e);
			}
			if(this.hideButton != null) {
				this.hideButton.mouseMove(e);
			}
		}
	}
	
	@Override
	public void mouseEvent(MouseEvent e, boolean state) {
		super.mouseEvent(e, state);
		if(this.closeButton != null) {
			this.closeButton.mouseEvent(e, state);
		}
		if(this.hideButton != null) {
			this.hideButton.mouseEvent(e, state);
		}
	}
	
	public void setContentVisible(boolean visible) {
		int yShift = 330;
		if(visible) {
			this.setY(this.getY() - yShift);
		} else {
			this.setY(this.getY() + yShift);
		}
		this.contentVisible = visible;
		for(FXNode node : this.childs) {
			node.setVisible(visible);
		}
	}
	
	public boolean isContentVisible() {
		return this.contentVisible;
	}
	
	public void setCloseable(boolean flag) {
		this.closeButton.setVisible(flag);
	}

	public void setHideable(boolean flag) {
		this.hideButton.setVisible(flag);
	}
	
	public void createProperty(String nodeName, String nodeValue) {
		super.createProperty(nodeName, nodeValue);
		if(nodeName.equalsIgnoreCase("text")) {
			this.title = nodeValue;
		}
	}
	
	@Override
	public int getWordY() {
		return super.getWordY() + this.titleHeight;
	}
	
}
