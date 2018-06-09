package cz.sam.novaengine.fxgui;

import java.util.ArrayList;
import java.util.List;

import cz.sam.novaengine.fxgui.css.CSS;
import cz.sam.novaengine.fxgui.css.CSSProperty;
import cz.sam.novaengine.fxgui.event.ClickListener;
import cz.sam.novaengine.fxgui.event.KeyEvent;
import cz.sam.novaengine.fxgui.event.KeyListener;
import cz.sam.novaengine.fxgui.event.MouseEvent;
import cz.sam.novaengine.fxgui.event.MouseListener;
import cz.sam.novaengine.fxgui.event.WheelEvent;
import cz.sam.novaengine.fxgui.graphics.FXGraphics;
import cz.sam.novaengine.fxgui.prop.IntProperty;
import cz.sam.novaengine.fxgui.util.FXUtil;

public abstract class FXNode implements MouseListener, KeyListener, WheelEvent {
	
	public List<FXNode> childs = new ArrayList<FXNode>();
	private List<ClickListener> clickListeners = new ArrayList<ClickListener>();
	private String name;
	protected CSS style = new CSS();
	private FXNode parent;
	
	private boolean focused = false;
	private boolean clicked = false;
	private boolean visible = true;
	private boolean enabled = true; 
	
	private IntProperty xProperty = new IntProperty();
	private IntProperty yProperty = new IntProperty();
	private IntProperty widthProperty = new IntProperty();
	private IntProperty heightProperty = new IntProperty();
	
	/* STYLE */
	
	public CSS getStyle() {
		return this.style;
	}
	
	public void setProperty(String styleName, String property) {
		this.style.createProperty(styleName, property);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getPropertyData(String identificator, String name) {
		return (T)this.style.getProperty(identificator, name);
	}
	
	/* TRANSFORM PROPERTIES */
	
	public FXNode setLocation(int x, int y) {
		this.xProperty.setValue(x);
		this.yProperty.setValue(y);
		return this;
	}
	
	public void setSize(int width, int height) {
		this.widthProperty.setValue(width);
		this.heightProperty.setValue(height);
	}
	
	public void setWidth(int width) {
		this.widthProperty.setValue(width);
	}
	
	public void setheight(int height) {
		this.heightProperty.setValue(height);
	}
	
	public int getWidth() {
		return this.widthProperty.getValue();
	}
	
	public int getHeight() {
		return this.heightProperty.getValue();
	}
	
	public void setX(int x) {
		this.xProperty.setValue(x);
	}
	
	public void setY(int y) {
		this.yProperty.setValue(y);
	}
	
	public int getX() {
		return this.xProperty.getValue();
	}
	
	public int getY() {
		return this.yProperty.getValue();
	}
	
	public int getWordX() {
		if(this.parent != null) {
			return this.parent.getWordX() + this.getX();
		}
		return this.getX();
	}
	
	public int getWordY() {
		if(this.parent != null) {
			return this.parent.getWordY() + this.getY();
		}
		return this.getY();
	}
	
	public IntProperty getxProperty() {
		return xProperty;
	}

	public IntProperty getyProperty() {
		return yProperty;
	}

	public IntProperty getWidthProperty() {
		return widthProperty;
	}

	public IntProperty getHeightProperty() {
		return heightProperty;
	}
	
	public boolean isEmpty() {
		if(this.widthProperty.getValue() == 0 || this.heightProperty.getValue() == 0) {
			return true;
		}
		return false;
	}
	
	/* OTHER */
	
	public boolean isClicked() {
		return this.clicked;
	}
	
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
	
	public boolean isFocused() {
		return focused;
	}

	public void setFocused(boolean focused) {
		this.focused = focused;
	}
	
	public boolean isVisible() {
		return visible && (this.parent != null ? this.parent.isVisible() : true);
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void createProperty(String nodeName, String nodeValue) {
		CSSProperty property = CSS.createPropertyData(nodeName, nodeValue);
		this.parseAttribute(nodeName, nodeValue);
		if(property == null) {
			return;
		}
		//System.out.println(property);
	}
	
	private void parseAttribute(String nodeName, String nodeValue) {
		if(nodeName.equals("layoutX")) {
			this.xProperty.setValue((int)Float.parseFloat(nodeValue));
		}
		if(nodeName.equals("layoutY")) {
			this.yProperty.setValue((int)Float.parseFloat(nodeValue));
		}
		
		if(nodeName.equals("prefWidth")) {
			this.widthProperty.setValue((int)Float.parseFloat(nodeValue));
		}
		if(nodeName.equals("prefHeight")) {
			this.heightProperty.setValue((int)Float.parseFloat(nodeValue));
		}
	}

	public void setCSS(String css) {
		this.style = CSS.parse(css);
	}
	
	public void add(FXNode node) {
		node.setParent(this);
		this.childs.add(node);
	}
	
	public void remove(FXNode node) {
		node.setParent(null);
		this.childs.remove(node);
	}
	
	private void setParent(FXNode node) {
		this.parent = node;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void doSnapshot(FXGraphics g) {
		for(FXNode node : this.childs) {
			node.doSnapshot(g);
		}
	}
	
	public List<FXNode> getChilds() {
		return this.childs;
	}
	
	@Override
	public void keyDown(KeyEvent event) {
		
	}

	@Override
	public void keyUp(KeyEvent event) {
		
	}

	@Override
	public void keyTyped(KeyEvent event) {
		
	}

	@Override
	public void mouseEvent(MouseEvent e, boolean state) {
		if(state && this.isFocused() && this.isClicked()) {
			this.callClickListeners();
		}
	}
	
	@Override
	public void onWheelAction(WheelAction action) {
		
	}
	
	public void addClickListener(ClickListener listener) {
		this.clickListeners.add(listener);
	}
	
	public void removeClickListener(ClickListener listener) {
		this.clickListeners.remove(listener);
	}
	
	public void callClickListeners() {
		for(ClickListener lis : this.clickListeners) {
			lis.onClick();
		}
	}
	
	@Override
	public void mouseMove(MouseEvent e) {
		if(!this.isEmpty() && FXUtil.MouseUtil.isMouseIntersects(this, this.getWidth(), this.getHeight(), e)) {
			this.setFocused(true);
			return;
		}
		this.setFocused(false);
	}

	public void reset() {
		this.doReset(this);
	}
	
	private void doReset(FXNode node) {
		for(FXNode subNode : node.getChilds()) {
			subNode.doReset(subNode);
		}
	}
	
}
