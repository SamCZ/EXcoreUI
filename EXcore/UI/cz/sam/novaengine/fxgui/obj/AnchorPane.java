package cz.sam.novaengine.fxgui.obj;

import cz.sam.novaengine.fxgui.NodeScene;
import cz.sam.novaengine.fxgui.graphics.FXGraphics;

public class AnchorPane extends NodeScene {
	
	//private UIColor color = UIColor.getRandom();
	
	public void doSnapshot(FXGraphics g) {
		//g.fillRect(getX(), getY(), getWidth(), getHeight(), color);
		super.doSnapshot(g);
	}
	
}
