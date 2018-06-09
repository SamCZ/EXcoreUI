package cz.sam.novaengine.fxgui.util;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.sam.novaengine.fxgui.FXNode;
import cz.sam.novaengine.fxgui.event.MouseEvent;

public class FXUtil {
	
	public static class MouseUtil {
		
		public static boolean isMouseIntersects(int x, int y, int width, int height, int mouseX, int mouseY) {
			float minX = x;
			float minY = y;
			float maxX = minX + width;
			float maxY = minY + height;
			return (mouseX >= minX && mouseX <= maxX) && (mouseY >= minY && mouseY <= maxY);
		}
		
		public static boolean isMouseIntersects(int x, int y, int width, int height, MouseEvent e) {
			return isMouseIntersects(x, y, width, height, e.getX(), e.getY());
		}
		
		public static boolean isMouseIntersects(FXNode nodeLocation, int width, int height, MouseEvent e) {
			return isMouseIntersects(nodeLocation.getWordX(), nodeLocation.getWordY(), width, height, e.getX(), e.getY());
		}

		public static boolean isMouseIntersects(FXNode node, MouseEvent e) {
			return isMouseIntersects(node, node.getWidth(), node.getHeight(), e);
		}
		
	}
	
	public static void setClipboard(String str) {
		StringSelection selection = new StringSelection(str);
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clipboard.setContents(selection, selection);
	}
	
	public static String getClipboard() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		try {
			return (String)clipboard.getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException e) {
		} catch (IOException e) {
		}
		return null;
	}
	
	public static GraphicsConfiguration getGraphicsConfiguration() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	}
	
	public static BufferedImage createImage(int width, int height) {
		return new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
	}
	
	public static int extractInt(String str) {
		Matcher match = Pattern.compile("[0-9]").matcher(str);
		String number = "";
		while(match.find()) {
			String subNumber = str.substring(match.start(), match.end());
			number += subNumber;
		}
		try {
			return Integer.parseInt(number);
		} catch(Exception e) {
			
		}
		return 0;
	}
	
	public static float extractFloat(String str) {
		Matcher match = Pattern.compile("[0-9]*\\.?[0-9]+").matcher(str);
		String number = "";
		while(match.find()) {
			String subNumber = str.substring(match.start(), match.end());
			number += subNumber;
		}
		try {
			return Float.parseFloat(number);
		} catch(Exception e) {
			
		}
		return 0;
	}
			
	public static String getBetween(String str, String start, String end, boolean normalize) {
		/*String regex = Pattern.quote(start) + "\\.*?" + Pattern.quote(end);
		System.out.println(regex);
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(str);
		if(match.find()) {
			return str.substring(match.start(), match.end());
		}*/
		return str.substring(str.indexOf(start) + start.length(), str.lastIndexOf(end));
	}
	
	public static String[] getBetweenAll(String str, String start, String end) {
		String regex = Pattern.quote(start) + "(.*?)" + Pattern.quote(end);
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(str);
		String[] matches = new String[match.groupCount()];
		int matchIndex = 0;
		while(match.find()) {
			matches[matchIndex++] = str.substring(match.start(), match.end());
		}
		return matches;
	}

	public static String preg_quote(String pStr) {
		return pStr.replaceAll("[.\\\\+*?\\[\\^\\]$(){}=!<>|:\\-]", "\\\\$0");
	}
	
	public static int parseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch(Exception e) { }
		return 0;
	}
	
	public static float parseFloat(String str) {
		try {
			return Float.parseFloat(str);
		} catch(Exception e) { }
		return 0;
	}

	public static float concate(float value, float min, float max) {
		if(value < min) {
			return min;
		}
		if(value > max) {
			return max;
		}
		return value;
	}
	
}
