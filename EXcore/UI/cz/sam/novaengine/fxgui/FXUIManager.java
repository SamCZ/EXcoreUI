package cz.sam.novaengine.fxgui;

import java.awt.image.BufferedImage;

import cz.sam.novaengine.fxgui.event.KeyEvent;
import cz.sam.novaengine.fxgui.event.KeyListener;
import cz.sam.novaengine.fxgui.event.MouseEvent;
import cz.sam.novaengine.fxgui.event.MouseListener;
import cz.sam.novaengine.fxgui.graphics.FXGraphics;
import cz.sam.novaengine.fxgui.util.UIColor;

public class FXUIManager implements MouseListener, KeyListener {
	
	private FXGraphics graphics;
	private FXNode activeUI;
	private boolean focusMoveFound = false;
	
	public FXUIManager(int width, int height) {
		this.resize(width, height);
	}

	public BufferedImage render(float time) {
		BufferedImage img = null;
		if(this.activeUI != null) {
			this.graphics.clear(UIColor.Transparent);
			this.activeUI.doSnapshot(this.graphics);
			this.activeUI.reset();
			img = this.graphics.getOutput();
		}
		this.focusMoveFound = false;
		return img;
	}

	public void resize(int width, int height) {
		this.graphics = new FXGraphics(width, height);
	}
	
	public void setUI(FXNode ui) {
		this.activeUI = ui;
	}
	
	private void onFXkeyDown(KeyEvent event, FXNode node) {
		if(node == null) return;
		for(FXNode subNode : node.getChilds()) {
			this.onFXkeyDown(event, subNode);
		}
		node.keyDown(event);
	}
	
	private void onFXkeyUp(KeyEvent event, FXNode node) {
		if(node == null) return;
		for(FXNode subNode : node.getChilds()) {
			this.onFXkeyUp(event, subNode);
		}
		node.keyUp(event);
	}
	
	private void onFXkeyTyped(KeyEvent event, FXNode node) {
		if(node == null) return;
		for(FXNode subNode : node.getChilds()) {
			this.onFXkeyTyped(event, subNode);
		}
		node.keyTyped(event);
	}
	
	private void onFXmouseEvent(MouseEvent event, boolean state, FXNode node) {
		if(node == null) return;
		for(int i = 0; i < node.getChilds().size(); i++) {
			FXNode subNode = node.getChilds().get(node.getChilds().size() - 1 - i);
			this.onFXmouseEvent(event, state, subNode);
		}
		node.mouseEvent(event, state);
	}
	
	private void onFXmouseMove(MouseEvent event, FXNode node) {
		if(node == null) return;
		for(int i = 0; i < node.getChilds().size(); i++) {
			FXNode subNode = node.getChilds().get(node.getChilds().size() - 1 - i);
			if(!this.focusMoveFound) {
				this.onFXmouseMove(event, subNode);
				if(subNode.isFocused()) {
					this.focusMoveFound = true;
					continue;
				}
			}
			if(this.focusMoveFound) {
				subNode.setFocused(false);
			}
		}
		node.mouseMove(event);
	}

	@Override
	public void keyDown(KeyEvent event) {
		this.onFXkeyDown(event, this.activeUI);
	}

	@Override
	public void keyUp(KeyEvent event) {
		this.onFXkeyUp(event, this.activeUI);
	}

	@Override
	public void keyTyped(KeyEvent event) {
		this.onFXkeyTyped(event, this.activeUI);
	}

	@Override
	public void mouseEvent(MouseEvent e, boolean state) {
		this.onFXmouseEvent(e, state, this.activeUI);
	}

	@Override
	public void mouseMove(MouseEvent e) {
		this.onFXmouseMove(e, this.activeUI);
	}
	
}
