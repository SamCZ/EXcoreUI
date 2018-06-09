package cz.sam.novaengine.fxgui.util;

import java.awt.Color;
import java.nio.ByteBuffer;
import java.util.Random;

public class UIColor {

private static Random rand = new Random();
	
	public static UIColor White = new UIColor(255, 255, 255);
	public static UIColor Black = new UIColor(0, 0, 0);
	public static UIColor Red = new UIColor(255, 0, 0);
	public static UIColor Green = new UIColor(0, 255, 0);
	public static UIColor Blue = new UIColor(0, 0, 255);
	public static UIColor Gray = new UIColor(127, 127, 127);
	public static UIColor Transparent = new UIColor(0F, 0F, 0F, 0);
	public static UIColor Gray_Black = new UIColor(0.0F, 0.0F, 0.0F, 0.4F);
	public static UIColor Yellow = new UIColor(255F, 255F, 0, 255F);
	
	private float red;
	private float green;
	private float blue;
	private float alpha;
	
	public static UIColor fromHex(String hex) {
		return new UIColor(Integer.valueOf( hex.substring( 1, 3 ), 16 ),
            Integer.valueOf( hex.substring( 3, 5 ), 16 ),
            Integer.valueOf( hex.substring( 5, 7 ), 16 ), 255);
	}
	
	public UIColor() {
		this(0, 0, 0, 0);
	}
	
	public UIColor(int color) {
		this.red = color >> 16 & 255;
		this.blue = color >> 8 & 255;
		this.green = color & 255;
		this.alpha = color >> 24 & 255;
	}
	
	public UIColor(float red, float green, float blue, float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
		
		if(this.red > 1F) this.red = 1F;
		if(this.green > 1F) this.green = 1F;
		if(this.blue > 1F) this.blue = 1F;
		if(this.alpha > 1F) this.alpha = 1F;
		
		if(this.red < 0) this.red = 0;
		if(this.green < 0) this.green = 0;
		if(this.blue < 0) this.blue = 0;
		if(this.alpha < 0) this.alpha = 0;
	}
	
	public void set(float red, float green, float blue, float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
		
		if(this.red > 1F) this.red = 1F;
		if(this.green > 1F) this.green = 1F;
		if(this.blue > 1F) this.blue = 1F;
		if(this.alpha > 1F) this.alpha = 1F;
		
		if(this.red < 0) this.red = 0;
		if(this.green < 0) this.green = 0;
		if(this.blue < 0) this.blue = 0;
		if(this.alpha < 0) this.alpha = 0;
	}
	
	public UIColor(float red, float green, float blue) {
		this(red, green, blue, 1F);
	}
	
	public UIColor(int red, int green, int blue) {
		this((float)(red & 255) / 255F, (float)(green & 255) / 255F, (float)(blue & 255) / 255F, 1.0f);
	}
	
	public UIColor(int red, int green, int blue, int alpha) {
		this(red / 255F, green / 255F, blue / 255F, alpha / 255F);
	}
	
	public void set(UIColor color) {
		this.red = color.getRed();
		this.green = color.getGreen();
		this.blue = color.getBlue();
		this.alpha = color.getAlpha();
	}
	
	public UIColor mulLocal(float i) {
		this.red *= i;
		this.green *= i;
		this.blue *= i;
		return this;
	}
	
	public UIColor mul(float exp) {
		return new UIColor(this.red * exp, this.green * exp, this.blue * exp, this.alpha);
	}
	
	public ByteBuffer store(ByteBuffer buffer) {
		int red = (int) (this.red * 255F);
		int green = (int) (this.green * 255F);
		int blue = (int) (this.blue * 255F);
		int alpha = (int) (this.alpha * 255F);
		buffer.put((byte)(red)).put((byte)green).put((byte)blue).put((byte)alpha);
		return buffer;
	}
	
	public Color getAWTColor() {
		return new Color(this.red, this.green, this.blue, this.alpha);
	}
	
	public static UIColor getRandom() {
		return new UIColor(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
	}
	
	@Override
	public boolean equals(Object o) {
		UIColor color = (UIColor) o;
		if(color.getRed() == this.red && color.getGreen() == this.green && color.getBlue() == this.blue && color.getAlpha() == this.alpha) {
			return true;
		}
		return false;
	}
	
	public int getGrayScale() {
		return (int)(((this.red + this.green + this.blue) / 3.0f) * 255.0f);
	}
	
	@Override
	public int hashCode() {
		return 0;
	}
	
	@Override
	public String toString() {
		return "UIColor(r=" + this.red + ",g=" + this.green + ",b=" + this.blue + ",a=" + this.alpha + ")";
	}

	public float getRed() {
		return red;
	}

	public float getGreen() {
		return green;
	}

	public float getBlue() {
		return blue;
	}

	public float getAlpha() {
		return alpha;
	}
	
	public void setAlpha(float a) {
		this.alpha = a;
	}

	public String toStringCss() {
		return "rgba(" + (int)(this.red * 255) + ", " + (int)(this.green * 255) + ", " + (int)(this.blue * 255) + ", " + this.alpha + ")";
	}
	
}
