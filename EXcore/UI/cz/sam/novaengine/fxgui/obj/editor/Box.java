package cz.sam.novaengine.fxgui.obj.editor;

public class Box {
	
	private String name;
	
	public Box(String name) {
		this.name = name;
	}
	
	public <T> void createInput(String name, Class<T> type, T defaultValue) {
		
	}
	
	public <T> void createOutput(String name, Class<T> type) {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
