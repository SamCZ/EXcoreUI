package cz.sam.novaengine.fxgui.event;

import java.awt.Point;

public class MouseEvent {
	
	private Point point;
	private Point delta;
	private int button;
	
	public MouseEvent() {
		this.point = new Point();
		this.delta = new Point();
	}

	public Point getPoint() {
		return this.point;
	}
	
	public int getX() {
		return (int) point.x;
	}
	
	public int getY() {
		return (int) point.y;
	}
	
	public int getDX() {
		return (int) delta.x;
	}
	
	public int getDY() {
		return (int) delta.y;
	}
	
	public void setX(int x) {
		this.point.x = x;
	}
	
	public void setY(int y) {
		this.point.y = y;
	}
	
	public void setDX(int x) {
		this.delta.x = x;
	}
	
	public void setDY(int y) {
		this.delta.y = y;
	}

	public int getButtonId() {
		return button;
	}
	
	public MouseButton getButton() {
		return MouseButton.getById(button);
	}

	public void setButton(int button) {
		this.button = button;
	}
	
}
