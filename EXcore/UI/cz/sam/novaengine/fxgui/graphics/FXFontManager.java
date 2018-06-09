package cz.sam.novaengine.fxgui.graphics;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FXFontManager {
	
	private Map<FontKey, FXFont> fonts;
	
	public FXFontManager() {
		this.fonts = new HashMap<FontKey, FXFont>();
	}
	
	public FXFont getFont(String name, int size, int style) {
		if(name.length() == 0) {
			name = "System";
		}
		FontKey key = new FontKey(name, size, style);
		FXFont font;
		if(this.fonts.containsKey(key)) {
			font = this.fonts.get(key);
		} else {
			font = new FXFont(name, size, style);
			this.fonts.put(key, font);
		}
		return font;
	}
	
	public static class FontKey {
		public String name;
		public int style;
		public int size;
		
		public FontKey(String name, int size, int style) {
			this.name = name;
			this.size = size;
			this.style = style;
		}
		
		@Override
		public int hashCode() {
			return this.name.hashCode() + this.size;
		}
		
		@Override
		public boolean equals(Object o) {
			if(o instanceof FontKey) {
				FontKey key = (FontKey) o;
				if(this.name.equals(key.name) && this.size == key.size && this.style == key.style) {
					return true;
				}
			}
			return false;
		}
	}
	
	public static class FXFont {
		
		private String name;
		private int size;
		private Font font;
		
		public FXFont(String name, int size, int style) {
			this.name = name;
			this.size = size;
			if(name.startsWith("FILE:")) {
				String fontPath = name.split("FILE:")[1];
				try {
					this.font = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)).deriveFont(style, size);
					System.out.println("Font loaded: " + fontPath);
				} catch (Exception e) {
					System.out.println("Cannot load font: " + fontPath);
				}
			} else {
				this.font = new Font(name, style, size);
			}
		}
		
		public Font getRawFont() {
			return this.font;
		}
		
		public int getFontHeight(FXRenderer g) {
			return g.getGraphics().getFontMetrics(this.font).getHeight();
		}
		
		public int getWidth(FXGraphics g, String str) {
			if(str == null) return 0;
			return g.getGraphics().getFontMetrics(this.font).stringWidth(str);
		}
		public int getHeight(FXGraphics g, String str) {
			if(str == null) return 0;
			FontMetrics m = g.getGraphics().getFontMetrics(this.font);
			return (int) m.getStringBounds(str, g.getGraphics()).getHeight()-1;
		}
		
		public int getBaseLine(FXGraphics g) {
			FontMetrics m = g.getGraphics().getFontMetrics(this.font);
			return m.getHeight() / 2;
		}

		public String getName() {
			return name;
		}

		public int getSize() {
			return size;
		}

		public int getCenterY(FXGraphics g, String text) {
			return this.getBaseLine(g) + this.getHeight(g, text) / 2;
		}
	}

}
