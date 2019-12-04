package name.ruiz.juanfco.importacsv.vista.generador;

import java.util.Iterator;


public class CollectionIterator implements IObjectIterator {

    private final String name;
	private Iterator<?> iterator;
	private Object currentElement;
    private int nextIndex = -1;

	public CollectionIterator(String name, Iterable<?> collection) {
		this.name = name;
        this.iterator = collection.iterator();
	}

	@Override
	public boolean next() {
		if (iterator.hasNext()) {
		    nextIndex++;
			currentElement = iterator.next();
			return true;
		}
		return false;
	}
	
	@Override
	public String getName() {
        return name + "[" + nextIndex + "]";
	}
	
	@Override
	public Object getValue() {
		return currentElement;
	}
	
}
