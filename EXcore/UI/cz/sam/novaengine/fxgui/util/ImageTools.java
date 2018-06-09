package cz.sam.novaengine.fxgui.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;

public class ImageTools {

	public static byte[] getRawByteBuffer(BufferedImage img) {
		return ((DataBufferByte)img.getRaster().getDataBuffer()).getData();
	}

	public static int[] getRawIntBuffer(BufferedImage img) {
		return ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
	}
	
	public static BufferedImage rotateImage(BufferedImage bufferedImage, float angle) {
		double sin = Math.abs(Math.sin(Math.toRadians(angle))), cos = Math.abs(Math.cos(Math.toRadians(angle)));
		int w = bufferedImage.getWidth(), h = bufferedImage.getHeight();
		int neww = (int) Math.floor(w*cos + h*sin), newh = (int) Math.floor(h*cos + w*sin);
		BufferedImage bimg = new BufferedImage(neww, newh, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = bimg.createGraphics();
		g.translate((neww-w)/2, (newh-h)/2);
		g.rotate(Math.toRadians(angle), w/2, h/2);
		g.drawRenderedImage(bufferedImage, null);
		g.dispose();
		return bimg;
	}

}
