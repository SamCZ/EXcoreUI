package cz.sam.novaengine.fxgui.util;

import java.io.FileInputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class HtmlParser {
	
	public static void parse(String file) {
		/*try {
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(new FileInputStream(file));
			
			if (doc.hasChildNodes()) {
				printNote(doc.getChildNodes(), 0);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}*/
	}

	private static void printNote(NodeList nodeList, int depth) {
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
			if(tempNode.getNodeType() != 1) continue;
			
			System.out.print(depth + " " + tempNode.getNodeName());
			
			if (tempNode.hasAttributes()) {
				System.out.print(" - ");
				NamedNodeMap nodeMap = tempNode.getAttributes();
				for (int i = 0; i < nodeMap.getLength(); i++) {
					Node node = nodeMap.item(i);
					String pn = node.getNodeName();
					String pv = node.getNodeValue();
					System.out.print("" + pn + " = " + pv + ", ");
				}
			}
			System.out.println();
			
			if (tempNode.hasChildNodes()) {
				printNote(tempNode.getChildNodes(), depth + 1);
			}
		}
	}
	
}
