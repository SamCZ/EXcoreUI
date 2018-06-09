package cz.sam.novaengine.fxgui.util;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.sam.novaengine.fxgui.NodeScene;
import cz.sam.novaengine.fxgui.obj.AnchorPane;
import cz.sam.novaengine.fxgui.obj.Button;
import cz.sam.novaengine.fxgui.obj.ImageView;
import cz.sam.novaengine.fxgui.obj.StackPane;
import cz.sam.novaengine.fxgui.obj.TitledPane;

public class FXParser {
	
	public static cz.sam.novaengine.fxgui.FXNode load(InputStream in) {
		cz.sam.novaengine.fxgui.FXNode rootNode = new NodeScene();
		
		try {
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(in);
			rootNode.setName(doc.getDocumentElement().getNodeName());
			
			if (doc.hasChildNodes()) {
				printNote(doc.getChildNodes(), rootNode);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return rootNode;
	}
	private static void printNote(NodeList nodeList, cz.sam.novaengine.fxgui.FXNode parent) {
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
			if(tempNode.getNodeType() != 1) continue;
			
			cz.sam.novaengine.fxgui.FXNode rootNode = null;
			switch(tempNode.getNodeName()) {
				case "Button":
					rootNode = new Button();
				break;
				case "AnchorPane":
					rootNode = new AnchorPane();
				break;
				case "ImageView":
					rootNode = new ImageView();
				break;
				case "TitledPane":
					rootNode = new TitledPane();
				break;
				case "StackPane":
					rootNode = new StackPane();
				break;
				default:
					rootNode = new NodeScene();
				break;
			}
			parent.add(rootNode);
			rootNode.setName(tempNode.getNodeName());
			
			if (tempNode.hasAttributes()) {
				NamedNodeMap nodeMap = tempNode.getAttributes();
				for (int i = 0; i < nodeMap.getLength(); i++) {
					Node node = nodeMap.item(i);
					rootNode.createProperty(node.getNodeName(), node.getNodeValue());
				}
			}
			
			if (tempNode.hasChildNodes()) {
				printNote(tempNode.getChildNodes(), rootNode);
			}
		}
		
	}
	
}
