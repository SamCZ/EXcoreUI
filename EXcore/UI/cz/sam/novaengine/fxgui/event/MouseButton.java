package cz.sam.novaengine.fxgui.event;

public enum MouseButton {
	
	Left, Right, Middle;
	
	public static MouseButton getById(int id) {
		for(MouseButton button : MouseButton.values()) {
			if(button.ordinal() == id) {
				return button;
			}
		}
		return null;
	}
	
}
