package cz.sam.novaengine.fxgui.obj.editor;

import cz.sam.novaengine.fxgui.NodeScene;
import cz.sam.novaengine.fxgui.graphics.FXGraphics;
import cz.sam.novaengine.fxgui.util.UIColor;

public class BoxEditor extends NodeScene {
	
	public BoxEditor(int width, int height) {
		this.setSize(width, height);
	}
	
	@Override
	public void doSnapshot(FXGraphics g) {
		g.clip(this.getWordX()-1, this.getWordY()-1, this.getWidth()+3, this.getHeight()+3);
		g.setBorder(1, 5, UIColor.Black);
		g.fillRect(this.getWordX(), this.getWordY(), this.getWidth(), this.getHeight(), UIColor.Gray);
		
		g.fillOval(100, 100, 10, UIColor.White);
		
		g.clip(null);
	}
	
}
