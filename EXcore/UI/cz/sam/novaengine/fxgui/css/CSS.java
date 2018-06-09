package cz.sam.novaengine.fxgui.css;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cz.sam.novaengine.fxgui.util.BlockLanguageParser;
import cz.sam.novaengine.fxgui.util.Statement;

public class CSS {
	
	private Map<String, Map<String, CSSProperty>> properties = new HashMap<String, Map<String, CSSProperty>>();
	
	public void setProperty(String identificator, String propName, CSSProperty property) {
		if(!this.properties.containsKey(identificator)) {
			this.properties.put(identificator, new HashMap<String, CSSProperty>());
		}
		this.properties.get(identificator).put(propName, property);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends CSSProperty> T getProperty(String identificator, String name) {
		if(!this.properties.containsKey(identificator)) {
			if(identificator.contains(":")) {
				identificator = identificator.split(":")[0];
			}
		}
		if(this.properties.containsKey(identificator)) {
			Map<String, CSSProperty> props = this.properties.get(identificator);
			if(props.containsKey(name)) {
				return (T) props.get(name);
			}
		}
		return null;
	}
	
	public void createProperty(String name, String style) {
		String style_name = style.split(":")[0].toLowerCase();
		String style_content = style.split(":")[1].toLowerCase();
		if(style_content.startsWith(" ")) {
			style_content = style_content.substring(1);
		}
		
		CSSProperty property = createPropertyData(style_name, style_content);
		if(property == null) return;
		this.setProperty(name, style_name, property);
	}
	
	@Override
	public String toString() {
		String css = "";
		for(Entry<String, Map<String, CSSProperty>> e : this.properties.entrySet()) {
			String block = "";
			for(Entry<String, CSSProperty> ep : e.getValue().entrySet()) {
				block += "	" + ep.getValue() + ";\r\n";
			}
			css += e.getKey() + " {\r\n" + block + "}\r\n";
		}
		return css;
	}
	
	public static CSSProperty createPropertyData(String style_name, String style_content) {
		Class<? extends CSSProperty> propertyType = CSSProperty.findLoader(style_name);
		CSSProperty property = createPropertyInstance(propertyType, style_name);
		if(property == null) return null;
		property.load(style_content);
		return property;
	}
	
	public static CSS parse(String css_full) {
		CSS css = new CSS();
		try {
			List<Statement> roots = BlockLanguageParser.parse(new ByteArrayInputStream(css_full.getBytes(StandardCharsets.UTF_8)));
			for(Statement state : roots) {
				String name = state.getLine();
				for(Statement content : state.getContents()) {
					String style = content.getLine();
					css.createProperty(name, style);
				}
			}
		} catch(Exception e) {
			System.out.println("Cannot parse style !");
		}
		
		return css;
	}

	private static CSSProperty createPropertyInstance(Class<? extends CSSProperty> propertyType, String name) {
		try {
			return (CSSProperty)propertyType.getConstructor(String.class).newInstance(name);
		} catch(Exception e) {
			try {
				return (CSSProperty)propertyType.getConstructor().newInstance();
			} catch (Exception e1) { }
		}
		return null;
	}
	
}
