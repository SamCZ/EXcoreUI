package cz.sam.novaengine.fxgui.obj;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

import cz.sam.novaengine.fxgui.NodeScene;
import cz.sam.novaengine.fxgui.event.MouseEvent;
import cz.sam.novaengine.fxgui.graphics.FXGraphics;
import cz.sam.novaengine.fxgui.prop.BooleanProperty;
import cz.sam.novaengine.fxgui.util.FXUtil;
import cz.sam.novaengine.fxgui.util.UIColor;

public class CheckBox extends NodeScene {
	
	private GeneralPath path = new GeneralPath();
	private BooleanProperty stateProperty = new BooleanProperty(false);
	
	public CheckBox() {
		super();
		this.setSize(15+1, 15+1);
		
		this.setProperty("checkbox", "background: rgba(0, 0, 0, 1.0)");
		this.setProperty("checkbox", "border: 1px 7px #8a8a8a");
		
		this.path.moveTo(10,17);
		this.path.lineTo(13,20);
		this.path.lineTo(20,13);
	}
	
	@Override
	public void doSnapshot(FXGraphics g) {
		this.drawBorder(g);
		g.setGradient(0, 0, UIColor.fromHex("#EFEDF0"), 0, 20, UIColor.fromHex("#D2D2D2"));
		//g.setGradient(0, 0, UIColor.fromHex("#a3a3a3"), 0, 10, UIColor.fromHex("#d4d4d4"));
		this.drawBackground(g);
		if(this.isChecked()) {
			Rectangle2D bounds = this.path.getBounds2D();
			Shape shape = AffineTransform.getTranslateInstance(this.getWordX() - 2 - bounds.getWidth() /2, this.getWordY() - bounds.getHeight() -1).createTransformedShape(path);
			Graphics2D g2d = g.getGraphics();
			g.setColor(UIColor.Black);
			g2d.draw(shape);
		}
	}
	
	public void setChecked(boolean state) {
		this.stateProperty.setValue(state);
	}
	
	public boolean isChecked() {
		return this.stateProperty.getValue();
	}
	
	@Override
	public void mouseEvent(MouseEvent e, boolean state) {
		if(state && this.isFocused()) {
			this.stateProperty.setValue(!this.stateProperty.getValue());
		}
	}
	
	@Override
	public void mouseMove(MouseEvent e) {
		if(FXUtil.MouseUtil.isMouseIntersects(this, this.getWidth(), this.getHeight(), e)) {
			this.setFocused(true);
			return;
		}
		this.setFocused(false);
	}
	
	@Override
	public String getStyleName() {
		return "checkbox";
	}

	public BooleanProperty getStateProperty() {
		return stateProperty;
	}
	
}
