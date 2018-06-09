package cz.sam.novaengine.fxgui.graphics;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import cz.sam.novaengine.fxgui.graphics.FXFontManager.FXFont;
import cz.sam.novaengine.fxgui.util.UIColor;

public class FXRenderer {
	
	private Graphics2D g2d;
	/* Border */
	private boolean border_enabled;
	private int border_size;
	private UIColor border_color;
	/* Border radius */
	private boolean border_radius_enabled;
	private int border_radius;
	private Paint paint;
	
	public FXFontManager getFontManager() {
		return null;
	}
	
	public Graphics2D getGraphics() {
		return g2d;
	}

	public void setGraphics(Graphics2D g2d) {
		this.g2d = g2d;
	}
	
	public FXRenderer createSubRenderer() {
		FXRenderer renderer = new FXRenderer();
		renderer.setGraphics((Graphics2D)this.g2d.create());
		renderer.setAntialising(true);
		return renderer;
	}
	
	public void drawPixel(int x, int y, UIColor color) {
		this.setColor(color);
		this.g2d.drawLine(x, y, x, y);
	}
	
	public void drawLine(int x1, int y1, int x2, int y2, UIColor color) {
		this.setColor(color);
		this.g2d.drawLine(x1, y1, x2, y2);
	}
	
	public void fillOval(int x, int y, int width, int height, UIColor color) {
		this.setColor(color);
		this.g2d.fillOval(x - width / 2, y - height / 2, width, height);
	}
	
	public void fillOval(int x, int y, int size, UIColor color) {
		this.fillOval(x, y, size, size, color);
	}
	
	public void drawOval(int x, int y, int width, int height, UIColor color) {
		this.setColor(color);
		this.g2d.drawOval(x - width / 2, y - height / 2, width, height);
	}
	
	public void drawOval(int x, int y, int size, UIColor color) {
		this.drawOval(x, y, size, size, color);
	}
	
	public FXFont drawString(String str, int x, int y, UIColor color, int size, String font) {
		FXFont f = this.getFontManager().getFont(font, size, Font.PLAIN);
		String[] spls = str.split("\r\n");
		int startX = x;
		for(String s : spls) {
			this.drawString(f.getRawFont(), s, x, y, color);
			x = startX;
			y += f.getFontHeight(this);
		}
		return f;
	}
	
	public void drawString(String str, int x, int y, UIColor color, FXFont font) {
		this.drawString(font.getRawFont(), str, x, y, color);
	}
	
	public void drawString(Font font, String str, int x, int y, UIColor color) {
		this.g2d.setFont(font);
		this.setColor(color);
		this.g2d.drawString(str, x, y + font.getSize());
	}
	
	public Shape getClip() {
		return this.g2d.getClip();
	}
	
	public void clip(Shape shape) {
		this.g2d.setClip(shape);
	}
	
	public void clip(int x, int y, int width, int height) {
		this.g2d.setClip(x, y, width, height);
	}
	
	public void setBorder(int size, int radius, UIColor color) {
		this.setBorderRadius(radius);
		if(size == 0) {
			this.border_enabled = false;
			return;
		}
		this.border_enabled = true;
		this.border_size = size;
		this.border_color = color;
		if(this.border_color == null) {
			this.border_color = UIColor.White;
		}
	}
	
	public void setBorderRadius(int radius) {
		//if(radius == 0) return;
		this.border_radius_enabled = true;
		this.border_radius = radius;
	}
	
	public void setGradient(int x1, int y1, UIColor color1, int x2, int y2, UIColor color2) {
		this.paint = new GradientPaint(x1, y1, color1.getAWTColor(), x2, y2, color2.getAWTColor(), false);
	}

	public void drawImage(BufferedImage img, int x, int y, int width, int height, UIColor color) {
		if(this.border_enabled) {
			this.border_enabled = false;
			this.setColor(this.border_color);
			this.fillRoundedRect(x, y, width + this.border_size * 2, height + this.border_size * 2, (int)(this.border_radius * 1.5f));
			x += this.border_size;
			y += this.border_size;
		}
		if(this.border_radius_enabled) {
			this.border_radius_enabled = false;
			FXRenderer rn = this.createSubRenderer();
			rn.getGraphics().setClip(new RoundRectangle2D.Float(x, y, width, height, this.border_radius, this.border_radius));
			rn.drawImage(img, x, y, width, height);
			rn.dispose();
		} else {
			this.g2d.drawImage(img, x, y, width, height, null);
		}
	}
	
	public void fillRect(int x, int y, int width, int height) {
		this.fillRect(x, y, width, height, UIColor.White);
	}
	
	public void drawRect(int x, int y, int width, int height, UIColor color) {
		this.setColor(color);
		this.g2d.drawRect(x, y, width, height);
	}

	public void fillRect(int x, int y, int width, int height, UIColor color) {
		if(this.border_enabled) {
			this.border_enabled = false;
			if(this.border_size != 0) {
				this.setColor(this.border_color);
				this.fillRoundedRect(x, y, width + this.border_size * 2, height + this.border_size * 2, (int)(this.border_radius * 1.5f));
			}
			x += this.border_size;
			y += this.border_size;
		}
		if(this.paint != null) {
			FXRenderer rn = this.createSubRenderer();
			Graphics2D subG2d = rn.getGraphics();
			AffineTransform transform = new AffineTransform();
			transform.translate(x, y);
			//transform.rotate(Math.toRadians(this.gradientRotation), x, y - rotHeight - rotHeight / 2);
			subG2d.setPaint(this.paint);
			subG2d.setTransform(transform);
			if(this.border_radius_enabled) {
				this.border_radius_enabled = false;
				subG2d.fill(new RoundRectangle2D.Float(0, 0, width, height, this.border_radius, this.border_radius));
			} else {
				subG2d.fill(new Rectangle(0, 0, width, height));
			}
			rn.dispose();
			this.paint = null;
		} else {
			this.setColor(color);
			if(this.border_radius_enabled) {
				this.border_radius_enabled = false;
				this.fillRoundedRect(x, y, width, height, this.border_radius);
			} else {
				this.g2d.fillRect(x, y, width, height);
			}
		}
	}
	
	private void fillRoundedRect(int x, int y, int width, int height, int radius) {
		this.g2d.fill(new RoundRectangle2D.Float(x, y, width, height, radius, radius));
	}
	
	public void setAntialising(boolean enabled) {
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, enabled ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
		rh.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		this.g2d.setRenderingHints(rh);
	}
	
	protected void setSrcClip() {
		this.g2d.setComposite(AlphaComposite.SrcIn);
	}

	protected void setToClip() {
		this.g2d.setComposite(AlphaComposite.Src);
	}
	
	public void drawImage(BufferedImage img, int x, int y, int width, int height) {
		this.drawImage(img, x, y, width, height, UIColor.White);
	}
	
	public void clear(int width, int height) {
		this.clear(width, height, UIColor.White);
	}
	
	public void clear(int width, int height, UIColor color) {
		this.g2d.setBackground(color.getAWTColor());
		this.g2d.clearRect(0, 0, width, height);
	}
	
	protected void setColor(UIColor color, float mult) {
		this.g2d.setColor(color.mul(mult).getAWTColor());
	}
	
	public void setColor(UIColor color) {
		this.setColor(color, 1.0f);
	}
	
	public void dispose() {
		this.g2d.dispose();
	}
}
