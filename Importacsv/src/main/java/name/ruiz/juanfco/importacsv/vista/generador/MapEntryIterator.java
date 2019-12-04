package name.ruiz.juanfco.importacsv.vista.generador;

import java.util.Map;


public class MapEntryIterator implements IObjectIterator {
	
	public enum Status {

		NEW, KEY, VALUE, CLOSED;
		
		public String getName() {
			return name().toLowerCase();
		}

		public Status next() {
			if (this.ordinal() + 1 < Status.values().length) {
				return Status.values()[this.ordinal() + 1];
			} else {
				return CLOSED;
			}
		}
	}

    private final String name;
	private Map.Entry<?,?> entry;
	private Status status = Status.NEW;

	public MapEntryIterator(String name, Map.Entry<?,?> entry) {
		this.name = name;
        this.entry = entry;
	}
	
	@Override
	public boolean next() {
		if (status.ordinal() < Status.VALUE.ordinal()) {
			status = status.next();
			return true;
		}

		return false;
	}

	@Override
	public String getName() {
		return status.getName();
	}
	
	@Override
	public Object getValue() {
		if (status == Status.KEY) {
			return entry.getKey();
		} else if (status == Status.VALUE) {
			return entry.getValue();
		}
		return null;
	}
	
}
