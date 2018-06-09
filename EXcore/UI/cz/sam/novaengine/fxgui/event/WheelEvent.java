package cz.sam.novaengine.fxgui.event;

public interface WheelEvent {
	
	public static enum WheelAction {
		Idle, Up, Down;
	}
	
	public void onWheelAction(WheelAction action);
	
}
