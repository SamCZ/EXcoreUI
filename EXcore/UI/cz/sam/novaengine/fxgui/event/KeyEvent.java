package cz.sam.novaengine.fxgui.event;

public class KeyEvent {
	
	private int keyCode;
	private char keyChar;
	private boolean state;
	private boolean altDown;
	private boolean controlDown;
	private boolean shiftDown;
	
	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	public void setKeyChar(char keyChar) {
		this.keyChar = keyChar;
	}

	public void setAltDown(boolean altDown) {
		this.altDown = altDown;
	}

	public void setControlDown(boolean controlDown) {
		this.controlDown = controlDown;
	}

	public void setShiftDown(boolean shiftDown) {
		this.shiftDown = shiftDown;
	}

	public int getKeyCode() {
		return this.keyCode;
	}
	
	public char getKeyChar() {
		return this.keyChar;
	}
	
	public boolean isAltDown() {
		return this.altDown;
	}
	
	public boolean isControlDown() {
		return this.controlDown;
	}
	
	public boolean isShiftDown() {
		return this.shiftDown;
	}

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
}
