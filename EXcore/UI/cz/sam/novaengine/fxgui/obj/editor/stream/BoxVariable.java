package cz.sam.novaengine.fxgui.obj.editor.stream;

public class BoxVariable<T> {
	
	private String name;
	private T value = null;
	private int index = -1;
	
	public BoxVariable(String name) {
		this.name = name;
	}
	
	public BoxVariable(String name, T value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
