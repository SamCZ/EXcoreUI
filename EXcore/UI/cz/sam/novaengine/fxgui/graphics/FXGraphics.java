package cz.sam.novaengine.fxgui.graphics;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cz.sam.novaengine.fxgui.graphics.FXFontManager.FXFont;
import cz.sam.novaengine.fxgui.util.UIColor;

public class FXGraphics extends FXRenderer {
	
	private FXFontManager fontManager;
	private BufferedImage main_image;
	private int width;
	private int height;
	
	public FXGraphics(int width, int height) {
		this.width = width;
		this.height = height;
		this.fontManager = new FXFontManager();
		this.resetImage();
	}
	
	@Override
	public FXFontManager getFontManager() {
		return this.fontManager;
	}
	
	public FXFont getFont(String font, int size) {
		return this.getFont(font, size, Font.PLAIN);
	}
	
	public FXFont getFont(String font, int size, int style) {
		return this.fontManager.getFont(font, size, style);
	}
	
	public void resize(int width, int height) {
		if(this.width != width || this.height != height) {
			this.width = width;
			this.height = height;
			this.resetImage();
		}
	}
	
	private void resetImage() {
		this.main_image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_4BYTE_ABGR);
		this.setGraphics(this.main_image.createGraphics());
		this.setAntialising(true);
	}
	
	public BufferedImage getOutput() {
		return this.main_image;
	}
	
	public void save(String path) {
		try {
			ImageIO.write(main_image, "png", new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void dispose() {
		super.dispose();
	}

	public void clear(UIColor color) {
		super.clear(this.width, this.height, color);
	}
	
}
