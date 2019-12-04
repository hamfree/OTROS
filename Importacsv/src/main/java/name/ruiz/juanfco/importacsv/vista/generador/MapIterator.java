package name.ruiz.juanfco.importacsv.vista.generador;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MapIterator implements IObjectIterator {

    private final String name;
    private Iterator<?> entryIterator;
    private Map.Entry<?, ?> currentEntry;
    private int nextIndex = -1;

    public MapIterator(String name, Map<?, ?> map) {
        this.name = name;
        this.entryIterator = map.entrySet().iterator();
    }

    @Override
    public boolean next() {
        if (entryIterator.hasNext()) {
            nextIndex++;
            currentEntry = (Entry<?, ?>) entryIterator.next();
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return name + "[" + nextIndex + "]";
        // return "entry";
    }

    @Override
    public Object getValue() {
        return currentEntry;
    }

}
