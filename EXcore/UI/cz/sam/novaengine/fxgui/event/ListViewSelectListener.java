package cz.sam.novaengine.fxgui.event;

import cz.sam.novaengine.fxgui.obj.ListView;

public interface ListViewSelectListener<T> {
	
	void onSelect(ListView<T> view, T obj, boolean removed);
	
}
