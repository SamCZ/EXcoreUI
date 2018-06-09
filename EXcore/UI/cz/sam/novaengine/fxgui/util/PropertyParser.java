package cz.sam.novaengine.fxgui.util;

import cz.sam.novaengine.fxgui.effect.Effect;
import cz.sam.novaengine.fxgui.prop.FloatProperty;
import cz.sam.novaengine.fxgui.prop.IntProperty;

public class PropertyParser {
	
	private Object[] objects;
	
	public PropertyParser(Object[] objects) {
		this.objects = objects;
		if(!this.isValidated()) {
			try {
				throw new Exception("Property parser have some null valuees !");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(int i) {
		if(i > this.objects.length - 1)
			return null;
		return (T)this.objects[i];
	}
	
	public int size() {
		return this.objects.length;
	}
	
	public boolean isValidated() {
		for(Object obj : this.objects) {
			if(obj == null) return false;
		}
		return true;
	}
	
	@SafeVarargs
	public static <T> PropertyParser parse(String str, T... types) {
		String[] groups = FXUtil.getBetweenAll(str, "(", ")");
		for(String group : groups) {
			if(group == null) continue;
			String escaped = group.replaceAll(" ", "");
			str = str.replace(group, escaped);
		}
		String[] exp = str.split(" ");
		Object[] objects = new Object[types.length];
		for(int i = 0; i < types.length; i++) {
			if(i > exp.length - 1) break;
			String part = exp[i];
			T type = types[i];
			if(type == IntProperty.class) {
				if(part.contains(".")) {
					part = part.substring(0, part.indexOf('.'));
				}
				objects[i] = IntProperty.parse(part);
			} else if(type == FloatProperty.class) {
				objects[i] = FloatProperty.parse(part);
			} else if(type == UIColor.class) {
				objects[i] = Effect.parseColor(part);
			}
		}
		return new PropertyParser(objects);
	}
	
}
